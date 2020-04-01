package com.kishan.heady_test_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private int    id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("variants")
    @Expose
    private List<Variant> variants = null;
    @SerializedName("tax")
    @Expose
    private Tax tax;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public Tax getTax() {
        return tax;
    }
}
