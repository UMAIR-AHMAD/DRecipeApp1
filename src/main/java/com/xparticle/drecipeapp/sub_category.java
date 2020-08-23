package com.xparticle.drecipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class sub_category extends AppCompatActivity implements ExampleAdapter_sub.onItemClickListener {

    RecyclerView cRecyclerView;
    ArrayList<Category> mCategory;
    RequestQueue requestQueue;
    ExampleAdapter_sub mExampleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        requestQueue = VollySinglenton.getInstance(this).getRequestQueue();
        cRecyclerView = findViewById(R.id.subCategoryRecyclerView);
        cRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        cRecyclerView.setLayoutManager(gridLayoutManager);

        mCategory =new  ArrayList<>();

        loadCategory();
    }
    public void loadCategory(){
        String categoryName = getIntent().getStringExtra("categoryName");
        String  finalUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?c="+ categoryName;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("meals");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String subName = jsonObject.getString("strMeal");
                        String subImage = jsonObject.getString("strMealThumb");

                        mCategory.add(new Category(subImage,subName));
                    }
                    mExampleAdapter = new ExampleAdapter_sub(sub_category.this,mCategory);
                    cRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(sub_category.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
//        Intent descriptionIntent = new Intent(this,Recipe_description.class);
        Category dishName= mCategory.get(position);

        performSearch(dishName.getmCategoryName());

        //descriptionIntent.putExtra("dishName",dishName.getmCategoryName());
        //startActivity(descriptionIntent);
    }

    public void performSearch(String recipeName){
        Intent intent = new Intent(this,Recipe_description.class);
        Bundle bundle = new Bundle();
        bundle.putString("dishName", recipeName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
