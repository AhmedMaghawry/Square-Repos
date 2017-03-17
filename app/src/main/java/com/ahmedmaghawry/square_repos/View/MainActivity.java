package com.ahmedmaghawry.square_repos.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmedmaghawry.square_repos.Control.JsonParserHTTP;
import com.ahmedmaghawry.square_repos.Control.JsonParserVolley;
import com.ahmedmaghawry.square_repos.Control.ReposAdabter;
import com.ahmedmaghawry.square_repos.Control.VolleySinglton;
import com.ahmedmaghawry.square_repos.Model.Repository;
import com.ahmedmaghawry.square_repos.R;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private JSONArray json;
    private List<Repository> reposList = new ArrayList();
    private ReposAdabter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        adapter = new ReposAdabter(reposList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //getDataFromAPIUsingVolley();
        getDataFromAPIUsingHTTP();
        adapter.notifyDataSetChanged();
    }

    private void getDataFromAPIUsingHTTP() {
        try {
            reposList = new JsonParserHTTP().execute("https://api.github.com/users/Square/repos").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromAPIUsingVolley() {
        //Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        //Network network = new BasicNetwork(new HurlStack());
        String url = "https://api.github.com/users/Square/repos";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                json = response;
                JsonParserVolley parser = null;
                try {
                    parser = new JsonParserVolley(json, reposList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reposList = parser.getList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    json = null;
            }
        });
        VolleySinglton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}
