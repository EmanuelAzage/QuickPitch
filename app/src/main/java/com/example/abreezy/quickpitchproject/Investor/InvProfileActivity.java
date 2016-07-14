package com.example.abreezy.quickpitchproject.Investor;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.abreezy.quickpitchproject.Login.LoginActivity;
import com.example.abreezy.quickpitchproject.R;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;

public class InvProfileActivity extends AppCompatActivity {

    private QuickPitchClient clientInstance;
    private InvestorAccount myInvAccount;
    private Button mProfileButton;
    private Button mFeedButton;
    private Button mLikesButton;
    private Button mConnectionsButton;
    private Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getActionBar().hide();
        getSupportActionBar().hide();
        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_inv_profile);

        mProfileButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invProfileButton);
        mProfileButton.getBackground().setColorFilter(getResources().getColor(R.color.tabbedPressedColor), PorterDuff.Mode.MULTIPLY);

        mFeedButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.FeedButton);
        mLikesButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.likesButton);
        mConnectionsButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invConnectionsButton);
        mLogoutButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.logoutButton);

        clientInstance = QuickPitchClient.getInstance();
        myInvAccount = clientInstance.getMyInvAccount();

        createTabs();
        loadInvestorProfile();
    }

    public static void setEntrepreneur(String ProductName, String CompanyName){
        EntrepreneurAccount likedAccount = null;
        /*  */
    }


    private void loadInvestorProfile(){
        //username
        //email
        //company name

        TextView investorNameView = (TextView) findViewById(R.id.investorNameView);
        TextView investorEmailView = (TextView) findViewById(R.id.investorEmailView);
        TextView investorCompanyView = (TextView) findViewById(R.id.investorCompanyView);

        investorNameView.setText(myInvAccount.getUsername());
        investorEmailView.setText(myInvAccount.getEmail());
        investorCompanyView.setText(myInvAccount.getCompanyName());
    }

    private void createTabs(){
        final Context thisActivity = this;

        mConnectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvConnectionsActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_left, com.example.abreezy.quickpitchproject.R.anim.pull_in_right);

            }
        });

        mFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvFeedActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_left, com.example.abreezy.quickpitchproject.R.anim.pull_in_right);
            }
        });

        mLikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvLikesActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_left, com.example.abreezy.quickpitchproject.R.anim.pull_in_right);
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_left, com.example.abreezy.quickpitchproject.R.anim.pull_in_right);
            }
        });
    }
}
