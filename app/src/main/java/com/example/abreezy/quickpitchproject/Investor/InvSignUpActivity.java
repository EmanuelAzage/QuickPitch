package com.example.abreezy.quickpitchproject.Investor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.abreezy.quickpitchproject.R;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.InvSignUpAttempt;

public class InvSignUpActivity extends AppCompatActivity {
    private EditText mUsernameField, mPasswordField, mRepeatPasswordField, mEmailField, mCompanyNameField;
    private String mUsername, mPassword, mRepeatPassword, mEmail, mCompanyName;
    private Button mCreateButton;
    private QuickPitchClient quickPitchClient = QuickPitchClient.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_sign_up);

        mUsernameField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.usernameText);
        mPasswordField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.passwordText);
        mRepeatPasswordField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.repeatText);
        mEmailField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.emailText);
        mCompanyNameField = (EditText)findViewById(com.example.abreezy.quickpitchproject.R.id.companyText);

        mCreateButton = (Button)findViewById(com.example.abreezy.quickpitchproject.R.id.nextButton);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUsername = mUsernameField.getText().toString();
                mPassword = mPasswordField.getText().toString();
                mRepeatPassword = mRepeatPasswordField.getText().toString();
                mEmail = mEmailField.getText().toString();
                mCompanyName = mCompanyNameField.getText().toString();

                if(!mPassword.equals(mRepeatPassword)) {
                    // pop up saying that passwords do not match and to re-enter
                    new AlertDialog.Builder(InvSignUpActivity.this)
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
                else if(!mPassword.matches(".*[0-9].*") ||
                        mPassword.equals(mPassword.toLowerCase())) {
                    // pop up saying password must have at least one capital and number
                    new AlertDialog.Builder(InvSignUpActivity.this)
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
                	mPassword = InvSignUpActivity.hashPassword(mPassword, 1738);
                    InvSignUpAttempt invSignUpAttempt = new InvSignUpAttempt(mUsername, mPassword, mEmail, mCompanyName);

                    InvestorAccount investorAccount = quickPitchClient.sendInvSignUpAttempt(invSignUpAttempt);
                    if(investorAccount == null) {
                        //pop up saying that sign-up failed - invalid username
                        new AlertDialog.Builder(InvSignUpActivity.this)
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
                        Intent intent = new Intent(InvSignUpActivity.this, InvProfileActivity.class);
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
