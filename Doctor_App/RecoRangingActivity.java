/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.example.d_hopeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * RECORangingActivity class is to range regions in the foreground.
 *
 * RECORangingActivity 클래스는 foreground 상태에서 ranging을 수행합니다.
 */
public class RecoRangingActivity extends RecoActivity implements RECORangingListener {

    ProgressDialog progressDialog = null;
    public static Context mContext;
    private RecoRangingListAdapter mRangingListAdapter;

    private long startTime = 0;
    private long endTime = 0;
    private long term = 0;
    public String receive_info = "";
    public String receive_parsh[];

    String Room_number[];
    String ID = "", PW = "", myPatient = "", unread = "", room_num = "";
    String order_list = "[ ";

    LinearLayout mypatient_layout;
    Button qna_btn, start, mypatient, home;
    TextView ol;

    String location = "0";
    int auto = 0;
    int type = 0;
    int current_move = 0;
    boolean start_rounding = false;
    boolean start_rounding_long = false;
    boolean start_auto = false;
    boolean change_button = false;
    int what_change = 9999;

    long cur_time = 0;
    long post_time = 0;

    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PW = intent.getStringExtra("PW");
        myPatient = intent.getStringExtra("myPatient");
        unread = intent.getStringExtra("unread_qna");
        room_num = intent.getStringExtra("room_num");   // 앞에서 넘겨준 파라미터 개수
        location = intent.getStringExtra("location");
        auto = Integer.parseInt(intent.getStringExtra("auto"));


        int length = Integer.parseInt(room_num) - 6;    // 받아온 것 중에 앞에 세 개는 딴거임

        Room_number = new String[length];
        builder = new AlertDialog.Builder(this);
        builder2 = new AlertDialog.Builder(this);

        home = (Button)findViewById(R.id.home);

        // 배열에 병실
        for(int i = 0; i < length; i++)
        {
            Room_number[i] = intent.getStringExtra("patient_room[" + (i+6) + "]");
            System.out.println("Room : " + Room_number[i]);

            if (i == length - 1) order_list += Room_number[i] + "호 ]";
            else order_list += Room_number[i] + "호 → ";
        }

        ol = (TextView) findViewById(R.id.order_list);
        mypatient_layout = (LinearLayout)findViewById(R.id.mypatient_layout);
        mypatient = (Button)findViewById(R.id.mypatient);                       // 나의환자 수
        qna_btn = (Button)findViewById(R.id.qna);                               // qna 리스트
        start = (Button)findViewById(R.id.start);

