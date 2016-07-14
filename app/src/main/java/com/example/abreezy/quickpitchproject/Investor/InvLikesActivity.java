package com.example.abreezy.quickpitchproject.Investor;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abreezy.quickpitchproject.R;

import java.util.Vector;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;

public class InvLikesActivity extends AppCompatActivity {

    private QuickPitchClient clientInstance;
    private InvestorAccount myInvAccount;
    private Vector<EntrepreneurAccount> likes;
    private String[] likeNames;
    private Button mProfileButton;
    private Button mFeedButton;
    private Button mLikesButton;
    private Button mConnectionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_inv_likes);

        mProfileButton = (Button) findViewById(com.example.abreezy.quickpitchproject.R.id.invProfileButton);
        mFeedButton = (Button) findViewById(com.example.abreezy.quickpitchproject.R.id.FeedButton);
        mConnectionsButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invConnectionsButton);

        mLikesButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.likesButton);
        mLikesButton.getBackground().setColorFilter(getResources().getColor(R.color.tabbedPressedColor), PorterDuff.Mode.MULTIPLY);


        // gets client instance, investorAccount, and list of connections
        clientInstance = QuickPitchClient.getInstance();
        myInvAccount = clientInstance.getMyInvAccount();
        likes = myInvAccount.getLikes();

        likeNames = new String[likes.size()];
        int i = 0;

        // get connection names and send to populate list view
        for (EntrepreneurAccount like : likes) {
            likeNames[i] = like.getCompanyName();
            i++;
        }

        createTabs();
        populateListView(likeNames);
    }

    private void createTabs(){
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
                overridePendingTransition(R.anim.push_out_left, R.anim.pull_in_right);
            }
        });

        mConnectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvConnectionsActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_left, com.example.abreezy.quickpitchproject.R.anim.pull_in_right);
            }
        });
    }

    private void populateListView(String [] likeNames){
        //create List of Items
        String[] entList = likeNames; // get from Client Model

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                com.example.abreezy.quickpitchproject.R.layout.items,
                entList
        );

        //Configure the list view
        ListView list = (ListView)findViewById(com.example.abreezy.quickpitchproject.R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();
                Intent intent = new Intent(InvLikesActivity.this, InvLikesProfilesActivity.class);
                intent.putExtra("index", String.format("%d", position));// pass the index to the next activity
                startActivity(intent);

                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                //pass string into method for investorProfile
                // InvProfileActivity.setEntrepreneur("product name", "company name");
            }
        });

    }
}
