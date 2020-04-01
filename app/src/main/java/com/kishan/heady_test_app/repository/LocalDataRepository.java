package com.kishan.heady_test_app.repository;

import android.content.Context;

import com.kishan.heady_test_app.db.AppDatabase;
import com.kishan.heady_test_app.db.entity.Category;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class LocalDataRepository {

    private AppDatabase db;
    private LocalCategoryRepository localCategoryRepository;
    private LocalCategorySubCategoryRepository localCategorySubCategoryRepository;
    private LocalProductRepository localProductRepository;

    public LocalDataRepository(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "heady-test-app-db").build();
    }

    public LocalCategoryRepository getLocalCategoryRepository() {
        if(localCategoryRepository == null) {
            localCategoryRepository = new LocalCategoryRepository(db);
        }
        return localCategoryRepository;
    }

    public LocalCategorySubCategoryRepository getLocalCategorySubCategoryRepository() {
        if(localCategorySubCategoryRepository == null) {
            localCategorySubCategoryRepository = new LocalCategorySubCategoryRepository(db);
        }
        return localCategorySubCategoryRepository;
    }

    public LocalProductRepository getLocalProductRepository() {
        if(localProductRepository == null) {
            localProductRepository = new LocalProductRepository(db);
        }
        return localProductRepository;
    }
}
