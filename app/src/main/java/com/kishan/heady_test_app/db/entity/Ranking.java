package com.kishan.heady_test_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Ranking")
public class Ranking {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "ranking_id")
    private int rankingId;
    @ColumnInfo(name = "ranking")
    private String ranking;

    public Ranking(int rankingId, String ranking) {
        this.rankingId = rankingId;
        this.ranking = ranking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRankingId() {
        return rankingId;
    }

    public String getRanking() {
        return ranking;
    }
}
