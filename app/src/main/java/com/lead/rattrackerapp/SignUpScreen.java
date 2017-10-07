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

import com.lead.rattrackerapp.Model.Accounts.Account;
import com.lead.rattrackerapp.Model.Accounts.AccountList;
import com.lead.rattrackerapp.Model.Accounts.AccountType;

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

        // Get all user input fields
        cancelButton = (Button) findViewById(R.id.cancelButton);
        signUpButton = (Button) findViewById(R.id.signUpConfirmButton);
        emailInput = (TextInputEditText) findViewById(R.id.input_email_signUp);
        passwordInput = (TextInputLayout) findViewById(R.id.password_SignUp);
        passwordConfirm = (TextInputLayout) findViewById(R.id.password_confirm_SignUp);
        accountSpinner = (Spinner) findViewById(R.id.accountType);

        // Set the array adapter to the two account types, user and admin
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.accountTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);

        // Allow the user to show their password while inputting it
        passwordInput.setPasswordVisibilityToggleEnabled(true);
        passwordConfirm.setPasswordVisibilityToggleEnabled(true);

        // Set the button's on-click to:
        // Check to see if the user can create an account with the credentials currently entered in
        // the field. If the two password fields do not match and the account does not currently
        // exist in the database, add the account and log the user into the main activity.
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

        // Set the button's on-click to:
        // Take the user back to the start screen activity.
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpScreen.this, StartScreen.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates a new account in the account list based on the passed in information
     * and throws an exception if there was a problem creating the account
     *
     * @param email the email with which the user is attempting to make an account with.
     * @param password the password with which the user is attempting to make an account with.
     * @param confirm the password to confirm the user typed their password accurately.
     * @param accountType the type of account the user is creating -- admin or user.
     * @throws Exception if the account already exists or if the passwords do not match.
     */
    public void createAccount(String email, String password, String confirm,
                                 String accountType) throws Exception {
        AccountType type = (accountType.equals("User")) ? AccountType.USER : AccountType.ADMIN;
        //Test if both password fields match
        if (password.equals(confirm)) {
            //Add new account to account list
            AccountList.createAccount(new Account(email, password, type, false));
        } else {
            throw new Exception("Passwords do not match");
        }

    }
}
