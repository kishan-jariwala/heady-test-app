package com.kishan.heady_test_app.callback;


import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.Product;

public interface ProductClickCallback {

    void onClick(Product product);
}
