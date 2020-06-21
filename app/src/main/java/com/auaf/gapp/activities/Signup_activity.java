package com.auaf.gapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auaf.gapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_activity extends AppCompatActivity {

    EditText etmail,etpassword,etrepassword;
    Button btnsave;
    TextView tvlogin;
    FirebaseAuth myauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);
        etmail = findViewById(R.id.Etsignupemail);
        etpassword = findViewById(R.id.ETsignuppassword);
        etrepassword = findViewById(R.id.Etsignuprepasswrod);
        btnsave = findViewById(R.id.btnsave);
        myauth = FirebaseAuth.getInstance();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,confpassword;
                email = etmail.getText().toString();
                password = etpassword.getText().toString();
                confpassword = etrepassword.getText().toString();
                if(password.equalsIgnoreCase(confpassword))
                {
                 myauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if (task.isSuccessful()) {

                             Intent intent = new Intent(Signup_activity.this, MainActivity.class);
                             startActivity(intent);
                             finish();
                         } else {

                             Toast.makeText(Signup_activity.this,"task was unsuccessful", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
                 {
                }
                }
                else
                {
                    Toast.makeText(Signup_activity.this,"Password and confirm password are not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
       /* tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Signup_activity.this, Login.class);
                startActivity(intent2);
            }
        });*/

    }
}
