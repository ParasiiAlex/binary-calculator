package com.example.binarywork;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button nsc;
    private Button codec;
    private Button calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nsc = (Button) findViewById(R.id.convNS);
        codec = (Button) findViewById(R.id.convC);
        calc = (Button) findViewById(R.id.calc);

        nsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNSC();
            }
        });
        codec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCC();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalc();
            }
        });
    }

    public void openNSC(){
        Intent intent = new Intent(this, NSConverter.class);
        startActivity(intent);
    }
    public void openCC(){
        Intent intent = new Intent (this, CConverter.class);
        startActivity(intent);
    }
    public void openCalc(){
        Intent intent = new Intent(this, Calculator.class);
        startActivity(intent);
    }
}


