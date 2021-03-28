package com.fisha.roomcodelabs;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fisha.roomcodelabs.adapter.GameAdapterListener;
import com.fisha.roomcodelabs.adapter.GameListAdapter;
import com.fisha.roomcodelabs.databinding.ActivityMainBinding;
import com.fisha.roomcodelabs.model.IGame;
import com.fisha.roomcodelabs.roomdatabase.Game;
import com.fisha.roomcodelabs.viewmodel.GameViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements GameAdapterListener
{
    private GameViewModel gameViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final GameListAdapter gameAdapter = new GameListAdapter(new GameListAdapter.GameDiff(), this);
        recyclerView.setAdapter(gameAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //gameViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(GameViewModel.class);
        //final GameViewModel gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        gameViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this)).get(GameViewModel.class);
        gameViewModel.getGameListLD().observe(this, gameAdapter::submitList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Game game = new Game("Overcooked 2", "https://upload.wikimedia.org/wikipedia/en/thumb/0/03/Overcooked_2_cover_art.png/220px-Overcooked_2_cover_art.png");
            gameViewModel.insert(game);
        });
    }

    @Override
    public void onGameClick(IGame game) {
        Toast.makeText(this, game.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.menu_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gameViewModel.setQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gameViewModel.setQuery(newText);
                return false;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search games...");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_delete) {
            gameViewModel.delete(new Game("Overcooked 2", "https://upload.wikimedia.org/wikipedia/en/thumb/0/03/Overcooked_2_cover_art.png/220px-Overcooked_2_cover_art.png"));
            Toast.makeText(this, "Delete Overcooked 2", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}