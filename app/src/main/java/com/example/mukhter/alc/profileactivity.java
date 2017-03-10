package com.example.mukhter.alc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class profileactivity extends MainActivity {

    TextView avatarr;
    ImageView imageView;
    TextView loginname ,avatarurll;

    RequestQueue requestQueue;
public void share(View view){

    Button button = (Button)findViewById(R.id.share);
    Log.i("","Button pressed");

    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
    sharingIntent.setType("text/html");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>@<github username>, <github profile url>.”\n</p>"));
    startActivity(Intent.createChooser(sharingIntent,"Check out this awesome developer"));

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);
imageView=(ImageView)findViewById(R.id.profilepic);
        try {

            Picasso .with(this).load("https://avatars1.githubusercontent.com/u/8110201?v=3").into(imageView);

        }catch (Exception e){
            e.printStackTrace();

        }

        loginname = (TextView) findViewById(R.id.loginname);
        avatarurll=(TextView) findViewById(R.id.avatarurl);
        requestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,
                "https://api.github.com/search/users?q=language:Java%20location:Lagos",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");

                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject item = jsonArray.getJSONObject(i);
                                String login = item.getString("login");
                                String avatarurl = item.getString("avatar_url");
                                loginname.setText(login);
                                avatarurll.setText(avatarurl);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error","Error!!!");
            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }
}
