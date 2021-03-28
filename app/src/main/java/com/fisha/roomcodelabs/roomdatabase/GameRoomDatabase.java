package com.fisha.roomcodelabs.roomdatabase;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameRoomDatabase extends RoomDatabase
{
    public abstract GameDao gameDao();

    private static volatile GameRoomDatabase INSTANCE;
    private static final int NUMBERS_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

    public static GameRoomDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (GameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, GameRoomDatabase.class, "game.db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                GameDao dao = INSTANCE.gameDao();
                dao.deleteAll();

                // https://en.wikipedia.org/wiki/List_of_PlayStation_5_games
                List<Game> gameList = Arrays.asList(
                        new Game("Assassin's Creed Valhalla", "https://upload.wikimedia.org/wikipedia/en/f/f8/ACValhalla.jpg"),
                        new Game("Call of Duty: Black Ops Cold War", "https://upload.wikimedia.org/wikipedia/en/4/40/Black_Ops_Cold_War.jpeg"),
                        new Game("Borderlands 3", "https://upload.wikimedia.org/wikipedia/en/2/21/Borderlands_3_cover_art.jpg"),
                        new Game("Cyberpunk 2077", "https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg"),
                        new Game("Crash Bandicoot 4: It's About Time", "https://upload.wikimedia.org/wikipedia/en/thumb/3/39/Crash_Bandicoot_4_Box_Art.jpeg/220px-Crash_Bandicoot_4_Box_Art.jpeg"),
                        new Game("Far Cry 6", "https://upload.wikimedia.org/wikipedia/en/3/35/Far_cry_6_cover.jpg"),
                        new Game("Ratchet & Clank: Rift Apart", "https://upload.wikimedia.org/wikipedia/en/thumb/3/3c/Ratchet_and_Clank_-_Rift_Apart_cover_art.jpg/220px-Ratchet_and_Clank_-_Rift_Apart_cover_art.jpg"),
                        new Game("Sackboy: A Big Adventure", "https://upload.wikimedia.org/wikipedia/en/thumb/b/bc/Sackboy_-_A_Big_Adventure_cover_art.jpg/220px-Sackboy_-_A_Big_Adventure_cover_art.jpg"),
                        new Game("The Witcher 3: Wild Hunt", "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Witcher_3_cover_art.jpg/220px-Witcher_3_cover_art.jpg"),
                        new Game("Ghost of Tsushima", "https://smartcdkeys.com/image/data/products/ghost-of-tsushima-ps4/cover/ghost-of-tsushima-ps4-smartcdkeys-cheap-cd-key-cover.jpg"),
                        new Game("Call of Duty: Modern Warfare", "https://upload.wikimedia.org/wikipedia/en/thumb/e/e9/CallofDutyModernWarfare%282019%29.jpg/220px-CallofDutyModernWarfare%282019%29.jpg")
                );
                dao.insertAll(gameList);
            });
        }
    };
}
