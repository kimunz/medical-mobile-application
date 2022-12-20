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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class RegisterActivity extends Activity {


    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    Myinfo my_Info;
    int pos;

    TextView record_date;
    TextView record;
    TextView title;
    TextView unit;
    EditText volume_text;
    Button btn_regit;
    String cur_unit;
    ListView listView;
    String[] spinner_items = {"소변량", "배설량", "수분섭취량"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.con_register);

        final Intent intent = getIntent();
        my_Info = (Myinfo) intent.getSerializableExtra("MyInfo");

        unit = (TextView) findViewById(R.id.unit);
        listView = (ListView) findViewById(R.id.listView);
        title = (TextView) findViewById(R.id.con_title);
        btn_regit = (Button) findViewById(R.id.btn_regit);
        volume_text = (EditText) findViewById(R.id.volume_text);

        //------ 스피너 부분 -------
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinner_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        // 스피너 내에 아이템 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 아이템이 선택되었을 때 호출됨
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                pos = position;
                volume_text.setText(null);
                switch (spinner_items[position]) {
                    case "소변량":
                        cur_unit = "mL";
                        break;
                    case "배설량":
                        cur_unit = "회";
                        break;
                    case "수분섭취량":
                        cur_unit = "mL";
                        break;
                }
                unit.setText("(" + cur_unit + ")");

                MyHttpTask myHttpTask = new MyHttpTask();
                myHttpTask.setBufferStr("p_ID=" + my_Info.getpID().toString() + "&carte=" + spinner_items[position]);
                myHttpTask.execute("http://hope2017.esy.es/p_read_register.php");

                title.setText(spinner_items[position]);
            }

            // 아무것도 선택되지 않았을 때 호출됨
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                title.setText("");
            }
        });

        btn_regit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                String getDate = sdf1.format(date);
                String getTime = sdf2.format(date);

                if(!volume_text.getText().toString().equals("")){
                    // 등록한 다음에
                    MyHttpTask myHttpTask = new MyHttpTask();
                    myHttpTask.setBufferStr("p_ID=" + my_Info.getpID().toString() + "&reg_date=" + getDate + "&reg_time=" + getTime + "&carte=" + spinner_items[pos] + "&volume=" + volume_text.getText());
                    myHttpTask.execute("http://hope2017.esy.es/p_write_register.php");

                    volume_text.setText(null);
                }
                else
                    Toast.makeText(RegisterActivity.this, "용량을 기입해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
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

    class RegisterAdapter extends BaseAdapter {
        private ArrayList<RegisterItem> items = new ArrayList();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            View itemView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.con_register_item, viewGroup, false);
            } else {
                itemView = convertView;
            }

            RegisterItem regitItem = items.get(position);

            record = (TextView) itemView.findViewById(R.id.record);
            record_date = (TextView) itemView.findViewById(R.id.record_date);

            record.setText(regitItem.getVolume() + cur_unit);
            record_date.setText(regitItem.getDate() + " " + regitItem.getTime());

            return itemView;
        }

        public void addItem(String carte, String date, String time, String volume) {
            RegisterItem item = new RegisterItem();
            item.setCartegory(carte);
            item.setDate(date);
            item.setTime(time);
            item.setVolume(volume);
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RegisterActivity.this, R.style.MyDialogStyle);
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

            RegisterAdapter adapter = new RegisterAdapter();
            if (stringArr[0].equals("Success")) {
                    listView.setAdapter(adapter);
                    for (int i = 1; i < stringArr.length; i = i + 3) {
                        adapter.addItem(spinner_items[pos], stringArr[i], stringArr[i + 1], stringArr[i + 2]);
                    }

            } else {
                    listView.setAdapter(adapter);
                    Toast.makeText(RegisterActivity.this, "등록된 기록이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
