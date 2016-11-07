package com.nstuttle.datacentertoolkit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PueActivity extends MainActivity {
    //Declarations
    Double totalFac, totalIt, totalPower, totalCost, dblPUE, dblDcie, emissions, kwCost;
    private TextView txtPueTotal, txtDCie, txtTotalPower, txtTotalCost, txtEmission;
    private EditText txtTotalFac, txtTotalIt, txtOverrideState;
    private CheckBox chkOverrideState;
    private ScrollView scrollPue;
    private Spinner spinnerState;
    private Integer statePosition;
    private Double stateCost = 0.0;
    private String spinnerId = "";
    String[] spinnerArray = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
            "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
            "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
            "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
            "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

    ArrayList<String> pueResults;
    private Calculator calc = new Calculator();
    DecimalFormat formatter = new DecimalFormat("#,###,###,###.##");


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pue);

        //Instantiate Variables
        txtPueTotal = (TextView) findViewById(R.id.txtPueTotal);
        txtDCie = (TextView) findViewById(R.id.txtDCiE);
        txtTotalPower = (TextView) findViewById(R.id.txtTotalPower);
        txtTotalCost = (TextView) findViewById(R.id.txtTotalCost);
        txtEmission = (TextView) findViewById((R.id.txtEmission));
        txtTotalFac = (EditText) findViewById(R.id.txtTotalFac);
        txtTotalIt = (EditText) findViewById(R.id.txtTotalIt);
        Button btnCalc = (Button) findViewById(R.id.btnCalc);
        Button btnToEffAct = (Button) findViewById(R.id.btnToEffAct);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        txtOverrideState = (EditText) findViewById(R.id.txtOverrideState);
        chkOverrideState = (CheckBox) findViewById(R.id.chkOverrideState);
        scrollPue = (ScrollView) findViewById(R.id.scrollPue);

        try {
            BufferedReader statesreader = new BufferedReader(new InputStreamReader(getAssets().open("states.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtOverrideState.setEnabled(false); //OverrideState set 'enabled' false
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerState.setAdapter(adapter);

        //Set Change Listener for State Select
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statePosition = position;
                getStateSelect(statePosition);
                stateCost = Double.parseDouble(getStates(spinnerId, ','));
                txtOverrideState.setText(String.format(Locale.getDefault(), "%.2f", stateCost));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stateCost = 0.111;
            }
        });

        //Calculate PUE/DCiE on BtnClick
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                try {
                    //Prep Variables
                    setStateCost();
                    if (txtTotalIt.getText().toString().isEmpty())
                        throw new Exception("Total IT Load must be greater than 0");
                    if (txtTotalFac.getText().toString().isEmpty())
                        throw new Exception("Total Facility Load must be greater than 0");
                    totalFac = Double.parseDouble(txtTotalFac.getText().toString());
                    totalIt = Double.parseDouble(txtTotalIt.getText().toString());
                    kwCost = Double.parseDouble(txtOverrideState.getText().toString());
                    //Get PUE
                    pueResults = calc.pueCalc(totalFac, totalIt, stateCost);
                    //Set Results
                    txtPueTotal.setText(pueResults.get(0));
                    txtDCie.setText(pueResults.get(1));
                    txtTotalPower.setText(pueResults.get(2) + getString(R.string.kW));
                    txtTotalCost.setText(getString(R.string.dollarSign) + pueResults.get(3));
                    txtEmission.setText(pueResults.get(4) + getString(R.string.txtTons));

                    //Delay the scroll to allow for softkey close
                    final Handler scrollDelay = new Handler();
                    scrollDelay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollPue.smoothScrollTo(0, 530);// Scroll Page After Calc
                        }
                    }, 500);
                    //calcPue();
                } catch (Exception e){
                    String message = e.getMessage();
                    showAlert(message);
                }
            }
        });
        //State Cost Override Listener
        chkOverrideState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinnerState.setEnabled(false);
                    txtOverrideState.setEnabled(true);
                    txtOverrideState.setText("");
                }
                if (!isChecked) {
                    spinnerState.setEnabled(true);
                    txtOverrideState.setEnabled(false);
                    txtOverrideState.setText(String.format(Locale.getDefault(), "%.2f", stateCost));
                }
            }
        });
        //Open Efficiency Activity On BtnClick
        btnToEffAct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(PueActivity.this, EfficiencyActivity.class));
                Intent i = new Intent(PueActivity.this, EfficiencyActivity.class);

                i.putExtra("PUE",txtPueTotal.getText());
                startActivity(i);
            }
        });
    }

    //Set stateCost
    private void setStateCost() {
        try {
            if (txtOverrideState.getText().toString().isEmpty())
                    throw new Exception("State Override Value must be greater than 0.");
            String cost = txtOverrideState.getText().toString();
            stateCost = Double.parseDouble(cost);
        } catch (Exception e) {
            String message = e.getMessage();
            showAlert(message);
        }
    }

    //Change Spinner Val on State Change
    private void getStateSelect(int position) {
        spinnerId = spinnerArray[position];
    }

    //Load in States Array
    private String getStates(String findMatch, Character splitChar) {
        String[] info = {""};
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("states.txt")));
            // Read
            String line = reader.readLine();
            while (line != null) {
                if(line.contains(findMatch)) {
                    if (splitChar == ',') {
                        info = line.split(",");
                    }
                    else if(splitChar == ':'){
                        info = line.split(":");
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return(info[1]);
    }

    //Alert Dialog Function
    private void showAlert(String type) {
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.AppTheme);
        new AlertDialog.Builder(ctw)
                .setMessage(type)
                .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Oops!")
                .setPositiveButton("Got it!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
