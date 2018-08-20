package com.example.pheonix.daeguuniversitynavigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    BottomNavigationView bottomNavigationView;
    private Button button;

    private GoogleMap mMap;
    public static MapActivity getInstance;
    private Spinner  spinner;

    double Lat = 35.896297;
    double Lng = 128.621863;

    String select_item = "";

    LatLng goal_location = new LatLng(35.896297, 128.621863);
    LatLng default_location = new LatLng(35.896297, 128.621863);
    LatLng west_gate_loaction = new LatLng(35.896121, 128.620238);
    LatLng main_gate_loaction = new LatLng(35.895213, 128.623659);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        spinner = (Spinner)findViewById(R.id.spinner);
        select_item = (String)spinner.getSelectedItem();

        //到着地を設定すると、到着地の緯度、経度を設定する
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 1: Lat = 35.896660 ; Lng = 128.620550;
                        break;
                    case 2: Lat = 35.896776 ; Lng = 128.621809;
                        break;
                    case 3: Lat = 35.895699 ; Lng = 128.620532;
                        break;
                    case 4: Lat = 35.895581 ; Lng = 128.621294;
                        break;
                    case 5: Lat = 35.896774 ; Lng = 128.622707;
                        break;
                    case 6: Lat = 35.896972 ; Lng = 128.622335;
                        break;
                    case 7: Lat = 35.896861 ; Lng = 128.623134;
                        break;
                    case 8: Lat = 35.896366 ; Lng = 128.622973;
                        break;
                }
                    if(position != 0 ) {
                        mMap.clear();
                        goal_location = new LatLng(Lat, Lng);
                        mMap.addMarker(new MarkerOptions().position(goal_location));

                        mMap.addMarker(new MarkerOptions().position(west_gate_loaction)
                                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.gate))));

                        mMap.addMarker(new MarkerOptions().position(main_gate_loaction)
                                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.gate))));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(default_location));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner Yeungjin = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter YeungjinAdapter = ArrayAdapter.createFromResource(this,
                R.array.Yeungjin,android.R.layout.simple_spinner_item);
        spinner.setPrompt("목표지");

        YeungjinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Yeungjin.setAdapter(YeungjinAdapter);



        //ユーザーが設定した到着地に案内するMapActivityに移動
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                intent.putExtra("Lat",Lat);
                intent.putExtra("Lng",Lng);
                startActivity(intent);
                finish();

         }
        });



        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //info
                    case R.id.action_info:
                        Intent intent1 = new Intent(getBaseContext(), InfoActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    //businfo
                    case R.id.action_businfo:
                        Intent intent2 = new Intent(getBaseContext(), MyBusInfoActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    //restaurant
                    case R.id.action_restaurant:
                        Intent intent3 = new Intent(getBaseContext(), MyRestaurantActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(goal_location).title("Yeungjin University"));
        mMap.addMarker(new MarkerOptions().position(west_gate_loaction).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gate))));
        mMap.addMarker(new MarkerOptions().position(main_gate_loaction).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gate))));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(default_location));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}