package com.kishan.heady_test_app.ui.main;

import android.app.Application;

import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.model.ApiResponse;
import com.kishan.heady_test_app.repository.CategoryListRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class MainViewModel extends AndroidViewModel {

//    private MediatorLiveData<List<Category>> categories = new MediatorLiveData<>();
    private final CategoryListRepository categoryListRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        categoryListRepository = CategoryListRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Category>> getCategries() {
        return categoryListRepository.getCategories();
//        return categories;
    }
}
