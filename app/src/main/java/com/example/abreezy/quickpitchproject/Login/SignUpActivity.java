package com.example.abreezy.quickpitchproject.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.abreezy.quickpitchproject.Entrepreneur.EntSignUpActivity;
import com.example.abreezy.quickpitchproject.Investor.InvProfileActivity;
import com.example.abreezy.quickpitchproject.Investor.InvSignUpActivity;
import com.example.abreezy.quickpitchproject.R;

public class SignUpActivity extends AppCompatActivity {
    private Button mInvestorButton;
    private Button mEntrepreneurButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mInvestorButton = (Button)findViewById(R.id.investorButton);
        mEntrepreneurButton = (Button)findViewById(R.id.entrepreneurButton);
        mInvestorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(SignUpActivity.this, InvSignUpActivity.class);
                    startActivity(intent);
            }
        });
        mEntrepreneurButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(SignUpActivity.this, EntSignUpActivity.class);
                    startActivity(intent);
            }
        });
    }












}
