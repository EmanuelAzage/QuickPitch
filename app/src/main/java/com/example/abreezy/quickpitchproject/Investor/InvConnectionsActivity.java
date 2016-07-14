package com.example.abreezy.quickpitchproject.Investor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.abreezy.quickpitchproject.R;

import java.util.Vector;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;

/**
 * wasn't created by Emanuel_Azage on 4/7/16.
 */

public class
InvConnectionsActivity extends AppCompatActivity{

    private QuickPitchClient clientInstance;
    private InvestorAccount myInvAccount;
    private Vector<EntrepreneurAccount> connections;
    private String[] connectionNames;
    private Button mProfileButton;
    private Button mFeedButton;
    private Button mLikesButton;
    private Button mConnectionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_inv_connections);

        mProfileButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invProfileButton);
        mFeedButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.FeedButton);
        mLikesButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.likesButton);

        mConnectionsButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invConnectionsButton);
        mConnectionsButton.getBackground().setColorFilter(getResources().getColor(R.color.tabbedPressedColor), PorterDuff.Mode.MULTIPLY);
        // gets client instance, investorAccount, and list of connections
        clientInstance = QuickPitchClient.getInstance();
        myInvAccount = clientInstance.getMyInvAccount();
        connections = myInvAccount.getConnections();

        connectionNames = new String[connections.size()];
        int i = 0;

        // get connection names and send to populate list view
        for (EntrepreneurAccount connection : connections) {
            connectionNames[i] = connection.getCompanyName();
            i++;
        }

        createTab();

        populateListView(connectionNames);
    }

    private void createTab(){
        final Context thisActivity = this;

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_right, com.example.abreezy.quickpitchproject.R.anim.pull_in_left);
            }
        });

        mFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvFeedActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_right, com.example.abreezy.quickpitchproject.R.anim.pull_in_left);
            }
        });

        mLikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvLikesActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_right, com.example.abreezy.quickpitchproject.R.anim.pull_in_left);
            }
        });
    }

    private void populateListView(String[] connectionNames){
        //create List of Items
        String[] entList = connectionNames;

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                com.example.abreezy.quickpitchproject.R.layout.items,
                entList
        );

        //Configure the list view
        ListView connectionsList = (ListView)findViewById(R.id.connectionsListView);
        connectionsList.setAdapter(adapter);

        connectionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InvConnectionsActivity.this, InvConnectionsProfilesActivity.class);
                intent.putExtra("index", String.format("%d", position));
                startActivity(intent);
            }
        });

    }
}
