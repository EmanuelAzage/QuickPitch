package com.example.abreezy.quickpitchproject.Login;

// import statements
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.abreezy.quickpitchproject.Entrepreneur.EntProfileActivity;
import com.example.abreezy.quickpitchproject.Investor.GuestActivity;
import com.example.abreezy.quickpitchproject.Investor.InvProfileActivity;
import com.example.abreezy.quickpitchproject.R;

import java.io.IOException;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Client.QuickPitchClient;
import BackEnd.Resources.LoginAttempt;

// LoginActivityClass
public class LoginActivity extends AppCompatActivity {

    // private member variables
    private QuickPitchClient clientInstance;
    private Button mGuestButton, mSignupButton, mLoginButton;
    private EditText mUsernameField, mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize private member variables
        mUsernameField = (EditText) findViewById(R.id.usernameField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        clientInstance = QuickPitchClient.getInstance();

        //GUEST BUTTON actionListener
        mGuestButton = (Button) findViewById(R.id.guestButton);
        mGuestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clientInstance.checkServer() != null) {
                    Intent intent = new Intent(LoginActivity.this, GuestActivity.class);
                    startActivity(intent);
                }
                else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Server Down.")
                            .setMessage("App cannot connect to the server.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        //SIGN-UP BUTTON actionListener
        mSignupButton = (Button) findViewById(R.id.signupButton);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clientInstance.checkServer()!=null) {
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                }
                else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Server Down.")
                            .setMessage("App cannot connect to the server.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        // LOGIN BUTTON actionListener
        mLoginButton = (Button) findViewById(R.id.button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clientInstance.checkServer()!=null) {
                    String username = String.valueOf(mUsernameField.getText());
                    String password = String.valueOf(mPasswordField.getText());
                    password = LoginActivity.hashPassword(password, 1738);
                    LoginAttempt loginAttempt = new LoginAttempt(username, password);
                    Object account = clientInstance.sendLoginAttempt(loginAttempt);

                    if (account == null) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Login Fail")
                                .setMessage("Login attempt fail: invalid username or password.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        mPasswordField.setText("");
                    } else if (account instanceof InvestorAccount) {
                        InvestorAccount investorAccount = (InvestorAccount) account;
                        Intent intent = new Intent(LoginActivity.this, InvProfileActivity.class);
                        startActivity(intent);
                    } else if (account instanceof EntrepreneurAccount) {
                        EntrepreneurAccount entrepreneurAccount = (EntrepreneurAccount) account;
                        Intent intent = new Intent(LoginActivity.this, EntProfileActivity.class);
                        startActivity(intent);
                    } else {
                        System.out.println("LoginActivity.mLoginButton.setOnClickListener: " +
                                "This line of code should never print...");
                    }
                }
                else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Server Down.")
                            .setMessage("App cannot connect to the server.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
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
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(com.example.abreezy.quickpitchproject.R.menu.menu_login, menu);
//            return true;
//        }

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == com.example.abreezy.quickpitchproject.R.id.action_settings) {
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }

}
