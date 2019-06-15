package com.example.pinpassword.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pinpassword.R;

public class MainActivity extends AppCompatActivity {

    private Button trainingButton;
    private Button verificationButton;
    private static final String TAG = "MyTagMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        final String s = settings.getString("num_of_input_files","0");

        trainingButton = (Button) findViewById(R.id.training_button);
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        verificationButton = (Button) findViewById(R.id.verification_button);
        verificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s.equals("0")){
                    Toast.makeText(getApplicationContext(),"Please train first!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                    MainActivity.this.startActivity(intent);
                }

            }
        });
    }
}
