package com.auaf.gapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.auaf.gapp.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.auaf.gapp.R;

public class RegisterActivity extends AppCompatActivity {


    private static final String TAG =RegisterActivity.class.getSimpleName() ;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;


    Button btnSignIn,btnnSignUp,btnSignUpWithGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(RegisterActivity.this, gso);

        btnnSignUp= findViewById(R.id.btnSignUp);
        btnSignIn= findViewById(R.id.btnSignIn);
        btnSignUpWithGoogle= findViewById(R.id.btnSignInWithGoogle);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(RegisterActivity.this,Login.class);
                startActivity(intent);

            }
        });

        btnnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,Signup_activity.class);
                startActivity(intent);
            }
        });

        btnSignUpWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            SessionManager.getInstance(RegisterActivity.this).setLoggedIn(true);
                            signin();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }

    private void signin() {
        if (!SessionManager.getInstance(RegisterActivity.this).getIsFirstTime()){

            Intent intent=new Intent(RegisterActivity.this, UploadUserProfile.class);
            SessionManager.getInstance(RegisterActivity.this).setIsFirstTime(true);
            startActivity(intent);
        }else {
            Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);

        }

    }


    @Override
    protected void onStart() {
        super.onStart();


//        if (SessionManager.getInstance(RegisterActivity.this).getLoggedIn() ){
//            signin();
//        }


       if (FirebaseAuth.getInstance().getCurrentUser()!=null){
        signin();
        }
        }
        }
