package com.example.week13_hw;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    MapFragment mapFrag;
    Button btnPrev, btnNext;
    List<String> lines = new ArrayList<>();
    int placeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("대전 맛집 지도");
        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);

        readCSV();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeCount++;
                if (placeCount >= lines.size()) placeCount = 0;
                updateMap();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeCount--;
                if (placeCount < 0) placeCount = lines.size() - 1;
                updateMap();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.3510, 127.3850), 13));
        gMap.getUiSettings().setZoomControlsEnabled(true);
        updateMap();
    }

    public void readCSV() {
        InputStream inputStream = getResources().openRawResource(R.raw.good_place);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            reader.readLine(); // 첫 번째 제목행을 버림
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMap() {
        if (gMap == null || lines.size() == 0) return;
        gMap.clear();
        String[] tokens = lines.get(placeCount).split(",");
        double lat = Double.parseDouble(tokens[0]);
        double lon = Double.parseDouble(tokens[1]);
        String restName = tokens[2];

        LatLng point = new LatLng(lat, lon);
        gMap.addMarker(new MarkerOptions().position(point).title(restName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.food)));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
        Toast.makeText(getApplicationContext(), restName, Toast.LENGTH_LONG).show();
    }
}
