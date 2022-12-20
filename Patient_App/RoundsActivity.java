package com.hope.patient.p_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class RoundsActivity extends Activity {

    ProgressDialog progressDialog = null;
    String receive_info = "";
    String receive_parsh[];
    Myinfo my_Info;
    ListView rounds_listview;
    TextView round_state;
    String cur_dlocate = "0";
    MyHttpTask myHttpTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rounds);

        final Intent intent = getIntent();
        my_Info = (Myinfo) intent.getSerializableExtra("MyInfo");
        rounds_listview = (ListView) findViewById(R.id.rounds_listview);
        round_state = (TextView) findViewById(R.id.round_state);

    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(RoundsActivity.this, LoginActivity.class);
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
        handler.removeMessages(0);
        this.finish();
    }

    public class RoundsAdapter extends BaseAdapter {
        private ArrayList<RoundsItem> roundsItemList = new ArrayList<RoundsItem>();
        TextView rounds_item_order, rounds_item_location, rounds_item_desc;

        @Override
        public int getCount() {
            return roundsItemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.rounds_item, parent, false);
            }

            rounds_item_order = (TextView) convertView.findViewById(R.id.rounds_item_order);
            rounds_item_location = (TextView) convertView.findViewById(R.id.rounds_item_location);
            rounds_item_desc = (TextView) convertView.findViewById(R.id.rounds_item_desc);

            RoundsItem rounds_item = roundsItemList.get(position);

            // Log.d("MRS",rounds_item.getDesc()+"/"+rounds_item.getLocation());
            rounds_item_order.setText(String.valueOf(position + 1));

            if (rounds_item.getColor() == 0) {
                rounds_item_location.setTextColor(Color.argb(255, 0x26, 0x26, 0x26));
            }
            rounds_item_location.setText(rounds_item.getLocation());

            //현재위치
            if (!(rounds_item.getDesc().equals(" "))) {
                rounds_item_desc.setBackgroundColor(Color.argb(255, 0xDC, 0xE9, 0xf5));
            }
            else {
                rounds_item_desc.setBackgroundColor(Color.argb(255, 0xff, 0xff, 0xff));
            }
            rounds_item_desc.setText(rounds_item.getDesc());

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return roundsItemList.get(position);
        }

        public void addItem(int color, String location, String desc) {
            RoundsItem item = new RoundsItem();
            item.setColor(color);
            item.setLocation(location);
            item.setDesc(desc);

            roundsItemList.add(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        handler.sendEmptyMessage(0);
    }

    public Handler handler = new Handler(){

        public void handleMessage(Message msg){

            super.handleMessage(msg);

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
            progressDialog = new ProgressDialog(RoundsActivity.this, R.style.MyDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시만 기다려주세요");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            //progressDialog.show();
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

            if (stringArr[0].equals("Success")) {
                cur_dlocate = stringArr[1];

                if (cur_dlocate.equals("0")) round_state.setText("의사가 회진 중이 아닙니다.");
                else round_state.setText("의사가 회진 중 입니다.");

                String cur_plocate = my_Info.getpRoomNum();

                RoundsAdapter myadapter = new RoundsAdapter();
                rounds_listview.setAdapter(myadapter);

                int color = 0;

                for (int i = 2; i < stringArr.length; i++) {
                    if (cur_dlocate.equals(stringArr[i])) {

                        if (cur_plocate.equals(cur_dlocate))
                            myadapter.addItem(color++, stringArr[i], "나,의사");
                        else
                            myadapter.addItem(color++, stringArr[i], "의사");

                    } else if (cur_plocate.equals(stringArr[i])) {
                        myadapter.addItem(color, stringArr[i], "내 병실");
                    } else
                        myadapter.addItem(color, stringArr[i], " ");
                }

                myadapter.notifyDataSetChanged();
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    }
}