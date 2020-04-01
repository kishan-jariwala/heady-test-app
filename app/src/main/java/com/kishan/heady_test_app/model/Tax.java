package com.kishan.heady_test_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tax {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private float  value;

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }
}