        if(!location.equals("0")) {
            current_move = Integer.parseInt(intent.getStringExtra("current_move")) + 1;

            type = 1;
            start_rounding = true;

            if(auto == 1) {
                start_auto = true;
                start.setText("종료");
            }
            else {
                start_auto = false;
                start.setText(location);
            }
        } else start.setText("시작");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, dm);

        // 해상도에 따른 버튼 크기 설정
        int circle_Width = (displayMetrics.widthPixels - pixel) / 3 ;
        int circle_Height = circle_Width;

        System.out.println("Width : " + circle_Width);
        System.out.println("Height : " + circle_Height);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(circle_Width, circle_Height);

        mypatient.setLayoutParams(params);
        qna_btn.setLayoutParams(params);
        start.setLayoutParams(params);

        mypatient.setText(myPatient);
        qna_btn.setText(unread);
        ol.setText(order_list);

        //If a user device turns off bluetooth, request to turn it on.
        //사용자가 블루투스를 켜도록 요청합니다.

        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }

        //mRecoManager will be created here. (Refer to the RECOActivity.onCreate())
        //mRecoManager 인스턴스는 여기서 생성됩니다. RECOActivity.onCreate() 메소들르 참고하세요.

        //Set RECORangingListener (Required)
        //RECORangingListener 를 설정합니다. (필수)
        mRecoManager.setRangingListener(this);

        /**
         * Bind RECOBeaconManager with RECOServiceConnectListener, which is implemented in RECOActivity
         * You SHOULD call this method to use monitoring/ranging methods successfully.
         * After binding, onServiceConenct() callback method is called.
         * So, please start monitoring/ranging AFTER the CALLBACK is called.
         *
         * RECOServiceConnectListener와 함께 RECOBeaconManager를 bind 합니다. RECOServiceConnectListener는 RECOActivity에 구현되어 있습니다.
         * monitoring 및 ranging 기능을 사용하기 위해서는, 이 메소드가 "반드시" 호출되어야 합니다.
         * bind후에, onServiceConnect() 콜백 메소드가 호출됩니다. 콜백 메소드 호출 이후 monitoring / ranging 작업을 수행하시기 바랍니다.
         */

        mRecoManager.bind(this);
        mypatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientintent = new Intent(RecoRangingActivity.this, patient_listActivity.class);
                patientintent.putExtra("ID" , ID);
                startActivity(patientintent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_rounding == false)
                {
                    builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                public boolean onKey(DialogInterface dialog,
                                                     int keyCode, KeyEvent event) {
                                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                                        dialog.dismiss();
                                        return true;
                                    }
                                    return false;
                                }
                            });

                    builder.setTitle("자동/수동 확인 대화 상자")        // 제목 설정
                            .setMessage("회진을 자동/수동을 선택해주세요.")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("자동", new DialogInterface.OnClickListener(){
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton){
                                    auto = 1;
                                    start.setText("종료");
                                    start_rounding_long = false;
                                    start_auto = true;
                                    type = 1;
                                    String Room = "";
                                    order_list = "[ ";
                                    for(int i = 0; i < Room_number.length; i++)
                                    {
                                        if(i==Room_number.length-1) Room += Room_number[i];
                                        else Room += Room_number[i] + "/";

                                        if (i == Room_number.length - 1) order_list += Room_number[i] + "호 ]";
                                        else order_list += Room_number[i] + "호 → ";
                                    }
                                    ol.setText(order_list);
                                    MyHttpTask myHttpTask = new MyHttpTask();
                                    myHttpTask.setBufferStr("d_ID=" + ID + "&location=" + "0" + "&order_list=" + Room + "&auto=" + auto + "&current_move=" + current_move);
                                    myHttpTask.execute("http://hope2017.esy.es/d_start_Rounding.php");

                                    mRangingListAdapter.init_beacon(Room_number, Room_number.length);
                                    start_rounding = true;
                                }
                            })
                            .setNegativeButton("수동", new DialogInterface.OnClickListener(){
                                // 취소 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton){
                                    auto = 0;
                                    start_auto = false;
                                    start_rounding_long = false;
                                    type = 1;
                                    String Room = "";
                                    order_list = "[ ";
                                    for(int i = 0; i < Room_number.length; i++)
                                    {
                                        if(i==Room_number.length-1) Room += Room_number[i];
                                        else Room += Room_number[i] + "/";

                                        if (i == Room_number.length - 1) order_list += Room_number[i] + "호 ]";
                                        else order_list += Room_number[i] + "호 → ";
                                    }

                                    ol.setText(order_list);
                                    MyHttpTask myHttpTask = new MyHttpTask();
                                    myHttpTask.setBufferStr("d_ID=" + ID + "&location=" + Room_number[current_move] + "&order_list=" + Room + "&auto=" + auto + "&current_move=" + current_move);
                                    myHttpTask.execute("http://hope2017.esy.es/d_start_Rounding.php");

                                    mRangingListAdapter.init_beacon(Room_number, Room_number.length);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }
                else
                {
                    if(current_move < Room_number.length && start_auto == false)
                    {
                        MyHttpTask myHttpTask = new MyHttpTask();
                        myHttpTask.setBufferStr("d_ID=" + ID + "&location=" + Room_number[current_move] + "&current_move=" + current_move);
                        myHttpTask.execute("http://hope2017.esy.es/d_move_Rounding.php");
                    }
                    else if(current_move == Room_number.length && start_auto == false)
                    {
                        start.setText("시작");
                        MyHttpTask myHttpTask = new MyHttpTask();
                        myHttpTask.setBufferStr("d_ID=" + ID);
                        myHttpTask.execute("http://hope2017.esy.es/d_delete_Rounding.php");
                        type = 2;
                    }
                }

            }
        });

        start.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(start_rounding == true)
                {
                    builder.setTitle("종료 확인 대화 상자")        // 제목 설정
                            .setMessage("회진을 종료하시겠습니까?")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton){
                                    location = "0";
                                    type=2;
                                    MyHttpTask myHttpTask = new MyHttpTask();
                                    myHttpTask.setBufferStr("d_ID=" + ID);
                                    myHttpTask.execute("http://hope2017.esy.es/d_delete_Rounding.php");
                                    start_rounding = false;
                                    start_rounding_long = true;
                                    current_move = 0;
                                    RecoRangingListAdapter.order = 0;
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                // 취소 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton){
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }
                return true;
            }
        });

        //////////////////// QnA 버튼 부분
        qna_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unreadintent = new Intent(RecoRangingActivity.this, qnaActivity.class);
                unreadintent.putExtra("ID" , ID);
                startActivity(unreadintent);
            }
        });


        //////////////////// 동적 버튼 생성 부분
        LinearLayout innerLayout = (LinearLayout)findViewById(R.id.innerLayout);
        final Button btn[] = new Button[Room_number.length];
        int room_num = 0;

        // 담당 병실 갯수가 홀수개이면
        if(Room_number[0] != null) {
            if (Room_number.length % 2 == 1) {
                room_num = Room_number.length / 2 + 1;
            } else {
                room_num = Room_number.length / 2;
            }

            // 해상도에 따른 버튼 크기 설정
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, dm);

            int Width = displayMetrics.widthPixels / 2 - (margin*2);
            int Height = displayMetrics.heightPixels / 10;

            System.out.println("Width : " + Width);
            System.out.println("Height : " + Height);

            LinearLayout ll[] = new LinearLayout[room_num];
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(Width, Height);

            param.topMargin = margin/2;
            param.bottomMargin = margin/2;
            param.leftMargin = margin/2;
            param.rightMargin = margin/2;

            for (int i = 0; i < Room_number.length; i++) {
                final int index = i;
                btn[i] = new Button(this);
                btn[i].setLayoutParams(param);
                btn[i].setText(Room_number[i] + "호");
                btn[i].setTextSize(20);
                btn[i].setId(i);
                //btn[i].setBackgroundResource(R.drawable.rounds_button);
                btn[i].setBackgroundResource(R.drawable.ol_button);
                btn[i].setTextColor(Color.BLACK);
                btn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (change_button == false) {
                            change_button = true;
                            what_change = index;
                            btn[index].setBackgroundResource(R.drawable.ol_button_clicked);
                        } else {
                            if (what_change != index) {
                                String temp = btn[what_change].getText().toString();
                                String temp2 = Room_number[what_change];

                                btn[what_change].setText(btn[index].getText().toString());
                                Room_number[what_change] = Room_number[index];

                                btn[index].setText(temp);
                                Room_number[index] = temp2;

                                change_button = false;
                                btn[what_change].setBackgroundResource(R.drawable.ol_button);
                            } else {
                                change_button = false;
                                btn[what_change].setBackgroundResource(R.drawable.ol_button);
                            }
                        }
                    }
                });

                if (i % 2 == 0) {
                    ll[i / 2] = new LinearLayout(this);
                    ll[i / 2].setGravity(Gravity.CENTER);
                }

                ll[i / 2].addView(btn[i], param);
                if (i == Room_number.length - 1 && Room_number.length % 2 == 1) {
                    Button btn1 = new Button(this);
                    btn1.setLayoutParams(param);
                    btn1.setText(Room_number[i] + "호");
                    btn1.setId(Room_number.length);
                    btn1.setBackgroundResource(R.drawable.rounds_button);
                    ll[i / 2].addView(btn1, param);
                    btn1.setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < ll.length; i++) {
                innerLayout.addView(ll[i]);
            }
        }
        mContext = this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            //If the request to turn on bluetooth is denied, the app will be finished.
            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onhomeButtonClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(RecoRangingActivity.this, LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        if(progressDialog == null){
            cur_time = System.currentTimeMillis();
            if(cur_time - post_time < 800){
                finishAffinity();
            }
            else
                Toast.makeText(RecoRangingActivity.this,"뒤로가기를 빠르게 두 번 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
            post_time = cur_time;
        }
    }

    public void update_info()
    {
        if(start.getText().toString().equals("시작")) type = 0;
        else type = 1;

        MyHttpTask myHttpTask = new MyHttpTask();
        myHttpTask.setBufferStr("d_ID=" + ID + "&d_PW=" + PW);
        myHttpTask.execute("http://hope2017.esy.es/d_login.php");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRangingListAdapter = new RecoRangingListAdapter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stop(mRegions);
        this.unbind();
    }

    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RECORangingActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("RECORangingActivity", "onServiceConnect()");
        mRecoManager.setDiscontinuousScan(LoginActivity.DISCONTINUOUS_SCAN);
        this.start(mRegions);
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
        Log.i("RECORangingActivity", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());
        endTime = System.currentTimeMillis();
        term = startTime - endTime;
        System.out.println("Time : " + term);
        startTime = System.currentTimeMillis();
        mRangingListAdapter.updateAllBeacons(recoBeacons);
        mRangingListAdapter.notifyDataSetChanged();

        if(recoBeacons.size() != 0 && type == 1 && start_rounding && start_auto)
        {
            System.out.println("Auto Rounding");

            mRangingListAdapter.init_beacon(Room_number, Room_number.length);
            mRangingListAdapter.Auto_rounding(ID, Room_number, Room_number.length);
        }
        //Write the code when the beacons in the region is received
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {
        /**
         * There is a known android bug that some android devices scan BLE devices only once. (link: http://code.google.com/p/android/issues/detail?id=65863)
         * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager.
         * This method is to set whether the device scans BLE devices continuously or discontinuously.
         * The default is set as FALSE. Please set TRUE only for specific devices.
         *
         * mRecoManager.setDiscontinuousScan(true);
         */

        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        Log.i("RECORangingActivity", "error code = " + errorCode);
        //Write the code when the RECOBeaconService is failed to range beacons in the region.
        //See the RECOErrorCode in the documents.
        return;
    }

    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;

        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RecoRangingActivity.this,R.style.MyDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시만 기다려주세요");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            String myResult = "";
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
                Log.d("회진 언제 와요?",url.toString());
                Log.d("회진 언제 와요?",bufferStr);
                Log.d("회진 언제 와요?",builder.toString());

                if(type == 0)
                {
                    receive_info = builder.toString();
                    receive_parsh = receive_info.split("/");

                    myPatient = receive_parsh[1];
                    unread = receive_parsh[2];
//                    location = Integer.parseInt(receive_parsh[3]);
//                    auto = Integer.parseInt(receive_parsh[4]);
//                    room_num = String.valueOf(receive_parsh.length - 5);
//
//                    Room_number = new String[receive_parsh.length - 5];
//                    for(int i = 0; i < receive_parsh.length - 5; i++)
//                    {
//                        Room_number[i] = receive_parsh[i + 5];
//                    }

                }

                return builder.toString();
            } catch (IOException e) {
                Log.d("회진 언제 와요?",e.toString());
                return "ERROR";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            String[] stringArr = result.split("/");
            progressDialog.dismiss();
            progressDialog = null;

            if(type == 1 && start_auto == false)
            {
                start.setText(Room_number[current_move]);

                if(start_rounding == false){

                    if(!start_rounding_long){
                        start_rounding = true;
                        current_move++;
                    }
                }
                else {

                    if(current_move < Room_number.length) current_move++;

                    else if(current_move == Room_number.length){
                        current_move = 0;
                        start_rounding = false;
                    }
                }
            }
            else if(type == 2)
            {
                start.setText("시작");
                MyHttpTask myHttpTask = new MyHttpTask();
                myHttpTask.setBufferStr("d_ID=" + ID);
                myHttpTask.execute("http://hope2017.esy.es/d_delete_Rounding.php");
                type = 3;
                current_move = 0;
                start_rounding = false;
            }

            if(stringArr[0].equals("Success"))
            {
                if(type == 0)
                {
                    mypatient.setText(myPatient);
                    qna_btn.setText(unread);
                }
            }
        }
    }

}
