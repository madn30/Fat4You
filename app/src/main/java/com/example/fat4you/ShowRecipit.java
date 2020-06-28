package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView title,recipit1;
    ImageView img;
    FirebaseStorage storage= FirebaseStorage.getInstance();
    StorageReference storageReference=storage.getReference().child(recipit.getImagePath());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipit);
        title = findViewById(R.id.title);

        recipit1 = findViewById(R.id.recipit);
        product = getIntent().getStringExtra("product");
        index = getIntent().getIntExtra("index",0);
        img=findViewById(R.id.imageview);
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

        //All the rest
       Toast.makeText(this, recipit.getImagePath(), Toast.LENGTH_SHORT).show();
        title.setText(recipit.getTitle());
        recipit1.setText(recipit.getProducts() + "\n\n" + recipit.getRecipit());
    }



}