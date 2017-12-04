package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that creates a Start Screen to enter account information
 */
public class StartScreen extends AppCompatActivity {
    private TextInputEditText emailInput;
    private TextInputLayout passwordInput;

    //Firebase Account Authentication instance data
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private CallbackManager mCallbackManager;


    /**
     * Initialize the activity
     *
     * @param savedInstanceState the Bundle object containing
     *                           the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Get all user input fields
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button forgotButton = (Button) findViewById(R.id.forgotPasswordButton);
        emailInput = (TextInputEditText) findViewById(R.id.input_email);
        passwordInput = (TextInputLayout) findViewById(R.id.input_password_layout);

        // Allow the user to toggle seeing their password while typing
        passwordInput.setPasswordVisibilityToggleEnabled(true);

        // Set sign-up button to take you to sign-up activity
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });

        // Set log-in button to check for proper credentials. If credentials are good,
        // take the user to the main activity. Otherwise, display toast informing user
        // their credentials were not valid.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getEditText().getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(StartScreen.this, R.string.sign_in_empty,
                            Toast.LENGTH_SHORT).show();
                } else {
                    signIn(email, password);
                }
            }
        });

        // Set forgot password button to take you to the forgot password activity
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        //Get instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //If the user is already logged in, automatically take user to main activity
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d("DEBUG", "YOOOOOOOOOOOOOOOOOOOOOOOO");
                if (user != null) {
                    Intent intent = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton fbloginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        fbloginButton.setReadPermissions("email", "public_profile");
        fbloginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("DEBUG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("DEBUG", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("DEBUG", "facebook:onError", error);
                // ...
            }
        });


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("DEBUG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // IT BREAKS HERE BECAUSE THE TASK NEVER SUCCEEDS
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "signInWithCredential:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(StartScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called when the activity had been stopped but is now
     * being displayed again to the user
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Called when the activity is no longer visible to
     * the user
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Called when the user is attempting to sign in
     *
     * @param email the email with which the user is attempting to sign in
     * @param password the password with which the user is attempting to sign in
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    emailInput.setText("");
                    passwordInput.getEditText().setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.sign_in_failed, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Intent intent = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
