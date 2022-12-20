package com.example.d_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 연진 on 2017-08-28.
 */
public class patient_listActivity extends Activity {

    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    String ID = "";
    ListView p_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_patient_list);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("d_ID=" + ID);
        myHttpTask.execute("http://hope2017.esy.es/d_getPlist.php");

        p_listview = (ListView) findViewById(R.id.patient_list);

        p_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                PatientItem detail = (PatientItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), patient_detailActivity.class);
                intent.putExtra("detail", detail);
                startActivity(intent);
            }
        });
    }

    public void onButtonClicked(View v) {
        if (v.getId() == R.id.home)
            showLogoutAlertDialog();
    }

    public void showLogoutAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 제목셋팅
        alertDialogBuilder.setTitle("로그아웃");
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(patient_listActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();
        // 다이얼로그 보여주기
        alertDialog.show();
    }

    public class PatientListAdapter extends BaseAdapter {
        private ArrayList<PatientItem> p_itemList = new ArrayList<PatientItem>();

        @Override
        public int getCount() {
            return p_itemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.patient_item, parent, false);
            }

            TextView p_name = (TextView) convertView.findViewById(R.id.p_name);
            TextView p_room = (TextView) convertView.findViewById(R.id.p_room);

            PatientItem p_item = p_itemList.get(position);

            p_name.setText(p_item.getpName() + " (" + p_item.getpAge() + "세, " + p_item.getpGen() + ")");
            p_room.setText(p_item.getpRoom() + "호");

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return p_itemList.get(position);
        }

        public void addItem(String p_id, String p_name, String p_room, String p_birth, String p_gen, String p_disease, String forbid_food) {
            PatientItem item = new PatientItem();
            int birth, p_age;
            String temp_year, temp_month, temp_day;
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            int getYear = Integer.parseInt(sdf1.format(date));

            birth = Integer.parseInt(p_birth);
            temp_year = String.valueOf(birth/10000);
            temp_month = String.valueOf((birth/100)%100);
            temp_day = String.valueOf(birth%100);
            p_age = getYear - (birth / 10000) + 1;

            item.setpID(p_id);
            item.setpName(p_name);
            item.setpRoom(p_room);
            item.setpBirth(temp_year+"년 "+temp_month+"월 "+temp_day+"일");
            item.setpGen(p_gen);
            item.setpDisease(p_disease);
            item.setpAge(String.valueOf(p_age));
            item.setForbid_food(forbid_food);

            p_itemList.add(item);
        }
    }

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(patient_listActivity.this, R.style.MyDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("환자 리스트을 받아오는 중...");
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
            String[] stringArr = result.split("/");
            progressDialog.dismiss();
            progressDialog = null;

            System.out.println(result);

            PatientListAdapter adapter = new PatientListAdapter();

            if (stringArr[0].equals("Success")) {
                p_listview.setAdapter(adapter);
                for (int i = 1; i < stringArr.length; i = i + 7) {
                    adapter.addItem(stringArr[i], stringArr[i + 1], stringArr[i + 2], stringArr[i + 3], stringArr[i + 4], stringArr[i + 5], stringArr[i + 6]);
                }
            } else {
                Toast.makeText(patient_listActivity.this, "담당 환자가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
