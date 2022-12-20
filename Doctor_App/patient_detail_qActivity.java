package com.example.d_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class patient_detail_qActivity extends Activity {

    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    TextView q_detail_date, q_detail_body;
    qna_item q_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_detail_qdetail);

        q_detail_date = (TextView) findViewById(R.id.p_q_detail_date);
        q_detail_body = (TextView) findViewById(R.id.p_q_detail_body);

        Intent intent = getIntent();
        q_detail = (qna_item) intent.getSerializableExtra("q_detail");

        q_detail_date.setText(q_detail.getQDate());
        q_detail_body.setText(q_detail.getBody());


        if(q_detail.getIs_read().equals("0")){
            MyHttpTask myHttpTask = new MyHttpTask();
            myHttpTask.setBufferStr("p_ID=" + q_detail.getP_id() + "&q_date=" + q_detail.getQDate());
            myHttpTask.execute("http://hope2017.esy.es/d_update_qna.php");
        }

    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(patient_detail_qActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        ((patient_detailActivity)patient_detailActivity.mContext).update_info();
        this.finish();
    }


    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(patient_detail_qActivity.this, R.style.MyDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시만 기다려주세요");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDefaultUseCaches(false);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");
                http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                // --------------------------
                //   서버로 값 전송
                // --------------------------
                StringBuffer buffer = new StringBuffer();
                buffer.append(bufferStr);
                OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();
                // --------------------------
                //   서버에서 전송받기
                // --------------------------
                InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                http.disconnect();
                Log.d("회진 언제 와요?", url.toString());
                Log.d("회진 언제 와요?", bufferStr);
                Log.d("회진 언제 와요?", builder.toString());
                receive_info = builder.toString();
                receive_parsh = receive_info.split("/");

                return builder.toString();
            } catch (IOException e) {
                Log.d("회진 언제 와요?", e.toString());
                return "ERROR";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            progressDialog = null;

            if (result.equals("Success")) {
            } else {
                System.out.println(result+"업뎃실패ㅜ따흑");
            }
        }
    }
}
