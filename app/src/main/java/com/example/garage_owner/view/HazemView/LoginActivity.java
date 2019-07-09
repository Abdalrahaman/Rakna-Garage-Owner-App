package com.example.garage_owner.view.HazemView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garage_owner.R;
import com.example.garage_owner.model.HazemModel.SendDataLogin;
import com.example.garage_owner.presenter.Islam.Data;
import com.example.garage_owner.view.Islam.Profile_Adapter;
import com.example.garage_owner.view.Islam.user_prof;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 2000;

    Button Signin;
    EditText emailsign;
    EditText passwordsign;
    ProgressBar lodin;

    TextView signup, forgot_password;

    LoginButton loginButtonFacebook;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    private  String URL_REGIST = "https://rakna-app.000webhostapp.com/Garage_owner_facebook_login.php";//"https://rakna-app.000webhostapp.com/register.php";
    private AccessTokenTracker accessTokenTracker;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    CheckBox checkBox;
    String garage_id, email;
    String ID, _email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessTokenTracker = new AccessTokenTracker() {
            // This method is invoked everytime access token changes
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                useLoginInformation(currentAccessToken);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id", "2");
                intent.putExtra("garage_id", garage_id);
                intent.putExtra("email", _email);
                Log.d("garage id", garage_id);
                startActivity(intent);
                finish();

            }
        };

        setContentView(R.layout.activity_login);

        lodin = findViewById(R.id.loading);
        Signin = (Button) findViewById(R.id.btn_login);
        emailsign = (EditText) findViewById(R.id.email);
        passwordsign = (EditText) findViewById(R.id.password);
        signup = findViewById(R.id.link_regist);
        loginButtonFacebook = (LoginButton) findViewById(R.id.facebood_login_btn);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();


        checkSharedPreference();
        if (checkBox.isChecked()) {
            editor.putString(getString(R.string.checkbox), "True");
            editor.commit();

            String shpremail = email;
            editor.putString(getString(R.string.email), shpremail);
            editor.commit();

            String shprpassword = passwordsign.getText().toString();
            editor.putString(getString(R.string.password), shprpassword);
            editor.commit();

            String shprgarageid = garage_id;
            editor.putString(getString(R.string.garage_id), shprgarageid);
            editor.commit();


            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", "1");
            intent.putExtra("garage_id", garage_id);
            intent.putExtra("email", email);
            startActivity(intent);

        } else {

            editor.putString(getString(R.string.checkbox), "False");
            editor.commit();

            editor.putString(getString(R.string.email), "");
            editor.commit();

            editor.putString(getString(R.string.password), "");
            editor.commit();

            editor.putString(getString(R.string.garage_id), "");
            editor.commit();


        }



        loginButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loginFacebook();

            }
        });


        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (emailsign.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this,
                            "Please enter the Email OR password_",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordsign.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this,
                            "Please enter the Email OR password_",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                lodin.setVisibility(View.VISIBLE);
                Signin.setVisibility(view.GONE);

                final Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.rotate);
                lodin.startAnimation(animation);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        login();
                    }

                }, SPLASH_TIME);


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });



    }

    private void checkSharedPreference() {
        String sCheckbox = sharedPreferences.getString(getString(R.string.checkbox), "False");
        email = sharedPreferences.getString(getString(R.string.email), "");
        String password = sharedPreferences.getString(getString(R.string.password), "");
        garage_id = sharedPreferences.getString(getString(R.string.garage_id), "");

        emailsign.setText(email);
        passwordsign.setText(password);
        if (sCheckbox.equals("True")) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }


    }



    public void login() {

        String signEmail = emailsign.getText().toString().trim();
        String signPassword = passwordsign.getText().toString().trim();




        Response.Listener<String> responselistner = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    Log.d("success", "onResponse: success is " + success
                            + " ,JSON : " + jsonArray);

                    if (success) {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name").trim();
                            email = object.getString("email").trim();
                            garage_id = object.getString("garage_id").trim();

                            lodin.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("id", "1");
                            intent.putExtra("garage_id", garage_id);
                            intent.putExtra("email", email);

                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
//                            checkSharedPreference();


                            if (checkBox.isChecked()) {
                                Toast.makeText(LoginActivity.this, "data saved", Toast.LENGTH_SHORT).show();
                                editor.putString(getString(R.string.checkbox), "True");
                                editor.commit();

                                String shpremail = email;
                                editor.putString(getString(R.string.email), shpremail);
                                editor.commit();

                                String shprpassword = passwordsign.getText().toString();
                                editor.putString(getString(R.string.password), shprpassword);
                                editor.commit();

                                String shprgarageid = garage_id;
                                Toast.makeText(LoginActivity.this, shprgarageid, Toast.LENGTH_SHORT).show();
                                editor.putString(getString(R.string.garage_id), shprgarageid);
                                editor.commit();



                            } else {

                                Toast.makeText(LoginActivity.this, "data not saved", Toast.LENGTH_SHORT).show();
                                editor.putString(getString(R.string.checkbox), "False");
                                editor.commit();

                                editor.putString(getString(R.string.email), "");
                                editor.commit();

                                editor.putString(getString(R.string.password), "");
                                editor.commit();

                                editor.putString(getString(R.string.garage_id), "");
                                editor.commit();


                            }


                        }

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "LOGIN failed",
                                Toast.LENGTH_LONG).show();

                        lodin.setVisibility(View.GONE);
                        Signin.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,
                            "LOGIN ERROR " + e.toString(),
                            Toast.LENGTH_LONG).show();
