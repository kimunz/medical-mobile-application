package com.hope.patient.p_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyinfoActivity extends Activity {

    Myinfo my_Info;
    TextView myinfo_name;
    TextView myinfo_gender;
    TextView myinfo_age;
    TextView myinfo_birth;
    TextView myinfo_roomnum;
    TextView myinfo_disease;
    TextView myinfo_doctor;
    TextView myinfo_forbid;
    Button changePW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo);

        final Intent intent = getIntent();
        my_Info = (Myinfo) intent.getSerializableExtra("MyInfo");

        myinfo_name = (TextView)findViewById(R.id.myinfo_name);
        myinfo_gender = (TextView)findViewById(R.id.myinfo_gender);
        myinfo_age = (TextView)findViewById(R.id.myinfo_age);
        myinfo_birth = (TextView)findViewById(R.id.myinfo_birth);
        myinfo_roomnum = (TextView)findViewById(R.id.myinfo_roomnum);
        myinfo_disease = (TextView)findViewById(R.id.myinfo_disease);
        myinfo_doctor = (TextView)findViewById(R.id.myinfo_doctor);
        myinfo_forbid = (TextView)findViewById(R.id.myinfo_forbid);
        changePW = (Button)findViewById(R.id.changePW);

        myinfo_name.setText(my_Info.getpName());
        myinfo_gender.setText(my_Info.getpGender());
        myinfo_age.setText(my_Info.getpAge());
        myinfo_birth.setText(my_Info.getpBirth());
        myinfo_roomnum.setText(my_Info.getpRoomNum());
        myinfo_disease.setText( my_Info.getpDiseaseName());
        myinfo_doctor.setText(my_Info.getdName());
        myinfo_forbid.setText(my_Info.getpForbidFood());

        changePW.setPaintFlags(changePW.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyinfoActivity.this, ChangePW_Activity.class);
                intent.putExtra("MyInfo", my_Info);
                startActivity(intent);
            }
        });
    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(MyinfoActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }
}
