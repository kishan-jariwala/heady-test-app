package com.kishan.heady_test_app.db.entity;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tax")
public class Tax {

    @NotNull
    @PrimaryKey
    private String name;
    @ColumnInfo(name = "value")
    private float  value;

    public Tax(@NotNull String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }
}
