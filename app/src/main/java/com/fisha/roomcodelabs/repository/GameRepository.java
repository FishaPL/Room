package com.fisha.roomcodelabs.repository;

import android.app.Application;

import com.fisha.roomcodelabs.roomdatabase.Game;
import com.fisha.roomcodelabs.roomdatabase.GameDao;
import com.fisha.roomcodelabs.roomdatabase.GameRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GameRepository
{
    private GameDao gameDao;
    private LiveData<List<Game>> gameListLD;

    public GameRepository(Application application) {
        GameRoomDatabase db = GameRoomDatabase.getDatabase(application);
        this.gameDao = db.gameDao();
        this.gameListLD = gameDao.getAllGames();
    }

    public LiveData<List<Game>> getGameListLD() {
        return gameListLD;
    }

    public LiveData<List<Game>> searchGames(String query) {
        return gameDao.searchAllGames(query);
    }

    public void insert(Game game) {
        GameRoomDatabase.databaseWriteExecutor.execute(() -> gameDao.insert(game));
    }

    public void delete(Game game) {
        GameRoomDatabase.databaseWriteExecutor.execute(() -> gameDao.delete(game));
    }
}
