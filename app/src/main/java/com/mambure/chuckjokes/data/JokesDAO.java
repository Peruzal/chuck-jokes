package com.mambure.chuckjokes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JokesDAO {
    @Query("SELECT * FROM chuckJokes")
    LiveData<List<Joke>> getAll();

    @Insert
    void insert(Joke joke);

    @Delete
    void delete(Joke joke);

}
