package com.nstuttle.datacentertoolkit;

import android.app.AlertDialog;
import android.content.*;
import android.net.ConnectivityManager;
import android.os.*;
/**
 * Created by nicktuttle on 1/27/2016.
 */
public class BlogSplash extends MainActivity {

    private String RSSFEEDURL = "http://www.42u.com/feed";
    RSSFeed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blogsplash);

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() == null
                && !conMgr.getActiveNetworkInfo().isConnected()
                && !conMgr.getActiveNetworkInfo().isAvailable()) {
            // No connectivity - Show alert
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(
                    "Unable to reach server, \nPlease check your connectivity.")
                    .setTitle("RSS Reader")
                    .setCancelable(false)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    finish();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        } else {
            // Connected - Start parsing
            new AsyncLoadXMLFeed().execute();

        }

    }

    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            // Obtain feed
            DOMParser myParser = new DOMParser();
            feed = myParser.parseXml(RSSFEEDURL);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Bundle bundle = new Bundle();
            bundle.putSerializable("feed", feed);

            // launch List activity
            Intent intent = new Intent(BlogSplash.this, BlogActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            // kill this activity
            finish();
        }

    }

}

