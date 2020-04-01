package com.kishan.heady_test_app.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.Tax;
import com.kishan.heady_test_app.db.entity.Variant;
import com.kishan.heady_test_app.repository.LocalDataRepository;

import java.util.List;

public class ProductDetailViewModel extends AndroidViewModel {

    private MediatorLiveData<List<Variant>> productVariants;
    private MutableLiveData<Tax> productTax;
    private static LocalDataRepository localDataRepository;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        localDataRepository = new LocalDataRepository(application.getApplicationContext());
        productVariants = new MediatorLiveData<>();
        productTax = new MutableLiveData<>();
    }

    public LiveData<List<Variant>> getProductVariants() {
        return productVariants;
    }

    public LiveData<Tax> getProductTax(int productId) {
        return localDataRepository.getLocalProductRepository().getProductTax(productId);
    }

    public void loadProductVariants(int productId) {
        LiveData<List<Variant>> source = localDataRepository.getLocalProductRepository().getProductVariants(productId);
        productVariants.addSource(source, variant -> {
            productVariants.setValue(variant);
            productVariants.removeSource(source);
        });
    }
}
