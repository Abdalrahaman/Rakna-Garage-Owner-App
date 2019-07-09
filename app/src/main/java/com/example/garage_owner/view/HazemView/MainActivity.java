package com.example.garage_owner.view.HazemView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garage_owner.R;
import com.example.garage_owner.presenter.Islam.Data;
import com.example.garage_owner.presenter.item2data;
import com.example.garage_owner.view.Islam.user_prof;
import com.example.garage_owner.view.Islam.user_profile;
import com.example.garage_owner.view.SaeedView.HistoryAdapter;
import com.example.garage_owner.view.SaeedView.history_frag;
import com.example.garage_owner.view.SaeedView.notify_frag;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView ;
    Fragment selectedFragment = null;
     public static  String id, garage_id, email;
     public static ArrayList<Data>dataList;
     public static ArrayList<item2data>item2dataList;
    private String url = "https://rakna-app.000webhostapp.com/read_online_rent.php";
    private final String URL_READ_NAME_PHONE = "https://rakna-app.000webhostapp.com/select_garage_owner_profile_details.php";
    private String urlHistory = "https://rakna-app.000webhostapp.com/garage_history.php";
    public static  String [] commingDataArray;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        garage_id = intent.getStringExtra("garage_id");
        email = intent.getStringExtra("email");
        fragmentManager = getSupportFragmentManager();
        dataList = new ArrayList<>();
        item2dataList = new ArrayList<>();
        Log.d("id", ""+id);
        loadData();
        initWidgets();
        actionWidgets();
        commingDataArray = getNameAndPhone(email);
        loadDataHistory();
        Log.d("commingDataArray ",""+ commingDataArray);
    }
    private void loadData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject profile = array.getJSONObject(i);

                                dataList.add(new Data(
                                        profile.getString("name"),
                                        profile.getString("phone"),
                                        profile.getString("image_url"),
                                        profile.getString( "overall_rate" )
                                ));
                            }
                            Log.d("dataList", ""+dataList);
                            initWidgets();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("garage_id", garage_id);
                return params;
            }
        };

        queue.add(stringRequest);
    }

        private String[] getNameAndPhone(final String email){
           final String [] commingData = new String[12] ;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_NAME_PHONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("user_info");
                    JSONObject object = jsonArray.getJSONObject(0);
                    String name_ = object.getString("name");
                    String phone_ = object.getString("phone");
                    String national_id_number = object.getString("national_id_number");
                    String lat = object.getString("lat");
                    String lng = object.getString("lng");
                    String wash_ = object.getString("wash");
                    String lubrication_ = object.getString("lubrication");
                    String maintenance_ = object.getString("maintenance");
                    String oil_change_ = object.getString("oil_change");
                    String width_ = object.getString("garage_width");
                    String length_ = object.getString("garage_length");
                    String height_ = object.getString("garage_height");
                    commingData[0] = name_;
                    commingData[1] = phone_;
                    commingData[2] = national_id_number;
                    commingData[3] = lat;
                    commingData[4] = lng;
                    commingData[5] = wash_;
                    commingData[6] = lubrication_;
                    commingData[7] = maintenance_;
                    commingData[8] = oil_change_;
                    commingData[9] = width_;
                    commingData[10] = length_;
                    commingData[11] = height_;

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        return  commingData;
    }

    private void loadDataHistory() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlHistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("mycheck",response);
                            //catching array
                            JSONArray array = new JSONArray(response);

                            //loop through all objects
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject history_items = array.getJSONObject(i);

                                //adding items to list
                                item2dataList.add(new item2data(
                                        history_items.getString("name"),
                                        history_items.getString("phone"),
                                        history_items.getString("date"),
                                        history_items.getString("from_time"),
                                        history_items.getString("to_time"),
                                        history_items.getString("image_url"),
                                        history_items.getDouble("cost"),
                                        history_items.getDouble("overall_rate")
                                ));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("Errorredponse",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("national_id_number", MainActivity.garage_id);
                return params;
            }
        };

        //adding string request to queue
        queue.add(stringRequest);
    }

    public void initWidgets(){

        navigationView = findViewById(R.id.bottom_navigation);

        selectedFragment = new user_prof();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected( MenuItem item) {
                    selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.action_rakna:
                            selectedFragment = new user_prof();
                            break;
                        case R.id.action_notification:
                            selectedFragment = new notify_frag();
                            break;
                        case R.id.action_camera:
                            selectedFragment = new LiveCameraFragment();
                            break;
                        case R.id.action_chat:
                            selectedFragment = new ChatHomeFragment();
                            break;
                        case R.id.action_profile:
                            selectedFragment = new user_profile();

                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void actionWidgets(){

        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (id.equals("2") ) {////facebook logout
            LoginManager.getInstance().logOut();
            finish();
        }

    }
}
