package com.xparticle.drecipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class Recipe_description extends AppCompatActivity {

    String searchUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    TextView instruction,dishName,country,category;
    ImageView imageView,youtube;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);
        instruction = findViewById(R.id.strInstructions);
        dishName = findViewById(R.id.dishName);
        country = findViewById(R.id.countryName);
        youtube = findViewById(R.id.youTube);
        category = findViewById(R.id.categoryName);
        imageView = findViewById(R.id.imageView);
        requestQueue = VollySinglenton.getInstance(this).getRequestQueue();

        String searchFinalUrl = searchUrl + getIntent().getStringExtra("dishName");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchFinalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("meals");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    final String instructions = jsonObject.getString("strInstructions");
                    String name = jsonObject.getString("strMeal");
                    String countryy = jsonObject.getString("strArea");
                    String categoryy = jsonObject.getString("strCategory");
                    String image = jsonObject.getString("strMealThumb");
                    final String youtubeStr = jsonObject.getString("strYoutube");

                    instruction.setText(instructions);
                    dishName.setText(name);
                    country.setText(countryy);
                    category.setText(categoryy);
                    youtube.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(youtubeStr));
                            startActivity(intent);
                        }
                    });
                    //use of picasso - to load image form web into your application;
                    // implement it into the gradle and one line of code below;
                    Picasso.get().load(image).into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);



//        String instruct = getIntent().getStringExtra("instructions");
//        instruction.setText(instruct);
//        String dish = getIntent().getStringExtra("dishName");
//        dishName.setText(dish);
//        String countryName = getIntent().getStringExtra("countryName");
//        country.setText(countryName);
//        String categoryName = getIntent().getStringExtra("category");
//        category.setText(categoryName);
//      String imageUrl = getIntent().getStringExtra("image");

        //use of picasso - to load image form web into your application;
        // implement it into the gradle and one line of code below;
//        Picasso.get().load(imageUrl).into(imageView);

    }
}