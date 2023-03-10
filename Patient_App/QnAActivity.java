package com.hope.patient.p_hopeapp;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

import static android.view.View.GONE;

public class QnAActivity extends Activity {

    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    ListView q_listview;
    EditText q_write_body;
    Myinfo my_Info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        final Intent intent = getIntent();
        my_Info = (Myinfo) intent.getSerializableExtra("MyInfo");

        q_listview = (ListView) findViewById(R.id.q_listview);
        q_write_body = (EditText) findViewById(R.id.q_write_body);

        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("p_ID=" + my_Info.getpID());
        myHttpTask.execute("http://hope2017.esy.es/p_read_qna.php");

        q_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                QnAItem detail = (QnAItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), QnADetail.class);
                intent.putExtra("detail", detail);
                startActivity(intent);
            }
        });

    }

    public void onButtonClicked(View v) {
        if (v.getId() == R.id.q_write_btn) {
            if (q_write_body.getText().toString().equals(""))
                Toast.makeText(QnAActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            else
                showWriteAlertDialog();
        } else if (v.getId() == R.id.home)
            showLogoutAlertDialog();
    }

    public void showWriteAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // ????????????
        alertDialogBuilder.setTitle("?????? ??????");
        // AlertDialog ??????
        alertDialogBuilder
                .setMessage("????????? ?????????????????????????")
                .setCancelable(false)
                .setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                long now = System.currentTimeMillis();
                                Date date = new Date(now);
                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy??? MM??? dd??? HH:mm:ss");
                                String getDate = sdf1.format(date);

                                MyHttpTask myHttpTask = new MyHttpTask();
                                myHttpTask.setBufferStr("q_date=" + getDate + "&p_ID=" + my_Info.getpID() + "&d_ID=" + my_Info.getdID() + "&body=" + q_write_body.getText() + "&is_it_read=" + 0);
                                myHttpTask.execute("http://hope2017.esy.es/p_write_qna.php");

                                q_write_body.setText(null);
                            }
                        })
                .setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // ?????????????????? ????????????
                                dialog.cancel();
                            }
                        });

        // ??????????????? ??????
        AlertDialog alertDialog = alertDialogBuilder.create();
        // ??????????????? ????????????
        alertDialog.show();
    }

    public void showLogoutAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // ????????????
        alertDialogBuilder.setTitle("????????????");
        // AlertDialog ??????
        alertDialogBuilder
                .setMessage("???????????? ???????????????????")
                .setPositiveButton("????????????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(QnAActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                            }
                        })
                .setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // ?????????????????? ????????????
                                dialog.cancel();
                            }
                        });

        // ??????????????? ??????
        AlertDialog alertDialog = alertDialogBuilder.create();
        // ??????????????? ????????????
        alertDialog.show();
    }

    public class QnAAdapter extends BaseAdapter {
        private ArrayList<QnAItem> q_itemList = new ArrayList<QnAItem>();

        @Override
        public int getCount() {
            return q_itemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.question_item, parent, false);
            }

            TextView q_item_date = (TextView) convertView.findViewById(R.id.q_item_date);
            TextView q_item_read = (TextView) convertView.findViewById(R.id.q_item_read);
            TextView q_item_body = (TextView) convertView.findViewById(R.id.q_item_body);

            QnAItem question_item = q_itemList.get(position);

            q_item_date.setText(question_item.getQDate());
            q_item_body.setText(question_item.getBody());

            if (question_item.getIsRead().equals("1")) {
                // ?????????
                q_item_read.setText("??????");
            } else {
                // ????????????
                q_item_read.setText("?????? ??????");
            }

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return q_itemList.get(position);
        }

        public void addItem(String q_date, String body, String isRead) {
            QnAItem item = new QnAItem();
            item.setQDate(q_date);
            item.setBody(body);
            item.setIsRead(isRead);
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
            progressDialog = new ProgressDialog(QnAActivity.this, R.style.MyDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("????????? ??????????????????");
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
                //   ????????? ??? ??????
                // --------------------------
                StringBuffer buffer = new StringBuffer();
                buffer.append(bufferStr);
                OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();
                // --------------------------
                //   ???????????? ????????????
                // --------------------------
                InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                http.disconnect();
                Log.d("?????? ?????? ???????", url.toString());
                Log.d("?????? ?????? ???????", bufferStr);
                Log.d("?????? ?????? ???????", builder.toString());
                receive_info = builder.toString();
                receive_parsh = receive_info.split("/");

                return builder.toString();
            } catch (IOException e) {
                Log.d("?????? ?????? ???????", e.toString());
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
                for (int i = 1; i < stringArr.length; i = i + 3) {
                    adapter.addItem(stringArr[i], stringArr[i + 1], stringArr[i + 2]);
                }

            } else {
                Toast.makeText(QnAActivity.this, "????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
