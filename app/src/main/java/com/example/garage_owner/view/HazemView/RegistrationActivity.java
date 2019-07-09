package com.example.garage_owner.view.HazemView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garage_owner.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, email, pass, c_password, phone, id_number, id_bank_account;
    private Button btn_regist;
    private ProgressBar loading;
    ImageView backbutoon;
    private  String URL_REGIST = "https://rakna-app.000webhostapp.com/Garage_Owner.php";//"https://rakna-app.000webhostapp.com/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        loading = (ProgressBar)findViewById(R.id.loading);
        name = (EditText)findViewById(R.id.name_main);
        email = (EditText)findViewById(R.id.email_main);
        phone = (EditText)findViewById(R.id.phone);
        id_number = (EditText)findViewById(R.id.id_number);
        id_bank_account = (EditText)findViewById(R.id.id_bank_account);
        pass = (EditText)findViewById(R.id.password_main);
        c_password = (EditText)findViewById(R.id.c_password);
        btn_regist = (Button)findViewById(R.id.btn_regist);
        backbutoon = findViewById(R.id.backbuton);

        backbutoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });


        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkDataEntered()) {
                    regist();
                    name.setText("");
                    email.setText("");
                    phone.setText("");
                    id_number.setText("");
                    pass.setText("");
                    c_password.setText("");
                    id_bank_account.setText("");
                }

            }
        });

    }



    private void regist(){
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String pass = this.pass.getText().toString().trim();
        final String phone = this.phone.getText().toString().trim();
        final String id_number = this.id_number.getText().toString().trim();
        final String id_bank_account = this.id_bank_account.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                      if(success.equals("1")){
                    Toast.makeText(RegistrationActivity.this, "Register success!",Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_regist.setVisibility(View.VISIBLE);


                          Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                          intent.putExtra("id", "1");
                          intent.putExtra("garage_id", id_number);
                          intent.putExtra("email", email);
                          startActivity(intent);

                    }
                }catch (JSONException e){
                    e.printStackTrace();
//                    Toast.makeText(RegistrationActivity.this, "Register error! 15 \n"+ e.toString(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegistrationActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_regist.setVisibility(View.VISIBLE);
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegistrationActivity.this, "Register error! 16 \n"+ error.toString(),Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_regist.setVisibility(View.VISIBLE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("email", email);
                params.put("password",pass);
                params.put("phone", phone);
                params.put("id_number",id_number );
                params.put("bank_account", id_bank_account);

                return params;
            }
        };
        // MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    ///////////////////validation////////////////////////////////////////////////////////

    public boolean isEmail(EditText text){
        CharSequence email = text.getText().toString().trim();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString().trim();
        return TextUtils.isEmpty(str);
    }
    public static boolean isPasswordValidMethod(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isIdValidMethod(final String id) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^[0-9]{14}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(id);

        return matcher.matches();

    }

    public static boolean isBankAccountValidMethod(final String id) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^[0-9]{16}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(id);

        return matcher.matches();

    }

    public static boolean isPhoneNumberValidMethod(final String id) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^01[0-9]{9}(\\-)?[^0\\D]{1}\\d{6}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(id);

        return matcher.matches();

    }

    public boolean checkDataEntered(){
        boolean flag = false;
        if(isEmpty(name)){
            name.setError("required!");
            flag = true;
        }
        if(isEmail(email)== false){

            email.setError("Enter valid email!");
            flag = true;
        }
        if (pass.getText().toString().trim().length() < 8 && !isPasswordValidMethod(pass.getText().toString().trim()) ){
            pass.setError("Enter valid password_");
            flag = true;
        }
        if (phone.getText().toString().trim().length() < 11 && !isPhoneNumberValidMethod(phone.getText().toString().trim()) ){
            phone.setError("Enter valid mobile number");
            flag = true;
        }
        if (id_number.getText().toString().trim().length() < 14 && !isIdValidMethod(id_number.getText().toString().trim()) ){
            id_number.setError("Enter valid national id");
            flag = true;
        }
        if (id_bank_account.getText().toString().trim().length() < 16 && !isBankAccountValidMethod(id_bank_account.getText().toString().trim()) ){
            id_bank_account.setError("Enter valid Bank Account id");
            flag = true;
        }
        if(!c_password.getText().toString().trim().equals(pass.getText().toString().trim())){
            c_password.setError("Enter the same passwor you entered");
            flag = true;
        }
        return flag;
    }

}



