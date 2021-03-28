package com.fisha.roomcodelabs.roomdatabase;

import com.fisha.roomcodelabs.model.IGame;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_table")
public class Game implements IGame
{
    @PrimaryKey
    @NonNull
    private String title;
    private String coverUrl;

    public Game(@NonNull String title, String coverUrl) {
        this.title = title;
        this.coverUrl = coverUrl;
    }

    @Override
    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Override
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
