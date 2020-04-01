package com.kishan.heady_test_app.ui.subcategory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.repository.CategoryListRepository;
import com.kishan.heady_test_app.repository.CategoryProductListService;
import com.kishan.heady_test_app.repository.LocalDataRepository;

import java.util.List;

public class SubCategoryViewModel extends AndroidViewModel {

    private MediatorLiveData<List<Category>> categories;
    private MutableLiveData<Integer> subCategoryCount;
    private static LocalDataRepository localDataRepository;

    public SubCategoryViewModel(@NonNull Application application) {
        super(application);
        localDataRepository = new LocalDataRepository(application.getApplicationContext());
        categories = new MediatorLiveData<>();
        subCategoryCount = new MutableLiveData<>();
    }

    public LiveData<List<Category>> getSubCategries() {
//        return categoryListRepository.getCategories();
        return categories;
    }

    public LiveData<Integer> isSubCategoryPresent(int parentId) {
        return localDataRepository.getLocalCategoryRepository().getSubCategoryCount(parentId);
    }

    public void loadSubCategories(int parentId) {
        LiveData<List<Category>> source = localDataRepository.getLocalCategoryRepository().getSubCategories(parentId);
        categories.addSource(source, categories1 -> {
            categories.setValue(categories1);
            categories.removeSource(source);
        });
    }
}
