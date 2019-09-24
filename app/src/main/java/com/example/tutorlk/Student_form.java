package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tutorlk.model.index;
import com.example.tutorlk.model.tutorDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Student_form extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private ImageView mImageView;



    DatabaseReference dbRef;
    EditText address,nic,DOB,EducationQ,sub1,sub2,Remarks,name,phonenumber;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        final String uid=firebaseAuth.getCurrentUser().getUid();

        mStorageRef = FirebaseStorage.getInstance().getReference("profilePics"); // storage path of the image

        mButtonChooseImage=findViewById(R.id.btnstdFormUploadPic);
        mProgressBar=findViewById(R.id.btnstdFormprogressbar);
        mImageView=findViewById(R.id.stdFromPic);

        phonenumber=findViewById(R.id.stdFormNumber);
        address=findViewById(R.id.stdFormAddress);
        nic=findViewById(R.id.stdFormNIC);
        DOB=findViewById(R.id.stdFromDateOfBirth);
        EducationQ=findViewById(R.id.stdFormEducationalState);
        Remarks=findViewById(R.id.stdFormRemarks);
        name=findViewById(R.id.stdFormName);


        Intent i=getIntent();
        final String email=i.getExtras().getString("email");





        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });







        Button btn=findViewById(R.id.btnStdCreateAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Student_form.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile(email,uid);
                }


            }
        });


    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            mImageView.setImageURI(mImageUri);

        }
    }



    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void uploadFile(final String email, final String uid) {
        if (mImageUri != null) {

            firebaseAuth = FirebaseAuth.getInstance();


            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;


                                    Student_tab sob= new Student_tab();



                                    sob.setUid(uid);
                                    sob.setImageUrl(downloadUrl.toString());
                                    sob.setEmail(email);                                                            //email will be added from the previous activ
                                    sob.setPhoneNumber(Integer.parseInt(phonenumber.getText().toString().trim()));
                                    sob.setAddress(address.getText().toString().trim());
                                    sob.setDateOfBirth(DOB.getText().toString().trim());
                                    sob.setEducationalState(EducationQ.getText().toString().trim());
                                    sob.setName(name.getText().toString().trim());
                                    sob.setNotableRemarks(Remarks.getText().toString().trim());
                                    sob.setNic(nic.getText().toString().trim());


                                    dbRef= FirebaseDatabase.getInstance().getReference().child("Student").child(uid); // set the student information
                                    dbRef.setValue(sob);



                                    Intent intent=new Intent(Student_form.this,Login.class);
                                    startActivity(intent);





                                    Toast.makeText(Student_form.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    // Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl.toString() , Toast.LENGTH_SHORT).show();
                                }
                            });




//                            tutorDetails tutor = new Upload_feed_adapter.ExampleViewHolder(mEditTextFileName.getText().toString().trim(),
//                                    taskSnapshot.getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Student_form.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }





}
