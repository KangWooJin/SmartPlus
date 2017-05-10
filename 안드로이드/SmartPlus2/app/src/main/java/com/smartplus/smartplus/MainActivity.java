package com.smartplus.smartplus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;



public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        try {
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            String str = pref.getString("first", "");

            if(str.compareTo("")==0)
            {
                try {

                    SharedPreferences pref2 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = pref2.edit();
                    editor2.putString("first","fristConnect");
                    editor2.commit();

                    SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = pref3.edit();

                    for(int i = 0 ; i < SC.MAX_LIST ; i ++)
                    {
                        editor3.putString( String.valueOf(i) ,"NONE");
                    }
                    editor3.commit();




                    SharedPreferences pref4 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor4 = pref4.edit();
                    editor4.putString("ip",SC.tmpIP);
                    editor4.commit();
                    // �̰Ÿ��� StaticCalss���ִ°Ž�ߴ�

                    SharedPreferences pref5 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor5 = pref5.edit();
                    editor5.putString("port", String.valueOf( SC.tmpPORT ) );
                    editor5.commit();
                    // �̰Ÿ��� StaticCalss���ִ°Ž�ߴ�





                } catch (Exception e) {
                }

            }



        } catch (Exception e) {
        }




        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        PacketSender.serverIP = pref.getString("ip", "");


        try {

            SharedPreferences pref2 = getSharedPreferences("pref", MODE_PRIVATE);
//            PacketSender.serverPort = Integer.parseInt(pref2.getString("port", ""));
        }catch (Exception e)
        {}











        Handler hh = new Handler();
        hh.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),ListActivity.class));
                finish();
            }
        }, 1300);



    }

}
