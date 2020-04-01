package com.kishan.heady_test_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id")
@Entity(tableName = "ProductRanking")
public class ProductRanking {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "count")
    private int count;
    @ColumnInfo(name = "ranking_id")
    private int type;

    public ProductRanking(int productId, int count, int type) {
        this.productId = productId;
        this.count = count;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public int getType() {
        return type;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(int type) {
        this.type = type;
    }
}
