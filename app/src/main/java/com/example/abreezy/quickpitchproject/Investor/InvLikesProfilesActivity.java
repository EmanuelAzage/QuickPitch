package com.example.abreezy.quickpitchproject.Investor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abreezy.quickpitchproject.Login.LoginActivity;
import com.example.abreezy.quickpitchproject.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Vector;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.AddConnectionAttempt;
import BackEnd.Resources.Constants;

/**
 * Created by abreezy on 4/10/16.
 */


public class InvLikesProfilesActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private QuickPitchClient clientInstance;
    private Vector<EntrepreneurAccount> myLikes;
    private EntrepreneurAccount myEntAccount;
    private YouTubePlayerView mYouTubeView;
    private YouTubePlayer mYouTubePlayer;

    private String DEVELOPER_KEY = Constants.APICredential;
    private String mVideoKey = "w9Sx34swEG0";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private boolean isDone = false;
    private TextView companyName;
    private TextView productName;
    private TextView productDescription;
    private Button backButton;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_likesprofiles);

        int indexOfEnt = Integer.valueOf( getIntent().getStringExtra("index") );

        final QuickPitchClient qpClient = QuickPitchClient.getInstance();
        final InvestorAccount thisInvestor =  qpClient.getMyInvAccount();
        final EntrepreneurAccount likedEntrepreneur = thisInvestor.getLikes().get(indexOfEnt);

        mVideoKey = getVideoKey(likedEntrepreneur.getVideo());

        companyName = (TextView) findViewById(R.id.invLikes_CompanyName);
        productDescription = (TextView) findViewById(R.id.invLikes_productDescription);
        productName = (TextView) findViewById(R.id.invLikes_ProductName);

        mYouTubeView = (YouTubePlayerView) findViewById(R.id.invLikes_youtube_view);
        mYouTubeView.initialize(DEVELOPER_KEY, this);

        companyName.setText(likedEntrepreneur.getCompanyName());
        productDescription.setText(likedEntrepreneur.getProductDescription());
        productDescription.setMovementMethod(new ScrollingMovementMethod());
        productName.setText(likedEntrepreneur.getCompanyInfo());

        connectButton = (Button) findViewById(R.id.invLikes_ConnectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisInvestor.addConnection(likedEntrepreneur);
                qpClient.addConnection(new AddConnectionAttempt(likedEntrepreneur.getUsername(),
                        thisInvestor.getUsername()));
                thisInvestor.removeLike(likedEntrepreneur);

                new AlertDialog.Builder(InvLikesProfilesActivity.this)
                        .setTitle(likedEntrepreneur.getCompanyName())
                        .setMessage(likedEntrepreneur.getEmail())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvLikesProfilesActivity.this, InvLikesActivity.class);
                startActivity(intent);
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
            player.loadVideo(mVideoKey);
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
        return (YouTubePlayerView) findViewById(R.id.invLikes_youtube_view);
    }

    private static String getVideoKey(String url) {
        url = url.substring(url.indexOf('=') + 1);
        return url;
    }


}

