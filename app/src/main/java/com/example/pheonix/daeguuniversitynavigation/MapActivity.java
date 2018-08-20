package com.example.pheonix.daeguuniversitynavigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private ImageView direction_img;
    private ImageView arrive_img;
    private TextView distance_view;

    boolean permission_boolean = false;

    private Context context;
    private GoogleMap mMap;
    public static MapActivity getInstance;
    private Intent intent;

    private double Lat;
    private double Lng;


    private static CameraPreview surfaceView;
    private SurfaceHolder holder;
    private static Camera mCamera;
    private int RESULT_PERMISSIONS = 100;

    LatLng default_location = new LatLng(35.896297, 128.621863);
    LatLng west_gate_loaction = new LatLng(35.896121, 128.620238);
    LatLng main_gate_loaction = new LatLng(35.895213, 128.623659);

    private MyLocation MyLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInit();

        intent = getIntent();
        Lat = intent.getExtras().getDouble("Lat");
        Lng = intent.getExtras().getDouble("Lng");



        //到着地の緯度、経度を使って加速度センサ、方向センサーを
        //使ってユーザーの到着地まで方向をリアルタイムで計算、更新するクラス生成
        MyLocation = new MyLocation(getBaseContext()
                ,Lat ,Lng,direction_img,distance_view);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    //カメラPreviewを画面に生成する部分
    public static Camera getCamera() {
        return mCamera;
    }
    @SuppressLint("WrongViewCast")
    private void setInit() {
        getInstance = this;

        mCamera = Camera.open();

        setContentView(R.layout.activity_maps);


        direction_img = (ImageView)findViewById(R.id.img);
        distance_view = (TextView)findViewById(R.id.textView3);

        surfaceView = findViewById(R.id.preview);

        holder = surfaceView.getHolder();
        holder.addCallback(surfaceView);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }



    //自分の位置を確認する GoogleMapを作る
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng destination = new LatLng(Lat, Lng);
        mMap.addMarker(new MarkerOptions().position(destination).title("Marker in Sydney"));


        googleMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(west_gate_loaction).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gate))));
        mMap.addMarker(new MarkerOptions().position(main_gate_loaction).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gate))));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if( event.getAction() == KeyEvent.ACTION_DOWN ){
            if( keyCode == KeyEvent.KEYCODE_BACK ){
                Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
                return true;
            }
        }
        return super.onKeyDown( keyCode, event );
    }


}