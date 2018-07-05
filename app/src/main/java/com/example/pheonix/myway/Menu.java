package com.example.pheonix.myway;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by pheonix on 2018-02-09.
 */

public class Menu extends AppCompatActivity implements OnMapReadyCallback , SurfaceHolder.Callback{

    private Menu context;
    //GPS관련 변수들
    protected LocationManager lm;


    double myLat;
    double myLng;
    double Lat = 35.896297;
    double Lng = 128.621863;
    double distance;

    //카메라 관련 변수들
    private SurfaceView mCameraView;
    private SurfaceHolder mCameraHolder;
    private Camera mCamera;

    TextView textView;
    Button button;
    Spinner spinner ;
    String select_item = "";
    String where = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu);

        textView = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        spinner = (Spinner)findViewById(R.id.spinner2);
        select_item = (String)spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: Lat = 35.896660 ; Lng = 128.620550;
                        break;
                    case 1: Lat = 35.896776 ; Lng = 128.621809;
                        break;
                    case 2: Lat = 35.895699 ; Lng = 128.620532;
                        break;
                    case 3: Lat = 35.895581 ; Lng = 128.621294;
                        break;
                    case 4: Lat = 35.896774 ; Lng = 128.622707;
                        break;
                    case 5: Lat = 35.896972 ; Lng = 128.622335;
                        break;
                    case 6: Lat = 35.896861 ; Lng = 128.623134;
                        break;
                    case 7: Lat = 35.896366 ; Lng = 128.622973;
                        break;
                }
                metercheck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner Yeungjin = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter YeungjinAdapter = ArrayAdapter.createFromResource(this,
                R.array.Yeungjin,android.R.layout.simple_spinner_item);
        spinner.setPrompt("목표지");

        YeungjinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Yeungjin.setAdapter(YeungjinAdapter);



        button.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {

//                        Loading loading = new Loading(context);
//                        new Thread(loading).start();
                metercheck();

            } });


        mCameraView = (SurfaceView)findViewById(R.id.surface);
        init();



        //뻐미션체크 ㅎㅎㅎ
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED
                ){

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION
                            ,Manifest.permission.CAMERA
                            ,Manifest.permission.INTERNET},
                    0);

        }

        //위치관리자 객체생성
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,1,mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2000,1,mLocationListener);


        //구글맵 구동부분
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    } // end of onCreate
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myLat = location.getLatitude();
            myLng = location.getLongitude();
            metercheck();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void init() {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);

        // surfaceview setting
        mCameraHolder = mCameraView.getHolder();
        mCameraHolder.addCallback(this);
        mCameraHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera == null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // View 가 존재하지 않을 때
        if (mCameraHolder.getSurface() == null) {
            return;
        }

        // 작업을 위해 잠시 멈춘다
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // 에러가 나더라도 무시한다.
        }

        // 카메라 설정을 다시 한다.
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        mCamera.setParameters(parameters);

        // View 를 재생성한다.
        try {
            mCamera.setPreviewDisplay(mCameraHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        LatLng SEOUL = new LatLng(myLat, myLng);
        LatLng Yeungjin = new LatLng(Lat, Lng);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(Yeungjin);

        markerOptions.title("영진전문대");

        markerOptions.snippet("운동장");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Yeungjin));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

    }

    public void metercheck(){
        Location locationA = new Location("point A");

        locationA.setLatitude(myLat);
        locationA.setLongitude(myLng);

        Location locationB = new Location("point B");

        locationB.setLatitude(Lat);
        locationB.setLongitude(Lng);

        distance = locationA.distanceTo(locationB);
        distance = Double.parseDouble(String.format("%.3f",distance/1000));
        String Distance=String.valueOf(distance);
        textView.setText(Distance+"KM");
    }

} // end of class

