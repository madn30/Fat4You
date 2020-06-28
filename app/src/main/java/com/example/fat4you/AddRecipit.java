package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddRecipit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText email,recipit,products,title;
    Button btnUpload,uploadimage;
    ImageView imageView;
    ArrayList<Recipit> recipitList;
    DatabaseReference databaseReference;
    Recipit newRecipit;
    String  downloadImageUrl;

    String text;
    private Uri ImageUri;

    private StorageReference mStorageRef;

    private static final int GalleryPick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipit);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        recipitList = new ArrayList<>();
        uploadimage = findViewById(R.id.btnProductImage);

        email = findViewById(R.id.email);
        recipit = findViewById(R.id.how);
        products = findViewById(R.id.products);
        title = findViewById(R.id.ProductName);
        imageView = findViewById(R.id.ivProductImage);
        btnUpload = findViewById(R.id.btnsend);
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this) ;

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();

            }
        });



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRecipit.this, downloadImageUrl, Toast.LENGTH_SHORT).show();
                addtostorage();
                newRecipit = new Recipit(ImageUri.getLastPathSegment(),title.getText().toString(),recipit.getText().toString(),text,email.getText().toString(),products.getText().toString());
                getListOfSameProduct();
                recipitList.add(newRecipit);
                databaseReference.child(text).setValue(recipitList);
                recipitList.clear();


            }
        });




    }

    private void addtostorage() {
        final StorageReference filePath = mStorageRef.child(ImageUri.getLastPathSegment() + ".png");
        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddRecipit.this, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddRecipit.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();



                        }
                    }
                });
            }
        });
    }




    private void getListOfSameProduct() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipitList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipit recipit = ds.getValue(Recipit.class);
                    recipitList.add(recipit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        databaseReference.child(text).addValueEventListener(valueEventListener);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text =parent.getItemAtPosition(position).toString();
        Toast.makeText(AddRecipit.this, text, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            imageView.setImageURI(ImageUri);

        }
    }
}