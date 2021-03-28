package com.fisha.roomcodelabs.roomdatabase;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface GameDao
{
    @Query("SELECT * FROM game_table ORDER BY title ASC")
    LiveData<List<Game>> getAllGames();

    @Query("SELECT * FROM game_table WHERE title LIKE :query")
    LiveData<List<Game>> searchAllGames(String query);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Game game);

    @Insert
    void insertAll(Collection<Game> games);

    @Query("DELETE FROM game_table")
    void deleteAll();

    //@Query("DELETE FROM game_table WHERE title = :title")
    //void delete(String title);

    @Delete
    void delete(Game game);
}
