package com.kishan.heady_test_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.Variant;

import java.util.List;

@Dao
public interface VariantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Variant product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Variant> productList);

    @Query("SELECT Variant.* FROM Variant WHERE Variant.product_id = :productId")
    LiveData<List<Variant>> getVariantList(int productId);

//    @Query("SELECT * FROM PRODUCT WHERE Product.category_id=:categoryId")
//    Single<List<ProductAndVariant>> getProductList(int categoryId);
//
//    @Query("SELECT Product.*,Variant.*,ProductRanking.* FROM Product " +
//            "INNER JOIN Variant ON Product.id = Variant.product_id " +
//            "INNER JOIN ProductRanking ON ProductRanking.p_id = Product.id " +
//            "WHERE Product.category_id=:categoryId")
//    Single<List<ProductDetails>> getProductDetails(int categoryId);
}
