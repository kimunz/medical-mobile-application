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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.perples.recosdk.RECOBeacon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class RecoRangingListAdapter extends BaseAdapter {

    public boolean end = false;
    private String[] room_a;
    boolean Auto = true;
    public static Context mContext;

    public class Room{
        // 임의로 넣은 정보들
        public String room = "";
        public String number = "";

        public Room(String r, String n){
            room = r;
            number = n;
        }
    }

    private ArrayList<RECOBeacon> mRangedBeacons;
    private LayoutInflater mLayoutInflater;

    public static int current = 1;
    public static String current_room = "";
    public static int order = 0;
    public static double distance = 1.5;

    Room[] room = new Room[4];

    public RecoRangingListAdapter(Context context) {
        super();
        mRangedBeacons = new ArrayList<RECOBeacon>();
        mLayoutInflater = LayoutInflater.from(context);

        // 각 방 정보로 초기화
    }

    public void updateBeacon(RECOBeacon beacon) {
        synchronized (mRangedBeacons) {
            if(mRangedBeacons.contains(beacon)) {
                mRangedBeacons.remove(beacon);
            }
            mRangedBeacons.add(beacon);
        }
    }

    public void updateAllBeacons(Collection<RECOBeacon> beacons) {
            synchronized (beacons) {
                mRangedBeacons = new ArrayList<RECOBeacon>(beacons);
            }
    }
    public void init_beacon(String[] room_b, int length)
    {
        room[0] = new Room("101", "26402");
        room[1] = new Room("102", "26403");
        room[2] = new Room("103", "26404");
        room[3] = new Room("104", "26408");

        Auto = true;
        current = 0;
        room_a = new String[room_b.length];
        for(int i = 0; i < length; i++)
        {
            room_a[i] = room_b[i];
        }
    }

    public void Auto_rounding(String ID, String[] Position, int length)
    {
        if((mRangedBeacons.get(0).getAccuracy() < distance) && mRangedBeacons.size() !=0 && Auto)
        {
            String data = String.valueOf(mRangedBeacons.get(0).getMajor() + mRangedBeacons.get(0).getMinor());

            for (int i = 0; i < room.length; i++) {
                if(room[i].number.equals(data)) current_room = room[i].room;
            }

            System.out.println("data="+data+"Accuracy="+mRangedBeacons.get(0).getAccuracy());
            System.out.println("Data : " + data + " Current : " + current_room);

            if (order != length && current_room.equals(Position[order])) {
                order++;
                MyHttpTask myHttpTask = new MyHttpTask();
                myHttpTask.setBufferStr("d_ID=" + ID + "&location=" + current_room + "&current_move=" + 0);
                myHttpTask.execute("http://hope2017.esy.es/d_move_Rounding.php");
                System.out.println("order : "+order);
            }
        }
    }

    public void clear() {
        mRangedBeacons.clear();
    }

    @Override
    public int getCount() {
        return mRangedBeacons.size();
    }

    @Override
    public Object getItem(int position) {
        return mRangedBeacons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_ranging_beacon, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.recoProximityUuid = (TextView)convertView.findViewById(R.id.recoProximityUuid);
            viewHolder.recoMajor = (TextView)convertView.findViewById(R.id.recoMajor);
            viewHolder.recoMinor = (TextView)convertView.findViewById(R.id.recoMinor);
            viewHolder.recoTxPower = (TextView)convertView.findViewById(R.id.recoTxPower);
            viewHolder.recoRssi = (TextView)convertView.findViewById(R.id.recoRssi);
            viewHolder.recoBattery = (TextView)convertView.findViewById(R.id.recoBattery);
            viewHolder.recoProximity = (TextView)convertView.findViewById(R.id.recoProximity);
            viewHolder.recoAccuracy = (TextView)convertView.findViewById(R.id.recoAccuracy);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        RECOBeacon recoBeacon = mRangedBeacons.get(position);

        String proximityUuid = recoBeacon.getProximityUuid();

        viewHolder.recoProximityUuid.setText(String.format("%s-%s-%s-%s-%s", proximityUuid.substring(0, 8), proximityUuid.substring(8, 12), proximityUuid.substring(12, 16), proximityUuid.substring(16, 20), proximityUuid.substring(20) ));
        viewHolder.recoMajor.setText(recoBeacon.getMajor() + "");
        viewHolder.recoMinor.setText(recoBeacon.getMinor() + "");
        viewHolder.recoTxPower.setText(recoBeacon.getTxPower() + "");
        viewHolder.recoRssi.setText(recoBeacon.getRssi() + "");
        viewHolder.recoBattery.setText(recoBeacon.getBattery() + "");
        viewHolder.recoProximity.setText(recoBeacon.getProximity() + "");
        viewHolder.recoAccuracy.setText(String.format("%.2f", recoBeacon.getAccuracy()));


        return convertView;
    }

    static class ViewHolder {
        TextView recoProximityUuid;
        TextView recoMajor;
        TextView recoMinor;
        TextView recoTxPower;
        TextView recoRssi;
        TextView recoBattery;
        TextView recoProximity;
        TextView recoAccuracy;
    }


    public class MyHttpTask extends AsyncTask<String, Void, String> {
        private String bufferStr;
        public void setBufferStr(String bufferStr) {
            this.bufferStr = bufferStr;
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
                Log.d("MRS",url.toString());
                Log.d("MRS",bufferStr);
                Log.d("MRS",builder.toString());

                return builder.toString();
            } catch (IOException e) {
                Log.d("MRS",e.toString());
                return "ERROR";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            String[] stringArr = result.split("/");
            if(stringArr[0].equals("Success"))
            {

            }
            else
            {

            }
        }
    }

}
