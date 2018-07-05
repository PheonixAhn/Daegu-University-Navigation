package com.example.pheonix.myway;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로딩후 다음페이지 슬라이드로 띄우는 애니메이션 적용
        overridePendingTransition(android.R.anim.slide_in_left,0);
        Settingpermission();
    }



    @Override
    public void onBackPressed() {
        this.finish();
        //로딩 장면에서 백키를 누르게 되면 종료를 하게함
        //슬라이드 아웃 애니메이션적용
        overridePendingTransition(0,android.R.anim.slide_out_right);
    }


    private void Settingpermission(){
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

        }else{

            Toast.makeText(this,"다음페이지로넘어갑니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 0:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED )
                {


                    Toast.makeText(this,"다음페이지로넘어갑니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Menu.class);
                    startActivity(intent);
                }
                else
                {
                    finish();
                }
                break;
            }
        }

    }

}
