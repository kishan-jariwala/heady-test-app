package com.kishan.heady_test_app.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;

@Entity(tableName = "Category")
public class Category implements Serializable/*Parcelable*/ {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "category_name")
    private String categoryName;

//    public Category() {
//    }
//
//    protected Category(Parcel in) {
//        categoryId = in.readInt();
//        categoryName = in.readString();
//    }

    public Category(@NotNull int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(categoryId);
//        parcel.writeString(categoryName);
//    }
//
//    public static final Creator<Category> CREATOR = new Creator<Category>() {
//        @Override
//        public Category createFromParcel(Parcel in) {
//            return new Category(in);
//        }
//
//        @Override
//        public Category[] newArray(int size) {
//            return new Category[size];
//        }
//    };

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
