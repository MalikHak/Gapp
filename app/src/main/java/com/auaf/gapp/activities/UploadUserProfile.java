package com.auaf.gapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.auaf.gapp.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class UploadUserProfile extends AppCompatActivity {

    final String TAG = UploadUserProfile.class.getSimpleName();
    private ProgressDialog progressDialog;
    CircleImageView ivProfileImage;

    String photouser = "";
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

   private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_profile);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");
       storageReference = FirebaseStorage.getInstance().getReference();



        etAge=findViewById(R.id.etAge);
        etJob=findViewById(R.id.etJob);
        etName=findViewById(R.id.etName);
        etLivingPlace=findViewById(R.id.etLivingPlace);
        swsingleOrMarried=findViewById(R.id.swMarriedOrNnot);
        btnUploadData=findViewById(R.id.btnUpload);
        ivProfileImage= findViewById(R.id.ivProfile);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECT_IMAGE_TYPE = PROFILE_IMAGE_TYPE;
                showPopupWindow(ivProfileImage);
            }
        });

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
                            String photo = userData.getPhoto();

                            SessionManager.getInstance(UploadUserProfile.this).setNameUser(userData.getName());

                            //Load Image Here

                            Glide.with(UploadUserProfile.this)
                                    .load(photo)
                                    .into(ivProfileImage);


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
                final String name= etName.getText().toString();
                String job= etJob.getText().toString();
                String place=etLivingPlace.getText().toString();

                int age = Integer.parseInt(etAge.getText().toString());
                boolean isMarried = swsingleOrMarried.isChecked();

                User user=new User(uid,name,place,job,age,isMarried,photouser);

                Map<String, Object> postValues = user.toMap();

                myRef.child(uid).setValue(postValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        SessionManager.getInstance(UploadUserProfile.this).setPhotoUSER(photouser);
                        SessionManager.getInstance(UploadUserProfile.this).setNameUser(name);

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

    void showPopupWindow(View view) {

        PopupMenu popup = new PopupMenu(UploadUserProfile.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popup.getMenuInflater().inflate(R.menu.menu_pick_profile_image, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.iPickCamera) {
                    pickImageFromCamera();

                }
                if (item.getItemId() == R.id.iPickGallery) {
                    pickImageFromGallery();

                    Toast.makeText(UploadUserProfile.this, "Camera", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
        popup.show();
    }

    private void pickImageFromCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(UploadUserProfile.this, permissionStorage) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(permissionStorage, REQUEST_EXTERNAL_STORAGE);
            } else if (ActivityCompat.checkSelfPermission(UploadUserProfile.this, permissionCamera) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(permissionCamera, REQUEST_CAMERA);
            } else {
                startActivityForResult(i, CODE_CHANGE_MY_PROFILE_PIC_CAMERA);
            }
        } else {
            startActivityForResult(i, CODE_CHANGE_MY_PROFILE_PIC_CAMERA);
        }
    }

    private void pickImageFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(UploadUserProfile.this, permissionStorage) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(permissionStorage, REQUEST_EXTERNAL_STORAGE);
            } else {
                startActivityForResult(i, CODE_CHANGE_MY_PROFILE_PIC_GALLERY);
            }
        } else {
            startActivityForResult(i, CODE_CHANGE_MY_PROFILE_PIC_GALLERY);
        }
    }

    private void requestPermission(String permission, int requstCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadUserProfile.this, permission)) {
            ActivityCompat.requestPermissions(UploadUserProfile.this, new String[]{permission}, requstCode);
        } else {
            ActivityCompat.requestPermissions(UploadUserProfile.this, new String[]{permission}, requstCode);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CODE_CHANGE_MY_PROFILE_PIC_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    showProgressDialog("Load Data");
                    imageUri = data.getData();
                    ivProfileImage.setImageURI(imageUri);
                    if (imageUri != null) {
                        final StorageReference filePath_profielpic = storageReference.child("Users").child("profile_pics").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(imageUri.getLastPathSegment());
                        filePath_profielpic.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("image", "Succeed");

                                filePath_profielpic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        hideProgressDialog();
                                        Log.d("imageLink", "onSuccess: uri= " + uri.toString());

                                        photouser = uri.toString();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgressDialog();
                                        Log.d("imageLink", "onFailed: uri= ");
                                    }
                                });

                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                hideProgressDialog();

                                Log.d("image", "Failed " + e);


                            }
                        });


                    } else {
                        Toast.makeText(this, "NothingP", Toast.LENGTH_SHORT).show();
                        //  newPost.child("profilePic").setValue("");
                        Log.d("nophoto", " profileempty");
                    }
                }
                break;

            case CODE_CHANGE_MY_PROFILE_PIC_CAMERA:
                if (resultCode == RESULT_OK && data != null) {
                    showProgressDialog("Loading Data");
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageUri = getImageUri(UploadUserProfile.this, photo);
                    ivProfileImage.setImageBitmap(photo);

                    Log.d("EditUserInfo", "onActivityResult: " + imageUri);
                    // ivProfileImage.setImageURI(imageUri);

                    if (imageUri != null) {
                        final StorageReference filePath_profielpic = storageReference.child("Users").child("profile_pics").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(imageUri.getLastPathSegment());
                        filePath_profielpic.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Log.d("image", "Succeed");

                                filePath_profielpic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        hideProgressDialog();
                                        Log.d("imageLink", "onSuccess: uri= " + uri.toString());
                                        photouser = uri.toString();


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgressDialog();
                                        Log.d("imageLink", "onFailed: uri= ");

                                    }
                                });

                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                hideProgressDialog();
                                Log.d("image", "Failed " + e);
                            }
                        });

                    } else {
                        hideProgressDialog();
                        Toast.makeText(this, "NothingP", Toast.LENGTH_SHORT).show();
                        //  newPost.child("profilePic").setValue("");
                        Log.d("nophoto", " profileempty");
                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }






    public void showProgressDialog(String textProgress) {


        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(UploadUserProfile.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage(textProgress);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            });
            progressDialog.show();

        }
    }

    public void hideProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {

            progressDialog.dismiss();
        }
    }




}