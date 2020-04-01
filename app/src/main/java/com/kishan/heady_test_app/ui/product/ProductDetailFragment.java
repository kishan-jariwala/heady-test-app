package com.kishan.heady_test_app.ui.product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import com.kishan.heady_test_app.BaseFragment;
import com.kishan.heady_test_app.R;
import com.kishan.heady_test_app.callback.ProductClickCallback;
import com.kishan.heady_test_app.db.entity.Product;
import com.kishan.heady_test_app.db.entity.Tax;
import com.kishan.heady_test_app.db.entity.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProductDetailFragment extends BaseFragment {

    private Product product;
    private Tax productTax;
    private LinearLayout llColors, llSizes;
//    private ProductAdapter productAdapter;
    private ProductDetailViewModel mViewModel;
    private TextView textPrice;
    private int idVariants[][];
    private HashMap<Integer, Variant> variants;
    private int selectedColorIndex, selectedSizeIndex;
    private static final String ARG_PRODUCT = "arg_product";

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment subCategoryFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PRODUCT, product);
        subCategoryFragment.setArguments(bundle);
        return subCategoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail_fragment, container, false);
        llColors = rootView.findViewById(R.id.ll_color);
        llSizes = rootView.findViewById(R.id.ll_size);
        textPrice = rootView.findViewById(R.id.text_price_value);

        product = (Product) getArguments().getSerializable(ARG_PRODUCT);
        setTitle(product.getProductName());
        variants = new HashMap<>();

        mViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);

//        TextView textMessage = rootView.findViewById(R.id.checkedTextView);
//        textMessage.setOnClickListener(view -> {
//            if(view.isSelected()) {
//                view.setSelected(false);
//                view.setEnabled(false);
//            } else if(view.isEnabled()) {
//                view.setSelected(true);
//            } else {
//                view.setEnabled(true);
//            }
//        });
//        textMessage.setText("Loading...");

//        RecyclerView recyclerView = rootView.findViewById(R.id.category_list);
//        productAdapter = new ProductAdapter(productClickCallback);
//        recyclerView.setAdapter(productAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeViewModel(mViewModel);
        mViewModel.getProductTax(product.getId()).observe(this, tax -> {
            productTax = tax;
            if(variants.size() > 0) {
                updateProductPrice();
            }
        });
        mViewModel.loadProductVariants(product.getId());
    }

    private void observeViewModel(ProductDetailViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProductVariants().observe(this, variantList -> {
            if (variantList != null && !variantList.isEmpty()) {
                ArrayList<String> colors = new ArrayList<>();
                ArrayList<Integer> sizes = new ArrayList<>();
                for(Variant variant : variantList) {
                    variants.put(variant.getVariantId(), variant);
                    if (!colors.contains(variant.getColor())) {
                        colors.add(variant.getColor());
                    }

                    if (!sizes.contains(variant.getSize())) {
                        sizes.add(variant.getSize());
                    }
                }

                idVariants = new int[colors.size()][sizes.size()];
                for(Variant variant : variantList) {
                    idVariants[colors.indexOf(variant.getColor())][sizes.indexOf(variant.getSize())] = variant.getVariantId();
                }
                Log.d("ProductDetailFragment", "idVariants[][] :: " + Arrays.deepToString(idVariants));

                for(int i=0;i<colors.size();i++) {
                    CustomStateTextView colorTextView = new CustomStateTextView(getContext());
                    colorTextView.setText(colors.get(i));
                    colorTextView.setPosition(i);
                    if(i==0) {
                        selectedColorIndex = 0;
                        colorTextView.setState(CustomStateTextView.State.SELECTED);
                    }
                    colorTextView.setOnStateChangedCallback(onColorStateChanged);
                    llColors.addView(colorTextView);
                }
                for(int i=0;i<sizes.size();i++) {
                    CustomStateTextView sizeTextView = new CustomStateTextView(getContext());
                    sizeTextView.setText(String.valueOf(sizes.get(i)));
                    sizeTextView.setPosition(i);
                    if(i==0) {
                        selectedSizeIndex = 0;
                        sizeTextView.setState(CustomStateTextView.State.SELECTED);
                    } else if(idVariants[0][i] > 0) {
                        sizeTextView.setState(CustomStateTextView.State.ENABLED);
                    } else {
                        sizeTextView.setState(CustomStateTextView.State.DISABLED);
                    }
                    sizeTextView.setOnStateChangedCallback(onSizeStateChanged);
                    llSizes.addView(sizeTextView);
                }
                updateProductPrice();
//                productAdapter.setProductList(categoryList);
//                if(textMessage != null) {
//                    textMessage.setVisibility(View.GONE);
//                }
            }
        });
    }

    private final CustomStateTextView.OnStateChangedCallback onColorStateChanged = (position, newState) -> {
        selectedColorIndex = position;
        selectedSizeIndex = -1;
        for(int i=0;i<llColors.getChildCount();i++) {
            if(i != position) {
                ((CustomStateTextView) llColors.getChildAt(i)).setState(CustomStateTextView.State.ENABLED);
            }
        }
        for(int i=0;i<idVariants[position].length;i++) {
            if(idVariants[position][i] > 0) {
                if(selectedSizeIndex == -1) {
                    selectedSizeIndex = i;
                    ((CustomStateTextView) llSizes.getChildAt(i)).setState(CustomStateTextView.State.SELECTED);
                } else {
                    ((CustomStateTextView) llSizes.getChildAt(i)).setState(CustomStateTextView.State.ENABLED);
                }
            } else {
                ((CustomStateTextView) llSizes.getChildAt(i)).setState(CustomStateTextView.State.DISABLED);
            }
        }
        updateProductPrice();
    };

    private final CustomStateTextView.OnStateChangedCallback onSizeStateChanged = (position, newState) -> {
        selectedSizeIndex = position;
        for(int i=0;i<idVariants[selectedColorIndex].length;i++) {
            if(i != position) {
                if (idVariants[selectedColorIndex][i] > 0) {
                    ((CustomStateTextView) llSizes.getChildAt(i)).setState(CustomStateTextView.State.ENABLED);
                } else {
                    ((CustomStateTextView) llSizes.getChildAt(i)).setState(CustomStateTextView.State.DISABLED);
                }
            }
        }
        updateProductPrice();
    };

    private void updateProductPrice() {
        if(variants != null) {
            Variant selectedVariant = variants.get(idVariants[selectedColorIndex][selectedSizeIndex]);
            int price = selectedVariant.getPrice();
            if(productTax == null) {
                textPrice.setText(price + " Rs");
            } else {
                float tax = (price * productTax.getValue()) / 100;
                textPrice.setText(price + " Rs(+" + tax + " Rs " + productTax.getName() + ")");
            }
        }
    }

    private final ProductClickCallback productClickCallback = product -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
//            mViewModel.isSubCategoryPresent(category.getCategoryId()).observe(this, count -> {
//                if(count > 0) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.add(R.id.container, ProductListFragment.newInstance(category), "subCategoryFragment");
//                    fragmentTransaction.addToBackStack("subCategoryFragment");
//                    fragmentTransaction.commitAllowingStateLoss();
//                }
//            });
        }
    };
}
