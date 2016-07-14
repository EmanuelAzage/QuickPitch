package com.example.abreezy.quickpitchproject.Entrepreneur;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abreezy.quickpitchproject.Login.LoginActivity;
import com.example.abreezy.quickpitchproject.R;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.Constants;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeInitializationResult;

/**
 * Created by Abrar on 4/8/16.
 */
public class EntProfileActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private QuickPitchClient clientInstance;
    private EntrepreneurAccount myEntAccount;
    private Button mProfileButton;
    private Button mConnectionsButton;
    private YouTubePlayerView mYouTubeView;
    private YouTubePlayer mYouTubePlayer;

    private String DEVELOPER_KEY = Constants.APICredential;
    private String mVideoKey = "";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private boolean isDone = false;
    private TextView companyName;
    private TextView productName;
    private TextView productDescription;
    private Button mLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_ent_profile);

        mProfileButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.entProfileButton);
        mProfileButton.getBackground().setColorFilter(getResources().getColor(R.color.tabbedPressedColor), PorterDuff.Mode.MULTIPLY);

        mConnectionsButton = (Button)findViewById(R.id.entConnectionsButton);
        mLogoutButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.logoutButton);

        clientInstance = QuickPitchClient.getInstance();
        myEntAccount = clientInstance.getMyEntAccount();
        Log.d("MyAccountInfo", myEntAccount.getUsername());
        Log.d("MyAccountInfo", myEntAccount.getPassword());
        Log.d("MyAccountInfo", myEntAccount.getEmail());
        Log.d("MyAccountInfo", myEntAccount.getVideo());
        Log.d("MyAccountInfo", myEntAccount.getCompanyName());
        Log.d("MyAccountInfo", myEntAccount.getCompanyInfo()); //This is actually Product Name
        Log.d("MyAccountInfo", myEntAccount.getProductDescription());

        mVideoKey = getVideoKey(myEntAccount.getVideo());
        mYouTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mYouTubeView.initialize(DEVELOPER_KEY, this);
        createTabs();

        //Set the labels for the entrepreneur info
        companyName = (TextView) findViewById(R.id.editText);
        companyName.setText(myEntAccount.getCompanyName());

        productName = (TextView) findViewById(R.id.textView4);
        productName.setText(myEntAccount.getCompanyInfo());

        productDescription = (TextView) findViewById(R.id.textView5);
        productDescription.setText(myEntAccount.getProductDescription());
        productDescription.setMovementMethod(new ScrollingMovementMethod());



//        while(!isDone)
//        {}

//        mYouTubePlayer.pause();
    }


    private void createTabs() {
        final Context thisActivity = this;

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mConnectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, EntConnectionActivity.class);
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

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            mYouTubePlayer = player;
            isDone = true;
            player.cueVideo(mVideoKey);
            // Hiding player controls
            //player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }

    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    private static String getVideoKey(String url) {
        url = url.substring(url.indexOf('=') + 1);
        return url;
    }

}