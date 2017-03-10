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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);


        dialog = new ProgressDialog(MainActivity.this);
        DownloadTask task = new DownloadTask();
        try {
            task.execute("https://api.github.com/search/users?q=language:java%20location:Lagos");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedPreferences = this.getSharedPreferences("com.example.mukhter.alc", Context.MODE_PRIVATE);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), profileactivity.class);

                startActivity(intent);

            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

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
                    jsonpart = array.getJSONObject(i);  //helps you get specific values

                    Log.i("id", jsonpart.getString("id"));
                    Log.i("login", jsonpart.getString("login"));
                    avatar = jsonpart.getString("avatar_url");
                    name = jsonpart.getString("login");
                    titles.add(name);
                    Log.d(name, "Output");
                }
                return items;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                return String.valueOf(titles);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPreExecute() {
            dialog.setMessage("Loading Developer's Info...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            listView.setAdapter(arrayAdapter);

            if (dialog != null && dialog.isShowing()) {
                dialog.setMessage("DownLoading finished");
                dialog.dismiss();
            }
        }
    }

        }


