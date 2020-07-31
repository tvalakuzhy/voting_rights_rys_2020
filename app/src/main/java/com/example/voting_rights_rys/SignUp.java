package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";

    private TextView alreadyUser;
    private EditText mName, mEmail, mPassword, mConfirmPass;
    private Button regBtn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        alreadyUser = (TextView) findViewById(R.id.already_user);
        mName = (EditText) findViewById(R.id.reg_fullname);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mConfirmPass = (EditText) findViewById(R.id.reg_confirmpass);
        regBtn = (Button) findViewById(R.id.signUpBtn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User registered
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully registered with: " + user.getEmail());
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Switch to the Login page if the user indicates they have an account
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(0,0);
            }
        });

        // Try to register the user with information given
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                createAccount(email, pass);
            }
        });

    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, redirects to home page
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            toastMessage("Registration failed.");
                        }
                    }
                });
    }

    private boolean validateForm(){
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String pass = mPassword.getText().toString();
        String confirmPass = mConfirmPass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            toastMessage("Please enter your name");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            toastMessage("Please enter your email");
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            toastMessage("Please enter your password");
            return false;
        }
        if (TextUtils.isEmpty(confirmPass)) {
            toastMessage("Please re-enter your password to confirm");
            return false;
        }
        if (!TextUtils.equals(pass, confirmPass)) {
            toastMessage("Your passwords do not match");
            return false;
        }
        return true;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}