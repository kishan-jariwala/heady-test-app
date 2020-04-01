package com.kishan.heady_test_app.repository;

import com.kishan.heady_test_app.db.AppDatabase;
import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.CategorySubCategory;

public class LocalCategorySubCategoryRepository {

    private AppDatabase db;

    public LocalCategorySubCategoryRepository(AppDatabase db) {
        this.db = db;
    }

    public void addItem(CategorySubCategory categorySubCategory) {
        db.categorySubCategoryDao().insert(categorySubCategory);
    }
}
