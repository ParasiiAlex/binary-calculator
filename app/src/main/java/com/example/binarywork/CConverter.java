package com.example.binarywork;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Math.abs;

public class CConverter extends AppCompatActivity {

    private Button nsc;
    private Button calc;
    private Button rs;
    private Button conv;
    private TextView input;
    private TextView DC, IC, CC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cconverter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nsc = findViewById(R.id.conv);
        conv = findViewById(R.id.convert);
        calc = findViewById(R.id.calc);
        rs = findViewById(R.id.reset);

        nsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNSC();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalc();
            }
        });
        rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(null);
                DC.setText(null);
                IC.setText(null);
                CC.setText(null);
            }
        });

        activity();
    }

    private void activity(){
        DC = findViewById(R.id.directCode);
        IC = findViewById(R.id.inversedCode);
        CC = findViewById(R.id.complementaryCode);
        input = findViewById(R.id.input);

        conv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert(input.getText().toString());
            }
        });
    }

    public void convert(String num){
        int n; //input number
        int pozitive;   //absolute value of input

        StringBuilder output1 = new StringBuilder(); //the output for DC
        StringBuilder output2 = new StringBuilder(); //the output for IC
        StringBuilder output3 = new StringBuilder(); //the output for CC

        if (num != ""){
            n = Integer.parseInt(num);

            pozitive = abs(n);

            // array to store binary number
            int[] binaryNum = new int[1000];

            // counter for binary array
            int i = 0;

            while (pozitive > 0)
            {
                // storing remainder in binary array
                binaryNum[i] = pozitive % 2;
                pozitive /= 2;
                i++;
            }

            if (n > 0){
                output1.append("0.");

                // printing binary array in reverse order
                for (int j = i - 1; j >= 0; j--)
                    output1.append(binaryNum[j]);

                //numbers are similar if the input is positive
                output2 = output1;
                output3 = output1;
            }

            if (n < 0){
                output1.append("1.");
                output2.append("1.");
                output3.append("1.");

                // printing binary array in reverse order
                for (int j = i - 1; j >= 0; j--){
                    output1.append(binaryNum[j]);
                    //inverse bites in array
                    if (binaryNum[j] == 0) binaryNum[j] = 1; else binaryNum[j] = 0;
                    output2.append(binaryNum[j]);
                }

                binaryNum[0]++; //add 1 to the last bit
                //check the array if there are no 2
                for (int j = 0; j < i; j++){
                    if (binaryNum[j] == 2) {
                        binaryNum[j+1] += 1;
                        binaryNum[j] = 0;
                    }
                }
                //print the array in reverse order with complementary code
                for (int j = i - 1; j >= 0; j--) {
                    output3.append(binaryNum[j]);
                }
            }
            //get output
            DC.setText(output1);
            IC.setText(output2);
            CC.setText(output3);
        } else DC.setText("enter a number");

    }
    public void openNSC(){
        Intent intent = new Intent (this, NSConverter.class);
        finish();
        startActivity(intent);
    }
    public void openCalc(){
        Intent intent = new Intent(this, Calculator.class);
        finish();
        startActivity(intent);
    }
}
