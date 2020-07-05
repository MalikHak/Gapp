package com.auaf.gapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.auaf.gapp.R;
import com.auaf.gapp.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class UploadUserProfile extends AppCompatActivity {

    final String TAG = UploadUserProfile.class.getSimpleName();

    EditText etName,etAge,etLivingPlace,etJob;
    Switch swsingleOrMarried;
    Button btnUploadData;

    //Uploading Photo
    Uri imageUri = null;
    String permissionStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String permissionCamera = Manifest.permission.CAMERA;

    private static final int REQUEST_EXTERNAL_STORAGE = 1012;
    private static final int REQUEST_CAMERA = 1013;
    private static final int CODE_CHANGE_MY_PROFILE_PIC_GALLERY = 1002;
    private static final int CODE_CHANGE_MY_PROFILE_PIC_CAMERA = 1003;
    int SELECT_IMAGE_TYPE = 0;
    int PROFILE_IMAGE_TYPE = 3;

 //   private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_profile);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");
     //   storageReference = FirebaseStorage.getInstance().getReference();



        etAge=findViewById(R.id.etAge);
        etJob=findViewById(R.id.etJob);
        etName=findViewById(R.id.etName);
        etLivingPlace=findViewById(R.id.etLivingPlace);
        swsingleOrMarried=findViewById(R.id.swMarriedOrNnot);
        btnUploadData=findViewById(R.id.btnUpload);

        if (getIntent().getStringExtra("type")!=null){

                    myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userData = snapshot.getValue(User.class);

                            etName.setText(userData.getName());
                            etAge.setText(userData.getAge()+"");
                            etJob.setText(userData.getJob());
                            etLivingPlace.setText(userData.getPlace());
                            swsingleOrMarried.setChecked(userData.isMarried());

                        }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }




        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                String name= etName.getText().toString();
                String job= etJob.getText().toString();
                String place=etLivingPlace.getText().toString();

                int age = Integer.parseInt(etAge.getText().toString());
                boolean isMarried = swsingleOrMarried.isChecked();

                User user=new User(uid,name,place,job,age,isMarried);

                Map<String, Object> postValues = user.toMap();

                myRef.child(uid).setValue(postValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UploadUserProfile.this, "Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UploadUserProfile.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadUserProfile.this, "Failed", Toast.LENGTH_SHORT).show();


                    }
                });





            }
        });


    }








}