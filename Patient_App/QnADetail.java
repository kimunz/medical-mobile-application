package com.hope.patient.p_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QnADetail extends Activity {

    TextView q_detail_date, q_detail_body, q_detail_read;
    QnAItem detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);

        q_detail_date = (TextView) findViewById(R.id.q_detail_date);
        q_detail_body = (TextView) findViewById(R.id.q_detail_body);
        q_detail_read = (TextView) findViewById(R.id.q_detail_read);

        Intent intent = getIntent();
        detail = (QnAItem) intent.getSerializableExtra("detail");

        q_detail_date.setText(detail.getQDate());
        q_detail_body.setText(detail.getBody());

        if(detail.getIsRead().equals("1")){
            // 읽었음
            q_detail_read.setText("읽음");
        }
        else{
            // 안읽었음
            q_detail_read.setText("읽지 않음");
        }
    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(QnADetail.this, LoginActivity.class);
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
