package com.ahmedmaghawry.square_repos.Control;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ahmed Maghawry on 3/16/2017.
 */
public class VolleySinglton {

    private static VolleySinglton instance;
    private RequestQueue requestQueue;
    private static Context content;

    private VolleySinglton(Context context) {
        content = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySinglton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySinglton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(content.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
