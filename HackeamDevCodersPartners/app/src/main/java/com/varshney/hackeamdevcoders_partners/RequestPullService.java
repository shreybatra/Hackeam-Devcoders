package com.varshney.hackeamdevcoders_partners;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yash on 11/2/18.
 */

public class RequestPullService extends Service {



    ArrayList <RequestPOJO> finalList;


    final static String MY_ACTION = "MY_ACTION";
    public static final String TAG = "Service";
    //public static final String URL = "https://devcoders.herokuapp.com/requestpharmacy";
    public static final String URL = "https://5124ff94.ngrok.io/requestpharmacy";
    private Timer mTimer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(timerTask,10000,10000);
        finalList = new ArrayList<>();
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            notifiy();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);

    }
/*

    @Override
    public void onDestroy() {
        super.onDestroy();

        try{
            mTimer.cancel();
            timerTask.cancel();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.varshney.hackeamdevcoders_partners");
        intent.putExtra("youralue","torestore");
        sendBroadcast(intent);
    }
*/

    JSONArray arr;
    JSONObject jso;
    JSONArray medicines;
    JSONArray quantities;

    ArrayList<String> meds;
    ArrayList<Integer> quants;

    //RequestPOJO rpojo;



    public void notifiy()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RSSPullService");

        //rpojo = new RequestPOJO();

        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        final Context context = getApplicationContext();

        Notification.Builder builder;

        final RequestQueue queue = Volley.newRequestQueue(context);
        final JSONObject jsonObject = new JSONObject();

//        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        try {

            jsonObject.put("username",user.getEmail().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response.toString());




                try {
                    arr = response.getJSONArray("requests");



                    int n = arr.length();

                    if(n>0)
                    {
                        for(int i=0;i<n;i++)
                        {
                            jso = (JSONObject) arr.get(i);

                            //Log.d(TAG, "onResponse: "+jso.toString());
                            /*
                            rpojo.setLatitude(Double.parseDouble(jso.getString("latitude")));
                            rpojo.setLongitude(Double.parseDouble(jso.getString("longitude")));
                            rpojo.setUsername(jso.getString("username").toString());
                            JSONArray jarr = jso.getJSONArray("medicines");
                            for()
                            //rpojo.setMedicines(jso.get("medicines"));
                            Log.d(TAG, "Medicines: "+rpojo.getMedicines().toString());
                            */
                            medicines = jso.getJSONArray("medicines");
                            quantities = jso.getJSONArray("quantities");

                            Log.d("Medicines",medicines.toString());

                            Log.d("Quantities",quantities.toString());

                            RequestPOJO rpojo = new RequestPOJO();

                            meds = new ArrayList<>();
                            quants= new ArrayList<>();

                            int medlength = medicines.length();
                            //int quanlength = quantities.length();

                            for(int j=0;j<medlength;j++)
                            {
                                meds.add(medicines.get(j).toString());
                                quants.add(Integer.parseInt(quantities.get(j).toString()));
                            }

                            rpojo.setMedicines(meds);
                            rpojo.setQuantities(quants);
                            finalList.add(rpojo);

                            Log.d(TAG, "onResponse: "+finalList.get(i).getMedicines().toString());


/*
                            // Intent in = new Intent(context,DashBoardActivity.class);
                            Intent in = new Intent();
                            in.setAction("Message");
                            in.putExtra("list", rpojo);
                            Log.d(TAG, "onResponse: Hello Yash");
                            //startActivity(in);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(in);
                            //Log.d("Final List",finalList.get(0).getMedicines().toString());
*/

                        }
                    }






                    if(n>0)
                    {
                        // Intent in = new Intent(context,DashBoardActivity.class);
                        Intent in = new Intent();
                        in.setAction("Message");
                        in.putExtra("list",(Serializable) finalList);
                        Log.d(TAG, "onResponse: Hello Yash");
                        //startActivity(in);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(in);
                        //Log.d("Final List",finalList.get(0).getMedicines().toString());
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*

                String s = response.toString();

                Log.d(TAG, "onResponse: "+s);

                int n = s.length();


                if(n>13)
                {
                    String latitude = "";

                    int i = 25;

                    do {
                        latitude += s.charAt(i); // latitude
                        i++;
                    }while(s.charAt(i)!='"');

                    i += 15;

                    String longitude = "";

                    do {
                        longitude += s.charAt(i); // longitude
                        i++;
                    }while(s.charAt(i)!='"');

                    i += 15;

                    ArrayList<String> medicines = new ArrayList<>();
                    ArrayList<Double> quantities = new ArrayList<>();

                    String ss = "";

                    int j = 0;
                    do {
                        if(s.charAt(i)=='"' && s.charAt(i-1)!=',' && s.charAt(i-1)!='[')
                        {
                            medicines.add(ss);
                            ss = "";
                        }
                        if(s.charAt(i)=='"' || s.charAt(i)==',')
                            i++;
                        else
                        {
                            ss += s.charAt(i); // medicines
                            i++;
                        }

                    }while(s.charAt(i)!=']');

                    i += 16;

                    ss = "";
                    j = 0;
                    do {
                        if(s.charAt(i)=='"' && s.charAt(i-1)!=',')
                        {
                            quantities.add(Double.parseDouble(ss));
                            ss = "";
                        }
                        if(s.charAt(i)=='"' || s.charAt(i)==',')
                            i++;
                        else
                        {
                            ss += s.charAt(i); // medicines
                            i++;
                        }

                    }while(s.charAt(i)!=']');

                    i += 17;

                    ss = "";

                    do{
                        ss += s.charAt(i);
                    }while(s.charAt(i)!=',' && s.charAt(i)!='}');

                    Integer timme = Integer.parseInt(ss);

                    i += 13;

                    ss = "";

                    do{
                        ss+= s.charAt(i);
                    }while(s.charAt(i)!='"');

                    String username = ss;

                    Log.d(TAG, "onResponse: Med"+medicines.get(0));
                    Log.d(TAG, "onResponse: Med"+quantities.get(0));
                    Log.d(TAG, "onResponse: Med"+username);

                    Log.d(TAG, "onResponse: "+ss);
                }
                */


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        queue.add(request);
        builder = new Notification.Builder(context)
                .setContentTitle("T")
                .setContentText("M")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);


    }
}
