package com.hope.patient.p_hopeapp;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IT on 2017-10-29.
 */

public class ChangePW_Activity extends Activity {
    Myinfo my_Info;
    EditText curPW;
    EditText newPW;
    EditText newPW2;
    Button changePWbtn;

    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pw);

        final Intent intent = getIntent();
        my_Info = (Myinfo) intent.getSerializableExtra("MyInfo");

        curPW = (EditText)findViewById(R.id.curPW);
        newPW = (EditText)findViewById(R.id.newPW);
        newPW2 = (EditText)findViewById(R.id.newPW2);
        changePWbtn = (Button)findViewById(R.id.changePWbtn);


        changePWbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newPW.getText().toString().equals("")) {
                    Toast.makeText(ChangePW_Activity.this, "새 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(newPW.getText().toString().equals(newPW2.getText().toString())) {
                    MyHttpTask myHttpTask = new MyHttpTask();
                    myHttpTask.setBufferStr("p_ID=" + my_Info.getpID().toString() + "&p_PW=" + curPW.getText() + "&p_newPW=" + newPW.getText());
                    myHttpTask.execute("http://hope2017.esy.es/p_changePW.php");
                }
                else{
                    Toast.makeText(ChangePW_Activity.this, "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(ChangePW_Activity.this, LoginActivity.class);
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


    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ChangePW_Activity.this, R.style.MyDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시만 기다려주세요");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
//            progressDialog.show();
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

                return builder.toString();
            } catch (IOException e) {
                Log.d("회진 언제 와요?", e.toString());
                return "ERROR";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            String[] stringArr = result.split("/");
            if(stringArr[0].equals("Success")) {
                curPW.setText("");
                newPW.setText("");
                newPW2.setText("");
                Toast.makeText(ChangePW_Activity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
            if(stringArr[0].equals("Fail")){
                Toast.makeText(ChangePW_Activity.this, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
