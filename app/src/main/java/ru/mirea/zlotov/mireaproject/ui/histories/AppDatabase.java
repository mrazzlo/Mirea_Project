package ru.mirea.zlotov.mireaproject.ui.histories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ru.mirea.zlotov.mireaproject.ui.histories.Cell.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ru.mirea.zlotov.mireaproject.ui.hihistories.HistoryDao storyDao();
}

