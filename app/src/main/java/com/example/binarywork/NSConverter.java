package com.example.binarywork;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class NSConverter extends AppCompatActivity {

    private Button cc;
    private Button calc;
    private Button rs;
    private TextView line1, line2;
    private Button bin, oct, hex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsconverter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cc = findViewById(R.id.conv);
        rs = findViewById(R.id.reset);
        calc = findViewById(R.id.calc);

        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCC();
            }
        });
        rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RS();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalc();
            }
        });

        init();
    }

    private void init() {
        bin = findViewById(R.id.bin);
        oct = findViewById(R.id.oct);
        hex = findViewById(R.id.hex);
        line1 = (EditText) findViewById(R.id.input);
        line2 = findViewById(R.id.output);

        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binConv(line1.getText().toString());
            }
        });
        oct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                octConv(line1.getText().toString());
            }
        });
        hex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hexConv(line1.getText().toString());
            }
        });
    }

    public void RS(){
        line1.setText(null);
        line2.setText(null);
    }
    public void openCC(){
        Intent intent = new Intent (this, CConverter.class);
        finish();
        startActivity(intent);
    }
    public void openCalc(){
        Intent intent = new Intent(this, Calculator.class);
        finish();
        startActivity(intent);
    }

    private void binConv(String s){

        float N;        //input number
        long decimal;    //decimal part of number
        double fraction; //fraction part of number

        N = Float.parseFloat(s);
        decimal = (int) N;
        DecimalFormat df = new DecimalFormat("#.#########");
        fraction = N - decimal;
        fraction = Double.parseDouble(df.format(fraction));

        // arrays to store binary numbers
        long [] binaryNumDecimal = new long [1000];
        int [] binaryNumFraction = new int [20];

        // counters for binary array
        int i = 0,j = 0;
        if (N > 8100 && fraction > 0)
            line2.setText("you've entered a very large floating number...");
        else {
            if (N > 0 && N < 999999999) {
                while (decimal > 0) {
                    // storing remainder in binary array
                    binaryNumDecimal[i] = decimal % 2;
                    decimal = decimal / 2;
                    i++;
                }

                //se determina bitii partii fractionare in urma inmultirii la 2
                //numarul are o aproximatie de 12 biti
                while (j < 13 && fraction > 0) {

                    //get integer part of the number
                    int integerPart = (int) fraction;

                    switch (integerPart) {
                        case 0:
                            binaryNumFraction[j] = 0;
                            break;
                        case 1:
                            binaryNumFraction[j] = 1;
                            fraction -= 1;
                            break;
                    }

                    fraction *= 2;
                    j++;
                }


                //variable to store the result
                StringBuilder text = new StringBuilder();

                // printing binary array in reverse order
                for (int k = i - 1; k >= 0; k--)
                    text.append(binaryNumDecimal[k]);

                //printing fraction part o number
                if (fraction > 0) {
                    text.append('.');
                    for (int k = 1; k < 13; k++)
                        text.append(binaryNumFraction[k]);
                }
                line2.setText(text);
            } else
                //set text null if the input number is 0
                if (N == 0) {
                    line2.setText("0");
                } else if (N > 999999999) {
                    line2.setText("you've entered a very large number...");
                }
        }



    }
    private void octConv(String s){

        float N;        //input number
        int decimal;    //decimal part of number
        double  fraction; //fraction part of number

        N = Float.parseFloat(s);
        decimal = (int) N;
        fraction = N % 1;
        DecimalFormat df = new DecimalFormat("#.#########");
        fraction = Double.parseDouble(df.format(fraction));

        // arrays to store binary numbers
        int [] binaryNumDecimal = new int [1000];
        int [] binaryNumFraction = new int [20];

        // counters for binary array
        int i = 0,j = 0;
        if (N > 8100 && fraction > 0)
            line2.setText("you've entered a very large floating number...");
        else {
            if (N > 0 && N < 999999999) {

                while (decimal > 0) {
                    // storing remainder in binary array
                    binaryNumDecimal[i] = decimal % 8;
                    decimal = decimal / 8;
                    i++;
                }

                //se determina bitii in urma inmultirii restului la 8
                //numarul are o aproximatie de 12 biti
                while (j < 13 && fraction > 0) {

                    int integerPart = (int) fraction;

                    switch (integerPart) {
                        case 0:
                            binaryNumFraction[j] = 0;
                            break;
                        case 1:
                            binaryNumFraction[j] = 1;
                            fraction -= 1;
                            break;
                        case 2:
                            binaryNumFraction[j] = 2;
                            fraction -= 2;
                            break;
                        case 3:
                            binaryNumFraction[j] = 3;
                            fraction -= 3;
                            break;
                        case 4:
                            binaryNumFraction[j] = 4;
                            fraction -= 4;
                            break;
                        case 5:
                            binaryNumFraction[j] = 5;
                            fraction -= 5;
                            break;
                        case 6:
                            binaryNumFraction[j] = 6;
                            fraction -= 6;
                            break;
                        case 7:
                            binaryNumFraction[j] = 7;
                            fraction -= 7;
                            break;
                    }

                    fraction *= 8;
                    j++;
                }
                //variable to store the result
                StringBuilder text= new StringBuilder();

                // printing binary array in reverse order
                for (int k = i - 1; k >= 0; k--)
                    text.append(binaryNumDecimal[k]);

                //printing fraction part o number
                if (fraction > 0 ){
                    text.append('.');
                    for (int k = 1; k < 13; k++)
                        text.append(binaryNumFraction[k]);
                }

                line2.setText(text);
            } else
                //set text null if the input number is 0
                if (N == 0) {
                    line2.setText("0");
                }
            else if (N > 999999999) {
                    line2.setText("you've entered a very large number...");
                }
        }
    }
    private void hexConv(String s) {

        float N;        //input number
        int decimal;    //decimal part of number
        double fraction; //fraction part of number

        N = Float.parseFloat(s);
        DecimalFormat df = new DecimalFormat("##.########");
        decimal = (int) N;
        fraction = N % 1;
        fraction = Double.parseDouble(df.format(fraction));


        // arrays to store binary numbers
        char[] binaryNumDecimal = new char[1000];
        char[] binaryNumFraction = new char[20];

        // counters for binary array
        int i = 0, j = 0;
        if (N > 8100 && fraction != 0)
            line2.setText("you've entered a very large floating number...");
        else {
            if (N > 0 && N < 999999999) {
                while (decimal > 0) {
                    // storing remainder in binary array
                    int remainder = decimal % 16;

                    switch (remainder) {
                        case 0:
                            binaryNumDecimal[i] = '0';
                            break;
                        case 1:
                            binaryNumDecimal[i] = '1';
                            break;
                        case 2:
                            binaryNumDecimal[i] = '2';
                            break;
                        case 3:
                            binaryNumDecimal[i] = '3';
                            break;
                        case 4:
                            binaryNumDecimal[i] = '4';
                            break;
                        case 5:
                            binaryNumDecimal[i] = '5';
                            break;
                        case 6:
                            binaryNumDecimal[i] = '6';
                            break;
                        case 7:
                            binaryNumDecimal[i] = '7';
                            break;
                        case 8:
                            binaryNumDecimal[i] = '8';
                            break;
                        case 9:
                            binaryNumDecimal[i] = '9';
                            break;
                        case 10:
                            binaryNumDecimal[i] = 'A';
                            break;
                        case 11:
                            binaryNumDecimal[i] = 'B';
                            break;
                        case 12:
                            binaryNumDecimal[i] = 'C';
                            break;
                        case 13:
                            binaryNumDecimal[i] = 'D';
                            break;
                        case 14:
                            binaryNumDecimal[i] = 'E';
                            break;
                        case 15:
                            binaryNumDecimal[i] = 'F';
                            break;
                    }

                    decimal = decimal / 16;
                    i++;
                }

                //se determina bitii in urma inmultirii restului la 8
                //numarul are o aproximatie de 10 biti
                while (j < 11 && fraction > 0) {

                    int integerPart = (int) fraction;

                    switch (integerPart) {
                        case 0:
                            binaryNumFraction[j] = '0';
                            break;
                        case 1:
                            binaryNumFraction[j] = '1';
                            fraction -= 1;
                            break;
                        case 2:
                            binaryNumFraction[j] = '2';
                            fraction -= 2;
                            break;
                        case 3:
                            binaryNumFraction[j] = '3';
                            fraction -= 3;
                            break;
                        case 4:
                            binaryNumFraction[j] = '4';
                            fraction -= 4;
                            break;
                        case 5:
                            binaryNumFraction[j] = '5';
                            fraction -= 5;
                            break;
                        case 6:
                            binaryNumFraction[j] = '6';
                            fraction -= 6;
                            break;
                        case 7:
                            binaryNumFraction[j] = '7';
                            fraction -= 7;
                            break;
                        case 8:
                            binaryNumFraction[j] = '8';
                            fraction -= 8;
                            break;
                        case 9:
                            binaryNumFraction[j] = '9';
                            fraction -= 9;
                            break;
                        case 10:
                            binaryNumFraction[j] = 'A';
                            fraction -= 10;
                            break;
                        case 11:
                            binaryNumFraction[j] = 'B';
                            fraction -= 11;
                            break;
                        case 12:
                            binaryNumFraction[j] = 'C';
                            fraction -= 12;
                            break;
                        case 13:
                            binaryNumFraction[j] = 'D';
                            fraction -= 13;
                            break;
                        case 14:
                            binaryNumFraction[j] = 'E';
                            fraction -= 14;
                            break;
                        case 15:
                            binaryNumFraction[j] = 'F';
                            fraction -= 15;
                            break;
                    }

                    fraction *= 16;
                    j++;
                }

                //variable to store the result
                StringBuilder text = new StringBuilder();

                // printing binary array in reverse order
                for (int k = i - 1; k >= 0; k--)
                    text.append(binaryNumDecimal[k]);

                //printing fraction part o number
                if (fraction > 0) {
                    text.append('.');
                    for (int k = 1; k < 11; k++)
                        text.append(binaryNumFraction[k]);
                }

                line2.setText(text);

            } else
                //set text null if the input number is 0
                if (N == 0) {
                    line2.setText("0");
                } else if (N > 999999999) {
                    line2.setText("you've entered a very large number...");
                }
        }
    }

}
