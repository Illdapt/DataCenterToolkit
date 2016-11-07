package com.nstuttle.datacentertoolkit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class ThermalActivity extends MainActivity {
    //Declarations
    private Calculator calc = new Calculator();
    ArrayList<String> thermalResults;
    private TextView txtKwBtu, txtTotalIt;
    private String spinnerId = "";
    private double totalIt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal);
        String[] spinnerArray = {"Watts", "kiloWatts"};
        Button btnKwBtu = (Button) findViewById(R.id.btnKwBtu);
        txtKwBtu = (TextView) findViewById(R.id.txtKwBtu);
        txtTotalIt = (TextView) findViewById(R.id.txtTotalIt);
        Spinner spinnerWkW = (Spinner) findViewById(R.id.spinnerWkW);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerWkW.setAdapter(adapter);

        //Spinner Change Listener
        spinnerWkW.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinnerId = "W";
                }
                if (position == 1) {
                    spinnerId = "kW";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerId = "W";
            }
        });

        //Convert Power->Thermal on BtnClick
        btnKwBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txtTotalIt.getText().toString().isEmpty())
                        throw new Exception("Total IT Load must be greater than 0");
                    totalIt = Double.parseDouble(txtTotalIt.getText().toString());
                    thermalResults = calc.thermalCalc(totalIt, spinnerId);
                    txtKwBtu.setText(thermalResults.get(0) + " BTU/hr");
                } catch (Exception e) {
                    String message = e.getMessage();
                    showAlert(message);
                }
            }
        });
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
