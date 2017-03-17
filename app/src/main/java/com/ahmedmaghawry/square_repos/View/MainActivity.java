package com.ahmedmaghawry.square_repos.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ahmedmaghawry.square_repos.Control.JsonParser;
import com.ahmedmaghawry.square_repos.Control.JsonParserHTTP;
import com.ahmedmaghawry.square_repos.Control.JsonParserVolley;
import com.ahmedmaghawry.square_repos.Control.RecycleListner;
import com.ahmedmaghawry.square_repos.Control.RecycleTouchListner;
import com.ahmedmaghawry.square_repos.Control.ReposAdabter;
import com.ahmedmaghawry.square_repos.Control.VolleySinglton;
import com.ahmedmaghawry.square_repos.Model.Repository;
import com.ahmedmaghawry.square_repos.R;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private JSONArray json;
    private List<Repository> reposList = new ArrayList();
    private ReposAdabter adapter;
    private RecyclerView recyclerView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = this.getResources().getString(R.string.URL);
        setContentView(R.layout.activity_main);
        /**
         * Parse with Volley
         */
        getDataFromAPIUsingVolley();
        /**
         * parse with HTTP
         */
        //getDataFromAPIUsingHTTP();
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        adapter = new ReposAdabter(reposList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Go To")
                .setMessage("Choose the url you want to go :");
        recyclerView.addOnItemTouchListener(new RecycleTouchListner(getApplicationContext(), recyclerView, new RecycleListner() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplication(), reposList.get(position).getRepoName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {
                dialog.setNegativeButton("Owner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Open the URL of the owner in the browser
                        goTo(reposList.get(position).getRepoUrlOwner());
                    }
                })
                        .setPositiveButton("Repositpry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Open the URL of the Repo in the browser
                                goTo(reposList.get(position).getRepoUrl());
                            }
                        })
                        .show();
            }
        }));
        adapter.notifyDataSetChanged();
    }

    /**
     * Go to the Current url on choosing from dialog
     * @param url the wanted url
     */
    private void goTo(String url) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browse);
    }

    /**
     * HTTP parse
     */
    private void getDataFromAPIUsingHTTP() {
        try {
            reposList = new JsonParserHTTP().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Volley parse
     */
    private void getDataFromAPIUsingVolley() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                json = response;
                JsonParserVolley parser = null;
                try {
                    parser = new JsonParserVolley(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reposList = parser.getList();
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    json = null;
            }
        });
        VolleySinglton.getInstance(this).addToRequestQueue(jsonArrayRequest);
        /**
         * Get the Caching data
         */
        Cache cache = VolleySinglton.getInstance(this).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null) {
            try {
                String data = new String(entry.data,"UTF-8");
                JsonParser jsonParser = new JsonParser() {
                    @Override
                    public void dummy() {
                        // Dummy method
                    }
                };
                jsonParser.fillArrays(data);
                jsonParser.createRepos();
                reposList = jsonParser.getList();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
