package com.example.week11_2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv1;
    ImageView imageView;
    Button btn;

    public MyViewHolder(View itemView) {
        super(itemView);

        tv1 = (TextView) itemView.findViewById(R.id.itemtextview);
        imageView = (ImageView) itemView.findViewById(R.id.itemimageview);
        btn = (Button) itemView.findViewById(R.id.itembutton);

    }

}

