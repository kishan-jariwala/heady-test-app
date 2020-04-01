package com.kishan.heady_test_app.ui.subcategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.kishan.heady_test_app.BaseFragment;
import com.kishan.heady_test_app.R;
import com.kishan.heady_test_app.adapter.CategoryAdapter;
import com.kishan.heady_test_app.callback.CategoryClickCallback;
import com.kishan.heady_test_app.db.entity.Category;
import com.kishan.heady_test_app.ui.main.MainViewModel;
import com.kishan.heady_test_app.ui.product.ProductListFragment;

public class SubCategoryFragment extends BaseFragment {

    private Category category;
    private CategoryAdapter categoryAdapter;
    private SubCategoryViewModel mViewModel;
    private TextView textMessage;

    private static final String ARG_CATEGORY = "arg_category";

    public static SubCategoryFragment newInstance(Category category) {
        SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CATEGORY, category);
        subCategoryFragment.setArguments(bundle);
        return subCategoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        category = (Category) getArguments().getSerializable(ARG_CATEGORY);
        setTitle(category.getCategoryName());
        mViewModel = ViewModelProviders.of(this).get(SubCategoryViewModel.class);

        textMessage = rootView.findViewById(R.id.message);
        textMessage.setText("Loading...");

        RecyclerView recyclerView = rootView.findViewById(R.id.category_list);
        categoryAdapter = new CategoryAdapter(categoryClickCallback);
        recyclerView.setAdapter(categoryAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeViewModel(mViewModel);
        mViewModel.loadSubCategories(category.getCategoryId());
    }

    private void observeViewModel(SubCategoryViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getSubCategries().observe(this, categoryList -> {
            if (categoryList != null && !categoryList.isEmpty()) {
                categoryAdapter.setCategoryList(categoryList);
                if(textMessage != null) {
                    textMessage.setVisibility(View.GONE);
                }
            }
        });
    }

    private final CategoryClickCallback categoryClickCallback = category -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            mViewModel.isSubCategoryPresent(category.getCategoryId()).observe(this, count -> {
                if(count > 0) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container, SubCategoryFragment.newInstance(category), "subCategoryFragment");
                    fragmentTransaction.addToBackStack("subCategoryFragment");
                    fragmentTransaction.commitAllowingStateLoss();
                } else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container, ProductListFragment.newInstance(category), "ProductListFragment");
                    fragmentTransaction.addToBackStack("ProductListFragment");
                    fragmentTransaction.commitAllowingStateLoss();
                }
            });
        }
    };
}
