package com.nstuttle.datacentertoolkit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    String[] menuArray = {"PUE Calculator", "Efficiency Savings Calculator", "Thermal Calculator", "Power Calculator", "Basic Calculator", "News Stand and Blog", "Flashlight", "Glossary", "AWG to mm"};
    private Flashlight flashlight = new Flashlight();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, menuArray);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (position == 0) {
                            //code specific to first list item: PUE Calculator
                            Intent pueIntent = new Intent(view.getContext(), PueActivity.class);
                            startActivity(pueIntent);
                        }
                        if (position == 1) {
                            //code specific to second list item: Efficiency Calc
                            Intent efficiencyIntent = new Intent(view.getContext(), EfficiencyActivity.class);
                            startActivity(efficiencyIntent);
                        }
                        if (position == 2) {
                            //code specific to third list item: Thermal Calc
                            Intent thermalIntent = new Intent(view.getContext(), ThermalActivity.class);
                            startActivity(thermalIntent);
                        }
                        if (position == 3) {
                            //code specific to fourth list item: Power Calculator
                            new CalenderParser().execute();
                        }
                        if (position == 4) {
                            //code specific to fifth list item: Basic Calculator
                        }
                        if (position == 5) {
                            //code specific to sixth list item: News Stand and Blog
                            Intent blogIntent = new Intent(view.getContext(), BlogSplash.class);
                            startActivity(blogIntent);
                        }
                        if (position == 6) {
                            //code specific to seventh list item: Flashlight
                            flashlight.runFlashlight(isFlashSupported(), view);
                        }
                        if (position == 7) {
                            Intent glossaryIntent = new Intent(view.getContext(), GlossaryActivity.class);
                            startActivity(glossaryIntent);
                        }
                        if (position == 8) {
                            Intent cableCvtIntent = new Intent(view.getContext(), CableCvtActivity.class);
                            startActivity(cableCvtIntent);
                        }
                    }
                }
        );//End OnItemClickListener
    }
    //Method determines if flashlight is supported
    private boolean isFlashSupported() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}//End Class MainActivity
