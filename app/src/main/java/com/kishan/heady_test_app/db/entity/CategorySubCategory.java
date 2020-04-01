package com.kishan.heady_test_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CategorySubCategory")
public class CategorySubCategory {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "sub_category_id")
    private int subCategoryId;
    @ColumnInfo(name = "category_id")
    private int categoryId;

    public CategorySubCategory(int subCategoryId, int categoryId) {
        this.subCategoryId = subCategoryId;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
