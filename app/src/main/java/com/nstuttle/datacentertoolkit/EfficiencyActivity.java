package com.nstuttle.datacentertoolkit;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.ArrayList;

public class EfficiencyActivity extends MainActivity {

    private EditText txtYourPue, txtDesiredPue;
    private TextView txtElec1, txtElec2, txtElec3, txtCost1, txtCost2, txtCost3, txtCarbon1,
            txtCarbon2, txtCarbon3, txtCars1, txtCars2, txtCars3;
    private ScrollView scrollEff;
    private Calculator calc = new Calculator();
    double curPue, desPue, totalIt, kwCost;
    ArrayList<String> effResults;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efficiency);
        txtYourPue = (EditText) findViewById(R.id.txtYourPue);
        txtElec1 = (TextView) findViewById(R.id.txtElec1);
        txtElec2 = (TextView) findViewById(R.id.txtElec2);
        txtElec3 = (TextView) findViewById(R.id.txtElec3);
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
                curPue = Double.parseDouble(txtYourPue.getText().toString());
                desPue = Double.parseDouble(txtDesiredPue.getText().toString());
                totalIt = 2000;
                kwCost = 0.13;
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                try {
                    effResults = calc.effCalc(curPue, desPue, totalIt, kwCost);
                    txtElec1.setText(effResults.get(0));
                    txtElec2.setText(effResults.get(4));
                    txtElec3.setText(effResults.get(5));
                    scroll();
                } catch (Exception e){
                    String message = e.getMessage();
                    //showAlert(message);
                }
            }
        });
    }

    public void scroll() {
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
