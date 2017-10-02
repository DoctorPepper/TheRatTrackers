package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lead.rattrackerapp.Model.AccountList;

public class StartScreen extends AppCompatActivity {
    Button loginButton;
    Button signUpButton;
    TextInputEditText emailInput;
    TextInputLayout passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        emailInput = (TextInputEditText) findViewById(R.id.input_email);
        passwordInput = (TextInputLayout) findViewById(R.id.input_password_layout);

        passwordInput.setPasswordVisibilityToggleEnabled(true);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkUsernamePassword(emailInput.getText().toString(),
                        passwordInput.getEditText().getText().toString());
                    Intent intent = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    emailInput.setText("");
                    passwordInput.getEditText().setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    public void checkUsernamePassword(String username, String password) throws Exception{
        AccountList.accountCorrect(username, password);
    }
}
