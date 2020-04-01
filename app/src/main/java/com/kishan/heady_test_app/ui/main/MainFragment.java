package com.kishan.heady_test_app.ui.main;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kishan.heady_test_app.BaseFragment;
import com.kishan.heady_test_app.R;
import com.kishan.heady_test_app.adapter.CategoryAdapter;
import com.kishan.heady_test_app.callback.CategoryClickCallback;
import com.kishan.heady_test_app.ui.subcategory.SubCategoryFragment;

public class MainFragment extends BaseFragment {

    private CategoryAdapter categoryAdapter;
    private MainViewModel mViewModel;
    private TextView textMessage;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        setTitle(getString(R.string.app_name));
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

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
    }

    private void observeViewModel(MainViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getCategries().observe(this, categoryList -> {
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
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, SubCategoryFragment.newInstance(category), "subCategoryFragment");
            fragmentTransaction.addToBackStack("subCategoryFragment");
            fragmentTransaction.commitAllowingStateLoss();
        }
    };
}
