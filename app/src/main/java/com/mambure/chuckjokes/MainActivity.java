package com.mambure.chuckjokes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String JOKES_SERVER_URL = "https://api.chucknorris.io/jokes/random/";
    private ProgressBar progressBar;
    private TextView jokeTextView;
    private GetJokeTask getJokeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeTextView = findViewById(R.id.txtJoke);
        progressBar = findViewById(R.id.progressBar);
        Button button = findViewById(R.id.btnGetJoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeTextView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getJoke();
            }
        });

        getJoke();
    }

    private void getJoke() {
        getJokeTask = new GetJokeTask();
        getJokeTask.execute(null, null);
    }





    private class GetJokeTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestUtil.makeRequest(JOKES_SERVER_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            jokeTextView.setVisibility(View.VISIBLE);
            jokeTextView.setText(s);
        }
    }


}
