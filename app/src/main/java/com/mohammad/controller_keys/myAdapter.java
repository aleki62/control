package com.mohammad.controller_keys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohammad on 3/15/2016.
 */
public class myAdapter extends BaseAdapter {
    private Context mcontext;
    public ArrayList<HashMap<String,String>> books;
    //    ArrayList<String> marrayList=new ArrayList<String>();
    private static LayoutInflater inflater=null;
    TextView number;
    TextView hint;
    public myAdapter (Context context,ArrayList<HashMap<String,String>> data){
        mcontext=context;
        books=data;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (convertView == null){
            view=inflater.inflate(R.layout.items,null);
}
            number = (TextView) view.findViewById(R.id.keyNumber);
            hint = (TextView) view.findViewById(R.id.Hint);

            HashMap<String , String> keys= new HashMap<>();
            keys=books.get(position);
            number.setText(keys.get("number"));
            hint.setText(keys.get("hint"));


        return view;
    }

    public void add(HashMap a){
        // add the items to the previous array
        books.add(a);
        // notify the data changed
        notifyDataSetChanged();
    }
}
