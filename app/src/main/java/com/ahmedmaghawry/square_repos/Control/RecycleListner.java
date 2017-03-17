package com.ahmedmaghawry.square_repos.Control;

import android.view.View;

/**
 * Created by Ahmed Maghawry on 3/17/2017.
 * Recycleview Listner interface which created to implement the onClick and on long click Listner
 */
public interface RecycleListner {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
}
