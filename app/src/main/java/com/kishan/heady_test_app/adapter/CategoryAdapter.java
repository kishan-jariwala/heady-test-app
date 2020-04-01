package com.kishan.heady_test_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kishan.heady_test_app.R;
import com.kishan.heady_test_app.callback.CategoryClickCallback;
import com.kishan.heady_test_app.db.entity.Category;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    List<Category> categoryList;
    CategoryClickCallback categoryClickCallback;

    public CategoryAdapter(CategoryClickCallback callback) {
        categoryClickCallback = callback;
    }

    public void setCategoryList(final List<Category> categoryList) {
        if (this.categoryList == null) {
            this.categoryList = categoryList;
            notifyItemRangeInserted(0, categoryList.size());
        } else {
            this.categoryList = categoryList;
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
        holder.textName.setText(categoryList.get(position).getCategoryName());

        holder.itemView.setOnClickListener(view -> {
            categoryClickCallback.onClick(categoryList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView textName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.name);
        }
    }
}
