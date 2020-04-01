package com.kishan.heady_test_app.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.ProductRanking;
import com.kishan.heady_test_app.db.entity.Ranking;
import com.kishan.heady_test_app.repository.LocalDataRepository;

import java.util.List;

public class ProductListViewModel extends AndroidViewModel {

    private MediatorLiveData<List<Product>> products;
    private MediatorLiveData<List<Ranking>> rankings;
    private MediatorLiveData<List<ProductRanking>> productRankings;
    private static LocalDataRepository localDataRepository;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        localDataRepository = new LocalDataRepository(application.getApplicationContext());
        products = new MediatorLiveData<>();
        rankings = new MediatorLiveData<>();
        productRankings = new MediatorLiveData<>();
    }

    public LiveData<List<Product>> getProductList() {
        return products;
    }

    public LiveData<List<Ranking>> getRankings() {
        return rankings;
    }

    public LiveData<List<ProductRanking>> getProductRankings() {
        return productRankings;
    }

    public void loadProducts(int categoryId) {
        LiveData<List<Product>> source = localDataRepository.getLocalProductRepository().getProducts(categoryId);
        products.addSource(source, productList -> {
            products.setValue(productList);
            products.removeSource(source);
        });
    }

    public void loadRankings() {
        LiveData<List<Ranking>> source = localDataRepository.getLocalProductRepository().getRankings();
        rankings.addSource(source, rankingList -> {
            rankings.setValue(rankingList);
            rankings.removeSource(source);
        });
    }

    public void loadProductRankings() {
        LiveData<List<ProductRanking>> source = localDataRepository.getLocalProductRepository().getProductRankingList();
        productRankings.addSource(source, productRankings -> {
            this.productRankings.setValue(productRankings);
            this.productRankings.removeSource(source);
        });
    }

    public void loadProducts(int categoryId, int rankingId) {
        LiveData<List<Product>> source = localDataRepository.getLocalProductRepository().getProducts(categoryId, rankingId);
        products.addSource(source, productList -> {
            products.setValue(productList);
            products.removeSource(source);
        });
    }
}
