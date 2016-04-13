package com.mohammad.controller_keys;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mohammad.controller_keys.R;

public class mdialog extends Dialog implements
        android.view.View.OnClickListener {
    EditText editText;
    public Activity c;
    public Dialog d;
    public Button save;
    String newHint;



    public String getNewHint() {
        return newHint;
    }

    public void setNewHint(String newHint) {
        this.newHint = newHint;
    }



    public mdialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mdialog);
        save = (Button) findViewById(R.id.mbutton);
        save.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.editText3);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.mbutton){
            setNewHint(editText.getText().toString());
        }
        dismiss();
    }
}