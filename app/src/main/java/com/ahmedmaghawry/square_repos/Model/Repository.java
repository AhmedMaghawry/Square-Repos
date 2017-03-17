package com.ahmedmaghawry.square_repos.Model;

/**
 * Created by Ahmed Maghawry on 3/16/2017.
 */
public class Repository {

    private String repoName;
    private String repoDescription;
    private String repoOwner;
    private String repoUrl;
    private String repoUrlOwner;
    private String repoAvatarUrl;

    public Repository () {
        repoName = "Reponame";
        repoDescription = "EnterDescription ...";
        repoOwner = "UserName";
        repoUrl = "http://url";
        repoUrlOwner = "http://url";
        repoAvatarUrl = "Don't Found";
    }

    public String getRepoName() {
        return repoName;
    }

    public Repository setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public Repository setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
        return this;
    }

    public String getRepoUrlOwner() {
        return repoUrlOwner;
    }

    public Repository setRepoUrlOwner(String repoUrlOwner) {
        this.repoUrlOwner = repoUrlOwner;
        return this;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public Repository setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
        return this;
    }

    public String getRepoAvatarUrl() {
        return repoAvatarUrl;
    }

    public Repository setRepoAvatarUrl(String repoAvatarUrl) {
        this.repoAvatarUrl = repoAvatarUrl;
        return this;
    }

    public String getRepoDescription() {
        return repoDescription;
    }

    public Repository setRepoDescription(String repoDescription) {
        this.repoDescription = repoDescription;
        return this;
    }
}
