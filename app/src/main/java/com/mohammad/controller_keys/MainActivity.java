package com.mohammad.controller_keys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static myAdapter myAdapter;
    int maxNumber;
    ArrayList<String> numbers = new ArrayList<String>();
    EditText editText;
    String newData = "";
    ArrayList<String> hints = new ArrayList<String>();
    GridView gw;
    SharedPreferences sharedpreferences;
    static ArrayList<HashMap<String,String>> Strings=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gw=(GridView) findViewById(R.id.gw);
        WritesharedPrefrence("1","+");
        ReadSharedPrefrence("12");
        SetMaxNumber();
//        delete();
        init();
//        delete();


        if (numbers.size()==0){
            hints.add(1+"");
            numbers.add(1+"");
        }
        for (int i=0;i<numbers.size()-1;i++){
            HashMap<String , String> data=new HashMap<>();
            data.put("number", numbers.get(i));
            data.put("hint",hints.get(i));
            Strings.add(data);
        }
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAnimation_Window);
        myAdapter=new myAdapter(getApplicationContext(),Strings);
            gw.setAdapter(myAdapter);
            gw.setLongClickable(true);
            gw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    editText = new EditText(MainActivity.this);
                    alert.setMessage("راهنمای جدید را وارد کنید");
                    alert.setTitle("ویرایش راهنما");
                    alert.setView(editText);
                    alert.setPositiveButton("ذخیره", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            newData = editText.getText().toString();
                            ReplaceSharedPrefrence(position + "", newData);
                            hints.set(position, newData);
                            myAdapter.notifyDataSetChanged();
                            Strings.clear();
                            for (int i = 0; i < numbers.size()-1; i++) {
                                HashMap<String, String> data = new HashMap<>();
                                data.put("number", numbers.get(i));
                                data.put("hint", hints.get(i));
                                Strings.add(data);
                                myAdapter.books = Strings;
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    });
                    alert.show();

                return true;
            }
    });

            gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemClicked;
                    itemClicked = String.valueOf(Strings.get(position).get("hint"));
                    HashMap<String, String> a = new HashMap<>();
                    if (position == Strings.size() - 1) {
                        a.put("number", "add");
                        a.put("hint", "new");
                        maxNumber += 1;
                        WritesharedPrefrence(maxNumber + "", "*");
//                        numbers.add(maxNumber + "");
//                        hints.add(maxNumber + "");

                        a.put("number", "add");
                        a.put("hint", "new");
                        myAdapter.add(a);
//
                        Strings.get(position).put("number", Integer.toString(maxNumber));
                        Strings.get(position).put("hint", Integer.toString(maxNumber));
//                        myAdapter.add(a);
                        Toast.makeText(getApplicationContext(), "new number added" + maxNumber, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "selected number " + itemClicked, Toast.LENGTH_SHORT).show();
                }
            });
            System.out.println("new data is : "+newData);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }

    @Override
    public void finish() {
        System.out.println("finish activity");
        System.runFinalizersOnExit(true) ;
        super.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    public void WritesharedPrefrence(String numbers , String hint){
        sharedpreferences = getSharedPreferences("keyNNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(numbers,hint);
        editor.commit();
    }

    public String ReadSharedPrefrence(String number){
        sharedpreferences = getSharedPreferences("keyNNumber", Context.MODE_PRIVATE);
        String a=sharedpreferences.getString(number, "");
        return a;
    }

    public void ReplaceSharedPrefrence(String number,String hint) {
        sharedpreferences = getSharedPreferences("keyNNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(number, hint);
        editor.commit();
    }
    public void SetMaxNumber() {
        sharedpreferences = getSharedPreferences("keyNNumber", Context.MODE_PRIVATE);
        maxNumber=sharedpreferences.getAll().size();
    }

    public void init(){
        for(int i=0;i<=maxNumber-1;i++){
//            if (i==maxNumber-1){
//                numbers.add("add");
//                hints.add("new");
//            }
//            else{
           numbers.add(String.valueOf(i));
            hints.add(ReadSharedPrefrence(String.valueOf(i)));
//        }
            System.out.println(numbers.toString());
        }
    }

    public void delete(){
        sharedpreferences = getSharedPreferences("keyNNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }


}