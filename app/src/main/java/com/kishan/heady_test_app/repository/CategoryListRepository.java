package com.kishan.heady_test_app.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.CategorySubCategory;
import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.ProductRanking;
import com.kishan.heady_test_app.db.entity.ProductTax;
import com.kishan.heady_test_app.db.entity.Tax;
import com.kishan.heady_test_app.db.entity.Variant;
import com.kishan.heady_test_app.model.ApiResponse;
import com.kishan.heady_test_app.model.Ranking;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryListRepository {

    private MediatorLiveData<List<Category>> categories = new MediatorLiveData<>();
    private CategoryProductListService categoryProductListService;
    private static CategoryListRepository categoryListRepository;
    private static LocalDataRepository localDataRepository;

    private CategoryListRepository(Context context) {
        localDataRepository = new LocalDataRepository(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CategoryProductListService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        categoryProductListService = retrofit.create(CategoryProductListService.class);
        refreshCategories(true);
//        if(categories.getValue() == null || categories.getValue().isEmpty()) {
//            getCategoryProductList();
//        }
    }

    private void refreshCategories(boolean isLoadFromServer) {
        LiveData<List<Category>> source = localDataRepository.getLocalCategoryRepository().getParentCategories();
        categories.addSource(source, categories -> {
            CategoryListRepository.this.categories.setValue(categories);
            CategoryListRepository.this.categories.removeSource(source);
            if(isLoadFromServer && (categories == null || categories.isEmpty())) {
                getCategoryProductList();
            }
        });
    }

    public synchronized static CategoryListRepository getInstance(Context context) {
        //TODO No need to implement this singleton in Part #2 since Dagger will handle it ...
        if (categoryListRepository == null) {
            if (categoryListRepository == null) {
                categoryListRepository = new CategoryListRepository(context);
            }
        }
        return categoryListRepository;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
//        LiveData<List<Category>> data = localDataRepository.getLocalCategoryRepository().getParentCategories();
//        if(data.getValue()!= null && !data.getValue().isEmpty()) {
//            return data;
//        } else {
//            return getCategoryProductList();
//        }
    }

    public LiveData<List<Category>> getCategoryProductList() {
        final MutableLiveData<List<Category>> data = new MutableLiveData<>();

        categoryProductListService.getCategoryProductList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                new StoreDataAsync(response.body(), data).execute();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    private class StoreDataAsync extends AsyncTask<Void, Void, Boolean> {
        ApiResponse apiResponse;
        MutableLiveData<List<Category>> data;

        StoreDataAsync(ApiResponse apiResponse, MutableLiveData<List<Category>> data) {
            this.apiResponse = apiResponse;
            this.data = data;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            storeData(apiResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            refreshCategories(false);
        }
    }

    private void storeData(ApiResponse apiResponse)
    {
        Log.d("CategoryListRepository", "storeData: ");

        try
        {
            for (com.kishan.heady_test_app.model.Category categoryResponse : apiResponse.getCategories())
            {
                Category category = new Category(categoryResponse.getId(), categoryResponse.getName());
                localDataRepository.getLocalCategoryRepository().addItem(category);

                if (!categoryResponse.getProducts().isEmpty())
                {

                    for (com.kishan.heady_test_app.model.Product productResponse : categoryResponse.getProducts())
                    {
                        Product product = new Product(productResponse.getId(), productResponse.getName(),
                                productResponse.getDateAdded(), categoryResponse.getId());
                        localDataRepository.getLocalProductRepository().addItem(product);

                        com.kishan.heady_test_app.model.Tax taxResponse = productResponse.getTax();

                        Tax tax = new Tax(taxResponse.getName(), taxResponse.getValue());

                        localDataRepository.getLocalProductRepository().addItem(tax);

                        ProductTax productTax = new ProductTax(productResponse.getId(), taxResponse.getName());
                        localDataRepository.getLocalProductRepository().addItem(productTax);

                        for (com.kishan.heady_test_app.model.Variant variantResponse : productResponse.getVariants())
                        {
                            Variant variant = new Variant(variantResponse.getId(), variantResponse.getColor(), variantResponse.getSize(),
                                    variantResponse.getPrice(), productResponse.getId());
                            localDataRepository.getLocalProductRepository().addItem(variant);
                        }

                    }

                } else if (!categoryResponse.getChildCategories().isEmpty())
                {
                    for (Integer childCategoryId : categoryResponse.getChildCategories())
                    {
                        CategorySubCategory categorySubCategory = new CategorySubCategory(childCategoryId, categoryResponse.getId());
                        localDataRepository.getLocalCategorySubCategoryRepository().addItem(categorySubCategory);
                    }

                }
            }

            for (int i=0;i<apiResponse.getRankings().size();i++)
            {
                Ranking rankingResponse = apiResponse.getRankings().get(i);
                int id = (i+1);
                localDataRepository.getLocalProductRepository().addItem(
                        new com.kishan.heady_test_app.db.entity.Ranking(id, rankingResponse.getRanking()));
                for (com.kishan.heady_test_app.model.ProductRanking productRankingResponse : rankingResponse.getProductRankingList())
                {
                    int count = 0;
                    if(productRankingResponse.getViewCount() > 0) {
                        count = productRankingResponse.getViewCount();
                    } else if(productRankingResponse.getOrderCount() > 0) {
                        count = productRankingResponse.getOrderCount();
                    } else if(productRankingResponse.getShares() > 0) {
                        count = productRankingResponse.getShares();
                    }
                    ProductRanking productRanking = new ProductRanking(productRankingResponse.getId(), count, id);
                    localDataRepository.getLocalProductRepository().addItem(productRanking);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
