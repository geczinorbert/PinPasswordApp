package com.example.pinpassword.Activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pinpassword.Fragment.DigitFragment;
import com.example.pinpassword.Model.Point;
import com.example.pinpassword.R;

import java.util.ArrayList;

import static com.example.pinpassword.DTW.DynamicTimeWarping.comparableArrays;
import static com.example.pinpassword.DataManipulation.FileOperation.getArrayOfSamples;

public class VerificationActivity extends AppCompatActivity {
    private Button clearBotton;
    private int sample_number = 0;
    private Button compareBotton;
    private static final String TAG = "MyTagVerificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        final String s = settings.getString("num_of_input_files","0");
        final int num_of_input_files;
        if(s.equals("0")){
            num_of_input_files = 0;
        }
        else {
            num_of_input_files = Integer.parseInt(s);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final DigitFragment fragment1 = new DigitFragment();
        final DigitFragment fragment2 = new DigitFragment();
        final DigitFragment fragment3 = new DigitFragment();
        final DigitFragment fragment4 = new DigitFragment();

        fragmentTransaction.add(R.id.fragment_placeholder1, fragment1);
        fragmentTransaction.add(R.id.fragment_placeholder2, fragment2);
        fragmentTransaction.add(R.id.fragment_placeholder3, fragment3);
        fragmentTransaction.add(R.id.fragment_placeholder4, fragment4);

        fragmentTransaction.commit();

        clearBotton = (Button) findViewById(R.id.button_clear);
        clearBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " Cleared!", Toast.LENGTH_SHORT).show();
                fragment1.clearData();
                fragment2.clearData();
                fragment3.clearData();
                fragment4.clearData();

            }
        });


        compareBotton = (Button) findViewById(R.id.button_cmp);
        compareBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the current one

                if(fragment1.getData().size() > 5 && fragment2.getData().size() > 5 &&
                        fragment3.getData().size() > 5 && fragment4.getData().size() > 5)
                {
                    ArrayList<Point> digit_1 = fragment1.getData();
                    ArrayList<Point> digit_2 = fragment2.getData();
                    ArrayList<Point> digit_3 = fragment3.getData();
                    ArrayList<Point> digit_4 = fragment4.getData();
                    ArrayList<ArrayList<Point>> templateArraylist = new ArrayList<>();
                    templateArraylist.add(digit_1);
                    templateArraylist.add(digit_2);
                    templateArraylist.add(digit_3);
                    templateArraylist.add(digit_4);
                    double sum = comparableArrays(templateArraylist,getArrayOfSamples(getApplicationContext(),num_of_input_files));
                    Log.d("mytag", "comparableArrays  " + sum);
                    if(sum == 1){
                        Toast.makeText(getApplicationContext(), "Correct ", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();

                    }

                    fragment1.clearData();
                    fragment2.clearData();
                    fragment3.clearData();
                    fragment4.clearData();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please draw in every cell!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}

