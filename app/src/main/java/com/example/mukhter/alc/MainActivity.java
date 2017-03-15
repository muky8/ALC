package com.example.mukhter.alc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.content;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    SQLiteDatabase titlesDB;
    String items;
    JSONObject jsonpart;
    JSONArray array;
    String avatar;
    String name;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;


itemsAdapter itemsadapt;
    ArrayList<items> itemsarraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
itemsarraylist = new ArrayList<items>();


        dialog = new ProgressDialog(MainActivity.this);
        DownloadTask task = new DownloadTask();
        try {
            task.execute("https://api.github.com/search/users?q=+language:java+location:lagos");
        } catch (Exception e) {
            e.printStackTrace();
        }

 itemsadapt=new itemsAdapter(getApplicationContext(),R.layout.listing2,itemsarraylist);
        listView.setAdapter(itemsadapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), profileactivity.class);
                startActivity(intent);

            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {

            String result = "";

            URL url;

            HttpURLConnection urlConnection = null;

            try {

                url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }


                Log.i("URL Content", result);

                JSONObject jsonObject = new JSONObject(result);
                items = jsonObject.getString("items");   //removes the items part in the api
                Log.i("", items);
                array = new JSONArray(items);
                for (int i = 0; i < array.length(); i++) {
                    items itemmodel = new items();
                    jsonpart = array.getJSONObject(i);  //helps you get specific values
                    itemmodel.setLogin(jsonpart.getString("login"));
                    itemmodel.setImageavatar(jsonpart.getString("avatar_url"));

                     itemsarraylist.add(itemmodel);
                }
                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPreExecute() {
            dialog.setMessage("Loading Developer's Info...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean s) {


         dialog.cancel();
            itemsadapt.notifyDataSetChanged();

        }

    }


        }


