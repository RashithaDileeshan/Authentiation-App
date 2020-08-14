package com.example.newgoogleauthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    TextView fullname,email,contactNo,dateOfbirth;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView imageView;
    Button changeProfile;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);
        imageView = findViewById(R.id.avatarIv);
        changeProfile = findViewById(R.id.changeprobtn);
        dateOfbirth = findViewById(R.id.dateofbirth);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference =fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                contactNo.setText(documentSnapshot.getString("contactNo"));
                fullname.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));
                dateOfbirth.setText(documentSnapshot.getString("DateOfBirth"));

            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //open galery

//                Intent openGaleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGaleryIntent,1000);
            Intent intent = new Intent(view.getContext(),EditProfile.class);
            intent.putExtra("fullName",fullname.getText().toString());
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("contactNo",contactNo.getText().toString());
            intent.putExtra("DateOfBirth",dateOfbirth.getText().toString());
            startActivity(intent);

            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1000){
//            if (requestCode == Activity.RESULT_OK){
//                Uri imageuri = data.getData();
//                imageView.setImageURI(imageuri);
//
//                uploadImageToFirebase(imageuri );
//            }
//        }
//    }

//    private void uploadImageToFirebase(Uri imageuri) {
//
//        //upload image to firebase storage
//        StorageReference fileRef = storageReference.child("profile.jpg");
//        fileRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(MainActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}