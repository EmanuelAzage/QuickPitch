package com.example.abreezy.quickpitchproject.Investor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abreezy.quickpitchproject.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Vector;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.Constants;

/**
 * Created by abreezy on 4/11/16.
 */
public class InvLikesConnectionsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_likesconnections);

        //int indexOfEnt = Integer.valueOf( getIntent().getStringExtra("index") );

//        final QuickPitchClient qpClient = QuickPitchClient.getInstance();
//        final InvestorAccount thisInvestor =  qpClient.getMyInvAccount();
//        final EntrepreneurAccount likedEntrepreneur = thisInvestor.getLikes().get(indexOfEnt);
//
//        mVideoKey = getVideoKey(likedEntrepreneur.getVideo());
//
//        companyName = (TextView) findViewById(R.id.invConnections_CompanyName);
//        productDescription = (TextView) findViewById(R.id.invConnections_productDescription);
//        productName = (TextView) findViewById(R.id.invConnections_ProductName);
//        EnvContactInfo = (TextView) findViewById(R.id.invConnections_entrepreneurInfo);
//
//        companyName.setText(likedEntrepreneur.getCompanyName());
//        productDescription.setText(likedEntrepreneur.getProductDescription());
//        productName.setText(likedEntrepreneur.getCompanyInfo());
//        EnvContactInfo.setText(likedEntrepreneur.getEmail());
        
        mYouTubeView = (YouTubePlayerView) findViewById(R.id.invConnections_youtube_view);
        mYouTubeView.initialize(DEVELOPER_KEY, this);


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
        return (YouTubePlayerView) findViewById(R.id.invConnections_youtube_view);
    }

    private static String getVideoKey(String url) {
        url = url.substring(url.indexOf('=') + 1);
        return url;
    }

}
