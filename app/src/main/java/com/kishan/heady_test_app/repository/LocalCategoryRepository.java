package com.kishan.heady_test_app.repository;

import com.kishan.heady_test_app.db.AppDatabase;
import com.kishan.heady_test_app.db.entity.Category;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LocalCategoryRepository {

    private AppDatabase db;

    public LocalCategoryRepository(AppDatabase db) {
        this.db = db;
    }

    public void addItem(Category category) {
        db.categoryDao().insert(category);
    }

    public void addItems(List<Category> items) {
        db.categoryDao().insertAll(items);
    }

    public LiveData<List<Category>> getCategories() {
        return db.categoryDao().getCategories();
    }

    public LiveData<List<Category>> getParentCategories() {
        return db.categoryDao().getParentCategories();
    }

    public LiveData<List<Category>> getSubCategories(int parentId) {
        return db.categoryDao().getParentSubCategoriesByParentCategoryId(parentId);
    }

    public LiveData<Integer> getSubCategoryCount(int parentId) {
        return db.categoryDao().getSubCategoryCount(parentId);
    }
}
