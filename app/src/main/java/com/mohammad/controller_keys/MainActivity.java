package com.mohammad.controller_keys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.HashMap;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

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
        crouton("خوش آمدید");
        gw=(GridView) findViewById(R.id.gw);
        SetMaxNumber();
        init();


//        final mdialog mdialog=new mdialog(this,R.style.MyAnimation_Window);
//        mdialog.show();
        final Dialog mdialog=new Dialog(new ContextThemeWrapper(this, R.style.DialogSlideAnim));
        mdialog.setContentView(R.layout.mdialog);

        if (maxNumber==0){
            hints.add("new");
            numbers.add("add");
        }

        for (int i=0;i<numbers.size();i++){
            HashMap<String , String> data=new HashMap<>();
            data.put("number", numbers.get(i));
            data.put("hint",hints.get(i));
            Strings.add(data);
        }
//        final AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.MyAnimation_Window));
        myAdapter=new myAdapter(getApplicationContext(),Strings);
            gw.setAdapter(myAdapter);
            gw.setLongClickable(true);
                         ViewAnimator
                                    .animate(gw)
                                    .translationY(-2000, 0)
                                 .textColor(Color.BLUE)
                                    .alpha(0, 1)
                                    .dp().translationX(0, 0)
                                    .descelerate()
                                    .duration(2000)
                                    .start();
            gw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                    mdialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
                    mdialog.setTitle("راهنمای جدید را وارد کنید");
                    final EditText meditText = (EditText) mdialog.findViewById(R.id.editText3);
                    Button button = (Button) mdialog.findViewById(R.id.mbutton);
//                    mdialog.getWindow().setGravity(Gravity.BOTTOM);

                    ViewAnimator
                            .animate(meditText)
                            .translationY(0, 0)
                            .alpha(0, 1)
                            .flash()
                            .descelerate()
                            .duration(3000)
                            .start();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(getApplicationContext(), meditText.getText().toString(), Toast.LENGTH_SHORT).show();

                            newData = meditText.getText().toString();
                            crouton("راهنمای جدید ذخیره شد");
                            ReplaceSharedPrefrence(position + "", newData);
                            hints.set(position, newData);
                            myAdapter.notifyDataSetChanged();
                            Strings.clear();
                            for (int i = 0; i < numbers.size(); i++) {
                                HashMap<String, String> data = new HashMap<>();
                                data.put("number", numbers.get(i));
                                data.put("hint", hints.get(i));
                                Strings.add(data);
                                myAdapter.books = Strings;
                            }
                            myAdapter.notifyDataSetChanged();
                            mdialog.cancel();
                        }
                    });

                    mdialog.show();
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
                        a.put("number", "add");
                        a.put("hint", "new");
                        myAdapter.add(a);
                        Strings.get(position).put("number", Integer.toString(maxNumber));
                        Strings.get(position).put("hint", Integer.toString(maxNumber));
                        crouton("کلید جدید ساخته شد" + maxNumber);
                    } else
                        crouton("انتخاب شد" + itemClicked);
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
        for(int i=0;i<maxNumber;i++){
            if (i==maxNumber-1){
                numbers.add("add");
                hints.add("new");
            }
            else {
                numbers.add(String.valueOf(i));
                hints.add(ReadSharedPrefrence(String.valueOf(i)));
            }
            }
    }

    public void delete(){
        sharedpreferences = getSharedPreferences("keyNNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }
public void crouton(String a){

        Configuration croutonConfiguration = new Configuration.Builder()
                .setDuration(2500).build();
        Style InfoStyle = new Style.Builder()
                .setTextShadowRadius(1)
                .setBackgroundColorValue(Color.parseColor("#0099cc"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(110)
                .setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();



        Style AlertStyle = new Style.Builder()
                .setBackgroundColorValue(Color.parseColor("#cc0000"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(110)
                .setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();


        Style ConfirmStyle = new Style.Builder()
                .setBackgroundColorValue(Color.parseColor("#FF00FF0D"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(110)
                .setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();
    if (a.contains("انتخاب")) {
        Crouton.makeText(this, a, InfoStyle).show();
    }
    else
        Crouton.makeText(this,a,ConfirmStyle).show();
    }

}