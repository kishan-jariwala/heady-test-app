package com.kishan.heady_test_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kishan.heady_test_app.R;
import com.kishan.heady_test_app.callback.ProductClickCallback;
import com.kishan.heady_test_app.db.entity.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CategoryViewHolder> {

    List<Product> productList;
    ProductClickCallback productClickCallback;

    public ProductAdapter(ProductClickCallback callback) {
        productClickCallback = callback;
    }

    public void setProductList(final List<Product> productList) {
        if (this.productList == null) {
            this.productList = productList;
            notifyItemRangeInserted(0, productList.size());
        } else {
            this.productList = productList;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.textName.setText(productList.get(position).getProductName());

        holder.itemView.setOnClickListener(view -> {
            productClickCallback.onClick(productList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView textName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.name);
        }
    }
}
