package com.mambure.chuckjokes.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

public class LocalJokeRepository implements
        DataSource<LiveData<List<Joke>>>{

    AppDatabase database;

    public LocalJokeRepository(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "jokes_database").build();
    }

    @Override
    public LiveData<List<Joke>> getDataStream() {
        return database.jokesDAO().getAll();
    }

    public void saveJoke(Joke joke) {
        Thread thread = new Thread(() -> database.jokesDAO().insert(joke));
        thread.start();
    }

    public void deleteJoke(Joke joke) {
        database.jokesDAO().delete(joke);
    }
}
