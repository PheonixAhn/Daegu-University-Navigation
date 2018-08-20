package com.example.pheonix.daeguuniversitynavigation;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyLocation extends Service implements LocationListener {

    private final Context context;

    double MyLat;
    double MyLng;
    double WantLat;
    double WantLng;

    double NaviAngle;
    int MyAngle=0;

    int counter = 0;

    private SensorManager mSensorManager;
    private Sensor mOrientation;
    private TextView x, y, z;

    float[] rotation;
    float[] accelerometerValues;
    float[] magneticFieldValues;

    protected LocationManager lm;
    public Location location;
    Location destination;

    private TextView ditance_view;
    private ImageView arrive_img;

    public MyLocation(
            final Context context,
            double Lat , double Lng,
            final ImageView imageView,
            TextView distance_view){

        this.context = context;
        this.ditance_view = distance_view;
        WantLat = Lat;
        WantLng = Lng;
        destination = new Location("Destination");
        destination.setLatitude(WantLat);
        destination.setLongitude(WantLng);
        getLocation();

        SensorManager sensorManager = (SensorManager)this.context.getSystemService(SENSOR_SERVICE);

        SensorEventListener mListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] v = event.values;

//                if(counter++ % 30 != 0)
//                    return;

                switch(event.sensor.getType()) {
                    case Sensor.TYPE_LIGHT:
                        break;
                    case Sensor.TYPE_PROXIMITY:
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                        accelerometerValues = v;
                        break;
                    case Sensor.TYPE_ORIENTATION:
                        break;

                    case Sensor.TYPE_MAGNETIC_FIELD:
                        magneticFieldValues = v;
                        break;
                }


                if(accelerometerValues != null && magneticFieldValues != null){
                    float[] values = new float[3];
                    float[] mr = new float[9];

                    SensorManager.getRotationMatrix(mr, null, accelerometerValues, magneticFieldValues);
                    SensorManager.getOrientation(mr, values);
                    values[0] = (float) Math.toDegrees(values[0]);
                    values[1] = (float) Math.toDegrees(values[1]);
                    values[2] = (float) Math.toDegrees(values[2]);




                    MyAngle = (int)values[0];
                    if(MyAngle < 0 )
                        MyAngle += 360 ;

                    MyAngle -= NaviAngle;

                    //スマホの方向角度と到着地への方向角度を
                    //比較して方向角度をイメージに反映
                    imageView.setRotation(-MyAngle);
                }

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //加速度センサー
        sensorManager.registerListener(mListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

        //方向センサー
        sensorManager.registerListener(mListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);

        //磁場センサー
        sensorManager.registerListener(mListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                sensorManager.SENSOR_DELAY_UI);

    }

    @SuppressLint("MissingPermission")
    public Location getLocation(){

        lm = (LocationManager)context.getSystemService(LOCATION_SERVICE);


        lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 0, 0, this);
        location = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);

        MyLat = location.getLatitude();
        MyLng = location.getLongitude();

        return location;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {

            MyLat = location.getLatitude();
            MyLng = location.getLongitude();

            NaviAngle = bearingP1toP2(MyLat, MyLng, WantLat, WantLng);

            String distance =String.valueOf ((int)location.distanceTo(destination));
            this.ditance_view.setText(distance);

//            Toast.makeText(context, "목적각:"+Double.toString(NaviAngle)+"\n 내각도:"+String.valueOf(MyAngle), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //別の場所の二つの緯度と経度をパラメータで到着地の方向角度を計算
    public short bearingP1toP2(double P1_latitude, double P1_longitude, double P2_latitude, double P2_longitude) {
        //現在位置
        double Cur_Lat_radian = P1_latitude * (3.141592 / 180);
        double Cur_Lon_radian = P1_longitude * (3.141592 / 180);


        //目標位置
        double Dest_Lat_radian = P2_latitude * (3.141592 / 180);
        double Dest_Lon_radian = P2_longitude * (3.141592 / 180);

        //radian distance
        double radian_distance = 0;
        radian_distance = Math.acos(Math.sin(Cur_Lat_radian) * Math.sin(Dest_Lat_radian) + Math.cos(Cur_Lat_radian) * Math.cos(Dest_Lat_radian) * Math.cos(Cur_Lon_radian - Dest_Lon_radian));

        //目標位置までの移動方向
        double radian_bearing = Math.acos((Math.sin(Dest_Lat_radian) - Math.sin(Cur_Lat_radian) * Math.cos(radian_distance)) / (Math.cos(Cur_Lat_radian) * Math.sin(radian_distance)));        // acos의 인수로 주어지는 x는 360분법의 각도가 아닌 radian(호도)값이다.

        double true_bearing = 0;
        if (Math.sin(Dest_Lon_radian - Cur_Lon_radian) < 0) {
            true_bearing = radian_bearing * (180 / 3.141592);
            true_bearing = 360 - true_bearing;
        } else {
            true_bearing = radian_bearing * (180 / 3.141592);
        }

        return (short) true_bearing;
    }
}


