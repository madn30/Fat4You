package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.fat4you.R.id.buttom;

public class listofproducts extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<Recipit> recipitArrayList;
    ListView listView;
    String product;
   ImageView imageView;
    StorageReference storageReference;
    TextView top,buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofproducts);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
       imageView=findViewById(R.id.listimage);
        product = getIntent().getStringExtra("product");
        listView = findViewById(R.id.listView);
        top=findViewById(R.id.top);
        buttom=findViewById(R.id.buttom);

        recipitArrayList = new ArrayList<>();
        getListOfSameProduct();



    }

    private void getListOfSameProduct() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipit recipit = ds.getValue(Recipit.class);
                    recipitArrayList.add(recipit);
                    updateUI();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        databaseReference.child(product).addValueEventListener(valueEventListener);
    }

    private void updateUI() {

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i =0;i<recipitArrayList.size();i++) {
            final StorageReference sref=storageReference.child("Product Images/").child(recipitArrayList.get(i).getImagePath()+".png");

            arrayList.add(recipitArrayList.get(i).getTitle()+recipitArrayList.get(i).getEmail());

            sref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Toast.makeText(listofproducts.this, sref.toString(), Toast.LENGTH_SHORT).show();
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(imageView);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(listofproducts.this, "failed to download", Toast.LENGTH_SHORT).show();
                }
            });


        }


        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simplerow,R.id.top,arrayList.toArray());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(listofproducts.this, "gdgd"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(listofproducts.this,ShowRecipit.class);
                intent.putExtra("product",product);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }



}