//                    lodin.setVisibility(View.GONE);
//                    Signin.setVisibility(View.VISIBLE);

                }
            }
        };
        SendDataLogin sendDataLogin = new SendDataLogin(signEmail, signPassword, responselistner);

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(sendDataLogin);

    }


    private void registForFacebook(final String name, final String email, final String ID) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(LoginActivity.this, "Register success!", Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Register error! 15 \n" + e.toString(), Toast.LENGTH_SHORT).show();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "Register error! 16 \n" + error.toString(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("email", email);
                params.put("id_number", ID);

                return params;
            }
        };
        // MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void getData(JSONObject object, String ID) {
        try {
            //URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"picture?width=120&height=120");
            String photo = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=normal";//"https://graph.facebook.com/"+object.getString("id")+"picture?width=120&height=120";
            _email = object.getString("email");
            String first_name = object.getString("first_name");
            String last_name = object.getString("last_name");
            String name = first_name + " " + last_name;

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id","2");
            intent.putExtra("garage_id", garage_id);
            intent.putExtra("photo", photo);
            intent.putExtra("email", _email);
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            registForFacebook(name, _email, ID);

            startActivity(intent);
            finish();

            Toast.makeText(this, "facebook : " + name + "\n" + _email, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void loginFacebook() {

        callbackManager = CallbackManager.Factory.create();
        loginButtonFacebook.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Retrieving data...");
                progressDialog.show();
                ID = loginResult.getAccessToken().getUserId();
                garage_id = ID;

//                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("facebook", object.toString());


                            getData(object, ID);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, " error/n" + e, Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(LoginActivity.this, "here", Toast.LENGTH_LONG).show();


                    }
                });
                Bundle params = new Bundle();
                params.putString("fields", "first_name, last_name, email, id");
                graphRequest.setParameters(params);
                graphRequest.executeAsync();
            }


            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
//                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("id", "2");
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void onStart() {
        super.onStart();
        accessTokenTracker.startTracking();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        useLoginInformation(accessToken);

//        AccessToken  accessToken  = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        Toast.makeText(LoginActivity.this, "Login status : " + isLoggedIn, Toast.LENGTH_SHORT).show();
        if (isLoggedIn) {
            useLoginInformation(accessToken);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", "2");
            intent.putExtra("garage_id", garage_id);
            intent.putExtra("email", _email);

            startActivity(intent);
            finish();
        }

    }


    public void onDestroy() {
        super.onDestroy();
        // We stop the tracking before destroying the activity
        accessTokenTracker.stopTracking();

    }


}


/**
 * for logout
 * LoginManager.getInstance().logOut();
 */
