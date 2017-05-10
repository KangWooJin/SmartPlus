package com.smartplus.smartplus;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;



public class ControlFrag4  extends Fragment implements View.OnClickListener {


    View view;

    TextView tv_frag4_day;
    TextView tv_frag4_week;
    TextView tv_frag4_month;

    TextView tv_cf4_type;
    TextView tv_cf4_time;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_control_frag4, container, false);

        tv_frag4_day = (TextView)view.findViewById(R.id.tv_frag4_day);
        tv_frag4_week = (TextView)view.findViewById(R.id.tv_frag4_week);
        tv_frag4_month = (TextView)view.findViewById(R.id.tv_frag4_month);
        tv_cf4_type = (TextView)view.findViewById(R.id.tv_cf4_type);
        tv_cf4_time = (TextView)view.findViewById(R.id.tv_cf4_time);


        tv_frag4_day.setOnClickListener(this);
        tv_frag4_week.setOnClickListener(this);
        tv_frag4_month.setOnClickListener(this);


        PacketSender  ps = new PacketSender( String.valueOf( TAG.GET_AIRC_USE_TIME ) );//pb.getPacket());
        ps.start();

        try {
            ps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String tmpStr2 = ps.return_msg;

        tv_cf4_time.setText(tmpStr2);

        dayGraph();


        return view;
    }





    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.tv_frag4_day)
        {
            dayGraph();

        }
        else if(v.getId()==R.id.tv_frag4_week)
        {
            //7

            tv_cf4_type.setText("주간 사용량은 총");

            tv_frag4_day.setTextColor(Color.parseColor("#FFCCCECE"));
            tv_frag4_week.setTextColor(Color.parseColor("#ff0094b0"));
            tv_frag4_month.setTextColor(Color.parseColor("#FFCCCECE"));


        }
        else if(v.getId()==R.id.tv_frag4_month)
        {

            tv_cf4_type.setText("월간 사용량은 총");

            tv_frag4_day.setTextColor(Color.parseColor("#FFCCCECE"));
            tv_frag4_week.setTextColor(Color.parseColor("#FFCCCECE"));
            tv_frag4_month.setTextColor(Color.parseColor("#ff0094b0"));
            //31

        }

    }


    void dayGraph() {



        tv_cf4_type.setText("일간 사용량은 총");

        tv_frag4_day.setTextColor(Color.parseColor("#ff0094b0"));
        tv_frag4_week.setTextColor(Color.parseColor("#FFCCCECE"));
        tv_frag4_month.setTextColor(Color.parseColor("#FFCCCECE"));

    }



}