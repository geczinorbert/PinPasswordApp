package com.example.pinpassword.DTW;

import java.util.ArrayList;

public class Standard {
    private static final String TAG = "MyTagStandard";

    public static double  meanCalc(ArrayList<Double> arrayList){
        double mean = 0;
        for(int i = 0; i < arrayList.size(); ++i){
            mean = mean + arrayList.get(i);
        }
        return mean/arrayList.size();
    }

    public static double standardDeviation(ArrayList<Double> arrayList){
        double sum = 0;
        double mean = meanCalc(arrayList);
        for(int i = 0; i < arrayList.size(); ++i){
            sum = sum + Math.pow((arrayList.get(i) - mean),2);
        }
        return Math.sqrt(sum/arrayList.size());
    }
}
