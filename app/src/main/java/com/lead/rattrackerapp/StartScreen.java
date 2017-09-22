package com.lead.rattrackerapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartScreen extends AppCompatActivity {
    Button loginButton;
    Button signUpButton;
    TextInputEditText emailInput;
    TextInputLayout passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
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
                if (checkUsernamePassword(emailInput.getText().toString(),
                        passwordInput.getEditText().getText().toString())) {
                    Intent intent = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    emailInput.setText("");
                    passwordInput.getEditText().setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Username and Password were not correct", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    public boolean checkUsernamePassword(String username, String password) {
        //can change this later to actually do it right
        if (username.equals("user") && password.equals("password")) {
            return true;
        } else {
            return false;
        }
    }
}
