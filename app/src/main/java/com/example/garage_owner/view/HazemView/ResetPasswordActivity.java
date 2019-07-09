package com.example.garage_owner.view.HazemView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText new_password, confirm_password;
    Button btn_reset;
    ProgressBar loading;
    private static String URL_REGIST = "https://rakna-app.000webhostapp.com/reset_password.php";//"https://rakna-app.000webhostapp.com/register.php";
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        btn_reset = (Button)findViewById(R.id.btn_reset);
        loading = (ProgressBar)findViewById(R.id.loading);

        Intent intent =getIntent();
        email = intent.getStringExtra("email").trim();
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkDataEntered()) {
                    resetPassword();
                }
            }
        });
    }



    private void resetPassword(){
        loading.setVisibility(View.VISIBLE);
        btn_reset.setVisibility(View.GONE);
//        Intent intent = getIntent();
        final String emailToSend = email;//"s.enghazemallm@gmail.com";//intent.getStringExtra("email");
        final String newPassword = this.new_password.getText().toString().trim();
        Toast.makeText(this, "email : "+email+"\n"+"newPassword: "+newPassword, Toast.LENGTH_SHORT).show();
        Log.d("token","email : "+emailToSend+"\n"+"newPassword: "+newPassword );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    if(success.equals("1")){
                    Toast.makeText(ResetPasswordActivity.this, "Register success!",Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_reset.setVisibility(View.VISIBLE);



//                    }
                }catch (JSONException e){
                    e.printStackTrace();
//                    Toast.makeText(RegistrationActivity.this, "Register error! 15 \n"+ e.toString(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    finish();
                    Toast.makeText(ResetPasswordActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_reset.setVisibility(View.VISIBLE);
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ResetPasswordActivity.this, "Register error! 16 \n"+ error.toString(),Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_reset.setVisibility(View.VISIBLE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", newPassword);


                return params;
            }
        };
        // MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public static boolean isPasswordValidMethod(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean checkDataEntered(){
        boolean flag = false;

        if (new_password.getText().toString().trim().length() < 8 && !isPasswordValidMethod(new_password.getText().toString().trim()) ){
            new_password.setError("Enter valid password_");
            flag = true;
        }

        if(!confirm_password.getText().toString().trim().equals(new_password.getText().toString().trim())){
            confirm_password.setError("Enter the same passwor you entered");
            flag = true;
        }
        return flag;
    }

}
