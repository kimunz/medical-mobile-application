package com.example.d_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import java.util.ArrayList;

/**
 * Created by 연진 on 2017-08-09.
 */

public class patient_detailActivity extends Activity {

    public static Context mContext;
    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    TextView p_name, p_gen, p_birth, p_room, p_disease, forbid_food;
    ListView patient_q_list;
    PatientItem detail;
    String p_id = "";
    int list_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_detail);

        p_name = (TextView) findViewById(R.id.p_name);
        p_gen = (TextView) findViewById(R.id.p_gen);
        p_birth = (TextView) findViewById(R.id.p_birth);
        p_room = (TextView) findViewById(R.id.p_room);
        p_disease = (TextView) findViewById(R.id.p_disease);
        forbid_food = (TextView) findViewById(R.id.forbid_food);
        patient_q_list = (ListView) findViewById(R.id.patient_q_list);

        Intent intent = getIntent();
        detail = (PatientItem) intent.getSerializableExtra("detail");

        p_name.setText(detail.getpName());
        p_gen.setText(detail.getpGen());
        p_birth.setText(detail.getpBirth());
        p_room.setText(detail.getpRoom() + "호");
        p_disease.setText(detail.getpDisease());
        forbid_food.setText(detail.getForbid_food());

        p_id = detail.getpID();

        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("p_ID=" + p_id);
        myHttpTask.execute("http://hope2017.esy.es/d_pDetail_qList.php");

        patient_q_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                qna_item q_detail = (qna_item) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),patient_detail_qActivity.class);
                intent.putExtra("q_detail", q_detail);
                startActivity(intent);
            }
        });
        mContext = this;

    }

    public void onBackPressed() {
        super.onBackPressed();
        ((RecoRangingActivity)RecoRangingActivity.mContext).update_info();
        this.finish();
    }

    public void update_info()
    {
        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("p_ID=" + p_id);
        myHttpTask.execute("http://hope2017.esy.es/d_pDetail_qList.php");
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        ListView lv = (ListView) findViewById(R.id.patient_q_list);

        int h = lv.getHeight();

        ViewGroup.LayoutParams params = lv.getLayoutParams();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, h, dm);

        params.height = h * list_count;

        System.out.println("갯수는여"+list_count);
        lv.setLayoutParams(params);
        lv.requestLayout();
    }


    public class PatientDetailAdapter extends BaseAdapter {
        private ArrayList<qna_item> q_list = new ArrayList<qna_item>();

        @Override
        public int getCount() {
            return q_list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.patient_detail_q_item, parent, false);
            }

            TextView q_date = (TextView) convertView.findViewById(R.id.p_Qdate);
            TextView q_body = (TextView) convertView.findViewById(R.id.p_Qbody);
            TextView is_read = (TextView) convertView.findViewById(R.id.p_QisRead);

            qna_item q_item = q_list.get(position);

            q_date.setText(q_item.getQDate());
            q_body.setText(q_item.getBody());
            if(q_item.getIs_read().equals("0")) is_read.setText("읽지 않음");
            else is_read.setText("읽음");


            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return q_list.get(position);
        }

        public void addItem(String q_date, String q_body, String is_read, String count) {
            qna_item item = new qna_item();

            item.setQDate(q_date);
            item.setBody(q_body);
            item.setP_id(p_id);

            list_count = Integer.valueOf(count).intValue();

            item.setIs_read(is_read);
            q_list.add(item);
        }
    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(patient_detailActivity.this, LoginActivity.class);
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

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(patient_detailActivity.this, R.style.MyDialogStyle);
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
            String[] stringArr = result.split("/");
            progressDialog.dismiss();
            progressDialog = null;

            PatientDetailAdapter adapter = new PatientDetailAdapter();

            if (stringArr[0].equals("Success")) {
                patient_q_list.setAdapter(adapter);
                for (int i = 1; i < stringArr.length; i = i + 4) {
                    adapter.addItem(stringArr[i], stringArr[i + 1], stringArr[i + 2], stringArr[i + 3]);
                }
            }
        }
    }
}

