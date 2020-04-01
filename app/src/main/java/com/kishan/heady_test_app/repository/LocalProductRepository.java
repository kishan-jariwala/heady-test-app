package com.kishan.heady_test_app.repository;

import androidx.lifecycle.LiveData;

import com.kishan.heady_test_app.db.AppDatabase;
import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.ProductRanking;
import com.kishan.heady_test_app.db.entity.ProductTax;
import com.kishan.heady_test_app.db.entity.Ranking;
import com.kishan.heady_test_app.db.entity.Tax;
import com.kishan.heady_test_app.db.entity.Variant;

import java.util.List;

public class LocalProductRepository {

    private AppDatabase db;

    public LocalProductRepository(AppDatabase db) {
        this.db = db;
    }

    public void addItem(Product product) {
        db.productDao().insert(product);
    }

    public void addItems(List<Product> items) {
        db.productDao().insertAll(items);
    }

    public LiveData<List<Product>> getProducts(int categoryId) {
        return db.productDao().getProductList(categoryId);
    }

    public LiveData<List<Product>> getProducts(int categoryId, int rankingId) {
        return db.productDao().getProductList(categoryId, rankingId);
    }

    public void addItem(Variant variant) {
        db.variantDao().insert(variant);
    }

    public void addVariants(List<Variant> items) {
        db.variantDao().insertAll(items);
    }

    public LiveData<List<Variant>> getProductVariants(int productId) {
        return db.variantDao().getVariantList(productId);
    }

    public void addItem(Tax tax) {
        db.taxDao().insert(tax);
    }

    public void addItem(ProductTax productTax) {
        db.productTaxDao().insert(productTax);
    }

    public LiveData<Tax> getProductTax(int productId) {
        return db.taxDao().getProductTax(productId);
    }

    public void addItem(Ranking ranking) {
        db.rankingDao().insert(ranking);
    }

    public void addItem(ProductRanking productRanking) {
        db.productRankingDao().insert(productRanking);
    }

    public LiveData<List<Ranking>> getRankings() {
        return db.rankingDao().getRankingList();
    }

    public LiveData<List<ProductRanking>> getProductRankingList() {
        return db.productRankingDao().getProductRankingList();
    }
}
