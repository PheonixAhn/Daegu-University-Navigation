package com.example.pheonix.daeguuniversitynavigation;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InfoActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    private Spinner  spinner;
    String select_item = "";

    private ImageView imgbtn1;
    private ImageView imgbtn2;

    private TextView b_introduce_text, b_information_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        spinner = (Spinner)findViewById(R.id.spinner);
        select_item = (String)spinner.getSelectedItem();

        b_introduce_text = (TextView)findViewById(R.id.building_introduce);
        b_information_text = (TextView)findViewById(R.id.building_information);

        imgbtn1 = (ImageView)findViewById(R.id.b_img1);
        imgbtn2 = (ImageView)findViewById(R.id.b_img2);

        //選択したビルによってビルの説明イメージとテキストが変わる
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 1:
                        imgbtn1.setImageResource(R.drawable.bon2);
                        imgbtn2.setImageResource(R.drawable.bon_m);


                        b_introduce_text.setText("1978년 준공된 건물로서 지하 1층 지상 5층 철근콘크리트 구조로 연 면적은 11.331이며 대학본부 및 부속기관, 컴퓨터정보계열, 콘텐츠디자인과, 간호학과, 연구실, 강의실, 실습실, 학생식당 등으로 사용되고 있다.");
                        b_information_text.setText("5F 콘텐츠디자인과, Global Zone, 간호과 기초간호과학실습실\n" +
                                "                                    \\n- 4F 간호학과사무실, 시뮬레이션센터, 기본간호학실습실, 간호학소그룹학습실, PC실습실, 간호학과 Global Zone, 모성간호실습실, 콘텐츠디자인과사무실, CG실습실, PC실습실\n" +
                                "                                    \\n- 3F 컴퓨터정보계열사무실, 게임프로그래밍실습실, DB프로그래밍실습실, 애니메이션컨텐츠실습실, 시스템실, 컴퓨터정보계열 Global Zone\n" +
                                "                                    \\n- 2F DB프로젝트실습실, 소규모프로젝트실, 컴퓨터정보계열 PC실습실, 총장실, 부총장실, 부속실, 기획처\n" +
                                "                                    \\n- 1F 학사운영처, 산학협력단, 취업상담실, 학생복지처, 학사지원처, 원스탑서비스센터, 사회봉사단, 주문식교육추진센터, 예비군대대시스템실, 학보사\n" +
                                "                                    \\n- B1 학생휴게실 및 식당");
                        break;
                    case 2:
                        imgbtn1.setImageResource(R.drawable.machine);
                        imgbtn2.setImageResource(R.drawable.machine_m);
                        b_introduce_text.setText("1979년에 준공된 건물로서 지하 1층, 지상 5층 철근콘크리트 구조로 연면적은 6.854이며 학생복지처, 원스탑지원센터, 우체국, 구내서점, 북카페 전자정보통신계열, 신재생에너지전기계열 연구실, 사무실, 강의실, 실습실 등으로 사용하고 있다.");
                        b_information_text.setText("5F 신재생에너지전기계열 AutoCAD실습실, 전자정보통신계열 강의실, 전공연구회, 무선국\n" +
                                "- 4F 전자정보통신계열 인터넷 실습실, 신재생에너지전기계열 전력실습실, 전기기기실습실, 바이오센서실습실, 강의실\n" +
                                "- 3F 전자정보통신계열 종합기자재실, 통신응용실습실, 전자정보응용실습실, 마이크로컴퓨터실습실, 정보통신실습실, 디지털전자실습실, Global Zone, 사무실\n" +
                                "- 2F 신재생에너지전기계열 전기응용실습실, 컴퓨터제어실습실, 전력전자실습실, PLC실습실, 시퀀스/제이실습실, 기초실험실습실, 강의실, 사무실, Global Zone");
                        break;
                    case 3:
                        imgbtn1.setImageResource(R.drawable.children);
                        imgbtn2.setImageResource(R.drawable.children_m);
                        b_introduce_text.setText("1986년에 준공된 건물로서 지하 1층, 지상 5층 철근콘크리트 구조로 연면적은 3,914이며 유아교육과, 보육교사교육원 연구실, 사무실, 강의실, 실습실, 유치원, 어린이집 등으로 사용하고 있다.");
                        b_information_text.setText("- 5F 유아교육과 멀티미디어실습실\n" +
                                "- 4F 유아교육과 사무실, 강의실, 미술/공예 실습실, Global Zone, 보육교사교육원 사무실, 강의실, 유아교육과 피아노실\n" +
                                "- 3F 보육교사교육원 강의실, 유치원 교실, 관찰실, 컴퓨터놀이실, 유아교육과 강의실, 유아정보화 교육실습실\n" +
                                "- 2F 어린이집 보육실, 놀이터, 유아교육과 표현활동실습실, 유치원 교실, 유아도서실, 아틀리에\n" +
                                "- 1F 어린이집 교무실, 보육실, 교재실, 유치원 교무실, 교실, 관찰실, 식당, 기재실, 어린이 방송국");
                        break;
                    case 4:
                        imgbtn1.setImageResource(R.drawable.information);
                        imgbtn2.setImageResource(R.drawable.information_m);
                        b_introduce_text.setText("2002년에 준공된 건물로서 지하 1층, 지상 5층 철골 구조로 연면적은 14,995이며 최신 IBS 건물이다.");
                        b_information_text.setText("5F 외국어 교육원 사무실, 종합강의실, 위성방송청취실, 강의실, 디지털한국문화체험실, 영재교육센터 교실, 아동창의성계발연구소\n" +
                                "- 4F 사이버대학 강의실, 총학생회실, 동아리실, 사무실, 한일기업지원센터 사무실, 입주업체, IT지원센터, 국제공인자격시험센터, 가상공학센터, 교수학습지원센터, 교수학습개발실, 보육교사교육원 강의실\n" +
                                "- 3F 컴퓨터정보계열 IT팜스쿨, 전자상거리지원센터(ECRC), 모션캡쳐실, IT지원센터, 주컴퓨터실, 평생교육원 Oracle교육장, 교양실, 교수학습지원센터 스튜디오/교육방송국, 유아교육과 강의실, 사이버대학 UMS실습실, 강의실, PC실습실\n" +
                                "- 2F 무용실, 미술실, 응급구조사실습실, 응급구조사강의실, PC실습실, 영진컨텍센터 교양실, 컴퓨터역사관 평생교육원 사무실, 국가인적자원개발컨소시엄 전자회로설계실습실, BIM실습실, 사이버대학 사무실\n" +
                                "- 1F 국제세미나실, 국가인적자원개발컨소시엄 유공압실습실, CAD/CAM실습실, 태양광발전실습실, 그린에너지실습실, 스마트방송통신실습실");
                        break;
                    case 5:
                        imgbtn1.setImageResource(R.drawable.yeon);
                        imgbtn2.setImageResource(R.drawable.yeon_m);
                        b_introduce_text.setText("1994년에 준공된 건물로서 지하 1층, 지상 5층 철근콘크리트 구조로 연면적은 9,483이며 컴퓨터응용기계계열, 국제관광조리계열 연구실, 사무실, 강의실, 실습실, 학생전공연구관, 서클실 등으로 사용하고 있다.");
                        b_information_text.setText("5F 국제관광조리계열 항공실습실, 강의실, 취업정보센터, 사무실, 라커룸, Global Zone\n" +
                                "4F 국제관광조리계열 호텔정보시스템실습실, T/C실습실, TOPAS실습실, 강의실, 컴퓨터응용기계계열 강의실\n" +
                                "3F 컴퓨터응용기계계열 CIM실습실, 유공압실습실, CAD II 실습실, PLC실습실, 강의실, 전공연구회, 국제관광조리계열 호텔실습실\n" +
                                "2F 컴퓨터응용기계계열 CAE실습실, 워크스테이션실, 제품개발프로세스실습실, CAD I 실습실, 사무실\n" +
                                "1F 컴퓨터응용기계계열 CAM프로그래밍실습실, CNC실습실, 워크스테이션실, CAD실습실, 금형가공실습실, 자동차설계실습실, 강의실, 남/여 샤워실, 전공연구회\n" +
                                "B1 용접실습실");
                        break;
                    case 6:
                        imgbtn1.setImageResource(R.drawable.gym);
                        imgbtn2.setImageResource(R.drawable.gym_m);
                        b_introduce_text.setText("1994년에 준공된 건물로서 지하 1층, 지상 5층 철근콘크리트 구조로 연면적은 2,980이며 실내 체육관, 헬스장 등으로 사용하고 있다.");
                        b_information_text.setText("- 1F 조명실, 방송실, 관람석\n" +
                                "- B1 농구장, 배구장, 탁구장, 무대, 대기실, 영진헬스장, 관리실");
                        break;
                    case 7:
                        imgbtn1.setImageResource(R.drawable.create);
                        imgbtn2.setImageResource(R.drawable.create_m);
                        b_introduce_text.setText("1997년에 준공된 건물로서 지하 1층, 지상 5층 철근콘크리트 구조로 연면적은 4,939이며 건축인테리어디자인계열 연구실, 실습실, 강의실 등으로 사용하고 있다.");
                        b_information_text.setText("- 5F 특수공간디테일실, 제도실습실, 모형실, 스터디누드시공실\n" +
                                "- 4F 건축인테리어디자인계열 PC실습실, 실내누드시공실\n" +
                                "- 3F 건축인테리어디자인계열 제도실습실, PC실습실, 강의실, 디지털경영계열 강의실, 부사관계열 통신응용실습실\n" +
                                "- 2F 건축인테리어디자인계열 사무실, AV실습실, Global Zone\n" +
                                "- 1F 자율학습 학생휴게실, 무인택배기");
                        break;
                    case 8:
                        imgbtn1.setImageResource(R.drawable.listen);
                        imgbtn2.setImageResource(R.drawable.listen_m);
                        b_introduce_text.setText("1991년에 준공된 건물로서 지하 1층, 지상 5층 철근콘크리트 구조로 연면적은 6,066이며 부사관계열, 스마트경영계열, 사회복지과 연구실, 강의실, 실습실 등으로 사용하고 있다.");
                        b_information_text.setText("- 5F 스마트경영계열 : 사회복지과 전공연구회\n" +
                                "- 4F 사회복지과 사무실, 강의실, 사회복지과 Global Zone, 소그룹실습실\n" +
                                "- 3F 스마트경영계열 정보처리실습실, 전산회계실습실, 전자상거래실습실, 웹디자인실습실, ERP실습실, 강의실, 사무실\n" +
                                "- 2F 스마트경영계열 고객감동실, OA실습실, 강의실, Global Zone, 사회복지과 현장실습실, 부사관계열 사무실, 강의실\n" +
                                "- 1F 부사관계열 PC실습실, 전자회로응용실습실, 연구실, 대구은행\n" +
                                "\n");
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //BottomNavigation選択設定
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_info);
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


        Spinner Yeungjin = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter YeungjinAdapter = ArrayAdapter.createFromResource(this,
                R.array.Yeungjin,android.R.layout.simple_spinner_item);
        spinner.setPrompt("건물 세부정보");

        YeungjinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Yeungjin.setAdapter(YeungjinAdapter);
    }
}
