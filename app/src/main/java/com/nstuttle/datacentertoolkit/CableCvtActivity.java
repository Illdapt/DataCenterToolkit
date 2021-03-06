package com.nstuttle.datacentertoolkit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The AWG Converter allows the user to select a common AWG cable size and convert to either mm or in.
 */

public class CableCvtActivity extends MainActivity {
    //Declarations
    Spinner spinnerAWG;
    private Calculator calc = new Calculator();
    private String awgResults;
    private int awgSelect;
    private TextView txtCvtAWG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cablecvt);
        ArrayList<String> spinnerArray = new ArrayList<>();
        Button btnCvtAWG = (Button) findViewById(R.id.btnCvtAWG);
        txtCvtAWG = (TextView) findViewById(R.id.txtCvtAWG);
        spinnerAWG = (Spinner) findViewById(R.id.spinnerAWG);
        spinnerArray.add("0000");
        spinnerArray.add("000");
        spinnerArray.add("00");
        for (int i = 0; i < 41; i++) {
            spinnerArray.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerArray);
        spinnerAWG.setAdapter(adapter);

        //Convert AWG->mm on BtnClick
        btnCvtAWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String spinTxt = spinnerAWG.getSelectedItem().toString();
                    switch (spinTxt) {
                        case "0000":
                            awgSelect = -3;
                            break;
                        case "000":
                            awgSelect = -2;
                            break;
                        case "00":
                            awgSelect = -1;
                            break;
                        default:
                            awgSelect = Integer.parseInt(spinTxt);
                            break;
                    }
                    System.out.println(awgSelect);
                    awgResults = calc.awgToMm(awgSelect) + " " + getString(R.string.mm);
                    txtCvtAWG.setText(awgResults);
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
