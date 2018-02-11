package com.varshney.hackeamdevcoders_partners;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yash on 11/2/18.
 */

public class BootReceiver extends Activity{

    public static final String TAG = "BroadCast receiver";



    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra("list");
            Toast.makeText(context, s.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceive: " + s.toString());

        }
    };

}
