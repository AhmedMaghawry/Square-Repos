package com.ahmedmaghawry.square_repos.Control;

import com.ahmedmaghawry.square_repos.Model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Maghawry on 3/17/2017.
 * Abstract class which consider the parent class of the two types of the parses :
 * Http parse
 * Volley parse
 */
public abstract class JsonParser {

    public List<String> reposName = new ArrayList<>();
    public List<String> reposOwner = new ArrayList<>();
    public List<String> reposDescription = new ArrayList<>();
    public List<String> reposAvatarURL = new ArrayList<>();
    public List<String> reposURL = new ArrayList<>();
    public List<String> reposURLOfOwner = new ArrayList<>();
    public ArrayList<Repository> repos = new ArrayList<>();

    /**
     * parse the json string to the needed objects and put them in the arraylists to use them later
     * @param cont this parameter consider the json string which get from the api to parse
     * @throws JSONException
     */
    public void fillArrays(String cont) throws JSONException {
        /**
         * the first Json array which came from the api
         */
        JSONArray totalReposArray = new JSONArray(cont);
        for (int i = 0; i < totalReposArray.length(); i++) {            /** Loop on the objects in the array to get the data from each*/
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

    /**
     * Create the repositories from the given data from fillArrays method
     * and put the repos in repoList arraylist to return it finally
     */
    public void createRepos() {
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
    }

    public ArrayList<Repository> getList() {
        return repos;
    }

    /**
     * dummy method to make sure that this class is an abstract class
     */
    public abstract void dummy();
}
