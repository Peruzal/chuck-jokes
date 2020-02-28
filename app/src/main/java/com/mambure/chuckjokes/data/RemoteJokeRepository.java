package com.mambure.chuckjokes.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RemoteJokeRepository implements DataSource<LiveData<Joke>>, Response.Listener<JSONObject>,
        Response.ErrorListener {

    RequestQueue requestQueue;
    MutableLiveData<Joke> jokeStream = new MutableLiveData<>();


    public RemoteJokeRepository(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getJoke(String url) {
        JsonObjectRequest request = new JsonObjectRequest(url, null, this, this);
        requestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String value = response.getString("value");
            String url = response.getString("url");
            String id = response.getString("id");
            jokeStream.postValue(new Joke(id, url, value));

        } catch (JSONException e) {
            e.printStackTrace();
            jokeStream.postValue(null);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        jokeStream.postValue(null);
    }

    @Override
    public LiveData<Joke> getDataStream() {
        return jokeStream;
    }
}
