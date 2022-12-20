package com.hope.patient.p_hopeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends Activity {

    ProgressDialog progressDialog = null;
    static Myinfo my_Info = new Myinfo();
    EditText login_id;
    EditText login_pw;
    public String receive_info = "";
    public String receive_parsh[];
    CheckBox login_auto;

    SharedPreferences setting;
    SharedPreferences.Editor editor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_id = (EditText) findViewById(R.id.login_id);
        login_pw = (EditText) findViewById(R.id.login_pw);
        login_auto = (CheckBox) findViewById(R.id.login_auto);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("Auto_Login_enabled", false)){
            login_id.setText(setting.getString("ID", ""));
            login_pw.setText(setting.getString("PW", ""));
            login_auto.setChecked(true);

            MyHttpTask myHttpTask = new MyHttpTask();
            myHttpTask.setBufferStr("p_ID=" + login_id.getText().toString() + "&p_PW=" + login_pw.getText().toString());
            myHttpTask.execute("http://hope2017.esy.es/p_login.php");
        }

        login_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    String ID = login_id.getText().toString();
                    String PW = login_pw.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);

                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }

    public void onButtonClicked(View v) {
        if (login_id.getText().toString().equals("") || login_pw.getText().toString().equals("")) {
            return;
        }
        Log.d("회진 언제 와요?", "서버로" + login_id.getText().toString());
        Log.d("회진 언제 와요?", "서버로" + login_pw.getText().toString());

        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("p_ID=" + login_id.getText().toString() + "&p_PW=" + login_pw.getText().toString());
        myHttpTask.execute("http://hope2017.esy.es/p_login.php");

    }

    public void run_login() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("MyInfo",my_Info);
        startActivity(intent);

    }

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyDialogStyle);
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
            String[] stringArr = result.split("/", 11);
            progressDialog.dismiss();
            progressDialog = null;

            int birth;
            int age;
            String temp_year, temp_month, temp_day;
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            int getYear = Integer.parseInt(sdf1.format(date));

            if (stringArr[0].equals("Success")) {

                my_Info.setpID(stringArr[1]);
                my_Info.setpName(stringArr[2]);
                my_Info.setdID(stringArr[3]);
                my_Info.setdName(stringArr[4]);
                my_Info.setpGender(stringArr[5]);
                my_Info.setpRoomNum(stringArr[6]);
                my_Info.setpDiseaseName(stringArr[7]);
                my_Info.setpForbidFood(stringArr[8]);

                birth = Integer.parseInt(stringArr[9]);

                temp_year = String.valueOf(birth/10000);
                temp_month = String.valueOf((birth/100)%100);
                temp_day = String.valueOf(birth%100);

                age = getYear - (birth / 10000) + 1;

                my_Info.setpBirth(temp_year+"년 "+temp_month+"월 "+temp_day+"일");
                my_Info.setpAge(String.valueOf(age));
                run_login();
            }
            else {
                Toast.makeText(LoginActivity.this, "입력하신 아이디와 패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
