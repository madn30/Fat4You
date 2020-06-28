package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListProducts extends AppCompatActivity {
private DatabaseReference reference;
private RecyclerView recyclerView;
    FirebaseRecyclerOptions<Products> options;
    FirebaseRecyclerAdapter<Products,MyViewHolder>adapter;
    DatabaseReference Dataref;
    DatabaseReference Datareff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);


        Dataref= FirebaseDatabase.getInstance().getReference().child("Items");

        recyclerView=findViewById(R.id.recyleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(false);


        LoadData("");




    }

    private void LoadData(String data) {
        Query query=Dataref.orderByChild("name").startAt(data).endAt(data+"\uf8ff");
        Datareff= FirebaseDatabase.getInstance().getReference();

        options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(query,Products.class).build();
        adapter=new FirebaseRecyclerAdapter<Products, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Products model) {
                holder.name.setText(model.getName());
                holder.products.setText(model.getProduct());
                holder.how.setText(model.getHow());

                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ListProducts.this,Single_View.class);
                        intent.putExtra("pid",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
         }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single__view,parent,false);
                return new MyViewHolder(v);
            }






        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}