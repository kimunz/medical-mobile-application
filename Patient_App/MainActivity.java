package com.hope.patient.p_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
 * Created by User on 2017-07-30.
 */

public class MainActivity extends Activity {

    static Context mcontext;
    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    Button b_rounds;
    ImageButton b_myinfo, b_register, b_emergency, b_QnA;
    TextView main_message;
    Myinfo my_Info;
    long cur_time = 0;
    long post_time = 0;
    MyHttpTask myHttpTask;
    String cur_dlocate = "0";
    int type = 0;
    CheckBox login_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 첫 화면 xxx님, 안녕하세요. 문구 설정
        final Intent intent = getIntent();
        my_Info = (Myinfo) intent.getSerializableExtra("MyInfo");
        main_message = (TextView) findViewById(R.id.main_message);
        main_message.setText(my_Info.getpName() + "님, 안녕하세요.");

        b_rounds = (Button) findViewById(R.id.rounds);
        b_myinfo = (ImageButton) findViewById(R.id.myinfo);
        b_register = (ImageButton) findViewById(R.id.con_register);
        b_emergency = (ImageButton) findViewById(R.id.emergency);
        b_QnA = (ImageButton) findViewById(R.id.QnA);

        login_auto = (CheckBox) findViewById(R.id.login_auto);

        MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
        myFirebaseInstanceIDService.onTokenRefresh(my_Info.getpID(), my_Info.getdID());

        // 버튼 눌렀을 때 화면 전환
        b_rounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RoundsActivity.class);
                intent.putExtra("MyInfo", my_Info);
                startActivity(intent);
            }
        });

        b_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyinfoActivity.class);
                intent.putExtra("MyInfo", my_Info);
                startActivity(intent);
            }
        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("MyInfo", my_Info);
                startActivity(intent);
            }
        });

        b_emergency.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                type = 1;
                MyHttpTask myHttpTask = new MyHttpTask();
                myHttpTask.setBufferStr("p_ID=" + my_Info.getpID().toString() + "&p_name=" + my_Info.getpName() + "&p_room=" + my_Info.getpRoomNum() + "&disease=" + my_Info.getpDiseaseName());
                myHttpTask.execute("http://hope2017.esy.es/p_emergency.php");

                return true;
            }
        });

        b_QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QnAActivity.class);
                intent.putExtra("MyInfo", my_Info);
                startActivity(intent);
            }
        });
        mcontext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        handler.sendEmptyMessage(0);
    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        if (progressDialog == null) {
            cur_time = System.currentTimeMillis();
            if (cur_time - post_time < 800) {
                finishAffinity();
            } else
                Toast.makeText(MainActivity.this, "뒤로가기를 빠르게 두 번 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            post_time = cur_time;
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            type = 0;

            myHttpTask = new MyHttpTask();
            myHttpTask.setBufferStr("d_id=" + my_Info.getdID());
            myHttpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://hope2017.esy.es/read_roundTable.php");
        }
    };

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this, R.style.MyDialogStyle);
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
            String[] stringArr = result.split("/");
            System.out.println("결과는요"+result);
            progressDialog = null;

            if (type == 0) {
                if (stringArr[0].equals("Success")) {

                    cur_dlocate = stringArr[1];
                    String cur_plocate = my_Info.getpRoomNum();
                    handler.sendEmptyMessageDelayed(0, 5000);

                    for (int i = 3; i < stringArr.length; i++) {
                        System.out.println("결과1");

                        if (i != 3 && cur_dlocate.equals(stringArr[i - 2]) && cur_plocate.equals(stringArr[i])) {
                            System.out.println("결과2 전전");
                            b_rounds.setText("실시간 회진 확인" + "\n" + "→ 전전");
                            break;
                        } else if (cur_dlocate.equals(stringArr[i - 1]) && cur_plocate.equals(stringArr[i])) {
                            b_rounds.setText("실시간 회진 확인" + "\n" + "→ 전");
                            System.out.println("결과3 전");
                            break;
                        } else {
                            b_rounds.setText("실시간 회진 확인");
                            System.out.println("이상한 애");\
                        }
                    }
                }
            }

            if (type == 1) {
                if (stringArr[0].equals("Success")) {
                    Toast.makeText(MainActivity.this, "긴급호출 버튼을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "이미 누르셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}