package com.smartplus.smartplus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class AddPage3Activity extends Activity implements View.OnClickListener {
    View in_main_titlebar;
    ImageView iv_titlebar_back;

    String Selected;
    TextView tv_addpage3_txt;
    ListView lv_addpage3_list;
    LinearLayout ll_addpage3_ll;
    TextView[]tv_addpage3_arr;
    TextView[]tv_addpage3_arr_receiver;
    Button bt_addpage3_next;
    int beforePos;
    LinearLayout ll_addpage3_recever;
    int []ids;
    int []idsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page3);

        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);

        iv_titlebar_back.setOnClickListener(this);

        tv_addpage3_txt= (TextView)findViewById(R.id.tv_addpage3_txt);
        lv_addpage3_list= (ListView)findViewById(R.id.lv_addpage3_list);
        ll_addpage3_ll= (LinearLayout)findViewById(R.id.ll_addpage3_ll);
        ll_addpage3_recever= (LinearLayout)findViewById(R.id.ll_addpage3_recever);
        bt_addpage3_next= (Button)findViewById(R.id.bt_addpage3_next);
        bt_addpage3_next.setOnClickListener(this);

        tv_addpage3_arr = new TextView[5];
        tv_addpage3_arr_receiver = new TextView[5];
        ids = new int[5];
        idsReceiver = new int[5];

        ids[0] = R.id.tv_addpage3_bt1;
        ids[1] = R.id.tv_addpage3_bt2;
        ids[2] = R.id.tv_addpage3_bt3;
        ids[3] = R.id.tv_addpage3_bt4;
        ids[4] = R.id.tv_addpage3_bt5;

        idsReceiver[0]= R.id.tv_addpage3_bta;
        idsReceiver[1]= R.id.tv_addpage3_btb;
        idsReceiver[2]= R.id.tv_addpage3_btc;
        idsReceiver[3]= R.id.tv_addpage3_btd;
        idsReceiver[4]= R.id.tv_addpage3_bte;

        for(int i = 0 ; i < 5 ; i ++)
        {
            tv_addpage3_arr[i] = (TextView)findViewById(ids[i]);
            tv_addpage3_arr[i].setOnClickListener(this);
            tv_addpage3_arr_receiver[i] = (TextView)findViewById(idsReceiver[i]);
            tv_addpage3_arr_receiver[i].setOnClickListener(this);
        }

        //////////##리시버가져오기##//////////////////

        //^^


//        packet_LIST_REQ packet_list_req = new packet_LIST_REQ();
//        PacketSender ps2 = new PacketSender(packet_list_req.getPacket());
//
//        ps2.start();
//
//        try {
//            ps2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Log.i("AddPage3", ps2.return_msg);
//        String []tmpStrArr = ps2.return_msg.split("@");
        //////////////////////////////////////////

//        if(ps2.return_msg.isEmpty())
//        {
//            Toast.makeText(getApplicationContext(),"연결가능한 수신기 목록이 없습니다",Toast.LENGTH_SHORT);
//            finish();
//        }

//        String[] strArr = ps2.return_msg.split("@");
        String [] strArr  = {"수신기01"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strArr);
        lv_addpage3_list.setAdapter(adapter);
        lv_addpage3_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Selected= ((TextView) view).getText().toString();

                SC.RST_RECEIVER = Selected;
                tv_addpage3_txt.setText(Selected+"\n");
                ll_addpage3_ll.setVisibility(View.VISIBLE);
                bt_addpage3_next.setVisibility(View.VISIBLE);
//
//                PacketBuilder pb = new PacketBuilder(TAG.SEND_BT_RIGHT);
//                pb.append(Selected);
//                PacketSender ps = new PacketSender(pb.getPacket());
//                ps.start();
//                try {
//                    ps.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            }
        });






    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.iv_titlebar_back)
        {

            onBackPressed();
            return;
        } if(v.getId() == R.id.iv_titlebar_back)
        {

            onBackPressed();
            return;
        }

        if(v.getId() == R.id.bt_addpage3_next)
        {

            SC.RST_BUTTONS[0]=tv_addpage3_arr[0].getText().toString().charAt(0);
            SC.RST_BUTTONS[1]=tv_addpage3_arr[1].getText().toString().charAt(0);
            SC.RST_BUTTONS[2]=tv_addpage3_arr[2].getText().toString().charAt(0);
            SC.RST_BUTTONS[3]=tv_addpage3_arr[3].getText().toString().charAt(0);
            SC.RST_BUTTONS[4]=tv_addpage3_arr[4].getText().toString().charAt(0);

            SC.STEP = 1;

            finish();

            return;
        }

        for(int i = 0 ; i < 5 ; i ++)
        {
            if(v.getId() == ids[i])
            {
                ll_addpage3_recever.setVisibility(View.VISIBLE);
                lv_addpage3_list.setVisibility(View.INVISIBLE);
                beforePos=i;
            }
        }

        for(int i = 0 ; i < 5 ; i ++)
        {
            if(v.getId() == idsReceiver[i])
            {
                if(i==0)
                    tv_addpage3_arr[beforePos].setText("A");
                if(i==1)
                    tv_addpage3_arr[beforePos].setText("B");
                if(i==2)
                    tv_addpage3_arr[beforePos].setText("C");
                if(i==3)
                    tv_addpage3_arr[beforePos].setText("D");
                if(i==4)
                    tv_addpage3_arr[beforePos].setText("E");

                ll_addpage3_recever.setVisibility(View.INVISIBLE);
                lv_addpage3_list.setVisibility(View.VISIBLE);
            }
        }

    }
}