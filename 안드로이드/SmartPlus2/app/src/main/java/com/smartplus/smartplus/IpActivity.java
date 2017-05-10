package com.smartplus.smartplus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class IpActivity extends ActionBarActivity {

    EditText et_ip_ip;
    EditText et_ip_port;
    Button bt_ip_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        et_ip_ip = (EditText) findViewById(R.id.et_ip_ip);
        et_ip_port = (EditText) findViewById(R.id.et_ip_port);
        bt_ip_ok = (Button) findViewById(R.id.bt_ip_ok);


        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        et_ip_ip.setText(pref.getString("ip", ""));


        SharedPreferences pref2 = getSharedPreferences("pref", MODE_PRIVATE);
        try {
            et_ip_port.setText((pref2.getString("port", "")));
        } catch (Exception e) {

            Log.i("ERROR", e.toString());

        }


        bt_ip_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SC.tmpIP = et_ip_ip.getText().toString();
                SC.tmpPORT = Integer.parseInt(et_ip_port.getText().toString());

                SharedPreferences pref4 = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor4 = pref4.edit();
                editor4.putString("ip", et_ip_ip.getText().toString());
                editor4.commit();

                SharedPreferences pref5 = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor5 = pref5.edit();
                editor5.putString("port", et_ip_port.getText().toString());
                editor5.commit();

                finish();

            }
        });

    }


}
