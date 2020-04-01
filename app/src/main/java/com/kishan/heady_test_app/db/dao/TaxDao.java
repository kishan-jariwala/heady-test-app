package com.kishan.heady_test_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kishan.heady_test_app.db.entity.Tax;

import java.util.List;

@Dao
public interface TaxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tax tax);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Tax> taxList);

    @Query("SELECT Tax.* FROM Tax, ProductTax where " +
            " Tax.name = ProductTax.tax_name AND ProductTax.product_id = :productId ")
    LiveData<Tax> getProductTax(int productId);
}
