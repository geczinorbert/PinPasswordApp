package com.example.pinpassword.DTW;

import android.util.Log;

import com.example.pinpassword.Model.Point;

import java.util.ArrayList;
import static com.example.pinpassword.Model.Point.Distance;
import static com.example.pinpassword.DTW.Standard.meanCalc;
import static com.example.pinpassword.DTW.Standard.standardDeviation;

public class DynamicTimeWarping {

    private static final String TAG = "MyTagDynamicTimeWarping";


    public static double compareTwoArrays(ArrayList<ArrayList<Point>> first_array,
                                          ArrayList<ArrayList<Point>> second_array){
        ArrayList<Point> first_array_1_digit;
        ArrayList<Point> first_array_2_digit;
        ArrayList<Point> first_array_3_digit;
        ArrayList<Point> first_array_4_digit;
        ArrayList<Point> second_array_1_digit;
        ArrayList<Point> second_array_2_digit;
        ArrayList<Point> second_array_3_digit;
        ArrayList<Point> second_array_4_digit;
        double score_1_digit;
        double score_2_digit;
        double score_3_digit;
        double score_4_digit;

        first_array_1_digit = first_array.get(0);
        first_array_2_digit = first_array.get(1);
        first_array_3_digit = first_array.get(2);
        first_array_4_digit = first_array.get(3);

        second_array_1_digit = second_array.get(0);
        second_array_2_digit = second_array.get(1);
        second_array_3_digit = second_array.get(2);
        second_array_4_digit = second_array.get(3);

        score_1_digit = DTW_digits(first_array_1_digit,second_array_1_digit);
        score_2_digit = DTW_digits(first_array_2_digit,second_array_2_digit);
        score_3_digit = DTW_digits(first_array_3_digit,second_array_3_digit);
        score_4_digit = DTW_digits(first_array_4_digit,second_array_4_digit);

        return (score_1_digit + score_2_digit + score_3_digit + score_4_digit)/4;
    }

    public static double comparableArrays(ArrayList<ArrayList<Point>> template,
                                          ArrayList<ArrayList<ArrayList<Point>>> sample_array){

        double sum;
        double thresholdLow;
        double thresholdUpper;
        double scoreStandardDeviation;
        double scoreMean;
        int k = 1;
        ArrayList<Double> scoreArray = new ArrayList<>();

        for(int i = 0; i < sample_array.size(); ++i){
            sum = 0;
            for(int j = 0; j < sample_array.size(); ++j){
                if(i != j){
                    sum = sum + compareTwoArrays( sample_array.get(i), sample_array.get(j));
                }
            }
            Log.d( TAG, " Added score before " + sum);
            sum = sum / ( sample_array.size() - 1);
            Log.d( TAG, " Added score after " + sum);
            scoreArray.add(sum);
        }
        scoreStandardDeviation = standardDeviation(scoreArray);
        scoreMean = meanCalc(scoreArray);
        Log.d( TAG, "scoreStandardDeviation " + scoreStandardDeviation + " scoreMean " + scoreMean);

        sum = 0;
        for(ArrayList<ArrayList<Point>> i : sample_array){
            sum = sum + compareTwoArrays(template,i);
            //Log.d( TAG, " size " + i.size() + " sum " + sum);
        }
        sum = sum / sample_array.size();

        Log.d( TAG, "Comparable sum " + sum);

        thresholdLow = scoreMean - k * scoreStandardDeviation;
        thresholdUpper= scoreMean + k * scoreStandardDeviation;
        Log.d( TAG, "Calculate thresholdLow " + thresholdLow + " thresholdUpper " +
                thresholdUpper);
        if(sum > thresholdLow && sum < thresholdUpper)
            return 1;
        else
            return -1;
    }

    public static double DTW_digits(ArrayList<Point> a, ArrayList<Point> b){

        int n = a.size();
        int m = b.size();
        double cost;
        double[][] dtw_array = new double[n][m];

        for (int i = 0; i < n; ++i) {
            dtw_array[i][0] = Double.MAX_VALUE;
        }
        for (int i = 0; i < m; ++i) {
            dtw_array[0][i] = Double.MAX_VALUE;
        }
        dtw_array[0][0] = 0;
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < m; ++j) {
                cost = Distance(a.get(i), b.get(j));
                dtw_array[i][j] = cost + (Math.min(Math.min(dtw_array[i - 1][j], dtw_array[i][j - 1]), dtw_array[i - 1][j - 1]));
            }
        }
        return dtw_array[n - 1][m - 1] / (n + m);
    }

}
