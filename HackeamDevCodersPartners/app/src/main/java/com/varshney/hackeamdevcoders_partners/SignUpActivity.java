package com.varshney.hackeamdevcoders_partners;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText etEmail,etUserName,etPassword;
    public static final int REQUEST_LOCATION =1;
    public static final String TAG = "SignUpActivity";
    LocationManager locationManager;
    LocationListener locationListener;
    public static final String URL = "https://9e35aacd.ngrok.io/addpharmacy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        etEmail = findViewById(R.id.etEmail);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final JSONObject jsonObject = new JSONObject();

        final Location latLong = getLocation();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    jsonObject.put("username",etUserName.getText().toString());
                    jsonObject.put("password",etPassword.getText().toString());
                    jsonObject.put("email",etEmail.getText().toString());
                    jsonObject.put("latitude",latLong.getLatitude());
                    jsonObject.put("longitude",latLong.getLongitude());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                });
                queue.add(request);

            }
        });

    }
    Location getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location!=null)
        {
            return location;
        }
        return null;
    }
}
