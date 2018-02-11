package com.varshney.hackeamdevcoders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {


    Button btnForm;
    Button btnMedicineForm;

    ViewFlipper vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vf = findViewById(R.id.vf);
        vf.setAutoStart(true);
        btnForm = findViewById(R.id.btnForm);
        btnMedicineForm = findViewById(R.id.btnForm1);
        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainForm.class));
            }
        });
         btnMedicineForm.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, LoginActivity.class));
             }
         });

    }
}
