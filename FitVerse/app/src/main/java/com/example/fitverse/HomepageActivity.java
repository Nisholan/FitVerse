package com.example.fitverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class HomepageActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Measurements");
    EditText height, weight, targetweight, targetcalorieintake, dailyweightchange;
    Button enter, view;
    String enteredHeight;
    String enteredWeight;
    String enteredTargetWeight;
    String enteredTargetCalorieIntake;
    String enteredDailyWeightChange;
    Measurements measurements;
    private ImageView imageView;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        imageView = findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        height = findViewById(R.id.et_height);
        weight = findViewById(R.id.et_weight);
        targetweight = findViewById(R.id.et_targetweight);
        targetcalorieintake = findViewById(R.id.et_targetcalorieintake);
        dailyweightchange = findViewById(R.id.et_dailyweightchange);
        enter = findViewById(R.id.btn_enter);
        view = findViewById(R.id.btn_view);

        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                enteredHeight = height.getText().toString().trim();
                enteredWeight = weight.getText().toString().trim();
                enteredTargetWeight = targetweight.getText().toString().trim();
                enteredTargetCalorieIntake = targetcalorieintake.getText().toString().trim();
                enteredDailyWeightChange = dailyweightchange.getText().toString().trim();
                measurements = new Measurements(enteredHeight, enteredWeight, enteredTargetWeight, enteredTargetCalorieIntake, enteredDailyWeightChange);

                myRef.push().setValue(measurements)
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(HomepageActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(HomepageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                });
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent openMeasurements = new Intent(HomepageActivity.this, ViewInformationActivity.class);
                startActivity(openMeasurements);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                choosePicture();
            }
        });

    }

    private void choosePicture()
    {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadPicture();

        }
    }

    private void uploadPicture()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
            {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Percentage: " + (int) progressPercent + "%");

            }
        });

    }
}