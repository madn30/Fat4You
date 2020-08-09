/*package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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


public class listofproducts extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<Recipit> recipitArrayList;
    ListView listView;
    String product;
    ImageView imageView;
    StorageReference storageReference;
    TextView top, buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofproducts);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        View convertView = getLayoutInflater().inflate(R.layout.simplerow, null);

        imageView = convertView.findViewById(R.id.listimage);
        product = getIntent().getStringExtra("product");
        listView = findViewById(R.id.listView);
        top = findViewById(R.id.top);
        buttom = findViewById(R.id.buttom);

        recipitArrayList = new ArrayList<>();
        getListOfSameProduct();
        //CustomAdapter customAdapter=new CustomAdapter();
        //listView.setAdapter(customAdapter);



    }

    class CustomAdapter extends ArrayAdapter<Recipit> {
        Context context;
        int resource;
        ArrayList<Recipit> recipitList;

        public CustomAdapter(Context context, int resource, ArrayList<Recipit> recipitList){
            super(context,resource,recipitList);
            this.context = context;
            this.resource = resource;
            this.recipitList = recipitList;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Recipit getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final StorageReference sref = storageReference.child("Product Images/").child(recipitArrayList.get(position).getImagePath() + ".png");


            convertView = getLayoutInflater().inflate(R.layout.simplerow, null);

                final ImageView imageView = (ImageView) convertView.findViewById(R.id.listimage);
                TextView textView_top = (TextView) convertView.findViewById(R.id.top);
                TextView textView_bottom = (TextView) convertView.findViewById(R.id.buttom);
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
                        imageView.setImageResource(R.drawable.noimage);
                        Toast.makeText(listofproducts.this, "failed to download", Toast.LENGTH_SHORT).show();
                    }
                });
                textView_top.setText("hellp");
                textView_bottom.setText("hi");

            return convertView;
        }
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


//        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<Recipit> arrayList = new ArrayList<>();

        for (int i = 0; i < recipitArrayList.size(); i++) {

            final StorageReference sref = storageReference.child("Product Images/").child(recipitArrayList.get(i).getImagePath() + ".png");



            //arrayList.add(recipitArrayList.get(i).getTitle() + recipitArrayList.get(i).getEmail());
            arrayList.add(recipitArrayList.get(i));

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
                    imageView.setImageResource(R.drawable.noimage);
                    Toast.makeText(listofproducts.this, "failed to download", Toast.LENGTH_SHORT).show();
                }
            });
            if (sref == null) {
                imageView.setImageResource(R.drawable.noimage);

            }
        }


//        CustomAdapter adapter = new CustomAdapter(this, R.layout.simplerow, arrayList.toArray());
        CustomAdapter adapter = new CustomAdapter(this, R.layout.simplerow, arrayList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(listofproducts.this, "gdgd" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(listofproducts.this, ShowRecipit.class);
                intent.putExtra("product", product);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });
    }


}

 */
package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fat4you.Recipit;
import com.example.fat4you.ShowRecipit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listofproducts extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<Recipit> recipitArrayList;
    ListView listView;
    String product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofproducts);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        product = getIntent().getStringExtra("product");
        listView = findViewById(R.id.listView);
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

        for (int i =0;i<recipitArrayList.size();i++){
            arrayList.add(recipitArrayList.get(i).getTitle());


        }

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simplerow,arrayList.toArray());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(listofproducts.this, "gdgd"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(listofproducts.this, ShowRecipit.class);
                intent.putExtra("product",product);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }

}