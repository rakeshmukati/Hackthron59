package com.example.itemgiveaway;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class MyRequestQueue {
    private static MyRequestQueue myRequestQueue = null;

    public static final String BASE_URL = "http://app-0a25c152-8614-4ab2-9826-d6f54ef39443.cleverapps.io/";
    private RequestQueue requestQueue;

    private MyRequestQueue() {
        Cache cache = new DiskBasedCache(App.context.getCacheDir(), 1024 * 1024*3); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();
    }

    public static MyRequestQueue getInstance() {
        if (myRequestQueue == null) {
            myRequestQueue = new MyRequestQueue();
        }
        return myRequestQueue;
    }

    public void addRequest(StringRequest stringRequest) {
        requestQueue.add(stringRequest);
    }
}
