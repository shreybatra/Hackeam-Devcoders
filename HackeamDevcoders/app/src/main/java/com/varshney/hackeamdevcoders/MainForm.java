package com.varshney.hackeamdevcoders;

import android.content.Intent;
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

public class MainForm extends AppCompatActivity {

    EditText etUserNameSign, etEmailSign, etPasswordSign;
    Button btnSignUp;

    public static final String TAG = "MAIn FOrm";
    public static final String URL = "https://5124ff94.ngrok.io/addclient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);


        final RequestQueue queue = Volley.newRequestQueue(this);
        final JSONObject jsonObject = new JSONObject();

        etUserNameSign = findViewById(R.id.etUserNameSign);
        etEmailSign = findViewById(R.id.etEmailSign);
        etPasswordSign = findViewById(R.id.etPasswordSign);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {



                    jsonObject.put("username",etUserNameSign.getText().toString());
                    jsonObject.put("password",etPasswordSign.getText().toString());
                    jsonObject.put("email",etEmailSign.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        startActivity(new Intent(MainForm.this,MainActivity.class));
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
}
