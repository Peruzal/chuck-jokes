package com.mambure.chuckjokes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.mambure.chuckjokes.data.Joke;
import com.mambure.chuckjokes.data.LocalJokeRepository;
import com.mambure.chuckjokes.data.RemoteJokeRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private RemoteJokeRepository remoteJokeRepository;
    private LocalJokeRepository localJokeRepository;
    private LiveData<Joke> jokeStream;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        remoteJokeRepository = new RemoteJokeRepository(application);
        jokeStream = remoteJokeRepository.getDataStream();
        localJokeRepository = new LocalJokeRepository(application);
        jokeStream.observeForever(this::saveJoke);
    }

    public void fetchNewJoke(){
        remoteJokeRepository.getJoke(MainActivity.JOKES_SERVER_URL);
    }

    public LiveData<List<Joke>> loadJokes() {
        return localJokeRepository.getDataStream();
    }

    private void saveJoke(Joke joke) {
        localJokeRepository.saveJoke(joke);
    }
}
