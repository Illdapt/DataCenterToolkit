package com.nstuttle.datacentertoolkit;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailActivity extends MainActivity {

    RSSFeed feed;
    TextView title;
    WebView desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

// Enable the vertical fading edge (by default it is disabled)
        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        sv.setVerticalFadingEdgeEnabled(true);

// Get the feed object and the position from the Intent
        feed = (RSSFeed) getIntent().getExtras().get("feed");
        int pos = getIntent().getExtras().getInt("pos");

// Initialize the views
        title = (TextView) findViewById(R.id.title);
        desc = (WebView) findViewById(R.id.desc);

// set webview properties
        WebSettings ws = desc.getSettings();
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.getPluginState();
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setJavaScriptEnabled(true);
        ws.setBuiltInZoomControls(false);

// Set the views
        title.setText(feed.getItem(pos).getTitle());
        desc.loadDataWithBaseURL("http://www.google.com/", feed
                .getItem(pos).getDescription(), "text/html", "UTF-8", null);
    }

}

