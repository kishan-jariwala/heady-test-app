package com.kishan.heady_test_app.db;

import com.kishan.heady_test_app.db.dao.CategoryDao;
import com.kishan.heady_test_app.db.dao.CategorySubCategoryDao;
import com.kishan.heady_test_app.db.dao.ProductDao;
import com.kishan.heady_test_app.db.dao.ProductRankingDao;
import com.kishan.heady_test_app.db.dao.ProductTaxDao;
import com.kishan.heady_test_app.db.dao.RankingDao;
import com.kishan.heady_test_app.db.dao.TaxDao;
import com.kishan.heady_test_app.db.dao.VariantDao;
import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.CategorySubCategory;
import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.ProductRanking;
import com.kishan.heady_test_app.db.entity.ProductTax;
import com.kishan.heady_test_app.db.entity.Ranking;
import com.kishan.heady_test_app.db.entity.Tax;
import com.kishan.heady_test_app.db.entity.Variant;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, CategorySubCategory.class, Product.class, Variant.class, Tax.class,
        ProductTax.class, Ranking.class, ProductRanking.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract CategorySubCategoryDao categorySubCategoryDao();

    public abstract ProductDao productDao();

    public abstract VariantDao variantDao();

    public abstract TaxDao taxDao();

    public abstract ProductTaxDao productTaxDao();

    public abstract RankingDao rankingDao();

    public abstract ProductRankingDao productRankingDao();
}
