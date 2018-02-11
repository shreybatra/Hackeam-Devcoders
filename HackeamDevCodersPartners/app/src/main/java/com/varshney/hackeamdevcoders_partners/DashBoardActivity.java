package com.varshney.hackeamdevcoders_partners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    ArrayList<RequestPOJO> pojoArrayList;
    //RecyclerView rvMedicineList;
    TextView tv1,tv2;


public static final String TAG= "DashBoard";
    //FirebaseUser user;
    MedicineListAdapter medicineListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);


        //rvMedicineList = findViewById(R.id.rvMedicines);
        Log.d(TAG, "onCreate: DashBoard");
        Toast.makeText(this, "DashBoard", Toast.LENGTH_SHORT).show();
        pojoArrayList = new ArrayList<>();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("Message"));
        //Log.d(TAG, "onCreate: "+i.getStrithisngExtra("list").toString());
        //pojoArrayList = (ArrayList<RequestPOJO>) i.getSerializableExtra("list");
            //Log.d(TAG, "onCreate: "+pojoArrayList.size());




    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //rvMedicineList.setLayoutManager(new LinearLayoutManager(context));

             pojoArrayList = (ArrayList<RequestPOJO>) intent.getSerializableExtra("list");
            Log.d(TAG, "onReceive: "+pojoArrayList.get(0).getMedicines().toString());

            //tv1.setText(""+pojoArrayList.size());
          // medicineListAdapter = new MedicineListAdapter(context,pojoArrayList);
            //rvMedicineList.setAdapter(medicineListAdapter);
            String ss = "";

            for(int i=pojoArrayList.size()-1;i>=0;i--)
            {
                ss += pojoArrayList.get(i).getMedicines().toString()+"\n";
            }

            tv1.setText(ss);

            ss = "";

            for(int i=pojoArrayList.size()-1;i>=0;i--)
            {
                ss += pojoArrayList.get(i).getQuantities().toString()+"\n";
            }
            //tv1.setText(pojoArrayList.get(0).getMedicines().toString());

            tv2.setText(ss);

        }
    };
}
