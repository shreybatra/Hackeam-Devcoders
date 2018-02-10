package com.varshney.hackeamdevcoders_partners;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoardActivity extends AppCompatActivity {


    TextView tvUserEmail;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        user = FirebaseAuth.getInstance().getCurrentUser();
        tvUserEmail = findViewById(R.id.tvUserEmail);

        tvUserEmail.setText(user.getEmail().toString());

    }
}
