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
        txtCost1 = (TextView) findViewById(R.id.txtCost1);
        txtCost2 = (TextView) findViewById(R.id.txtCost2);
        txtCost3 = (TextView) findViewById(R.id.txtCost3);
        txtCarbon1 = (TextView) findViewById(R.id.txtCarbon1);
        txtCarbon2 = (TextView) findViewById(R.id.txtCarbon2);
        txtCarbon3 = (TextView) findViewById(R.id.txtCarbon3);
        txtCars1 = (TextView) findViewById(R.id.txtCars1);
        txtCars2 = (TextView) findViewById(R.id.txtCars2);
        txtCars3 = (TextView) findViewById(R.id.txtCars3);
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
                    txtCost1.setText(effResults.get(1));
                    txtCost2.setText(effResults.get(6));
                    txtCost3.setText(effResults.get(7));
                    txtCarbon1.setText(effResults.get(2));
                    txtCarbon2.setText(effResults.get(8));
                    txtCarbon3.setText(effResults.get(9));
                    txtCars1.setText(effResults.get(3));
                    txtCars2.setText(effResults.get(10));
                    txtCars3.setText(effResults.get(11));
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
