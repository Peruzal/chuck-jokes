package com.mambure.chuckjokes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mambure.chuckjokes.data.Joke;
import com.mambure.chuckjokes.data.RemoteJokeRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String JOKES_SERVER_URL = "https://api.chucknorris.io/jokes/random/";
    private TextView jokeTextView;
    private ProgressBar progressBar;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider.
                AndroidViewModelFactory(getApplication()).create(MainActivityViewModel.class);
        progressBar = findViewById(R.id.progressBar);
        jokeTextView = findViewById(R.id.txtJoke);

        Button getJokeButton = findViewById(R.id.btnGetJoke);
        getJokeButton.setOnClickListener(v -> getJoke());


    }

    @Override
    protected void onStart() {
        super.onStart();

        LiveData<List<Joke>> jokeStream = viewModel.loadJokes();

        jokeStream.observe(this, jokes -> {
            progressBar.setVisibility(View.INVISIBLE);
            jokeTextView.setVisibility(View.VISIBLE);
            if (!jokes.isEmpty()) {
                Joke joke = jokes.get(0);
                jokeTextView.setText(joke.value);
                Log.d("Jokes", jokes.toString());
            }
        });

        getJoke();
    }

    private void getJoke() {
        progressBar.setVisibility(View.VISIBLE);
        jokeTextView.setVisibility(View.INVISIBLE);
        viewModel.fetchNewJoke();

    }

    private void createNotificationChannel() {


    }



    private void scheduleJoke() {

    }

    private void scheduleJokeWithWorkManager() {

    }


}
