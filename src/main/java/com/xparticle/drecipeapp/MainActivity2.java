package com.xparticle.drecipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements ExampleAdapter.onItemClickListener{
    EditText searchBox;
    RequestQueue requestQueue;
    RecyclerView cRecyclerView;
    ExampleAdapter mExampleAdapter;
    ArrayList<Category> mCategory;
    SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        requestQueue = VollySinglenton.getInstance(this).getRequestQueue();
        searchBox = findViewById(R.id.searchBox);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        cRecyclerView = findViewById(R.id.recipeRecyclerView);
        cRecyclerView.setHasFixedSize(true);//this improve performace of the recylerview
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity2.this,3);
        cRecyclerView.setLayoutManager(gridLayoutManager);

        mCategory = new ArrayList<>();
        loadCategory();
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCategory();
                if(amIConnected()){
                    Toast.makeText(MainActivity2.this,"Refresh Successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity2.this,"No Internet Access",Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if(!amIConnected()){
            Toast.makeText(MainActivity2.this,"No Internet Access",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean amIConnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        Network activeNetworkInfo = connectivityManager.getActiveNetwork();
        return activeNetworkInfo != null;
    }

    public void loadCategory(){
        String categoryUrl = "https://www.themealdb.com/api/json/v1/1/categories.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, categoryUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("categories");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cName = jsonObject.getString("strCategory");
                        String cImage = jsonObject.getString("strCategoryThumb");

                        mCategory.add(new Category(cImage,cName));

                    }
                    mExampleAdapter = new ExampleAdapter(MainActivity2.this,mCategory);
                    cRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(MainActivity2.this);
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

    }
    public void performSearch(){
        String recipeName = searchBox.getText().toString();
        if(recipeName.equals("")){
            searchBox.setError("Enter Name");
            return;
        }
        searchBox.setText("");
        Intent intent = new Intent(MainActivity2.this,Recipe_description.class);
        Bundle bundle= new Bundle();
        bundle.putString("dishName",recipeName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent subCategoryIntent = new Intent(this,sub_category.class);
        Category category = mCategory.get(position);

        subCategoryIntent.putExtra("categoryName",category.getmCategoryName());
        startActivity(subCategoryIntent);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}