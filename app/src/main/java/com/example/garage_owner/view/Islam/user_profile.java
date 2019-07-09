package com.example.garage_owner.view.Islam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garage_owner.R;
import com.example.garage_owner.view.HazemView.LoginActivity;
import com.example.garage_owner.view.HazemView.MainActivity;
import com.example.garage_owner.view.SaeedView.history_frag;
import com.facebook.login.LoginManager;

import java.util.HashMap;
import java.util.Map;


public class user_profile extends Fragment{
    TextView user_name, phone, email, old, new_, confirm, national_id, latitude, longitude, width, length, height;
    AlertDialog dialog, d1, d2, d3, d4, d5, d6, d7, d8;
    EditText editText, et1, et2, et3, et4, et5, et6, et7, et8;
    Button save;
    ImageButton GPS, More;

    CheckBox wash, lubrication, maintenance, oil_change;
    int wash_action = 0;
    int lubrication_action = 0;
    int maintenance_action = 0;
    int oil_change_action = 0;

    LocationManager locationManager;
    String latitude_, longitude_;



    private final String URL_UPDATE = "https://rakna-app.000webhostapp.com/update_garage_profile.php";



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        More = v.findViewById(R.id.btn_menu);

        email = v.findViewById(R.id.email);
        save = v.findViewById(R.id.save);
        GPS = v.findViewById(R.id.GPS);
        latitude = v.findViewById(R.id.latitude);
        longitude = v.findViewById(R.id.longitude);

        wash = v.findViewById(R.id.wash);
        lubrication = v.findViewById(R.id.lubrication);
        maintenance = v.findViewById(R.id.maintenance);
        oil_change = v.findViewById(R.id.oil_change);



        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.history:
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                Fragment his = new history_frag();
                                ft.add(R.id.fragment_container, his);
                                ft.commit();
                                break;
                            case R.id.logout_:
                                if (MainActivity.id.equals("2") ) {////facebook logout
                                    LoginManager.getInstance().logOut();
                                    getActivity().finish();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    getActivity().finish();
                                    startActivity(intent);
                                }
                                else if (MainActivity.id.equals("1")){//login logout
                                    LoginActivity.editor.putString(getString(R.string.checkbox), "False");
                                    LoginActivity.editor.commit();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                }
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.inflate(R.menu.more_profile);
                popupMenu.show();
            }
        });
