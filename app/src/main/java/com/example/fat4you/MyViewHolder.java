package com.example.fat4you;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView name;
    TextView products;
    TextView how;

    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image_single_view);
        name=itemView.findViewById(R.id.name);
        products=itemView.findViewById(R.id.product);
        how=itemView.findViewById(R.id.hows);



        v=itemView;
    }
}
