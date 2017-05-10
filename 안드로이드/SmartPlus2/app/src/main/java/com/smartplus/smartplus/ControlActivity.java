package com.smartplus.smartplus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class ControlActivity extends Activity implements View.OnClickListener {

    LinearLayout ll_control_ll;

    ImageView bt_control_bt1;
    ImageView bt_control_bt2;
    ImageView bt_control_bt3;
    ImageView bt_control_bt4;
    Fragment fr;

    View in_main_titlebar;
    ImageView iv_titlebar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ll_control_ll=(LinearLayout)findViewById(R.id.ll_control_ll);
        bt_control_bt1=(ImageView)findViewById(R.id.bt_control_bt1);
        bt_control_bt2=(ImageView)findViewById(R.id.bt_control_bt2);
        bt_control_bt3=(ImageView)findViewById(R.id.bt_control_bt3);
        bt_control_bt4=(ImageView)findViewById(R.id.bt_control_bt4);

        bt_control_bt1.setOnClickListener(this);
        bt_control_bt2.setOnClickListener(this);
        bt_control_bt3.setOnClickListener(this);
        bt_control_bt4.setOnClickListener(this);

        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);

        iv_titlebar_back.setOnClickListener(this);

        setColor(1);
        fr = new ControlFrag1();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.ll_control_ll, fr);
        fragmentTransaction.commit();

    }




    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.iv_titlebar_back)
        {
            onBackPressed();
        }
        if(v.getId() == R.id.bt_control_bt1)
        {
            setColor(1);
            fr = new ControlFrag1();
        }
        else if(v.getId() == R.id.bt_control_bt2)
        {
            setColor(2);
            fr = new ControlFrag2();
        }
        else if(v.getId() == R.id.bt_control_bt3)
        {
            setColor(3);
            fr = new ControlFrag3();
        }
        else if(v.getId() == R.id.bt_control_bt4)
        {
            setColor(4);
            fr = new ControlFrag4();
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.ll_control_ll, fr);
        fragmentTransaction.commit();

    }

    void setColor(int pos)
    {
        bt_control_bt1.setBackgroundColor(Color.parseColor("#ffffff"));
        bt_control_bt2.setBackgroundColor(Color.parseColor("#ffffff"));
        bt_control_bt3.setBackgroundColor(Color.parseColor("#ffffff"));
        bt_control_bt4.setBackgroundColor(Color.parseColor("#ffffff"));

        if(pos==1)
            bt_control_bt1.setBackgroundColor(Color.parseColor("#ffdedede"));
        if(pos==2)
            bt_control_bt2.setBackgroundColor(Color.parseColor("#ffdedede"));
        if(pos==3)
            bt_control_bt3.setBackgroundColor(Color.parseColor("#ffdedede"));
        if(pos==4)
            bt_control_bt4.setBackgroundColor(Color.parseColor("#ffdedede"));


    }

    @Override
    public void onBackPressed() {

//        PacketBuilder pb = new PacketBuilder(TAG.SEND_CURRENT_INFO);
//        pb.append(SC.BT_NAME);
//        pb.append(SC.CURRENT_TEMPER);
//        pb.append(SC.CURRENT_POWER);
//
//        PacketSender ps = new PacketSender(pb.getPacket());
//        ps.start();
//        try {
//            ps.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        super.onBackPressed();
    }
}
