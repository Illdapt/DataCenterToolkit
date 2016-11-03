package com.nstuttle.datacentertoolkit;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by nicktuttle on 8/3/2016.
 */
public class Calculator {

    public double add(double x, double y) {
        double sum = x + y;
        return sum;
    }

    public double subtract(double x, double y) {
        double diff = x - y;
        return diff;
    }

    public double multiply(double x, double y) {
        double res = x * y;
        return res;
    }

    public double divide(double x, double y) {
        double res = x / y;
        return res;
    }

    public ArrayList<Double> pueCalc(double totalFac, double totalIt) {
        ArrayList<Double> results = new ArrayList<Double>();
        double pue = totalFac / totalIt;
        results.add(pue);
        double dcie = (totalIt / totalFac) * 100;
        results.add(dcie);
        return results;
    }

    public ArrayList<String> effCalc(double curPue, double desPue, double totalIt, double kwCost) {
        ArrayList<String> results = new ArrayList<String>();
        double totalFac = totalIt * curPue;
        double totalFac2 = totalIt * desPue;
        double elecTotal = totalFac * 8760;
        double elecTotal2 = totalFac2 * 8760;
        double moneyTotal = kwCost * elecTotal;
        double moneyTotal2 = kwCost * elecTotal2;
        double emissionTotal = (elecTotal * 1.21) / 2000;
        double emissionTotal2 = (elecTotal2 * 1.21) / 2000;
        double carTotal = emissionTotal / 5.30;
        double carTotal2 = emissionTotal2 / 5.30;

        double finalElec = elecTotal - elecTotal2;
        String sfinalElec = String.format(Locale.getDefault(), "%.2f", finalElec);
        results.add(sfinalElec);
        double finalMoney = moneyTotal - moneyTotal2;
        String sfinalMoney = String.format(Locale.getDefault(), "%.2f", finalMoney);
        results.add(sfinalMoney);
        double finalEmission = emissionTotal - emissionTotal2;
        String sfinalEmission = String.format(Locale.getDefault(), "%.2f", finalEmission);
        results.add(sfinalEmission);
        double finalCar = carTotal - carTotal2;
        String sfinalCar = String.format(Locale.getDefault(), "%.2f", finalCar);
        results.add(sfinalCar);
        double elecTotal5yr = finalElec * 5;
        String selecTotal5yr = String.format(Locale.getDefault(), "%.2f", elecTotal5yr);
        results.add(selecTotal5yr);
        double elecTotal10yr = finalElec * 10;
        String selecTotal10yr = String.format(Locale.getDefault(), "%.2f", elecTotal10yr);
        results.add(selecTotal10yr);
        double moneyTotal5yr = finalMoney * 5;
        String smoneyTotal5yr = String.format(Locale.getDefault(), "%.2f", moneyTotal5yr);
        results.add(smoneyTotal5yr);
        double moneyTotal10yr = finalMoney * 10;
        String smoneyTotal10yr = String.format(Locale.getDefault(), "%.2f", moneyTotal10yr);
        results.add(smoneyTotal10yr);
        double emissionTotal5yr = finalEmission * 5;
        String semissionTotal5yr = String.format(Locale.getDefault(), "%.2f", emissionTotal5yr);
        results.add(semissionTotal5yr);
        double emissionTotal10yr = finalEmission * 10;
        String semissionTotal10yr = String.format(Locale.getDefault(), "%.2f", emissionTotal10yr);
        results.add(semissionTotal10yr);
        double carTotal5yr = finalCar * 5;
        String scarTotal5yr = String.format(Locale.getDefault(), "%.2f", carTotal5yr);
        results.add(scarTotal5yr);
        double carTotal10yr = finalCar * 10;
        String scarTotal10yr = String.format(Locale.getDefault(), "%.2f", carTotal10yr);
        results.add(scarTotal10yr);

        return results;
    }

}
