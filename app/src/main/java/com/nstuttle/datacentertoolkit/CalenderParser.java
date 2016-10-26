package com.nstuttle.datacentertoolkit;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

class CalenderParser extends AsyncTask<Void, Void, Void>{

        private Exception exception;
        @Override
        protected Void doInBackground(Void... params) {

            try {
                org.jsoup.nodes.Document doc = Jsoup.connect("http://www.42u.com/events/list").get();
                Elements events = doc.getElementsByClass("tribe-event-url");
                Elements eventsStartDate = doc.getElementsByClass("tribe-event-date-start");
                Elements eventsEndDate = doc.getElementsByClass("tribe-event-date-end");
                for (Element event : events) {
                    String eventTitle = event.text();
                    System.out.print(eventTitle);
                }
            } catch (IOException e) {
            }
            return null;
        }
}


