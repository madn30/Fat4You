package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ShowRecipit extends AppCompatActivity {
    String product;
    int index;
    DatabaseReference databaseReference;
    Recipit recipit;
    TextView title,recipit1,email;
    ImageView img;

    private String filepath;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipit);
        title = findViewById(R.id.title);
        storageReference=FirebaseStorage.getInstance().getReference();
        recipit1 = findViewById(R.id.recipit);
        product = getIntent().getStringExtra("product");
        index = getIntent().getIntExtra("index",0);
        img=findViewById(R.id.imageview);
        email=findViewById(R.id.email);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(product).child(String.valueOf(index));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipit = dataSnapshot.getValue(Recipit.class);
                try {
                    updateUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void updateUI() throws IOException {
        //image
        final StorageReference sref=storageReference.child("Product Images/").child(recipit.getImagePath()+".png");

             sref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {

               Toast.makeText(ShowRecipit.this, sref.toString(), Toast.LENGTH_SHORT).show();
               Glide.with(getApplicationContext())
                       .load(uri)
                       .into(img);

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(ShowRecipit.this, "failed to download", Toast.LENGTH_SHORT).show();
           }
       });
        //All the rest
        email.setText("הועלה עי"+" "+recipit.getEmail());
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEmail(recipit.getEmail());
            }
        });
      // Toast.makeText(this, recipit.getImagePath(), Toast.LENGTH_SHORT).show();
        title.setText(recipit.getTitle());
        recipit1.setText(recipit.getProducts() + "\n\n" + recipit.getRecipit());

    }

    private void createEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
        intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
        intent.setData(Uri.parse("mailto:"+email)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
    }

}


