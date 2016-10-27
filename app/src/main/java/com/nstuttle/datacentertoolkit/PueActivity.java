package com.nstuttle.datacentertoolkit;

import android.app.AlertDialog;
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
import java.util.Locale;


public class PueActivity extends MainActivity {
    //Declarations
    private TextView txtPueTotal, txtDCie, txtTotalPower, txtTotalCost, txtEmission;
    private EditText txtTotalFac, txtTotalIt, txtOverrideState;

    private CheckBox chkOverrideState;
    private ScrollView scrollPue;
    private Spinner spinnerState;
    private Integer statePosition;
    private Double stateCost = 0.0;
    private String spinnerId = "";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pue);
        String[] spinnerArray = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
                "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
                "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

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
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statePosition = position;
                getStateSelect(statePosition);
                stateCost = Double.parseDouble(getStates(spinnerId, ','));
                txtOverrideState.setText(String.format(Locale.getDefault(), "%.2f", stateCost));
                System.out.println(stateCost);
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
                    calcPue();
                } catch (Exception e){
                    String message = e.getMessage();
                    showAlert(message);
                }
            }
        });
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
    public void calcPue() {
        Double totalFac, totalIt, totalPower, totalCost, dblPUE, dblDcie, emissions;
        String strPUE, strDcie;
        DecimalFormat formatter = new DecimalFormat("#,###,###,###.##");

        try {
            if(txtTotalIt.getText().toString().isEmpty())
                throw new Exception("Total IT Load must be greater than 0");
            if(txtTotalFac.getText().toString().isEmpty())
                throw new Exception("Total Facility Load must be greater than 0");
            totalFac = Double.parseDouble(txtTotalFac.getText().toString());
            totalIt = Double.parseDouble(txtTotalIt.getText().toString());
            dblPUE = totalFac / totalIt;
            strPUE = String.format(Locale.getDefault(), "%.2f", dblPUE);
            dblDcie = (totalIt / totalFac) * 100;
            strDcie = String.format(Locale.getDefault(), "%.2f", dblDcie) + "%";

            //Set PUE and DCiE
            txtPueTotal.setText(strPUE);
            txtDCie.setText(strDcie);
            //Set Total Power/Year
            totalPower = totalFac * 8760;
            String strTotalPower = formatter.format(totalPower);
            txtTotalPower.setText(strTotalPower + getString(R.string.kW));
            //Set Total Cost/Year
            if(chkOverrideState.isChecked()) {
                if (txtOverrideState.getText().toString().isEmpty())
                    throw new Exception("State Override Value must be greater than 0.");
                totalCost = totalFac * 8760 * Double.parseDouble(txtOverrideState.getText().toString());
            }
            else
                totalCost = totalFac * 8760 * stateCost;
            String strTotalCost = formatter.format(totalCost);
            txtTotalCost.setText(getString(R.string.dollarSign) + strTotalCost);
            //Set Total Emissions/Year
            emissions = (totalPower * 1.21) / 2000;
            String strEmissions = formatter.format(emissions);
            txtEmission.setText(strEmissions + getString(R.string.txtTons));
            //Delay the scroll to allow for softkey close
            final Handler scrollDelay = new Handler();
            scrollDelay.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollPue.smoothScrollTo(0, 530);// Scroll Page After Calc
                }
            }, 500);
        }catch (Exception e){
            String message = e.getMessage();
            showAlert(message);
        }
    }

    public void getStateSelect(int position){
        if (position == 0) spinnerId = "Alabama";
        if (position == 1) spinnerId = "Alaska";
        if (position == 2) spinnerId = "Arizona";
        if (position == 3) spinnerId = "Arkansas";
        if (position == 4) spinnerId = "California";
        if (position == 5) spinnerId = "Colorado";
        if (position == 6) spinnerId = "Connecticut";
        if (position == 7) spinnerId = "Delaware";
        if (position == 8) spinnerId = "District of Columbia";
        if (position == 9) spinnerId = "Florida";
        if (position == 10) spinnerId = "Georgia";
        if (position == 11) spinnerId = "Hawaii";
        if (position == 12) spinnerId = "Idaho";
        if (position == 13) spinnerId = "Illinois";
        if (position == 14) spinnerId = "Indiana";
        if (position == 15) spinnerId = "Iowa";
        if (position == 16) spinnerId = "Kansas";
        if (position == 17) spinnerId = "Kentucky";
        if (position == 18) spinnerId = "Louisiana";
        if (position == 19) spinnerId = "Maine";
        if (position == 20) spinnerId = "Maryland";
        if (position == 21) spinnerId = "Massachusetts";
        if (position == 22) spinnerId = "Michigan";
        if (position == 23) spinnerId = "Minnesota";
        if (position == 24) spinnerId = "Mississippi";
        if (position == 25) spinnerId = "Missouri";
        if (position == 26) spinnerId = "Montana";
        if (position == 27) spinnerId = "Nebraska";
        if (position == 28) spinnerId = "Nevada";
        if (position == 29) spinnerId = "New Hampshire";
        if (position == 30) spinnerId = "New Jersey";
        if (position == 31) spinnerId = "New Mexico";
        if (position == 32) spinnerId = "New York";
        if (position == 33) spinnerId = "North Carolina";
        if (position == 34) spinnerId = "North Dakota";
        if (position == 35) spinnerId = "Ohio";
        if (position == 36) spinnerId = "Oklahoma";
        if (position == 37) spinnerId = "Oregon";
        if (position == 38) spinnerId = "Pennsylvania";
        if (position == 39) spinnerId = "Rhode Island";
        if (position == 40) spinnerId = "South Carolina";
        if (position == 41) spinnerId = "South Dakota";
        if (position == 42) spinnerId = "Tennessee";
        if (position == 43) spinnerId = "Texas";
        if (position == 44) spinnerId = "Utah";
        if (position == 45) spinnerId = "Vermont";
        if (position == 46) spinnerId = "Virginia";
        if (position == 47) spinnerId = "Washington";
        if (position == 48) spinnerId = "West Virginia";
        if (position == 49) spinnerId = "Wisconsin";
        if (position == 50) spinnerId = "Wyoming";
    }

    public String getStates(String findMatch, Character splitChar){
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
