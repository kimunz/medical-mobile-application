package com.example.d_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import java.util.ArrayList;

/**
 * Created by 연진 on 2017-08-24.
 */
public class qnaActivity extends Activity {

    public static Context mContext;
    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    String ID;
    ListView q_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.qna);

        final Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        q_listview = (ListView) findViewById(R.id.q_listview);

        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("d_ID=" + ID);
        myHttpTask.execute("http://hope2017.esy.es/d_read_qna.php");

        q_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                qna_item detail = (qna_item) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),qna_detailActivity.class);
                intent.putExtra("detail", detail);
                startActivity(intent);
            }
        });
        mContext = this;
    }
    public void onButtonClicked(View v){
        if(v.getId() == R.id.home)
            showLogoutAlertDialog();
    }

    public void showLogoutAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 제목셋팅
        alertDialogBuilder.setTitle("로그아웃");
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(qnaActivity.this, LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((RecoRangingActivity)RecoRangingActivity.mContext).update_info();
        this.finish();
    }

    public void update_info()
    {
        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("d_ID=" + ID);
        myHttpTask.execute("http://hope2017.esy.es/d_read_qna.php");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class QnAAdapter extends BaseAdapter {
        private ArrayList<qna_item> q_itemList = new ArrayList<qna_item>() ;

        @Override
        public int getCount() {
            return q_itemList.size() ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.qna_item, parent, false);
            }

            TextView q_item_date = (TextView) convertView.findViewById(R.id.q_item_date) ;
            TextView p_name = (TextView) convertView.findViewById(R.id.p_name) ;
            TextView q_item_body = (TextView) convertView.findViewById(R.id.q_item_body) ;

            qna_item question_item = q_itemList.get(position);

            q_item_date.setText(question_item.getQDate());
            p_name.setText(question_item.getP_name()+" ("+question_item.getP_room()+"호)");
            q_item_body.setText(question_item.getBody());


            return convertView;
        }

        @Override
        public long getItemId(int position) { return position ; }

        @Override
        public Object getItem(int position) {
            return q_itemList.get(position) ;
        }

        public void addItem(String p_id, String p_name, String p_room, String q_date, String body) {
            qna_item item = new qna_item();
            item.setP_id(p_id);
            item.setP_name(p_name);
            item.setP_room(p_room);
            item.setQDate(q_date);
            item.setBody(body);
            q_itemList.add(item);
        }
    }

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(qnaActivity.this, R.style.MyDialogStyle);
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

            QnAAdapter adapter = new QnAAdapter();

            if (stringArr[0].equals("Success")) {
                q_listview.setAdapter(adapter);
                for (int i = 1; i < stringArr.length; i = i + 5) {
                    adapter.addItem(stringArr[i], stringArr[i + 1], stringArr[i + 2], stringArr[i + 3], stringArr[i + 4]);
                }

            } else {
                q_listview.setAdapter(adapter);
                Toast.makeText(qnaActivity.this, "등록된 질문이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
