package com.kishan.heady_test_app.db.entity;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id")
@Entity(tableName = "Variant")
public class Variant {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "variant_id")
    private int variantId;
    @ColumnInfo(name = "color")
    private String color;
    @ColumnInfo(name = "size")
    private int size;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "product_id")
    private int productId;

    public Variant(@NotNull int variantId, String color, int size, int price, int productId) {
        this.variantId = variantId;
        this.color = color;
        this.size = size;
        this.price = price;
        this.productId = productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }
}
