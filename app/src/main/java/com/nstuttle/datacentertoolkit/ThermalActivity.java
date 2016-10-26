package com.nstuttle.datacentertoolkit;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.text.DecimalFormat;

public class ThermalActivity extends MainActivity {
    private TextView txtKwBtu, txtTotalIt;
    private String spinnerId = "";
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
        btnKwBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat formatter = new DecimalFormat("#,###,###,###.##");
                Double totalIt = Double.parseDouble(txtTotalIt.getText().toString());
                if (spinnerId.equals("W")){
                    totalIt = totalIt * 3.412142;
                    String strTotalBtu = formatter.format(totalIt);
                    txtKwBtu.setText(strTotalBtu + " BTU/hr");
                }
                if (spinnerId.equals("kW")){
                    totalIt = totalIt * 3412.14;
                    String strTotalBtu = formatter.format(totalIt);
                    txtKwBtu.setText(strTotalBtu + " BTU/hr");
                }
            }
        });
    }
}
