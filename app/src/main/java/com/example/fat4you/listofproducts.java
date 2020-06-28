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
                Intent intent = new Intent(listofproducts.this,ShowRecipit.class);
                intent.putExtra("product",product);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }

}