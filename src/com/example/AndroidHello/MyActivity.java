package com.example.AndroidHello;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;




public class MyActivity extends Activity {

    Calculator calc;
    StringBuilder exp;
    Toast toast;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;

    public static final String[] notations = {"Bin", "Oct", "Dec", "Hex"};
    int checkedItem = 2;
    int oldCheckedItem = 2;

    Map<String, Integer> nots = new LinkedHashMap<String, Integer>();
    {
        nots.put(notations[0], 2);
        nots.put(notations[1], 8);
        nots.put(notations[2], 10);
        nots.put(notations[3], 16);
    }

    ProgressBar pb;
    private static final int DLG_EXIT = 1;
    private static final int DLG_RADIO = 2;

    Button btnPlus;
    Button btnMinus;
    Button btnDiv;
    Button btnMult;
    Button btnRez;
    Button btnClear;

    int step = 0;
    int calcT = 0;
    int calcStepT = 0;


    boolean isOk =true;
    TextView menu;

    TextView expLine;
   // ProgressDialog pd ;
    Thread t;
    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != -1) {

                pb.incrementProgressBy(msg.what);

            } else {
                toast = Toast.makeText(MyActivity.this, calcT/1000D + " m spent for expression", Toast.LENGTH_SHORT);
                toast.show();

                expLine.setText("");

                if(checkedItem != 2){
                    exp = new StringBuilder(main.parseStringExpression(exp.toString(), nots.get(notations[checkedItem]), 10));
                    exp = new StringBuilder(main.parseStringExpression(Integer.toString(new Integer(Calculator.parseExp(exp))), 10, nots.get(notations[checkedItem])));
                    expLine.setText(exp);
                }
                else
                    exp = new StringBuilder(Integer.toString(new Integer((Calculator.parseExp(exp)))));
                    expLine.setText(exp);

                //exp.setLength(0);
                calcT = 0;
                pb.setProgress(0);


            }
        }
    };



    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (id){

            case DLG_EXIT:
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("Yes!",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
               MyActivity.this.finish();
            }
        });
        builder.setNegativeButton("No!",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNeutralButton("Cancel!", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
                break;

            case DLG_RADIO:
                builder.setTitle("Select the scale of notation\n");

                builder.setSingleChoiceItems(notations, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isOk==true)
                            oldCheckedItem = checkedItem;
                        checkedItem = which;

                        isOk = false;
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        isOk = true;

                        String str = main.parseStringExpression(exp.toString(), nots.get(notations[oldCheckedItem]), nots.get(notations[checkedItem]));
                        exp = new StringBuilder(str);
                        //expLine.setText("");
                        expLine.setText(exp);
                        Toast.makeText(MyActivity.this, "Выбрано: " + notations[checkedItem], Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        return builder.create();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.menu:
                menu.add("2");
                menu.add("8");
                menu.add("10");
                menu.add("16");
                break;

        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        String str = main.parseStringExpression(exp.toString(), nots.get(notations[oldCheckedItem]), nots.get(notations[checkedItem]));
        exp = new StringBuilder(str);
        //expLine.setText("");
        expLine.setText(exp);

        Toast.makeText(this, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();

        if(item.isCheckable()) {
            item.setChecked(!item.isChecked());
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.main);
        calc = new Calculator();
        exp = new StringBuilder();

        btn1 = (Button) findViewById(R.id.Button01);
        btn2 = (Button) findViewById(R.id.Button02);
        btn3 = (Button) findViewById(R.id.Button03);
        btn4 = (Button) findViewById(R.id.Button04);
        btn5 = (Button) findViewById(R.id.Button05);
        btn6 = (Button) findViewById(R.id.Button06);
        btn7 = (Button) findViewById(R.id.Button07);
        btn8 = (Button) findViewById(R.id.Button08);
        btn9 = (Button) findViewById(R.id.Button09);
        btn0 = (Button) findViewById(R.id.Button0);

        pb = (ProgressBar) findViewById(R.id.progress);

        btnPlus = (Button) findViewById(R.id.ButtonPlus);
        btnMinus = (Button) findViewById(R.id.ButtonMinus);
        btnDiv = (Button) findViewById(R.id.ButtonDiv);
        btnMult = (Button) findViewById(R.id.ButtonMult);
        btnRez = (Button) findViewById(R.id.ButtonRez);
        btnClear = (Button) findViewById(R.id.ButtonClear);

        expLine = (TextView) findViewById(R.id.TextView01);

        menu = (TextView) findViewById(R.id.menu);
        registerForContextMenu(menu);
    }

    public void onclick(View v) throws InterruptedException {
            switch (v.getId()) {

                case R.id.ButtonRez:

                    t = new Thread() {
                        @Override
                        public void run() {
                            try {

                                int i = 0;
                                i =  1 + (int)(Math.random() * ((99 - 1) + 1));
                                step=i;
                                while (true) {

                                    if(i>100){
                                        i = 100-(i-step);
                                        h.sendEmptyMessage(i);

                                        calcStepT = 1 + (int)(Math.random() * ((1000 - 1) + 1));
                                        calcT += calcStepT;

                                        Thread.sleep(calcStepT);
                                        break;
                                    }
                                    h.sendEmptyMessage(step);
                                    step = i + (int)(Math.random() * ((99 - i) + 1));
                                    i += step;

                                    calcStepT = 1 + (int)(Math.random() * ((1000 - 1) + 1));
                                    calcT += calcStepT;
                                    System.out.println("=="+step+"=="+calcStepT);
                                    Thread.sleep(calcStepT);
                                }

                                h.sendEmptyMessage(-1);

                            } catch (InterruptedException ignored) {
                            }

                        }
                    };

                    t.start();

                    break;

                case R.id.ButtonClear:
                    exp.setLength(0);
                    expLine.setText("");
                    break;

                case R.id.Button0:
                case R.id.Button01:
                case R.id.Button02:
                case R.id.Button03:
                case R.id.Button04:
                case R.id.Button05:
                case R.id.Button06:
                case R.id.Button07:
                case R.id.Button08:
                case R.id.Button09:
                case R.id.a:
                case R.id.b:
                case R.id.c:
                case R.id.d:
                case R.id.e:
                case R.id.f:
                case R.id.ButtonMinus:
                case R.id.ButtonMult:
                case R.id.ButtonDiv:
                case R.id.ButtonPlus:
                case R.id.left_bkt:
                case R.id.right_bkt:
                    exp.append(((TextView) v).getText());
                    expLine.setText(exp);
                    break;
                case R.id.Exit:
                    showDialog(DLG_EXIT);
                    break;
                case R.id.back:
                    exp.delete(exp.length()-1, exp.length());
                    expLine.setText(exp);
                    break;
                case R.id.selectNotation:
                    showDialog(DLG_RADIO);

            }
    }



}