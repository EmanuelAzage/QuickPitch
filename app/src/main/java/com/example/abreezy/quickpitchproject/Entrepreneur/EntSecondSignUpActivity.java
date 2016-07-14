package com.example.abreezy.quickpitchproject.Entrepreneur;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.abreezy.quickpitchproject.R;

public class EntSecondSignUpActivity extends AppCompatActivity {
    private Button mCreateButton;
    private Button mAddVideoButton;
    private boolean choosingVideo = false;
    private Uri videoURI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ent_second_sign_up);

        mAddVideoButton = (Button)findViewById(R.id.entAddVideoButton);
        mAddVideoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        mCreateButton = (Button)findViewById(R.id.entCreateButton);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EntSecondSignUpActivity.this, EntProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
