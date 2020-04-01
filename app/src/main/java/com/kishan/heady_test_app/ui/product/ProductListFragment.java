package com.kishan.heady_test_app.ui.product;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.kishan.heady_test_app.BaseFragment;
import com.kishan.heady_test_app.R;
import com.kishan.heady_test_app.adapter.ProductAdapter;
import com.kishan.heady_test_app.callback.ProductClickCallback;
import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.ProductRanking;
import com.kishan.heady_test_app.db.entity.Ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductListFragment extends BaseFragment {

    private Category category;
    private ProductAdapter productAdapter;
    private ProductListViewModel mViewModel;
    private TextView textMessage;
    private int filterOption = 0;
    private List<Ranking> rankingList;
    private List<Product> fullProductList;

    private static final String ARG_CATEGORY = "arg_category";

    public static ProductListFragment newInstance(Category category) {
        ProductListFragment subCategoryFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CATEGORY, category);
        subCategoryFragment.setArguments(bundle);
        return subCategoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_list_fragment, container, false);

        category = (Category) getArguments().getSerializable(ARG_CATEGORY);
        setTitle(category.getCategoryName());
        mViewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);

        textMessage = rootView.findViewById(R.id.message);
        textMessage.setText("Loading...");

        rootView.findViewById(R.id.sort).setOnClickListener(view -> {
            showFilterOptions();
        });

        rankingList = new ArrayList<>();

        RecyclerView recyclerView = rootView.findViewById(R.id.category_list);
        productAdapter = new ProductAdapter(productClickCallback);
        recyclerView.setAdapter(productAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeViewModel(mViewModel);
        mViewModel.loadProducts(category.getCategoryId());
        mViewModel.loadRankings();
        mViewModel.loadProductRankings();
    }

    private void observeViewModel(ProductListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProductList().observe(this, productList -> {
            if (productList != null && !productList.isEmpty()) {
                ArrayList<Product> tempList = new ArrayList<>();
                tempList.addAll(productList);
                if(fullProductList == null) {
                    fullProductList = new ArrayList<>();
                    fullProductList.addAll(productList);
                } else if(fullProductList.size() != tempList.size()){
                    for(Product fullProduct : fullProductList) {
                        boolean isPresent = false;
                        for(Product filteredProduct : productList) {
                            if(filteredProduct.getId() == fullProduct.getId()) {
                                isPresent = true;
                                break;
                            }
                        }
                        if(!isPresent) {
                            tempList.add(fullProduct);
                        }
                    }
                }
                productAdapter.setProductList(tempList);
                if(textMessage != null) {
                    textMessage.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getRankings().observe(this, rankings -> {
            if (rankings != null && !rankings.isEmpty()) {
//                for (Ranking ranking : rankings) {
//                    System.out.println("Ranking ::: " + ranking.getRanking());
//                }
                rankingList.addAll(rankings);
            }
        });

//        viewModel.getProductRankings().observe(this, rankings -> {
//            if (rankings != null && !rankings.isEmpty()) {
//                for (ProductRanking ranking : rankings) {
//                    System.out.println("ProductRankings ::: " + ranking.getId() + " :: " + ranking.getProductId()
//                            + " :: " + ranking.getCount() + " :: " + ranking.getType());
//                }
//            }
//        });
    }

    private final ProductClickCallback productClickCallback = product -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
//            mViewModel.isSubCategoryPresent(category.getCategoryId()).observe(this, count -> {
//                if(count > 0) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container, ProductDetailFragment.newInstance(product), "ProductDetailFragment");
                    fragmentTransaction.addToBackStack("ProductDetailFragment");
                    fragmentTransaction.commitAllowingStateLoss();
//                }
//            });
        }
    };

    private void showFilterOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Sort Option");
        String[] options = new String[rankingList.size() + 1];
        int count = 0;
        options[count++] = "None";
        for(Ranking ranking : rankingList) {
            options[count++] = ranking.getRanking();
        }
        builder.setSingleChoiceItems(options, filterOption, (dialog, which) -> {
            if(filterOption != which) {
                filterOption = which;
                if (filterOption == 0) {
                    mViewModel.loadProducts(category.getCategoryId());
                } else {
                    mViewModel.loadProducts(category.getCategoryId(), filterOption);
                }
            }
            dialog.dismiss();
        });
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // user clicked OK
//            }
//        });
//        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
