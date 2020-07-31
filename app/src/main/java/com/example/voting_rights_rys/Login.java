package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmail, mPassword;
    private TextView textForgotPass, textSignUp;
    private CheckBox chbxShowPass;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.login_emailid);
        mPassword = (EditText) findViewById(R.id.login_password);
        btnSignIn = (Button) findViewById(R.id.loginBtn);
        chbxShowPass = (CheckBox) findViewById(R.id.show_hide_password);
        textForgotPass = (TextView) findViewById(R.id.forgot_password);
        textSignUp = (TextView) findViewById(R.id.createAccount);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                loginUserAccount(email, pass);
            }
        });
        chbxShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
                overridePendingTransition(0,0);
            }
        });
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

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    private void loginUserAccount(String email, String pass){
        // Validate the email and the password given
        if (TextUtils.isEmpty(email)) {
            toastMessage("Please enter your email");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            toastMessage("Please enter your password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Login is successful");
                                }
                                else {
                                    // Signal that sign in has failed
                                    Log.d(TAG, "Login has failed");
                                    toastMessage("Failed Login");
                                    String errorCode = ((FirebaseException) task.getException()).getLocalizedMessage();
                                    String errorCode1 = ((FirebaseException) task.getException()).getMessage();
                                    Log.d(TAG, errorCode);
                                    Log.d(TAG, errorCode1);

                                    /**switch (errorCode) {

                                        case "ERROR_INVALID_CUSTOM_TOKEN":
                                            toastMessage("The custom token format is incorrect. Please check the documentation.");
                                            break;

                                        case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                            toastMessage("The custom token corresponds to a different audience.");
                                            break;

                                        case "ERROR_INVALID_CREDENTIAL":
                                            toastMessage("The supplied auth credential is malformed or has expired.");
                                            break;

                                        case "ERROR_INVALID_EMAIL":
                                            toastMessage("The email address is badly formatted.");
                                            mEmail.setError("The email address is badly formatted.");
                                            mEmail.requestFocus();
                                            break;

                                        case "ERROR_WRONG_PASSWORD":
                                            toastMessage("The password is invalid or the user does not have a password.");
                                            mPassword.setError("password is incorrect ");
                                            mPassword.requestFocus();
                                            mPassword.setText("");
                                            break;

                                        case "ERROR_USER_MISMATCH":
                                            toastMessage("The supplied credentials do not correspond to the previously signed in user.");
                                            break;

                                        case "ERROR_REQUIRES_RECENT_LOGIN":
                                            toastMessage("This operation is sensitive and requires recent authentication. Log in again before retrying this request.");
                                            break;

                                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                            toastMessage("An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.");
                                            break;

                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            toastMessage("The email address is already in use by another account.");
                                            mEmail.setError("The email address is already in use by another account.");
                                            mEmail.requestFocus();
                                            break;

                                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                            toastMessage("This credential is already associated with a different user account.");
                                            break;

                                        case "ERROR_USER_DISABLED":
                                            toastMessage("The user account has been disabled by an administrator.");
                                            break;

                                        case "ERROR_USER_TOKEN_EXPIRED":

                                        case "ERROR_INVALID_USER_TOKEN":
                                            toastMessage("The user's credential is no longer valid. The user must sign in again.");
                                            break;

                                        case "ERROR_USER_NOT_FOUND":
                                            toastMessage("There is no user record corresponding to this identifier. The user may have been deleted.");
                                            break;

                                        case "ERROR_OPERATION_NOT_ALLOWED":
                                            toastMessage("This operation is not allowed. You must enable this service in the console.");
                                            break;

                                        case "ERROR_WEAK_PASSWORD":
                                            toastMessage("The given password is invalid.");
                                            mPassword.setError("The password is invalid it must 6 characters at least");
                                            mPassword.requestFocus();
                                            break;
                                    }**/
                                }
                            }
                        });
    }
}