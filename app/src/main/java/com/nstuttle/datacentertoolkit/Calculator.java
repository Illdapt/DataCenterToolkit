package com.nstuttle.datacentertoolkit;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by nicktuttle on 8/3/2016.
 */
public class Calculator {

    public ArrayList calcPue (String num1, String num2, Boolean chk, String num3){
        ArrayList pueArray = null;
        Double totalFac, totalIt, totalPower, totalCost, dblPUE, dblDcie, emissions;
        String strPUE, strDcie;
        DecimalFormat formatter = new DecimalFormat("#,###,###,###.##");

        try {
            if (num1.isEmpty())
                throw new Exception("Total IT Load must be greater than 0");
            if (num2.isEmpty())
                throw new Exception("Total Facility Load must be greater than 0");
            totalFac = Double.parseDouble(num1);
            totalIt = Double.parseDouble(num2);
            dblPUE = totalFac / totalIt;
            pueArray.add(String.format("%.2f", dblPUE));
            dblDcie = (totalIt / totalFac) * 100;
            pueArray.add(String.format("%.2f", dblDcie));
            //Set Total Power/Year
            totalPower = totalFac * 8760;
            String strTotalPower = formatter.format(totalPower);
            pueArray.add(strTotalPower);
            //Set Total Cost/Year
            if(chk) {
                if (num3.isEmpty())
                    throw new Exception("State Override Value must be greater than 0.");
                totalCost = totalFac * 8760 * Double.parseDouble(num3);
            }
            else
                totalCost = totalFac * 8760 * Double.parseDouble(num3);
            String strTotalCost = formatter.format(totalCost);
            pueArray.add(strTotalCost);
            //Set Total Emissions/Year
            emissions = (totalPower * 1.21) / 2000;
            String strEmissions = formatter.format(emissions);
            pueArray.add(strEmissions);

        } catch (Exception e){

        }
        return pueArray;
    }








}
