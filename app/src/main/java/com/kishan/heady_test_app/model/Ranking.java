package com.kishan.heady_test_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ranking {


    @SerializedName("ranking")
    @Expose
    private String ranking;
    @SerializedName("products")
    @Expose
    private List<ProductRanking> productRankingList = null;

    public String getRanking() {
        return ranking;
    }

    public List<ProductRanking> getProductRankingList() {
        return productRankingList;
    }

}
