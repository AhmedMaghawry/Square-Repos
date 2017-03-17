package com.ahmedmaghawry.square_repos.Control;

import android.util.Log;

import com.ahmedmaghawry.square_repos.Model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Maghawry on 3/17/2017.
 */
public abstract class JsonParser {

    private List<String> reposName = new ArrayList<>();
    private List<String> reposOwner = new ArrayList<>();
    private List<String> reposDescription = new ArrayList<>();
    private List<String> reposAvatarURL = new ArrayList<>();
    private List<String> reposURL = new ArrayList<>();
    private List<String> reposURLOfOwner = new ArrayList<>();
    private ArrayList<List> repos = new ArrayList<>();

    private void fillArrays(String cont) throws JSONException {
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

    /*private void createRepos() {
        for(int i = 0; i < reposName.size(); i++) {
            Repository repo = new Repository();
            repo.setRepoName(reposName.get(i))
                    .setRepoOwner(reposOwner.get(i))
                    .setRepoDescription(reposDescription.get(i))
                    .setRepoAvatarUrl(reposAvatarURL.get(i))
                    .setRepoUrl(reposURL.get(i))
                    .setRepoUrlOwner(reposURLOfOwner.get(i));
            repos.add(repo);
        }
    }*/
}
