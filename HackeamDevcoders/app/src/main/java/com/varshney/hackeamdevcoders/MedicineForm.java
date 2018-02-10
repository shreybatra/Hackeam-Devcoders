package com.varshney.hackeamdevcoders;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MedicineForm extends AppCompatActivity {

    EditText etMedicine1, etMedicine2, etMedicine3, etMedicine4;
    EditText etQty1, etQty2, etQty3, etQty4;
    Button btnFindShopkeeper;
    TextView tvMedicines, tvQty;
    public static final int REQUEST_LOCATION =1;
    LocationManager locationManager;
    LocationListener locationListener;
    public static final String TAG = "MedicineForm";
    public static final String URL = "https://37c1fcc7.ngrok.io/sendreqs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_form);


        btnFindShopkeeper = findViewById(R.id.btnFindShopKeeper);
        etMedicine1 = findViewById(R.id.etMedicine1);
        etMedicine2 = findViewById(R.id.etMedicine2);
        etMedicine3 = findViewById(R.id.etMedicine3);
        etMedicine4 = findViewById(R.id.etMedicine4);

        etQty1 = findViewById(R.id.etQty1);
        etQty2 = findViewById(R.id.etQty2);
        etQty3 = findViewById(R.id.etQty3);
        etQty4 = findViewById(R.id.etQty4);

        tvMedicines = findViewById(R.id.tvMedicines);
        tvQty = findViewById(R.id.tvQty);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final JSONObject jsonObject = new JSONObject();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final Location latLong = getLocation();
        Toast.makeText(this, latLong.getLatitude() +"Long" +latLong.getLongitude(), Toast.LENGTH_SHORT).show();
        btnFindShopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String med = "";
                String qty = "";
                final String[] lat = {""};
                final String[] lon = {""};

                tvMedicines.setText("");
                tvQty.setText("");

                try {

                    if (!etMedicine1.getText().toString().trim().equalsIgnoreCase("")) {
                        if (etQty1.getText().toString().trim().equalsIgnoreCase("")) {
                            etQty1.setError("Fill This");
                        } else {
                            med += etMedicine1.getText().toString();
                            qty += etQty1.getText().toString();
                        }
                    }

                    if (!etMedicine2.getText().toString().trim().equalsIgnoreCase("")) {
                        if (etQty2.getText().toString().trim().equalsIgnoreCase("")) {
                            etQty2.setError("Fill This");
                        } else {
                            med += "$" + etMedicine2.getText().toString();
                            qty += "$" + etQty2.getText().toString();
                        }
                    }
                    if (!etMedicine3.getText().toString().trim().equalsIgnoreCase("")) {
                        if (etQty3.getText().toString().trim().equalsIgnoreCase("")) {
                            etQty3.setError("Fill This");
                        } else {
                            med += "$" + etMedicine3.getText().toString();
                            qty += "$" + etQty3.getText().toString();
                        }
                    }
                    if (!etMedicine4.getText().toString().trim().equalsIgnoreCase("")) {
                        if (etQty4.getText().toString().trim().equalsIgnoreCase("")) {
                            etQty4.setError("Fill This");
                        } else {
                            med += "$" + etMedicine4.getText().toString();
                            qty += "$" + etQty4.getText().toString();
                        }
                    }

                    jsonObject.put("username", "chutia");
                    Log.d(TAG, "onInsideReq: " + med + " qty: " + qty);
                    jsonObject.put("medicines", med);
                    jsonObject.put("quantities", qty);
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

                tvMedicines.setText(med);
                tvQty.setText(qty);
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
