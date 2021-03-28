package com.fisha.roomcodelabs.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import com.fisha.roomcodelabs.repository.GameRepository;
import com.fisha.roomcodelabs.roomdatabase.Game;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

public class GameViewModel extends AndroidViewModel
{
    private static final String QUERY_KEY = "QUERY";
    private final GameRepository gameRepository;
    private final LiveData<List<Game>> gameListLD;
    private final SavedStateHandle mSavedStateHandler;

    public GameViewModel(@NonNull Application application, @NonNull SavedStateHandle savedStateHandler) {
        super(application);
        this.gameRepository = new GameRepository(application);
        this.mSavedStateHandler = savedStateHandler;

        gameListLD = Transformations.switchMap(
                savedStateHandler.getLiveData(QUERY_KEY, null),
                (Function<CharSequence, LiveData<List<Game>>>) query -> {
                    if (TextUtils.isEmpty(query)) {
                        return gameRepository.getGameListLD();
                    }
                    return gameRepository.searchGames("%" + query + "%");
                });
    }

    public LiveData<List<Game>> getGameListLD() {
        return gameListLD;
    }

    public void insert(Game game) {
        gameRepository.insert(game);
    }

    public void setQuery(CharSequence query) {
        mSavedStateHandler.set(QUERY_KEY, query);
    }

    public void delete(Game game) {
        gameRepository.delete(game);
    }
}
