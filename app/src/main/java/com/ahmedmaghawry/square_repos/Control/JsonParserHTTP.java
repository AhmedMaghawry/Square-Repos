package com.ahmedmaghawry.square_repos.Control;

import android.os.AsyncTask;
import android.util.Log;

import com.ahmedmaghawry.square_repos.Model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Maghawry on 3/17/2017.
 */
public class JsonParserHTTP extends AsyncTask<String, Void, ArrayList> {

    private List<String> reposName = new ArrayList<>();
    private List<String> reposOwner = new ArrayList<>();
    private List<String> reposDescription = new ArrayList<>();
    private List<String> reposAvatarURL = new ArrayList<>();
    private List<String> reposURL = new ArrayList<>();
    private List<String> reposURLOfOwner = new ArrayList<>();
    private ArrayList<List> repos = new ArrayList<>();

    @Override
    protected ArrayList doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonStr = buffer.toString();
            Log.i("Json", jsonStr);
            fillArrays(jsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            //e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        repos.add(reposName);
        repos.add(reposOwner);
        repos.add(reposDescription);
        repos.add(reposAvatarURL);
        repos.add(reposURL);
        repos.add(reposURLOfOwner);
        return repos;
    }
    private void fillArrays(String cont) throws JSONException{
        JSONArray totalReposArray = new JSONArray(cont);
        for (int i = 0; i < totalReposArray.length(); i++) {
            JSONObject repo = totalReposArray.getJSONObject(i);
            String repoName = repo.getString("name");
            reposName.add(repoName);
            String repoDescription = repo.getString("description");
            reposDescription.add(repoDescription);
            String repoUrl = repo.getString("html_url");
            reposURL.add(repoUrl);
            String repoOwner = repo.getString("owner");
            JSONObject repoOwn = new JSONObject(repoOwner);
            String repoUsername = repoOwn.getString("login");
            reposOwner.add(repoUsername);
            String repoAvatar = repoOwn.getString("avatar_url");
            reposAvatarURL.add(repoAvatar);
            String userURL = repoOwn.getString("html_url");
            reposURLOfOwner.add(userURL);
        }
    }
}
