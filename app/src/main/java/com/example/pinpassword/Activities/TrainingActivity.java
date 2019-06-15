package com.example.pinpassword.Activities;

import android.content.Intent;
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
import com.example.pinpassword.R;

import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.pinpassword.DataManipulation.FileOperation.pointsToString;

public class TrainingActivity extends AppCompatActivity {


    private Button saveBotton;
    private Button clearBotton;
    private int sample_number = 0;
    private Button homeBotton;
    private static final String TAG = "MyTagTrainingActivity";
    private static final int MINFILENUMBER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

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

        saveBotton = (Button) findViewById(R.id.button_save);
        saveBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fragment1.getData().size() > 5 && fragment2.getData().size() > 5 &&
                        fragment3.getData().size() > 5 && fragment4.getData().size() > 5)
                {
                    sample_number++;
                    String file_1_content = pointsToString(fragment1.getData(), sample_number, 1);
                    String file_2_content = pointsToString(fragment2.getData(), sample_number, 2);
                    String file_3_content = pointsToString(fragment3.getData(), sample_number, 3);
                    String file_4_content = pointsToString(fragment4.getData(), sample_number, 4);

                    try {
                        FileOutputStream file;
                        String file_name = "my_files" + sample_number + ".csv";
                        Log.d(TAG, "file_name " + file_name);
                        file = openFileOutput(file_name, getApplicationContext().MODE_PRIVATE);
                        file.write(file_1_content.getBytes());
                        file.write(file_2_content.getBytes());
                        file.write(file_3_content.getBytes());
                        file.write(file_4_content.getBytes());

                        Log.d(TAG, "wrote " + file_1_content);
                        file.close();
                    } catch (IOException e) {
                        Log.d(TAG, "Error " + e.toString());

                    }

                    //clear after insert
                    fragment1.clearData();
                    fragment2.clearData();
                    fragment3.clearData();
                    fragment4.clearData();
                    Toast.makeText(getApplicationContext(),"Saved!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please draw in every cell!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearBotton = (Button) findViewById(R.id.button_clear);
        clearBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cleared!", Toast.LENGTH_SHORT).show();
                fragment1.clearData();
                fragment2.clearData();
                fragment3.clearData();
                fragment4.clearData();

            }
        });


        homeBotton = (Button) findViewById(R.id.button_cmp);
        homeBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sample_number >= MINFILENUMBER)
                {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("num_of_input_files",Integer.toString(sample_number));
                    editor.apply();

                    Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
                    TrainingActivity.this.startActivity(intent);

                }
                else {
                    int number = (MINFILENUMBER - sample_number);
                    Toast.makeText(getApplicationContext(),"Please insert " +  number + " more drawings!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
