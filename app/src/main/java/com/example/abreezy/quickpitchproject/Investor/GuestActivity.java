package com.example.abreezy.quickpitchproject.Investor;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abreezy.quickpitchproject.Investor.SimpleGestureFilter;
import com.example.abreezy.quickpitchproject.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.AddLikeAttempt;
import BackEnd.Resources.Constants;
import BackEnd.Resources.GrabNewsFeedAttempt;

public class GuestActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,SimpleGestureFilter.SimpleGestureListener {
    private SimpleGestureFilter detector;
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

    private YouTubePlayer mYouTubePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(com.example.abreezy.quickpitchproject.R.layout.activity_guest);

        qpClient = QuickPitchClient.getInstance();

        currentEnt = qpClient.grabNewsfeed(new GrabNewsFeedAttempt("fds"));
        mVideoKey = this.getVideoKey(currentEnt.getVideo());
        nextEnt = qpClient.grabNewsfeed(new GrabNewsFeedAttempt("fds"));


        mYesButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.yesButton);
        mNoButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.noButton);

        mEntrepreneurName = (TextView)findViewById(R.id.guestEntTitle);
        mProductName = (TextView)findViewById(R.id.guestProductName);

        mEntrepreneurName.setText(currentEnt.getCompanyName());
        mProductName.setText(currentEnt.getCompanyInfo());


        mYouTubeView = (YouTubePlayerView) findViewById(R.id.guest_youtube_view);
        mYouTubeView.initialize(DEVELOPER_KEY, this);

        addLikeDislikeActions();

        detector = new SimpleGestureFilter(this,this);
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
    }

    private void likeCurrentEnt(){
        //add to likes list
        //refresh info (video and entrepreneur title
        refreshFeed();

        //implement animation here or in refresh, probably here since we need to know which direction

        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mYouTubeView.startAnimation(animationFadeOut);
        mYouTubeView.startAnimation(animationFadeIn);
    }

    private void dislikeCurrentEnt(){
        //refresh info
        refreshFeed();
        //implement animation here or in refresh, probably here since we need to know which direction

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
        return (YouTubePlayerView) findViewById(R.id.guest_youtube_view);
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
