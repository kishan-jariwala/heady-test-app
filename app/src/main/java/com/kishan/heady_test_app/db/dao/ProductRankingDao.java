package com.kishan.heady_test_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kishan.heady_test_app.db.entity.ProductRanking;
import com.kishan.heady_test_app.db.entity.Ranking;

import java.util.List;

@Dao
public interface ProductRankingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductRanking productRanking);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductRanking> productRankingList);

    @Query("SELECT * FROM ProductRanking")
    LiveData<List<ProductRanking>> getProductRankingList();
}
