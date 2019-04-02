package com.example.binarywork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Calculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String operator;
    private Button nsc;
    private Button cc;
    private Button rs;
    private Button calc;
    private EditText output;
    private EditText line1, line2;
    private EditText sign1, sign2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operator, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        cc = findViewById(R.id.conv2);
        rs = findViewById(R.id.reset);
        nsc = findViewById(R.id.conv);
        calc = findViewById(R.id.button);

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
        nsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNSC();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void calculate(){
        line1 = findViewById(R.id.num1);
        line2 = findViewById(R.id.num2);
        sign1 = findViewById(R.id.sign1);
        sign2 = findViewById(R.id.sign2);

        output = findViewById(R.id.res);

        String num1 = line1.getText().toString();
        String num2 = line2.getText().toString();
        String sq1 = sign1.getText().toString();
        String sq2 = sign2.getText().toString();
        boolean err = false;    //daca se intalneste o eroare, algoritmul se opreste

        if (num2.isEmpty()) {output.setText("Enter second operand..."); err = true;}
        if (sq2.isEmpty()) {output.setText("Error at second sign..."); err = true;}
        if (num1.isEmpty()) {output.setText("Enter first operand..."); err = true;}
        if (sq1.isEmpty()) {output.setText("Error at first sign..."); err = true;}

        if (!err){
            switch (operator){
                case "+ (addition)" : output.setText(addition(num1, num2));
                    break;
                case "- (subtraction)" : output.setText(subtracion(num1, num2));
                    break;
                case "* (multiplication)" : output.setText(multiplication(num1, num2));
                    break;
            case "/ (division)" : output.setText(division(num1, num2));
            }
        }

    }

    private StringBuilder addition(String st1, String st2) {
        StringBuilder result = new StringBuilder();

        String sq1, sq2, sqResult = "0";  //semnul rezultatului
        String nst="";  //solutia
        char t='0', transportFromFirstBit='0', transportFromSign='0'; //transport

        sq1 = sign1.getText().toString();
        sq2 = sign2.getText().toString();


            //se egaleaza numarul de biti al operanzilor
            int l1 = st1.length(), l2 = st2.length();
            if (l1 < l2)
                for (int a = 1; a <= l2 - l1; a++)
                    st1 = "0" + st1;
            else if (l2 < l1)
                for (int a = 1; a <= l1 - l2; a++)
                    st2 = "0" + st2;

            l1 = st1.length();
            for (int a = l1 - 1; a >= 0; a--) {
                if (st1.charAt(a) == '1' && st2.charAt(a) == '0') {
                    if (t == '1') {
                        nst = '0' + nst;
                        t = '1';
                    } else
                        nst = '1' + nst;
                } else if (st1.charAt(a) == st2.charAt(a) && st2.charAt(a) == '1') {
                    if (t == '1') {
                        nst = '1' + nst;
                        t = '1';
                    } else {
                        nst = '0' + nst;
                        t = '1';
                    }

                } else if (st1.charAt(a) == '0' && st2.charAt(a) == '1') {
                    if (t == '1') {
                        nst = '0' + nst;
                        t = '1';
                    }
                    else {
                        nst = '1' + nst;
                        t = '0';
                    }
                } else {
                    if (t == '1') {
                        nst = '1' + nst;
                        t = '0';
                    } else
                        nst = '0' + nst;
                }


                //gasim transportul din bitul cel mai semnificativ
                //daca este transport, il adaugam la bitul semnului
                if (a == 0 && t == '1')
                    transportFromFirstBit = '1';


                //determinam semnul solutiei
                if (sq1.equals(sq2))
                    if (sq1.equals("1") && transportFromFirstBit == '1'){
                        sqResult =  "1";
                        transportFromSign = '1';
                    } else
                        if (sq1.equals("0") && transportFromFirstBit == '1'){
                        sqResult = "1";
                        transportFromSign = '0';
                    } else {
                            sqResult = "0";
                            if (sq1.equals("1")) transportFromSign = '1'; else
                                transportFromSign = '0';
                        }
                if (!sq1.equals(sq2))
                    if (transportFromFirstBit == '1') {
                            sqResult = "0";
                            transportFromSign = '1';
                    } else
                        if (transportFromFirstBit == '0'){
                                sqResult = "1";
                                transportFromSign = '0';
                        }
            }

            //verificam daca este depasire
            if (transportFromFirstBit == '1' && transportFromSign =='0') {
                nst = sqResult + nst;
                sqResult = "0";
            }
                else
                    if (transportFromFirstBit =='0' && transportFromSign == '1') {
                        nst = sqResult + nst;
                        sqResult = "1";
                    }

            result.append(sqResult);
            result.append('.');
            result.append(nst);

        return result;
    }

    private StringBuilder subtracion(String st1, String st2) {
        StringBuilder result = new StringBuilder();
        StringBuilder string2 = new StringBuilder();    //operandul 2 inversat.

        String sq1, sq2, sqResult = "0";  //semnul rezultatului
        String nst="";      //solutia
        char t='0', transportFromFirstBit='0', transportFromSign='0'; //transport

        sq1 = sign1.getText().toString();
        sq2 = sign2.getText().toString();


            //se egaleaza numarul de biti al operanzilor
            int l1 = st1.length(), l2 = st2.length();
            if (l1 < l2)
                for (int a = 1; a <= l2 - l1; a++)
                    st1 = "0" + st1;
            else if (l2 < l1)
                for (int a = 1; a <= l1 - l2; a++)
                    st2 = "0" + st2;


            //inversam operandul 2 cu semn
            if (sq2.equals("0")) sq2 = "1"; else sq2 = "0";
            string2.append(st2);
            string2 = inverse(st2.length(), string2);

            //adunarea propriu-zisa
            l1 = st1.length();
            for (int a = l1 - 1; a >= 0; a--) {
                if (st1.charAt(a) == '1' && string2.charAt(a) == '0') {
                    if (t == '1') {
                        nst = '0' + nst;
                        t = '1';
                    } else
                        nst = '1' + nst;
                } else if (st1.charAt(a) == string2.charAt(a) && string2.charAt(a) == '1') {
                    if (t == '1') {
                        nst = '1' + nst;
                        t = '1';
                    } else {
                        nst = '0' + nst;
                        t = '1';
                    }

                } else if (st1.charAt(a) == '0' && string2.charAt(a) == '1') {
                    if (t == '1') {
                        nst = '0' + nst;
                        t = '1';
                    }
                    else {
                        nst = '1' + nst;
                        t = '0';
                    }
                } else {
                    if (t == '1') {
                        nst = '1' + nst;
                        t = '0';
                    } else
                        nst = '0' + nst;
                }


                //gasim transportul din bitul cel mai semnificativ
                //daca este transport, il adaugam la bitul semnului
                if (a == 0 && t == '1')
                    transportFromFirstBit = '1';


                //determinam semnul solutiei
                if (sq1.equals(sq2))
                    if (sq1.equals("1") && transportFromFirstBit == '1'){
                        sqResult =  "1";
                        transportFromSign = '1';
                    } else
                    if (sq1.equals("0") && transportFromFirstBit == '1'){
                        sqResult = "1";
                        transportFromSign = '0';
                    } else {
                        sqResult = "0";
                        if (sq1.equals("1")) transportFromSign = '1'; else
                            transportFromSign = '0';
                    }
                if (!sq1.equals(sq2))
                    if (transportFromFirstBit == '1') {
                        sqResult = "0";
                        transportFromSign = '1';
                    } else
                    if (transportFromFirstBit == '0'){
                        sqResult = "1";
                        transportFromSign = '0';
                    }
            }

            //verificam daca este depasire
            if (transportFromFirstBit == '1' && transportFromSign =='0') {
                nst = sqResult + nst;
                sqResult = "0";
            }
                else
                    if (transportFromFirstBit =='0' && transportFromSign == '1') {
                        nst = sqResult + nst;
                        sqResult = "1";
                    }

            result.append(sqResult);
            result.append('.');
            result.append(nst);

        return result;
    }

    private StringBuilder multiplication(String st1, String st2) {

        StringBuilder result = new StringBuilder();
        StringBuilder sum = new StringBuilder();        //suma intermediara
        StringBuilder string1 = new StringBuilder(); //operandul 1 pentru sumarea succesiva
        StringBuilder string2 = new StringBuilder(); //operandul 2
        StringBuilder absSt2 = new StringBuilder();     //valoarea absoluta a operandului 2
        StringBuilder absSt1 = new StringBuilder();     //valoarea absoluta a operandului 1

        String sq1, sq2, sqResult;  //semnul rezultatului
        int numarBitiSolutie;


        sq1 = sign1.getText().toString();
        sq2 = sign2.getText().toString();


        string1.append(st1);
        string2.append(st2);
            //gasim valoarea absoluta a operandului 1
            if (sq1.equals("0")) {
                absSt1.append(string1);
            } else{
                absSt1.append(inverse(string1.length(), string1));
            }
            //gasim valoarea absoluta a operandului 2
            if (sq2.equals("0")) {
                absSt2.append(string2);
            } else{
                absSt2.append(inverse(string2.length(), string2));
            }

        //se egaleaza numarul de biti al operanzilor
        int l1 = absSt1.length(), l2 = absSt2.length();
        if (l1 < l2)
            for (int a = 1; a <= l2 - l1; a++)
                absSt1.insert(0, '0');
        else if (l2 < l1)
            for (int a = 1; a <= l1 - l2; a++)
                absSt2.insert(0, '0');


        l2 = absSt2.length();
        sum.append("0");

            for (int i = l2 - 1; i >= 0; i--){
                if (absSt2.charAt(i) == '1') sum = sum2Numbers(sum, absSt1);
                absSt1.append('0');
            }

            //verificam daca numarul de biti al solutiei este egal cu dublul numarului de biti al unui operand
            numarBitiSolutie = string1.length() * 2;
                //adaugam zerouri daca este necesar
                for (int i = numarBitiSolutie - sum.length(); i > 0; i--)
                    sum.insert(0, '0');

            //analizam semnul solutiei
            if (sq1.equals(sq2)) sqResult = "0"; else {
                sqResult = "1";
                sum = inverse(sum.length(), sum);
            }


            result.append(sqResult);
            result.append('.');
            result.append(sum);

        return result;
    }

    private StringBuilder division(String st1, String st2) {

        StringBuilder result = new StringBuilder();
        StringBuilder sum = new StringBuilder();    //suma intermediara
        StringBuilder modSt1 = new StringBuilder(); //valoarea absoluta pentru operandul 1
        StringBuilder string1 = new StringBuilder(st1);
        StringBuilder string2 = new StringBuilder(st2);
        StringBuilder modSt2 = new StringBuilder(); //valoarea absoluta pentru operandul 2
        StringBuilder negSt2 = new StringBuilder(); //valoarea negativa pentru operandul 2

        String sq1 = sign1.getText().toString();
        String sq2 = sign2.getText().toString();
        String sqResult;
        boolean zero = true;   //impartirea la zero
        StringBuilder textError = new StringBuilder();

        //analizam semnul solutiei
        if (sq1.equals(sq2)) sqResult = "0"; else sqResult = "1";

        //se egaleaza numarul de biti al operanzilor
                int l1 = st1.length(), l2 = st2.length();
                for (int i = 0; i < l2; i++)
                    if (st2.charAt(i) == '1') zero = false;
                if (zero) return textError.append("division by zero");
                if (l1 < l2){
                    if (sq1.equals("0"))
                        for (int a = 1; a <= l2 - l1; a++)
                            string1.insert(0, '0');
                    else for (int a = 1; a <= l2 - l1; a++)
                        string1.insert(0, '1');

                } else
                    if (l2 < l1){
                        if (sq2.equals("0"))
                            for (int a = 1; a <= l1 - l2; a++)
                                string2.insert(0, '0');
                        else for (int a = 1; a <= l1 - l2; a++)
                                string2.insert(0, '1');
                    }

        //initializam variabilele de lucru
            //gasim valoarea absoluta a operandului 1
            if (sq1.equals("0")) {
                modSt1.append("0");
                modSt1.append(string1);
            } else{
                modSt1.append("0");
                modSt1.append(inverse(string1.length(), string1));
            }

            //gasim valoarea absoluta a operandului 2
            if (sq2.equals("0")) {
                modSt2.append("0");
                modSt2.append(string2);
            } else{
                modSt2.append("0");
                modSt2.append(inverse(string2.length(), string2));
            }

            //gasim valoarea negativa a operandului 2
            if (sq2.equals("1")) {
                negSt2.append("1");
                negSt2.append(string2);
            } else{
                negSt2.append("1");
                negSt2.append(inverse(string2.length(), string2));
            }

        //verificam ca operandul 1 sa fie mai mic ca operandul 2
        for (int i = 0; i < modSt1.length(); i++){
                if (modSt1.charAt(i) == '1' && modSt2.charAt(i) == '0')
                {
                    return result.append("abs. val. of dividend >= divisor's");
                }
                else if (modSt1.charAt(i) == '0' && modSt2.charAt(i) == '1') break;
        }

        //succesiune de adunari conform algoritmului
            sum.append(sum2Numbers(modSt1, negSt2));
            for (int i = 0; i < 8; i++){
                if (sum.charAt(0) == '1') sum = sum2Numbers(shiftLeft(1, sum), modSt2);
                    else sum = sum2Numbers(shiftLeft(1, sum), negSt2);

                //adaugam cate un bit al solutiei
                 if (sum.charAt(0) == '1') result.append('0'); else result.append('1');
            }

        if (sqResult.equals("0")) result.insert(0, "0.");
            else{
                result = inverse(result.length(), result);
                result.insert(0, "1.");
        }

        result.append("...");
        return result;
    }

    private StringBuilder sum2Numbers(StringBuilder st1, StringBuilder st2) {

        StringBuilder result = new StringBuilder();
        char t = '0';           //transport
        String nst = "";        //solutia

        //se egaleaza numarul de biti al operanzilor
        int l1 = st1.length(), l2 = st2.length();
        if (l1 < l2)
            for (int a = 1; a <= l2 - l1; a++)
                st1.insert(0,'0');
        else if (l2 < l1)
            for (int a = 1; a <= l1 - l2; a++)
                st2.insert(0,'0');


        l1 = st1.length();
        for (int a = l1 - 1; a >= 0; a--) {
            if (st1.charAt(a) == '1' && st2.charAt(a) == '0') {
                if (t == '1'){
                    nst = '0' + nst;
                    t = '1';
                } else
                    nst = '1' + nst;
            } else if (st1.charAt(a) == st2.charAt(a) && st2.charAt(a) == '1') {
                if (t == '1') {
                    nst = '1' + nst;
                    t = '1';
                } else {
                    nst = '0' + nst;
                    t = '1';
                }
            } else if (st1.charAt(a) == '0' && st2.charAt(a) == '1') {
                if (t == '1') {
                    nst = '0' + nst;
                    t = '1';
                }
                else {
                    nst = '1' + nst;
                    t = '0';
                }
            } else {
                if (t == '1') {
                    nst = '1' + nst;
                    t = '0';
                } else
                    nst = '0' + nst;
            }
        }

        //verificam la depasire
        if (t == '1') nst = '1' + nst;
        result.append(nst);
        return result;
    }

    private StringBuilder shiftLeft (int n, StringBuilder st){
        for (int i = n; i > 0; i--){
            st.deleteCharAt(0);
            st.append('0');
        }
        return st;
    }

    private StringBuilder inverse (int length, StringBuilder string){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++){
            if (string.charAt(i) == '1' ) result.append('0'); else
                result.append('1');
        }
        //adaugam unitate la bitul mai putin semnificativ
        {
            int i = length;
            while (result.charAt(--i) == '1' && i <= length){
                result.deleteCharAt(i);
                result.insert(i, "0");
            }
            if (result.charAt(i) == '0'){
                result.deleteCharAt(i);
                result.insert(i, "1");
            }
        }
        return result;
    }

    public void RS(){
        Intent intent = new Intent(this, Calculator.class);
        finish();
        startActivity(intent);
    }
    public void openNSC(){
        Intent intent = new Intent (this, NSConverter.class);
        finish();
        startActivity(intent);
    }
    public void openCC(){
        Intent intent = new Intent(this, CConverter.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        operator = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
