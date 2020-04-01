package com.kishan.heady_test_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id")
@Entity(tableName = "ProductTax")
public class ProductTax {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "product_id")
    public int productId;
    @ColumnInfo(name = "tax_name")
    private String taxName;

    public ProductTax(int productId, String taxName) {
        this.productId = productId;
        this.taxName = taxName;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getTaxName() {
        return taxName;
    }
}
