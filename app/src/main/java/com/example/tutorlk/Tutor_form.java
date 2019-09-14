package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

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

import com.example.tutorlk.model.tutorDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Tutor_form extends AppCompatActivity {

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
        setContentView(R.layout.activity_tutor_form);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        final String uid=firebaseAuth.getCurrentUser().getUid();

        mStorageRef = FirebaseStorage.getInstance().getReference("profilePics");
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");  //in video

        mButtonChooseImage=findViewById(R.id.btnChooseTutorimage);
        mProgressBar=findViewById(R.id.TutorFormProgressBar);
        mImageView=findViewById(R.id.TutorProfilePic);

        phonenumber=findViewById(R.id.tutorPhoneNumber);
        address=findViewById(R.id.tutorAddress);
        nic=findViewById(R.id.tutorNIC);
        DOB=findViewById(R.id.tutorDOB);
        EducationQ=findViewById(R.id.tutorEduQulify);
        sub1=findViewById(R.id.tutorexp1);
        sub2=findViewById(R.id.tutorexp2);
        Remarks=findViewById(R.id.tutorNotable);
        name=findViewById(R.id.tutorName);


        Intent i=getIntent();
        final String email=i.getExtras().getString("email");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });








        Button btn=findViewById(R.id.btnCreateAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Tutor_form.this, "Upload in progress", Toast.LENGTH_SHORT).show();
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


                                    tutorDetails tob= new tutorDetails();



                                    tob.setImageUrl(downloadUrl.toString());
                                    tob.setEmail(email);                                                            //email will be added from the previous activ
                                    tob.setPhoneNumber(Integer.parseInt(phonenumber.getText().toString().trim()));
                                    tob.setAddress(address.getText().toString().trim());
                                    tob.setDateOfBirth(DOB.getText().toString().trim());
                                    tob.setEducationQualification(EducationQ.getText().toString().trim());
                                    tob.setName(name.getText().toString().trim());
                                    tob.setNic(nic.getText().toString().trim());
                                    tob.setNotableRemarks(Remarks.getText().toString().trim());
                                    tob.setSub1(sub1.getText().toString().trim());
                                    tob.setSub2(sub2.getText().toString().trim());



                                    dbRef=FirebaseDatabase.getInstance().getReference().child("Tutor").child(uid);
                                    dbRef.setValue(tob);
                                    Intent intent=new Intent(Tutor_form.this,Login.class);
                                    startActivity(intent);





                                    Toast.makeText(Tutor_form.this, "Upload successful", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(Tutor_form.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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



        public  String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }


}
