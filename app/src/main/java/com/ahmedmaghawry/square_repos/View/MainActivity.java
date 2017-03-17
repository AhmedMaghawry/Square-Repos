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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ahmedmaghawry.square_repos.Control.JsonParserHTTP;
import com.ahmedmaghawry.square_repos.Control.JsonParserVolley;
import com.ahmedmaghawry.square_repos.Control.RecycleListner;
import com.ahmedmaghawry.square_repos.Control.RecycleTouchListner;
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
    private List<String> reposName = new ArrayList<>();
    private List<String> reposOwner = new ArrayList<>();
    private List<String> reposDescription = new ArrayList<>();
    private List<String> reposAvatarURL = new ArrayList<>();
    private List<String> reposURL = new ArrayList<>();
    private List<String> reposURLOfOwner = new ArrayList<>();
    private ArrayList<List> repos = new ArrayList<>();
    private AlertDialog.Builder dialog;

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
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Go To")
                .setMessage("Choose the url you want to go :");
        recyclerView.addOnItemTouchListener(new RecycleTouchListner(getApplicationContext(), recyclerView, new RecycleListner() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplication(),"Item Clicked",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {
                dialog.setNegativeButton("Owner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(reposURLOfOwner.get(position)));
                        startActivity(browse);
                    }
                })
                .setPositiveButton("Repositpry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(reposURL.get(position)));
                        startActivity(browse);
                    }
                })
                .show();
            }
        }));
        //getDataFromAPIUsingVolley();
        getDataFromAPIUsingHTTP();
        createRepos();
        adapter.notifyDataSetChanged();
    }

    private void goTo(String url) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browse);
    }

    private void getDataFromAPIUsingHTTP() {
        try {
            repos = new JsonParserHTTP().execute("https://api.github.com/users/Square/repos").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        reposName = repos.get(0);
        reposOwner = repos.get(1);
        reposDescription = repos.get(2);
        reposAvatarURL = repos.get(3);
        reposURL = repos.get(4);
        reposURLOfOwner = repos.get(5);
    }

    private void createRepos() {
        for(int i = 0; i < reposName.size(); i++) {
            Repository repo = new Repository();
            repo.setRepoName(reposName.get(i))
                    .setRepoOwner(reposOwner.get(i))
                    .setRepoDescription(reposDescription.get(i))
                    .setRepoAvatarUrl(reposAvatarURL.get(i))
                    .setRepoUrl(reposURL.get(i))
                    .setRepoUrlOwner(reposURLOfOwner.get(i));
            reposList.add(repo);
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
                    parser = new JsonParserVolley(json);
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
