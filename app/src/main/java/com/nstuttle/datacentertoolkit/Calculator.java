/* The Calculator Class handles all calculation operations for the Data Center Toolkit. This Class
houses both basic(add, subtract, etc) and advanced(PUE, Thermal, Electric, etc) calculator
functionality*/

package com.nstuttle.datacentertoolkit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

class Calculator {
    private DecimalFormat formatterTwo = new DecimalFormat("#,###,###,###.##");
    private DecimalFormat formatterFour = new DecimalFormat("#,###,###,###.####");

    //Methods
    double add(double x, double y) {
        return x + y;
    }

    double subtract(double x, double y) {
        return x - y;
    }

    double multiply(double x, double y) {
        return x * y;
    }

    double divide(double x, double y) {
        return x / y;
    }

    ArrayList<String> pueCalc(double totalFac, double totalIt, double kwCost) {
        ArrayList<String> results = new ArrayList<>(); //Instantiate result Array
        //Do Calcs
        double pue = divide(totalFac, totalIt);
        double dcie = divide(totalIt, totalFac) * 100;
        double totalPower = multiply(totalFac, 8760);
        double totalCost = multiply(multiply(totalFac, 8760), kwCost);
        double emissions = divide(multiply(totalPower, 1.21), 2000);
        //Format Results
        String sPue = String.format(Locale.getDefault(), "%.2f", pue);
        String sDcie = String.format(Locale.getDefault(), "%.2f", dcie);
        String sTotalPower = formatterTwo.format(totalPower);
        String sTotalCost = formatterTwo.format(totalCost);
        String sEmissions = formatterTwo.format(emissions);
        //Set Array
        results.add(sPue);
        results.add(sDcie);
        results.add(sTotalPower);
        results.add(sTotalCost);
        results.add(sEmissions);
        //Return Array
        return results;
    }

    ArrayList<String> effCalc(double curPue, double desPue, double totalIt, double kwCost) {
        ArrayList<String> results = new ArrayList<>(); //Instantiate result Array
        //Do Calcs
        double totalFac = multiply(totalIt, curPue);
        double totalFac2 = multiply(totalIt, desPue);
        double elecTotal = multiply(totalFac, 8760);
        double elecTotal2 = multiply(totalFac2, 8760);
        double moneyTotal = multiply(kwCost, elecTotal);
        double moneyTotal2 = multiply(kwCost, elecTotal2);
        double emissionTotal = divide(multiply(elecTotal, 1.21), 2000);
        double emissionTotal2 = divide(multiply(elecTotal2, 1.21), 2000);
        double carTotal = divide(emissionTotal, 5.30);
        double carTotal2 = divide(emissionTotal2, 5.30);
        double finalElec = subtract(elecTotal, elecTotal2);
        double finalMoney = subtract(moneyTotal, moneyTotal2);
        double finalEmission = subtract(emissionTotal, emissionTotal2);
        double finalCar = subtract(carTotal, carTotal2);
        double elecTotal5yr = multiply(finalElec, 5);
        double elecTotal10yr = multiply(finalElec, 10);
        double moneyTotal5yr = multiply(finalMoney, 5);
        double moneyTotal10yr = multiply(finalMoney, 10);
        double emissionTotal5yr = multiply(finalEmission, 5);
        double emissionTotal10yr = multiply(finalEmission, 10);
        double carTotal5yr = multiply(finalCar, 5);
        double carTotal10yr = multiply(finalCar, 10);
        //Format Results
        String sfinalElec = formatterTwo.format(finalElec);
        String sfinalMoney = formatterTwo.format(finalMoney);
        String sfinalEmission = formatterTwo.format(finalEmission);
        String sfinalCar = formatterTwo.format(finalCar);
        String selecTotal5yr = formatterTwo.format(elecTotal5yr);
        String selecTotal10yr = formatterTwo.format(elecTotal10yr);
        String smoneyTotal5yr = formatterTwo.format(moneyTotal5yr);
        String smoneyTotal10yr = formatterTwo.format(moneyTotal10yr);
        String semissionTotal5yr = formatterTwo.format(emissionTotal5yr);
        String semissionTotal10yr = formatterTwo.format(emissionTotal10yr);
        String scarTotal5yr = formatterTwo.format(carTotal5yr);
        String scarTotal10yr = formatterTwo.format(carTotal10yr);
        //Set Array
        results.add(sfinalElec);
        results.add(sfinalMoney);
        results.add(sfinalEmission);
        results.add(sfinalCar);
        results.add(selecTotal5yr);
        results.add(selecTotal10yr);
        results.add(smoneyTotal5yr);
        results.add(smoneyTotal10yr);
        results.add(semissionTotal5yr);
        results.add(semissionTotal10yr);
        results.add(scarTotal5yr);
        results.add(scarTotal10yr);
        //Return Array
        return results;
    }

    ArrayList<String> thermalCalc(double totalIt, String cvtTo) {
        ArrayList<String> results = new ArrayList<>(); //Instantiate result Array
        //Do Calcs
        if (cvtTo.equals("W")) {
            totalIt = totalIt * 3.412142;
            String sTotalBtu = formatterTwo.format(totalIt);
            results.add(sTotalBtu);
        }
        if (cvtTo.equals("kW")) {
            totalIt = totalIt * 3412.14;
            String sTotalBtu = formatterTwo.format(totalIt);
            results.add(sTotalBtu);
        }
        //Return Results
        return results;
    }

    String awgToMm(int awg) {
        //Do Calcs
        float base = 92;
        double ex = (1.0 * 36 - awg) / 39;
        double res = Math.pow(base, ex);
        double mm = 0.127 * res;
        //Format Results
        //Return Results
        return formatterFour.format(mm);
    }
}