//
//
//
//
        // email
        email.setText(MainActivity.email);
        Log.d("profileUser", MainActivity.commingDataArray[0]);


        // user name dialog
        user_name = v.findViewById(R.id.user_name);
        dialog = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        editText = new EditText(getActivity());
        editText.setTextColor(Color.WHITE);
        dialog.setTitle(Html.fromHtml("<font color='#009688'>User name</font>"));
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().trim().length() != 0)
                    user_name.setText(editText.getText());
            }
        });
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(user_name.getText().toString().trim());
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });


        // phone dialog
        phone = v.findViewById(R.id.phone);
        d1 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et1 = new EditText(getActivity());
        et1.setTextColor(Color.WHITE);
        d1.setTitle(Html.fromHtml("<font color='#009688'>Phone</font>"));
        d1.setView(et1);

        d1.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et1.getText().toString().trim().length() != 0)
                    phone.setText(et1.getText());
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText(phone.getText().toString().trim());
                d1.show();
                d1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });


        // national id num
        national_id = v.findViewById(R.id.national_id);
        d5 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et5 = new EditText(getActivity());
        et5.setTextColor(Color.WHITE);
        d5.setTitle(Html.fromHtml("<font color='#009688'>National ID number</font>"));
        d5.setView(et5);

        d5.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et5.getText().toString().trim().length() != 0)
                    national_id.setText(et5.getText());
            }
        });
        national_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et5.setText(national_id.getText().toString().trim());
                d5.show();
                d5.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d5.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });


        // old password_ dialog
        old = v.findViewById(R.id.old_);
        d2 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et2 = new EditText(getActivity());
        et2.setTextColor(Color.WHITE);
        et2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        d2.setTitle(Html.fromHtml("<font color='#009688'>Old password_</font>"));
        d2.setView(et2);

        d2.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et2.getText().toString().trim().length() != 0)
                    old.setText(et2.getText());
                old.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d2.show();
                d2.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d2.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });


        // new password_ dialog
            new_ = v.findViewById(R.id.new_);
            d3 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
            et3 = new EditText(getActivity());
            et3.setTextColor(Color.WHITE);
            et3.setTransformationMethod(PasswordTransformationMethod.getInstance());
            d3.setTitle(Html.fromHtml("<font color='#009688'>New password_</font>"));
            d3.setView(et3);

            d3.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (et2.getText().toString().trim().length() == 0) {
                        Toast.makeText(getActivity(), "Please enter old password_", Toast.LENGTH_SHORT).show();
                    }
                    else if (et3.getText().toString().trim().length() != 0) {
                        new_.setText(et3.getText());
                        new_.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            });
            new_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d3.show();
                    d3.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                    d3.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
                }
            });



        // confirm password_ dialog
        confirm = v.findViewById(R.id.confirm_);
        d4 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et4 = new EditText(getActivity());
        et4.setTextColor(Color.WHITE);
        et4.setTransformationMethod(PasswordTransformationMethod.getInstance());
        d4.setTitle(Html.fromHtml("<font color='#009688'>Confirm password_</font>"));
        d4.setView(et4);

        d4.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (et3.getText().toString().trim().equals(et4.getText().toString().trim())){
                    if (et4.getText().toString().trim().length() != 0)
                        confirm.setText(et4.getText());
                        confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    Toast.makeText(getActivity(), "Error confirm password_", Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d4.show();
                d4.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d4.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude_ = String.valueOf(location.getLatitude());
                longitude_ = String.valueOf(location.getLongitude());
                latitude.setText(latitude_);
                longitude.setText(longitude_);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
            }
        };

        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
                }
                catch(SecurityException e) {
                    e.printStackTrace();
                }
            }
        });


        // width dialog
        width = v.findViewById(R.id.width);
        d6 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et6 = new EditText(getActivity());
        et6.setTextColor(Color.WHITE);
        d6.setTitle(Html.fromHtml("<font color='#009688'>Width</font>"));
        d6.setView(et6);

        d6.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et6.getText().toString().trim().length() != 0 &&
                        Double.parseDouble(et6.getText().toString().trim()) < 3.00){
                    Toast.makeText(getActivity(), "Width should be more than 3m", Toast.LENGTH_SHORT).show();
                }
                else if (et6.getText().toString().trim().length() != 0 &&
                        Double.parseDouble(et6.getText().toString().trim()) >= 3.00) {
                    width.setText(et6.getText());
                }
            }
        });
        width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et6.setText(width.getText().toString().trim());
                d6.show();
                d6.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d6.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });



        // length dialog
        length = v.findViewById(R.id.length);
        d7 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et7 = new EditText(getActivity());
        et7.setTextColor(Color.WHITE);
        d7.setTitle(Html.fromHtml("<font color='#009688'>Length</font>"));
        d7.setView(et7);

        d7.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et7.getText().toString().trim().length() != 0 &&
                        Double.parseDouble(et7.getText().toString().trim()) < 4.00){
                    Toast.makeText(getActivity(), "Length should be more than 4m", Toast.LENGTH_SHORT).show();
                }
                else if (et7.getText().toString().trim().length() != 0 &&
                        Double.parseDouble(et7.getText().toString().trim()) >= 4.00) {
                    length.setText(et7.getText());
                }
            }
        });
        length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et7.setText(length.getText().toString().trim());
                d7.show();
                d7.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d7.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });


        // height dialog
        height = v.findViewById(R.id.height);
        d8 = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog).create();
        et8 = new EditText(getActivity());
        et8.setTextColor(Color.WHITE);
        d8.setTitle(Html.fromHtml("<font color='#009688'>Height</font>"));
        d8.setView(et8);

        d8.setButton(DialogInterface.BUTTON_POSITIVE, " SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et8.getText().toString().trim().length() != 0 &&
                        Double.parseDouble(et8.getText().toString().trim()) < 3.50){
                    Toast.makeText(getActivity(), "Height should be more than 3.5m", Toast.LENGTH_SHORT).show();
                }
                else if (et8.getText().toString().trim().length() != 0 &&
                        Double.parseDouble(et8.getText().toString().trim()) >= 3.50) {
                    height.setText(et8.getText());
                }
            }
        });
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et8.setText(height.getText().toString().trim());
                d8.show();
                d8.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alert));
                d8.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alert));
            }
        });






        // save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String _national_id_number = national_id.getText().toString().trim();
                final String _name = user_name.getText().toString().trim();
                final String _phone = phone.getText().toString().trim();
                final String _lat = latitude.getText().toString().trim();
                final String _password = new_.getText().toString().trim();
                final String _lng = longitude.getText().toString().trim();
                final String _width = width.getText().toString().trim();
                final String _length = length.getText().toString().trim();
                final String _height = height.getText().toString().trim();

                // checkbox
                if (wash.isChecked()){
                    wash_action = 1;
                }
                if (lubrication.isChecked()){
                    lubrication_action = 1;
                }
                if (maintenance.isChecked()){
                    maintenance_action = 1;
                }
                if (oil_change.isChecked()){
                    oil_change_action = 1;
                }

                if (_lat.equals("Latitude") || _lng.equals("Longitude")){
                    Toast.makeText(getActivity(), "Please enter Latitude and Longitude !", Toast.LENGTH_SHORT).show();
                }


                if (_width.equals("Garage width") || _length.equals("Garage length")
                        || _height.equals("Garage height")){
                    Toast.makeText(getActivity(), "Please enter garage information !", Toast.LENGTH_SHORT).show();
                }


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", MainActivity.email);
                        params.put("national_id_number", _national_id_number);
                        params.put("name", _name);
                        params.put("phone", _phone);
                        params.put("lat", _lat);
                        params.put("password_", _password);
                        params.put("lng", _lng);
                        params.put("wash", String.valueOf(wash_action));
                        params.put("lubrication", String.valueOf(lubrication_action));
                        params.put("maintenance", String.valueOf(maintenance_action));
                        params.put("oil_change", String.valueOf(oil_change_action));
                        params.put("garage_width", _width);
                        params.put("garage_length", _length);
                        params.put("garage_height", _height);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);

            }

        });
        validation();

        return v;
    }


