package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lead.rattrackerapp.Model.Accounts.Account;
import com.lead.rattrackerapp.Model.Accounts.AccountList;
import com.lead.rattrackerapp.Model.Accounts.AccountType;
import com.lead.rattrackerapp.Model.Sightings.Sighting;

public class SignUpScreen extends AppCompatActivity {
    Button signUpButton;
    Button cancelButton;
    TextInputEditText emailInput;
    TextInputLayout passwordInput;
    TextInputLayout passwordConfirm;
    Spinner accountSpinner;

    FirebaseAuth mAuth;

    /**
     * Initialize the activity
     *
     * @param savedInstanceState the Bundle object containing
     *                           the activity's previously saved state
     */
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
                    String password = passwordInput.getEditText().getText().toString();
                    String confirm = passwordConfirm.getEditText().getText().toString();
                    if (password != null && password.equals(confirm)
                            && emailInput.getText().toString() != null) {
                        createFireAccount(emailInput.getText().toString(),
                                passwordInput.getEditText().getText().toString());
                    } else {
                        Toast.makeText(SignUpScreen.this, R.string.password_match_fail,
                                Toast.LENGTH_SHORT).show();
                    }
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

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Create a new FireBase account
     *
     * @param email the email of the account
     * @param password the password of the account
     */
    public void createFireAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpScreen.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignUpScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
