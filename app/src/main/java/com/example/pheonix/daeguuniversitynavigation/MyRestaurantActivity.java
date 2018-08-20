package com.example.pheonix.daeguuniversitynavigation;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//RecyclerViewで周りのいい食堂のリストを見せる「Class」
public class MyRestaurantActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<FoodInfo> foodInfoArrayList = new ArrayList<>();
        foodInfoArrayList.add(new FoodInfo(R.drawable.bobs, "밥스"));
        foodInfoArrayList.add(new FoodInfo(R.drawable.dducbul, "뚝불"));
        foodInfoArrayList.add(new FoodInfo(R.drawable.onigiri, "오니기리"));
        foodInfoArrayList.add(new FoodInfo(R.drawable.seoul, "서울식당"));
        foodInfoArrayList.add(new FoodInfo(R.drawable.mcd, "맥도날드"));
        foodInfoArrayList.add(new FoodInfo(R.drawable.onemill, "집밥한끼"));
        foodInfoArrayList.add(new FoodInfo(R.drawable.ojjam, "오늘은 짬뽕 땡기는날"));

        FoodAdapter myAdapter = new FoodAdapter(foodInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_restaurant);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //home
                    case R.id.action_home:
                        Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    //info
                    case R.id.action_info:
                        Intent intent2 = new Intent(getBaseContext(), InfoActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    //businfo
                    case R.id.action_businfo:
                        Intent intent3 = new Intent(getBaseContext(), MyBusInfoActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

}
