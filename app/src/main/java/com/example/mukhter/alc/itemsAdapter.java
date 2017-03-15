package com.example.mukhter.alc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUKHTER on 14/03/2017.
 */

public class itemsAdapter extends ArrayAdapter<items> {
    ArrayList<items> arrayList;
    int Resource;
    Context context;
    LayoutInflater vi;
    public itemsAdapter(Context context, int resource, ArrayList<items> objects) {
        super(context, resource, objects);
        arrayList=objects;
        Resource=resource;
        this.context=context;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       viewholder holder;
        if(convertView==null){
          convertView=  vi.inflate(Resource,null);
            holder=new viewholder();
            holder.imageView=(ImageView)convertView.findViewById(R.id.listImage);
            holder.textView=(TextView) convertView.findViewById(R.id.listTitle);
            convertView.setTag(holder);
        }else{
            holder=(viewholder)convertView.getTag();
        }
        new DownloadImage(holder.imageView).execute(arrayList.get(position).getImageavatar());
        holder.textView.setText(arrayList.get(position).getLogin());

        return convertView;
    }
    static class viewholder{
        public ImageView imageView;
        public TextView textView;
    }

    private class DownloadImage extends AsyncTask<String,Void,Bitmap>{
        ImageView bitmapimage;
        public DownloadImage(ImageView bitmapimage){
            this.bitmapimage=bitmapimage;

        }

        @Override
        protected Bitmap doInBackground(String... urls) {
String urldisplay = urls[0];
            Bitmap icon=null;
            try{
                InputStream in = new java.net.URL(urldisplay).openStream();
                icon= BitmapFactory.decodeStream(in);
            }catch (Exception e){

                Log.e("Error",e.getMessage());
     e.printStackTrace();
            }

            return icon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bitmapimage.setImageBitmap(bitmap);
        }
    }
}
