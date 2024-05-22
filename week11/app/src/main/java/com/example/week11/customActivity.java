package com.example.week11;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class customActivity extends AppCompatActivity{
    ListView lv;
    ArrayList<PaintTitle> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_simple);

        lv = (ListView) findViewById(R.id.listview);

        data = new ArrayList<PaintTitle>();
        data.add(new PaintTitle(R.drawable.kim, "kim"));
        data.add(new PaintTitle(R.drawable.hong, "hong"));

        MyBaseAdapter adapter = new MyBaseAdapter(this, data);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "Click: position=" + arg2, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "LongClick: position=" + arg2, Toast.LENGTH_SHORT).show();
                return false;

            }
        });
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
                itemLayout = View.inflate(mContext, R.layout.listview_item, null);
                Log.d("hwang", "convertView=null pos="+position);
            } else {
                itemLayout = convertView;
                Log.d("hwang", "convertView!=null pos="+position);
            }


            ImageView imageView = (ImageView) itemLayout.findViewById(R.id.imageview);
//            imageView.setImageResource(mData.get(position).imageId);
             imageView.setImageResource(mData.get(newposition).imageId);

            TextView textView = (TextView) itemLayout.findViewById(R.id.textitemcustom);
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
