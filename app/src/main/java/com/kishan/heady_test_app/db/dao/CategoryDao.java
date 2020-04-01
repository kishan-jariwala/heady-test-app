package com.kishan.heady_test_app.db.dao;

import com.kishan.heady_test_app.db.entity.Category;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Category> categories);

    @Query("SELECT DISTINCT Category.* FROM Category INNER JOIN CategorySubCategory ON" +
            " Category.category_id = CategorySubCategory.category_id " +
            "WHERE Category.category_id NOT IN (SELECT DISTINCT CategorySubCategory.sub_category_id FROM CategorySubCategory)")
    LiveData<List<Category>> getParentCategories();

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getCategories();

    @Query("SELECT Category.* FROM Category INNER JOIN CategorySubCategory ON" +
            " Category.category_id = CategorySubCategory.sub_category_id WHERE " +
            "CategorySubCategory.category_id=:parentCategoryId")
    LiveData<List<Category>> getParentSubCategoriesByParentCategoryId(int parentCategoryId);

    @Query("SELECT COUNT(sub_category_id) FROM CategorySubCategory WHERE CategorySubCategory.category_id=:parentCategoryId")
    LiveData<Integer> getSubCategoryCount(int parentCategoryId);
}
