package com.kishan.heady_test_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    @SerializedName("child_categories")
    @Expose
    private List<Integer> childCategories = null;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Integer> getChildCategories() {
        return childCategories;
    }
}
