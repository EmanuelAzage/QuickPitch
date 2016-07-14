package com.example.abreezy.quickpitchproject.Entrepreneur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.abreezy.quickpitchproject.R;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Resources.EntSignUpAttempt;

public class EntSignUpActivity extends AppCompatActivity {
    private EditText mUsernameField, mPasswordField, mRepeatPasswordField, mEmailField, mCompanyNameField,
            mCompanyInfoField, mProductDescriptionField, mVideoURLField;
    private String mUsername, mPassword, mRepeatPassword, mEmail, mCompanyName, mCompanyInfo, mProductDescription,
            mVideoURL;
    private Button mCreateButton;
    private QuickPitchClient quickPitchClient = QuickPitchClient.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ent_sign_up);

        mUsernameField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.usernameText);
        mPasswordField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.passwordText);
        mRepeatPasswordField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.repeatText);
        mEmailField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.emailText);
        mCompanyNameField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.companyText);
        mCompanyInfoField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.productText);
        mVideoURLField = (EditText)findViewById(R.id.youtubeText);
        mProductDescriptionField = (EditText)findViewById(R.id.productDescriptionText);

        //missing videoURL field and companyName field in the GUI
        //DON'T FORGET THIS


        mCreateButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.nextButton);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUsername = mUsernameField.getText().toString();
                mPassword = mPasswordField.getText().toString();
                mRepeatPassword = mRepeatPasswordField.getText().toString();
                mEmail = mEmailField.getText().toString();
                mCompanyInfo = mCompanyInfoField.getText().toString();
                mProductDescription = mProductDescriptionField.getText().toString();
                //GET VIDEOURL AND COMPANY NAME STRINGS
                mCompanyName = mCompanyNameField.getText().toString();
                mVideoURL = mVideoURLField.getText().toString();


                if (!mPassword.equals(mRepeatPassword)) {
                    // pop up saying that passwords do not match and to re-enter
                    new AlertDialog.Builder(EntSignUpActivity.this)
                            .setTitle("Invalid Password")
                            .setMessage("Passwords do not match. Please re-enter.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mPasswordField.setText("");
                                    mRepeatPasswordField.setText("");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else if(!mPassword.matches(".*[0-9].*") || mPassword.equals(mPassword.toLowerCase())) {
                    // pop up saying password must have at least one capital and number
                    new AlertDialog.Builder(EntSignUpActivity.this)
                            .setTitle("Invalid Password")
                            .setMessage("Password must contain at least one capital letter and at least one number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mPasswordField.setText("");
                                    mRepeatPasswordField.setText("");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                	mPassword = EntSignUpActivity.hashPassword(mPassword, 1738);
                    EntSignUpAttempt entSignUpAttempt = new EntSignUpAttempt(mUsername, mPassword, mEmail,
                            mCompanyName, mCompanyInfo, mProductDescription, mVideoURL);
                    EntrepreneurAccount entrepreneurAccount = quickPitchClient.sendEntSignUpAttempt(entSignUpAttempt);
                    if (entrepreneurAccount == null) {
                        //pop up saying that sign-up failed - invalid username
                        new AlertDialog.Builder(EntSignUpActivity.this)
                                .setTitle("Sign-Up Failed")
                                .setMessage("Username is already taken. Please try another username.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mUsernameField.setText("");
                                        mPasswordField.setText("");
                                        mRepeatPasswordField.setText("");
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    else {
                        Intent intent = new Intent(EntSignUpActivity.this, EntProfileActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    
    private static String hashPassword(String password, int private_key){
		String newPassword = "";
		for(int i = 0; i < password.length(); i++){
			char ch = password.charAt(i);
			int asciiVal = ch;
			int modVal = private_key % 23;
			asciiVal = asciiVal - modVal;
			ch = (char) asciiVal;
			newPassword += ch;
		}
		return newPassword;
	}
}
