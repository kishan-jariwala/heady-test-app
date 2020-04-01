package com.kishan.heady_test_app.db.entity;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id")
@Entity(tableName = "Product")
public class Product implements Serializable {

    @NotNull
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "date_added")
    private String dateAdded;
    @ColumnInfo(name = "category_id")
    private int categoryId;

    public Product(int id, String productName, String dateAdded, int categoryId) {
        this.id = id;
        this.productName = productName;
        this.dateAdded = dateAdded;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
