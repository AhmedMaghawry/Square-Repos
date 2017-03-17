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
 * HTTPUrlConnection implementation which extends AsyncTask its parameter is the url
 * and its output is the arrayList of Repos to diplay in recycleview
 * This class must extends JsonParser but because it extends AsyncTask (in java) we can't extends another
 * class (JsonParser) so i used JsonParser as Object
 */
public class JsonParserHTTP extends AsyncTask<String, Void, ArrayList> {

    /**
     * because can't be extended
     * and it si the main reason for creating the dummy method
     */
    private JsonParser jsonParser = new JsonParser() {
        @Override
        public void dummy() {
            //Don't Do No thing Just to make the JsonParse class abstract
        }
    };

    @Override
    protected ArrayList<Repository> doInBackground(String... params) {
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
            jsonParser.fillArrays(jsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
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
        jsonParser.createRepos();
        return jsonParser.getList();
    }
}
