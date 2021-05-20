package ru.mirea.zlotov.mireaproject.ui.hihistories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM Cell")
    List<ru.mirea.zlotov.mireaproject.ui.histories.Cell> getAll();
    @Query("SELECT * FROM Cell WHERE id = :id")
    ru.mirea.zlotov.mireaproject.ui.histories.Cell getById(long id);
    @Insert
    void insert(ru.mirea.zlotov.mireaproject.ui.histories.Cell cell);
    @Update
    void update(ru.mirea.zlotov.mireaproject.ui.histories.Cell cell);
    @Delete
    void delete(ru.mirea.zlotov.mireaproject.ui.histories.Cell cell);

}
