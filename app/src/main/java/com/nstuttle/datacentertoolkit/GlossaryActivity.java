package com.nstuttle.datacentertoolkit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GlossaryActivity extends MainActivity {

    ListView lv;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        lv = (ListView) findViewById(R.id.listView);
        getGlossary();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // actions to be performed when a list item clicked
                int pos = arg2;
            }
        });
    }

    public void getGlossary(){
        String[] info = {""}; // For Terms
        String[] info2 = {""}; // For Definitions
        List<String> infoList = new ArrayList<String>();
        List<String> infoList2 = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("glossary.txt")));
            // Read
            String line = reader.readLine();
            while (line != null) {
                info = line.split(":");
                infoList.add(info[0]);
                infoList2.add(info[1]);
                line = reader.readLine();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, infoList);
            lv.setAdapter(arrayAdapter);
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
