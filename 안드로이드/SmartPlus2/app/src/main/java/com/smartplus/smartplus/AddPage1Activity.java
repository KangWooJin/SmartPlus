
package com.smartplus.smartplus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AddPage1Activity extends Activity implements View.OnClickListener {

    View in_main_titlebar;
    ImageView iv_titlebar_back;

    ImageView iv_addpage1_question;
    ImageView[] iv_addpage1_arr;
    int[] ids;
    int selectedNum=0;

    Button bt_addpage_next;
    TextView tv_addpage_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page1);

        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);

        iv_titlebar_back.setOnClickListener(this);

        iv_addpage1_question = (ImageView) findViewById(R.id.iv_addpage1_question);

        tv_addpage_txt = (TextView)findViewById(R.id.tv_addpage1_txt);

        bt_addpage_next = (Button)findViewById(R.id.bt_addpage1_next);


        bt_addpage_next.setOnClickListener(this);
        ids = new int[8];
        iv_addpage1_arr = new ImageView[8];

        ids[0] = R.id.iv_addpage1_air;
        ids[1] = R.id.iv_addpage1_gonggi;
        ids[2] = R.id.iv_addpage1_setak;
        ids[3] = R.id.iv_addpage1_right;
        ids[4] = R.id.iv_addpage1_refr;
        ids[5] = R.id.iv_addpage1_gasb;
        ids[6] = R.id.iv_addpage1_custom;
        ids[7] = R.id.iv_addpage1_boil;

        for (int i = 0; i < 8; i++) {
            iv_addpage1_arr[i] = (ImageView) findViewById(ids[i]);
            iv_addpage1_arr[i].setOnClickListener(this);
        }


    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.iv_titlebar_back)
        {


            onBackPressed();
            return;
        }

        SC.IS_LIGHT=false;

        if(v.getId() == bt_addpage_next.getId())
        {
            if(selectedNum==0)
            {
                startActivity(new Intent(getApplicationContext(), AddPage2Activity.class));
            }
            else if(selectedNum==3)
            {
//                SC.IS_ARRAYED=true;

                SC.RST_RECEIVER="";
                SC.RST_TITLE ="전구";
                SC.RST_TEMPER2POWER2OPTION3=null;
                SC.RST_BUTTONS=null;
                SC.STEP = 1;
                SC.IS_LIGHT=true;
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "준비중인 기능입니다", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        for (int i = 0; i < 8; i++ )
        {
            if (ids[i] == v.getId())
            {
                bt_addpage_next.setVisibility(View.VISIBLE);
                Drawable tmpInt = iv_addpage1_arr[i].getDrawable();
                iv_addpage1_question.setImageDrawable(tmpInt);

                selectedNum=i;

                if(i==0)
                    setTxt("에어콘");
                else if(i==1)
                    setTxt("공기청정기");
                else if(i==2)
                    setTxt("세탁기");
                else if(i==3)
                    setTxt("전구");
                else if(i==4)
                    setTxt("냉장고");
                else if(i==5)
                    setTxt("가습기");
                else if(i==6)
                    setTxt("커스텀");
                else if(i==7)
                    setTxt("보일러");




            }
        }


    }



    void setTxt(String s)
    {
        SC.RST_TITLE = s;
        tv_addpage_txt.setText(s+" 선택\n");
    }

    @Override
    protected void onResume() {
        if(SC.STEP==1)
            finish();

        super.onResume();
    }
}