private void validation(){
                user_name.setText(MainActivity.commingDataArray[0]);
                    phone.setText(MainActivity.commingDataArray[1]);
                    if (MainActivity.commingDataArray[2].equals("null")){
                        national_id.setText("National ID number");
                    }else {
                        national_id.setText(MainActivity.commingDataArray[2]);
                    }
                    if (MainActivity.commingDataArray[3].equals("null")){
                        latitude.setText("Latitude");
                    }else {
                        latitude.setText(MainActivity.commingDataArray[3]);
                    }
                    if (MainActivity.commingDataArray[4].equals("null")){
                        longitude.setText("Longitude");
                    }else {
                        longitude.setText(MainActivity.commingDataArray[4]);
                    }
                    if (MainActivity.commingDataArray[5].equals("1")){
                        wash.setChecked(true);
                    }
                    if (MainActivity.commingDataArray[6].equals("1")){
                        lubrication.setChecked(true);
                    }
                    if (MainActivity.commingDataArray[7].equals("1")){
                        maintenance.setChecked(true);
                    }
                    if (MainActivity.commingDataArray[8].equals("1")){
                        oil_change.setChecked(true);
                    }
                    if (MainActivity.commingDataArray[9].equals("null")){
                        width.setText("Garage width");
                    }else {
                        width.setText(MainActivity.commingDataArray[9]);
                    }
                    if (MainActivity.commingDataArray[10].equals("null")){
                        length.setText("Garage length");
                    }else {
                        length.setText(MainActivity.commingDataArray[10]);
                    }
                    if (MainActivity.commingDataArray[11].equals("null")){
                        height.setText("Garage height");
                    }else {
                        height.setText(MainActivity.commingDataArray[11]);
                    }
                    if (MainActivity.commingDataArray[1].equals("null")){
                        phone.setText("Phone");
                    }else {
                        phone.setText(MainActivity.commingDataArray[1]);
                    }


}
}
