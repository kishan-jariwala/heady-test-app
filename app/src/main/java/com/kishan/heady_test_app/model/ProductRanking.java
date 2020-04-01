package com.kishan.heady_test_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRanking {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("view_count")
    @Expose
    private int viewCount;
    @SerializedName("order_count")
    @Expose
    private int orderCount;
    @SerializedName("shares")
    @Expose
    private int shares;

    public int getId() {
        return id;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public int getShares() {
        return shares;
    }
}
