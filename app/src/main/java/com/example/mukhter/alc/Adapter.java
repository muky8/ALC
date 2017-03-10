package com.example.mukhter.alc;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUKHTER on 09/03/2017.
 */

 public class Adapter extends ArrayAdapter {

List list = new ArrayList();
    public Adapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(parts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        partholder partholderr;
        View view;
        view=convertView;
        if(view==null){
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view=layoutInflater.inflate(R.layout.list_item,parent,false);

            partholderr =new partholder();
            partholderr.Login=(TextView) view.findViewById(R.id.title);
            partholderr.id=(TextView) view.findViewById(R.id.desc);
            view.setTag(partholderr);
        }else {
            partholderr =(partholder) view.getTag();
        }
        parts partss = (parts) this.getItem(position);
        partholderr.Login.setText(partss.getLogin());
        partholderr.id.setText(partss.getId());
        return view;
    }

    static class partholder{

        TextView Login,id,image;




    }
}