package com.example.pheonix.daeguuniversitynavigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyBusInfoActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    BottomNavigationView bottomNavigationView;

    String returnValue = "";
    String IP = "http://businfo.daegu.go.kr/ba/arrbus/arrbus.do?act=bsArr&bsNm=00328";
    LinearLayout BusInfo_layout1,BusInfo_layout2,BusInfo_layout3;
    private RadioGroup rg;
    private RadioButton west , main ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);


        BusInfo_layout1 = (LinearLayout)findViewById(R.id.BusInfoLinear1);
        BusInfo_layout2 = (LinearLayout)findViewById(R.id.BusInfoLinear2);
        BusInfo_layout3 = (LinearLayout)findViewById(R.id.BusInfoLinear3);

        rg = (RadioGroup)findViewById(R.id.radio_g);
        west = (RadioButton)findViewById(R.id.west_r_btn);
        main = (RadioButton)findViewById(R.id.main_r_btn);

        rg.setOnCheckedChangeListener(this);


        Set_BusInfo();

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_businfo);
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

    //バース運行情報を「TextView」で動的に追加する部分
    private void Set_BusInfo(){
        try {

            BusInfo_layout1.removeAllViews();
            BusInfo_layout2.removeAllViews();
            BusInfo_layout3.removeAllViews();

            returnValue = new MyAsyncTask(IP).execute().get();
            if(returnValue == null){
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MyBusInfoActivity.this);
                alert_confirm.setMessage("인터넷연결이 안되었거나 운행시간이 아닙니다.").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();

                return;
            }
            Source src = new Source(returnValue);
            List<Element> all = src.getAllElements();


            for(int i = 0 ; i < all.size(); i++){
                Element temp = all.get(i);

                TextView[][] textViews = new TextView[all.size()][3];

                if(temp.getName().equals("span")){
                    String str = temp.getAttributeValue("class");

                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 170);
                    llp.setMargins(0,0,0,20);

                    for(int j = 0; j<3; j++){
                        textViews[i][j] = new TextView(MyBusInfoActivity.this);

                        textViews[i][j].setLayoutParams(llp);
                        textViews[i][j].setTextColor(Color.parseColor("#000000"));
                        textViews[i][j].setTextSize(25);
                        textViews[i][j].setShadowLayer(1, 0, 0, Color.BLACK);
                        textViews[i][j].setGravity(Gravity.CENTER_VERTICAL);
                    }
                    textViews[i][0].setBackgroundColor(Color.parseColor("#eeeeee"));
                    textViews[i][2].setTextSize(18);


                    switch(str){
                        case "route_no":
                            String temp_busInfo1 = temp.getTextExtractor().toString();
                            textViews[i][0].setText(temp_busInfo1);
                            BusInfo_layout1.addView(textViews[i][0]);
                            break;
                        case "arr_state":
                            String temp_busInfo2 = temp.getTextExtractor().toString();
                            textViews[i][1].setText(temp_busInfo2);
                            BusInfo_layout2.addView(textViews[i][1]);
                            break;
                        case "cur_pos":
                            String temp_busInfo3 = temp.getTextExtractor().toString();
                            textViews[i][2].setText(temp_busInfo3);
                            BusInfo_layout3.addView(textViews[i][2]);
                            break;
                        case "cur_pos nsbus":
                            String temp_busInfo4 = temp.getTextExtractor().toString();
                            textViews[i][2].setText(temp_busInfo4);
                            BusInfo_layout3.addView(textViews[i][2]);
                            break;
                    }

                }

            }//for end

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }
    }

    //停留所を選択する部分、正門と西門中で選択
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(i){
            case R.id.west_r_btn:
                IP = "http://businfo.daegu.go.kr/ba/arrbus/arrbus.do?act=bsArr&bsNm=00328";
                Set_BusInfo();
                Toast.makeText(this, "뒷문", Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_r_btn:
                IP = "http://businfo.daegu.go.kr/ba/arrbus/arrbus.do?act=bsArr&bsNm=00329";
                Set_BusInfo();
                Toast.makeText(this, "정문", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
