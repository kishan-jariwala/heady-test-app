package com.kishan.heady_test_app.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.kishan.heady_test_app.db.entity.ProductTax;

import java.util.List;

@Dao
public interface ProductTaxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductTax productTax);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductTax> productTaxes);
}
