package com.ahmedmaghawry.square_repos.Control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedmaghawry.square_repos.Model.Repository;
import com.ahmedmaghawry.square_repos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ahmed Maghawry on 3/16/2017.
 * Place holder which takes the data from the Repos and put them in the Recycle view List
 */
public class ReposAdabter extends RecyclerView.Adapter<ReposAdabter.MyViewHolder> {

    private List<Repository> repositoryList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView repoName;
        private TextView repoOwner;
        private TextView repoDescription;
        private ImageView repoAvatar;

        public MyViewHolder(View itemView) {
            super(itemView);
            repoName = (TextView) itemView.findViewById(R.id.repoName);
            repoOwner = (TextView) itemView.findViewById(R.id.repoOwner);
            repoDescription = (TextView) itemView.findViewById(R.id.repoDescription);
            repoAvatar = (ImageView) itemView.findViewById(R.id.repoAvatar);
        }
    }

    public ReposAdabter(List<Repository> repositoryList, Context context) {
        this.repositoryList = repositoryList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View repoItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new MyViewHolder(repoItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Repository repo = repositoryList.get(position);
        holder.repoName.setText(repo.getRepoName());
        holder.repoOwner.setText(repo.getRepoOwner());
        holder.repoDescription.setText((repo.getRepoDescription().equals("null")) ? "No Description" : repo.getRepoDescription());
        //Using the Picasso library to view the avatar
        Picasso .with(context)
                .load(repo.getRepoAvatarUrl())
                .resize(100,100)
                .centerCrop()
                .error(R.mipmap.image_not_found_medium)
                .placeholder(R.mipmap.loading)
                .into(holder.repoAvatar);
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }
}
