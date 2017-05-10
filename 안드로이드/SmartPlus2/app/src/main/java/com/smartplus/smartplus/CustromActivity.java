package com.smartplus.smartplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class CustromActivity extends ActionBarActivity implements View.OnClickListener {

    View in_main_titlebar;
    ImageView iv_titlebar_back;
    Button bt_custom_next;


    int []ids;
    EditText[]et_custom_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custrom);

        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);
        bt_custom_next = (Button)findViewById(R.id.bt_custom_next);

        iv_titlebar_back.setOnClickListener(this);
        bt_custom_next.setOnClickListener(this);

        et_custom_arr = new EditText[7];
        ids = new int[7];
        ids[0]=R.id.et_custom_0;
        ids[1]=R.id.et_custom_1;
        ids[2]=R.id.et_custom_2;
        ids[3]=R.id.et_custom_3;
        ids[4]=R.id.et_custom_4;
        ids[5]=R.id.et_custom_5;
        ids[6]=R.id.et_custom_6;

        for(int i = 0 ; i <  7 ; i ++)
        {
            et_custom_arr[i] =(EditText)findViewById( ids[i]);
        }









    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_titlebar_back)
        {

            onBackPressed();
            return;
        }
        else if(v.getId() == R.id.bt_custom_next)
        {

            for(int i = 0 ; i < 7 ; i++)
            {
                SC.RST_TEMPER2POWER2OPTION3[i] = Integer.parseInt(et_custom_arr[i].getText().toString());

            }
            startActivity(new Intent(getApplicationContext(),AddPage3Activity.class));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SC.STEP==1)
            finish();
    }
}
