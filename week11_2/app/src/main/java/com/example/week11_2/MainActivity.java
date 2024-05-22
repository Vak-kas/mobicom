package com.example.week11_2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);  // for general
        //    mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);  // for general, horizontal
        //  mLayoutManager = new GridLayoutManager(this,2);   //  Grid, (linear  cardview )
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter PaintTitle
        ArrayList<PaintTitle> myDataset=new ArrayList<PaintTitle>();
        myDataset.add(new PaintTitle(R.drawable.kim, "kim"));
        myDataset.add(new PaintTitle(R.drawable.hong, "hong"));

        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }
    class MyBaseAdapter extends BaseAdapter {

        Context mContext = null;
        ArrayList<PaintTitle> mData = null;


        public MyBaseAdapter(Context context, ArrayList<PaintTitle> data) {
            mContext = context;
            mData = data;
        }
        @Override
        public int getCount() {
//            return mData.size();
            return 100;  // for test

        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public PaintTitle getItem(int position) {
//            return mData.get(position);
            return mData.get(position%2);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemLayout;
            int newposition = position % 2;

            if (convertView == null) {
                itemLayout = View.inflate(mContext, R.layout.list, null);
                Log.d("hwang", "convertView=null pos="+position);
            } else {
                itemLayout = convertView;
                Log.d("hwang", "convertView!=null pos="+position);
            }


            ImageView imageView = (ImageView) itemLayout.findViewById(R.id.itemimageview);
//            imageView.setImageResource(mData.get(position).imageId);
            imageView.setImageResource(mData.get(newposition).imageId);

            TextView textView = (TextView) itemLayout.findViewById(R.id.itemtextview);
//            textView.setText(mData.get(position).title);
            textView.setText(mData.get(newposition).title);

            return itemLayout;

        }
    }

    class PaintTitle {
        int imageId;
        String title;

        public PaintTitle(int id, String str) {
            imageId = id;
            title = str;
        }
    }

}