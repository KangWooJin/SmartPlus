package com.smartplus.smartplus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Console;


public class LightActivity extends Activity {

    View in_main_titlebar;
    ImageView iv_titlebar_back;

    TextView tv_light_type;
    TextView tv_light_time;

    ImageView iv_lightpage_button;

    boolean isOff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_page);

        tv_light_type = (TextView)findViewById(R.id.tv_light_type);
        tv_light_time = (TextView)findViewById(R.id.tv_light_time);

        iv_lightpage_button= (ImageView)findViewById(R.id.iv_lightpage_button);

        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);

        iv_titlebar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                return;
            }
        });

        iv_lightpage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PacketSender ps = new PacketSender( String.valueOf(TAG.TURN_LIGHT ) );//pb.getPacket());
                ps.start();

                try {
                    ps.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(isOff==true)
                {
                    iv_lightpage_button.setImageDrawable(getResources().getDrawable(R.drawable.a_lighnt01));
                    isOff=false;
                }
                else
                {
                    iv_lightpage_button.setImageDrawable(getResources().getDrawable(R.drawable.a_lighnt02));
                    isOff=true;

                    PacketSender ps2 = new PacketSender( String.valueOf(TAG.GET_LIGHT_TIME ) );//pb.getPacket());
                    ps2.start();

                try {
                    ps2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    String tmpStr2 = ps2.return_msg;

                    Log.i("tmpStr2 : " , tmpStr2);

                    tv_light_time.setText(tmpStr2);
                }





            }
        });

        PacketSender ps = new PacketSender( String.valueOf(TAG.GET_LIGHT_TIME ) );//pb.getPacket());
        ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        String tmpStr2 = ps.return_msg;

        tv_light_time.setText(tmpStr2);



    }





}
