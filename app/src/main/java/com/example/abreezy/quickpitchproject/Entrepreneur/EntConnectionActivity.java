package com.example.abreezy.quickpitchproject.Entrepreneur;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.abreezy.quickpitchproject.R;

import java.util.Vector;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.RefreshEntrepreneurAttempt;

/**
 * Created by Abrar on 4/9/16.
 */
public class EntConnectionActivity extends AppCompatActivity {

    private QuickPitchClient clientInstance;
    private EntrepreneurAccount myEntAccount;
    private Vector<InvestorAccount> connections;
    private String[] connectionNames;
    private Button mProfileButton;
    private Button mConnectionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_ent_connections);

        mProfileButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.entProfileButton);

        mConnectionsButton = (Button)findViewById(R.id.entConnectionsButton);
        mConnectionsButton.getBackground().setColorFilter(getResources().getColor(R.color.tabbedPressedColor), PorterDuff.Mode.MULTIPLY);

        // gets client instance, investorAccount, and list of connections
        clientInstance = QuickPitchClient.getInstance();
        myEntAccount = clientInstance.getMyEntAccount();
        connections = clientInstance.sendRefreshEntrepreneurAttempt(new RefreshEntrepreneurAttempt(myEntAccount.getUsername()));

        connectionNames = new String[connections.size()];
        int i = 0;

        // get connection names and send to populate list view
        for (InvestorAccount connection : connections) {
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
                Intent intent = new Intent(thisActivity, EntProfileActivity.class);
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
        ListView connectionsList = (ListView)findViewById(R.id.entConnectionsListView);
        connectionsList.setAdapter(adapter);

    }

}
