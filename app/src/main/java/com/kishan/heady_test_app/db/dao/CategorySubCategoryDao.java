package com.kishan.heady_test_app.db.dao;

import com.kishan.heady_test_app.db.entity.CategorySubCategory;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CategorySubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategorySubCategory categorySubCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategorySubCategory> categorySubCategoryList);
}
