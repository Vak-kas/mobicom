package com.example.week11_hw;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView price;
    ImageView imageView;
    Button btn;

    public MyViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.itemTextView);
        price = (TextView) itemView.findViewById(R.id.itemPriceView);
        imageView = (ImageView) itemView.findViewById(R.id.itemImageView);
        btn = (Button) itemView.findViewById(R.id.itembutton);
    }
}