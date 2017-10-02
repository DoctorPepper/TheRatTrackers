package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.lead.rattrackerapp.Model.Account;
import com.lead.rattrackerapp.Model.AccountList;
import com.lead.rattrackerapp.Model.AccountType;

public class SignUpScreen extends AppCompatActivity {
    Button signUpButton;
    Button cancelButton;
    TextInputEditText emailInput;
    TextInputLayout passwordInput;
    TextInputLayout passwordConfirm;
    Spinner accountSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        signUpButton = (Button) findViewById(R.id.signUpConfirmButton);
        emailInput = (TextInputEditText) findViewById(R.id.input_email_signUp);
        passwordInput = (TextInputLayout) findViewById(R.id.password_SignUp);
        passwordConfirm = (TextInputLayout) findViewById(R.id.password_confirm_SignUp);
        accountSpinner = (Spinner) findViewById(R.id.accountType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.accountTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);

        passwordInput.setPasswordVisibilityToggleEnabled(true);
        passwordConfirm.setPasswordVisibilityToggleEnabled(true);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createAccount(emailInput.getText().toString(),
                        passwordInput.getEditText().getText().toString(),
                        passwordConfirm.getEditText().getText().toString(),
                        (String) accountSpinner.getSelectedItem());
                    Intent intent = new Intent(SignUpScreen.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpScreen.this, StartScreen.class);
                startActivity(intent);
            }
        });
    }

    public void createAccount(String email, String password, String confirm,
                                 String accountType) throws Exception {
        AccountType type = (accountType.equals("User")) ? AccountType.USER : AccountType.ADMIN;
        if (password.equals(confirm)) {
            AccountList.createAccount(new Account(email, password, type, false));
        } else {
            throw new Exception("Passwords do not match");
        }

    }
}
