package com.example.week11_hw;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<PaintTitle> myDataset = new ArrayList<>();
        myDataset.add(new PaintTitle(R.drawable.kim, "김채원", "₩10,000",
                "김채원은 대한민국에서 유명한 국내 아이돌 '르세라핌'의 리더로써\n" +
                        " 현재도 매우 활발히 활동중이다.\n" +
                        "대학행사 섭외 가격은 10000원 이다"));
        myDataset.add(new PaintTitle(R.drawable.hong, "홍은채", "₩15,000",
                "홍은채는 대한민국에서 유명한 국내 아이돌 '르세라핌'의 멤버로써\n" +
                        " 현재도 매우 활발히 활동중이다.\n" +
                        "대학행사 섭외 가격은 15000원 이다"));
        myDataset.add(new PaintTitle(R.drawable.heo, "허윤진", "₩20,000",
                "허윤진은 대한민국에서 유명한 국내 아이돌 '르세라핌'의 멤버로써\n" +
                        " 현재도 매우 활발히 활동중이다.\n" +
                        "대학행사 섭외 가격은 20000원 이다"));

        MyAdapter adapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(adapter);
    }

    class PaintTitle {
        int imageId;
        String title;
        String price; // 가격 정보 추가

        String description; //자세히

        public PaintTitle(int id, String title, String price, String description) {
            this.imageId = id;
            this.title = title;
            this.price = price;
            this.description = description;
        }
    }
}