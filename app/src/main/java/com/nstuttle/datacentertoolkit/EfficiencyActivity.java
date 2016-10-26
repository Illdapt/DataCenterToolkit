package com.nstuttle.datacentertoolkit;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class EfficiencyActivity extends MainActivity {

    private EditText txtYourPue, txtDesiredPue;
    private ScrollView scrollEff;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efficiency);
        txtYourPue = (EditText) findViewById(R.id.txtYourPue);
        txtDesiredPue = (EditText) findViewById(R.id.txtDesiredPue);
        scrollEff = (ScrollView) findViewById(R.id.scrollEff);
        Button btnCalcSave = (Button) findViewById(R.id.btnCalcSave);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            txtYourPue.setText(extras.getString("PUE"));
        }

        btnCalcSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                try {
                    calcEfficiency();
                } catch (Exception e){
                    String message = e.getMessage();
                    //showAlert(message);
                }
            }
        });
    }

    public void calcEfficiency(){

        //Delay the scroll to allow for softkey close
        final Handler scrollDelay = new Handler();
        scrollDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollEff.smoothScrollTo(0, 320);// Scroll Page After Calc
            }
        }, 200);
    }

}
