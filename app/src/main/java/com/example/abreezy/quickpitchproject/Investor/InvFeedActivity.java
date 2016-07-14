package com.example.abreezy.quickpitchproject.Investor;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abreezy.quickpitchproject.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.example.abreezy.quickpitchproject.Investor.SimpleGestureFilter.SimpleGestureListener;
import android.app.Activity;
import android.view.MotionEvent;
import android.widget.Toast;

import org.w3c.dom.Text;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.AddLikeAttempt;
import BackEnd.Resources.Constants;
import BackEnd.Resources.GrabNewsFeedAttempt;


public class InvFeedActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,SimpleGestureListener {
    private SimpleGestureFilter detector;
    private Button mProfileButton;
    private Button mFeedButton;
    private Button mConnectionsButton;
    private Button mLikesButton;
    private Button mYesButton;
    private Button mNoButton;
    private YouTubePlayerView mYouTubeView;

    private TextView mEntrepreneurName;
    private TextView mProductName;

    private String DEVELOPER_KEY = Constants.APICredential;
    private String mVideoKey = "_jHpnb-QmTA";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private EntrepreneurAccount currentEnt, nextEnt;
    private QuickPitchClient qpClient;
    private InvestorAccount investor;

    private YouTubePlayer mYouTubePlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_inv_feed);

        qpClient = QuickPitchClient.getInstance();
        investor = qpClient.getMyInvAccount();

        currentEnt = qpClient.grabNewsfeed(new GrabNewsFeedAttempt("fds"));
        mVideoKey = this.getVideoKey(currentEnt.getVideo());
        nextEnt = qpClient.grabNewsfeed(new GrabNewsFeedAttempt("fds"));



        mFeedButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.FeedButton);
        mFeedButton.getBackground().setColorFilter(getResources().getColor(R.color.tabbedPressedColor), PorterDuff.Mode.MULTIPLY);

        mProfileButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invProfileButton);
        mConnectionsButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.invConnectionsButton);
        mLikesButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.likesButton);

        mYesButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.yesButton);
        mNoButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.noButton);

        mEntrepreneurName = (TextView)findViewById(R.id.entTitle);
        mProductName = (TextView)findViewById(R.id.productName);

        mEntrepreneurName.setText(currentEnt.getCompanyName());
        mProductName.setText(currentEnt.getCompanyInfo());


        mYouTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mYouTubeView.initialize(DEVELOPER_KEY, this);

        createTabs();
        addLikeDislikeActions();

        detector = new SimpleGestureFilter(this,this);

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

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_right, com.example.abreezy.quickpitchproject.R.anim.pull_in_left);
            }
        });

        mLikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, InvLikesActivity.class);
                startActivity(intent);
                overridePendingTransition(com.example.abreezy.quickpitchproject.R.anim.push_out_right,
                        com.example.abreezy.quickpitchproject.R.anim.pull_in_left);
            }
        });

    }

    private void addLikeDislikeActions(){
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               likeCurrentEnt();
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeCurrentEnt();
            }
        });

//        // adding swipe listener
//        mYouTubeView.setOnTouchListener(new OnSwipeTouchListener(this) {
//            @Override
//            public void onSwipeRight() {
//                likeCurrentEnt();
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                dislikeCurrentEnt();
//            }
//        });
    }

    private void likeCurrentEnt(){
        //add to likes list
        //refresh info (video and entrepreneur title
        boolean result = qpClient.addLike(new AddLikeAttempt(currentEnt.getUsername(), investor.getUsername()));
        if (result)
            investor.addLike(currentEnt);
        refreshFeed();

        //implement animation here or in refresh, probably here since we need to know which direction
        //TODO add animation
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mYouTubeView.startAnimation(animationFadeOut);
        mYouTubeView.startAnimation(animationFadeIn);
    }

    private void dislikeCurrentEnt(){
        //refresh info
        refreshFeed();
        //implement animation here or in refresh, probably here since we need to know which direction
        //TODO add animation
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mYouTubeView.startAnimation(animationFadeOut);
        mYouTubeView.startAnimation(animationFadeIn);
    }

    public void refreshFeed() {
        currentEnt = nextEnt;
        nextEnt = qpClient.grabNewsfeed(new GrabNewsFeedAttempt(""));

        mVideoKey = this.getVideoKey(currentEnt.getVideo());

        if (mYouTubePlayer != null) {
            mYouTubePlayer.loadVideo(mVideoKey);
            mYouTubePlayer.play();
        }
        mEntrepreneurName.setText(currentEnt.getCompanyName());
        mProductName.setText(currentEnt.getCompanyInfo());
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
            player.loadVideo(mVideoKey);
            mYouTubePlayer = player;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : likeCurrentEnt();;
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  dislikeCurrentEnt();;
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Does Nothing";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Does Nothing";
                break;

        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
        //Does nothing
    }

    private String getVideoKey(String url){
        return url.substring(url.indexOf("=") + 1);
    }
}
