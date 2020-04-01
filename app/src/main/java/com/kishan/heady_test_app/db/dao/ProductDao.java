package com.kishan.heady_test_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kishan.heady_test_app.db.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Product> productList);

    @Query("SELECT Product.* FROM Product WHERE Product.category_id=:categoryId")
    LiveData<List<Product>> getProductList(int categoryId);

    @Query("SELECT Product.* FROM Product LEFT OUTER JOIN ProductRanking ON Product.id = ProductRanking.product_id " +
            "WHERE (ProductRanking.ranking_id = :rankingId OR ProductRanking.ranking_id IS NULL) AND " +
            "Product.category_id = :categoryId ORDER BY ProductRanking.count DESC")
    LiveData<List<Product>> getProductList(int categoryId, int rankingId);

//    @Query("SELECT * FROM (SELECT * FROM Product LEFT JOIN ProductRanking ON Product.id = ProductRanking.product_id" +
//            " ORDER BY ProductRanking.count DESC) as A WHERE (a.ranking_id = :rankingId OR a.ranking_id IS NULL)" +
//            " AND a.category_id = :categoryId")
//    LiveData<List<Product>> getProductList(int categoryId, int rankingId);

//    @Query("SELECT * FROM PRODUCT WHERE Product.category_id=:categoryId")
//    Single<List<ProductAndVariant>> getProductList(int categoryId);
//
//    @Query("SELECT Product.*,Variant.*,ProductRanking.* FROM Product " +
//            "INNER JOIN Variant ON Product.id = Variant.product_id " +
//            "INNER JOIN ProductRanking ON ProductRanking.p_id = Product.id " +
//            "WHERE Product.category_id=:categoryId")
//    Single<List<ProductDetails>> getProductDetails(int categoryId);
}
