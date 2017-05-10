package com.smartplus.smartplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class ListActivity extends Activity implements View.OnClickListener {

    TextView tv_titlebar_title;
    View in_main_titlebar;
    ImageView iv_titlebar_back;
    ImageView iv_titlebar_plus;

    int isStartReady=0;


    ListOne [] lists = new ListOne[SC.MAX_LIST];
    ListAdapter listAdapter;
    GridView gv_list_list;

    ImageView mv_list_adddlfghldyd;

    private String m_DialogText = "";

    ListOne tmpListOne;
    int tmpArrayedBeforeInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);





        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);
        tv_titlebar_title = (TextView)in_main_titlebar.findViewById(R.id.tv_titlebar_title);
        iv_titlebar_plus = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_plus);

        mv_list_adddlfghldyd = (ImageView)findViewById(R.id.mv_list_adddlfghldyd);

        gv_list_list =(GridView)findViewById(R.id.gv_list_list);

        iv_titlebar_plus.setVisibility(View.VISIBLE);

        iv_titlebar_plus.setVisibility(View.VISIBLE);
        iv_titlebar_back.setOnClickListener(this);
        iv_titlebar_plus.setOnClickListener(this);
        mv_list_adddlfghldyd.setOnClickListener(this);

        isStartReady=2;
        // 처음추가
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        String tmpStr;
        for(int i = 0 ; i < SC.MAX_LIST ; i ++)
        {
            tmpStr = pref.getString( String.valueOf(i), "");
            Log.i("LOAD", String.valueOf(i) + " -> " + tmpStr);

            if(tmpStr.compareTo("NONE")==0)
            {
                lists[i] = new ListOne();
            }
            else if(tmpStr.compareTo("@전구@")==0)
            {
                lists[i]= new ListOne(-1); //-1 의미x
            }
            else
            {
                String[]tmpStrArr = tmpStr.split("@");
                int []tmpIntArr = new int[7];
                char []tmpCharArr = new char[5];

                for(int j = 0 ; j < 7 ; j ++)
                {
                    tmpIntArr[j] = Integer.parseInt(tmpStrArr[j + 2]);
                }
                for(int j = 0 ; j < 5 ; j ++)
                {
                    tmpCharArr[j] = tmpStrArr[j+9].charAt(0);
                }
                lists[i]= new ListOne(tmpStrArr[0],tmpStrArr[1],tmpIntArr,tmpCharArr);

            }

        }

        listAdapter = new ListAdapter(this,lists,0);
        gv_list_list.setAdapter(listAdapter);

        listAdapter.setSelectedListener(onSelectedListener);
        listAdapter.setLongSelectedListener(onLongSelectedListener);

        mv_list_adddlfghldyd.setVisibility(View.VISIBLE);
        for(int i = 0 ;  i < SC.MAX_LIST;i++)
        {
            if(lists[i].titlename.compareTo("NONE")!=0)
                mv_list_adddlfghldyd.setVisibility(View.INVISIBLE);
        }

//        registerReceiver(broadcastReceiver, new IntentFilter("TAG_NOTI"));
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_titlebar_back)
        {
            startActivity(new Intent(getApplicationContext(),IpActivity.class));

        }
        else if(v.getId() == R.id.iv_titlebar_plus)
        {
            startActivity(new Intent(getApplicationContext(),AddPage1Activity.class));

        }
        else if(v.getId() == R.id.mv_list_adddlfghldyd)
        {
//            startActivity(new Intent(getApplicationContext(),IpActivity.class));
        }
    }

    @Override
    protected void onResume() {

        if(SC.STEP==1)
        {
            mv_list_adddlfghldyd.setVisibility(View.INVISIBLE);
            changeCheckMode();

            SC.STEP = 2;
        }
        super.onResume();
    }


    void changeCheckMode()
    {
        listAdapter.setTag(1); // 체크모드
        listAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "아이콘 위치할 곳을 선택하세요", Toast.LENGTH_SHORT).show();

    }

    void changeCheckMode2()
    {
        listAdapter.setTag(10); // 체크모드
        listAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "배치 변경할 곳을 선택하세요", Toast.LENGTH_SHORT).show();

        //onselcetListener로 ISARRAY true인상태로
    }



    ListAdapter.OnSelectedListener onSelectedListener = new ListAdapter.OnSelectedListener() {
        @Override
        public int getPos(int pos) {

            if(SC.IS_ARRAYED==true)
            {
                SC.IS_ARRAYED=false;

                lists[tmpArrayedBeforeInt] = lists[pos];









                try {
                    SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = pref3.edit();

                    String saveStr="";
                    if(lists[pos].titlename.compareTo("NONE")==0)
                    {
                        saveStr="NONE";
//                        saveStr="null@NONE@0@0@0@0@0@0@0@a@a@a@a@a";
                    }
                    else {
                        saveStr = lists[pos].btname + "@" +
                                lists[pos].titlename + "@" +
                                lists[pos].temper2power2option3[0] + "@" +
                                lists[pos].temper2power2option3[1] + "@" +
                                lists[pos].temper2power2option3[2] + "@" +
                                lists[pos].temper2power2option3[3] + "@" +
                                lists[pos].temper2power2option3[4] + "@" +
                                lists[pos].temper2power2option3[5] + "@" +
                                lists[pos].temper2power2option3[6] + "@" +
                                lists[pos].bts[0] + "@" +
                                lists[pos].bts[1] + "@" +
                                lists[pos].bts[2] + "@" +
                                lists[pos].bts[3] + "@" +
                                lists[pos].bts[4];
                    }

                    editor3.putString( String.valueOf(tmpArrayedBeforeInt) ,saveStr );
                    editor3.commit();

                    Log.i("SVAE1111", saveStr);

                } catch (Exception e) {
                    Log.i("SVAE1111", "ERROR");
                }
















                lists[pos]=tmpListOne;
                listAdapter.setTag(0);
                listAdapter.reflashList(lists);
                listAdapter.notifyDataSetChanged();

//////////////////////////////////// 세이브 /////////////////////////


                try {
                    SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = pref3.edit();

                    String saveStr = tmpListOne.btname+"@"+
                            tmpListOne.titlename+"@"+
                            tmpListOne.temper2power2option3[0]+"@"+
                            tmpListOne.temper2power2option3[1]+"@"+
                            tmpListOne.temper2power2option3[2]+"@"+
                            tmpListOne.temper2power2option3[3]+"@"+
                            tmpListOne.temper2power2option3[4]+"@"+
                            tmpListOne.temper2power2option3[5]+"@"+
                            tmpListOne.temper2power2option3[6]+"@"+
                            tmpListOne.bts[0]+"@"+
                            tmpListOne.bts[1]+"@"+
                            tmpListOne.bts[2]+"@"+
                            tmpListOne.bts[3]+"@"+
                            tmpListOne.bts[4];

                    editor3.putString( String.valueOf(pos) ,saveStr );
                    editor3.commit();

                    Log.i("SVAE", saveStr);

                } catch (Exception e) {
                    Log.i("SVAE", "FAIL");
                }


////////////////////////////////

                if(isStartReady==1 )
                    isStartReady=2;

                return 0;
            }

            Log.i("ListAcitivty", "getPos ");
            if(SC.STEP==0)
            {
                //실행
                if(isStartReady==1 )
                    isStartReady=2;
                else if(isStartReady==2) {

                    if(lists[pos].titlename.compareTo("전구") == 0)
                    {
                        startActivity(new Intent(getApplicationContext(), LightActivity.class));
                    }
                    else
                    {
                        SC.PACK = lists[pos].btname + "@"
                                +lists[pos].temper2power2option3[0]+"@"
                                +lists[pos].temper2power2option3[1]+"@"
                                +lists[pos].temper2power2option3[2]+"@"
                                +lists[pos].temper2power2option3[3]+"@"
                                +lists[pos].temper2power2option3[4]+"@"
                                +lists[pos].temper2power2option3[5]+"@"
                                +lists[pos].temper2power2option3[6]+"@"
                                +lists[pos].bts[0]+"@"
                                +lists[pos].bts[1]+"@"
                                +lists[pos].bts[2]+"@"
                                +lists[pos].bts[3]+"@"
                                +lists[pos].bts[4];

                        startActivity(new Intent(getApplicationContext(), ControlActivity.class));
                    }


                }
            }

            if(SC.STEP == 2) // 추가
            {
                if(SC.IS_LIGHT==false)
                    applySet(pos);
                else
                    applySetLight(pos);

            }

            return 0;
        }
    };

    ListAdapter.OnLongSelectedListener onLongSelectedListener = new ListAdapter.OnLongSelectedListener() {
        @Override
        public int getPos(int pos) {
            if(SC.STEP==0)
            {
                //설정
                DialogRadio(pos);
            }

            return 0;
        }
    };

    void applySet(int pos)
    {
        listAdapter.setTag(0);
        lists[pos]= new ListOne(SC.RST_RECEIVER, SC.RST_TITLE ,SC.RST_TEMPER2POWER2OPTION3,SC.RST_BUTTONS);
        listAdapter.reflashList(lists);
        listAdapter.notifyDataSetChanged();

        /////////////##추가##/////////
        		//^^
//		Packet_ADD_MODULE packet_add_module = new Packet_ADD_MODULE(
//                SC.RST_RECEIVER,
//                SC.RST_TEMPER2POWER2OPTION3[0],
//                SC.RST_TEMPER2POWER2OPTION3[1],
//                SC.RST_TEMPER2POWER2OPTION3[2],
//                SC.RST_TEMPER2POWER2OPTION3[3],
//                SC.RST_TEMPER2POWER2OPTION3[4],
//                SC.RST_TEMPER2POWER2OPTION3[5],
//                SC.RST_TEMPER2POWER2OPTION3[6],
//                (int)(SC.RST_BUTTONS[0]-'A'),
//                (int)(SC.RST_BUTTONS[1]-'A'),
//                (int)(SC.RST_BUTTONS[2]-'A'),
//                (int)(SC.RST_BUTTONS[3]-'A'),
//                (int)(SC.RST_BUTTONS[4]-'A'));
        String tmpStr = TAG.SET_SETTING+"@"+SC.RST_TEMPER2POWER2OPTION3[0]+"@"+SC.RST_TEMPER2POWER2OPTION3[1]+"@"+SC.RST_TEMPER2POWER2OPTION3[2]+"@"+SC.RST_TEMPER2POWER2OPTION3[3];

        PacketSender ps = new PacketSender( tmpStr );//pb.getPacket());
        ps.start();

        try {
            ps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //밑 다섯개 STRING에서 CHAR 로바꿈

//                Log.i("AA",SC.RST_RECEIVER);
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[0]));
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[1]));
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[2]));
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[3]));
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[4]));
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[5]));
//                Log.i("AA",iTs(SC.RST_TEMPER2POWER2OPTION3[6]));


//        PacketSender ps2 = new PacketSender(packet_add_module.getPacket());
//
//        ps2.start();
//
//        try {
//			ps2.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		////this
//
//        Log.i("ListActivity", ps2.return_msg);
//
//        if( sTi(ps2.return_msg) == 1 )
//        {
//            //성공
//            Log.i("isError", "NO");
//
//        }
//        else
//        {
//            // 에러
//            Log.i("isError", "ERROR");
//
//        }

        /////////////// IO 추가

        try {
        SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor3 = pref3.edit();

        String saveStr = SC.RST_RECEIVER+"@"+
                SC.RST_TITLE+"@"+
                SC.RST_TEMPER2POWER2OPTION3[0]+"@"+
                SC.RST_TEMPER2POWER2OPTION3[1]+"@"+
                SC.RST_TEMPER2POWER2OPTION3[2]+"@"+
                SC.RST_TEMPER2POWER2OPTION3[3]+"@"+
                SC.RST_TEMPER2POWER2OPTION3[4]+"@"+
                SC.RST_TEMPER2POWER2OPTION3[5]+"@"+
                SC.RST_TEMPER2POWER2OPTION3[6]+"@"+
                SC.RST_BUTTONS[0]+"@"+
                SC.RST_BUTTONS[1]+"@"+
                SC.RST_BUTTONS[2]+"@"+
                SC.RST_BUTTONS[3]+"@"+
                SC.RST_BUTTONS[4];

        editor3.putString( String.valueOf(pos) ,saveStr );
        editor3.commit();

        Log.i("SVAE", saveStr);

    } catch (Exception e) {
        Log.i("SVAE", "FAIL");
    }

        /////////////////////////////

        SC.STEP=0;
        isStartReady=1;
    }

    void applySetLight(int pos)
    {
        listAdapter.setTag(3);
        lists[pos]= new ListOne(SC.RST_RECEIVER, SC.RST_TITLE ,SC.RST_TEMPER2POWER2OPTION3,SC.RST_BUTTONS);
        listAdapter.reflashList(lists);
        listAdapter.notifyDataSetChanged();

        /////////////##추가##/////////
        //^^
//		Packet_ADD_MODULE packet_add_module = new Packet_ADD_MODULE(
//                SC.RST_RECEIVER,
//                SC.RST_TEMPER2POWER2OPTION3[0],
//                SC.RST_TEMPER2POWER2OPTION3[1],
//                SC.RST_TEMPER2POWER2OPTION3[2],
//                SC.RST_TEMPER2POWER2OPTION3[3],
//                SC.RST_TEMPER2POWER2OPTION3[4],
//                SC.RST_TEMPER2POWER2OPTION3[5],
//                SC.RST_TEMPER2POWER2OPTION3[6],
//                (int)(SC.RST_BUTTONS[0]-'A'),
//                (int)(SC.RST_BUTTONS[1]-'A'),
//                (int)(SC.RST_BUTTONS[2]-'A'),
//                (int)(SC.RST_BUTTONS[3]-'A'),
//                (int)(SC.RST_BUTTONS[4]-'A'));

        // $$Light로저장

        String tmpStr = "ASD";

        PacketSender ps = new PacketSender( tmpStr );//pb.getPacket());
//        ps.start();

//        try {
//            ps.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        try {
            SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor3 = pref3.edit();

            String saveStr = SC.RST_RECEIVER+"@"+
                    SC.RST_TITLE+"@";
            editor3.putString( String.valueOf(pos) ,saveStr );
            editor3.commit();

            Log.i("SVAE", saveStr);

        } catch (Exception e) {
            Log.i("SVAE", "FAIL");
        }

        /////////////////////////////

        SC.STEP=0;
        isStartReady=1;

        finish();
        startActivity(getIntent());

    }

    void deleList(int pos)
    {
        String tmpStr = lists[pos].btname;
        listAdapter.setTag(0);
        lists[pos]= new ListOne();
        listAdapter.reflashList(lists);
        listAdapter.notifyDataSetChanged();


        try {
            SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor3 = pref3.edit();

            editor3.putString( String.valueOf(pos) ,"NONE" );
            editor3.commit();

            Log.i("SVAE", "save NONE");

        } catch (Exception e) {
            Log.i("SVAE", "FAIL");
        }

        String tmpStr2 = String.valueOf( TAG.DELETE_AIRC );

        PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
        ps.start();

        try {
            ps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        PacketBuilder pb = new PacketBuilder(TAG.SEND_DROP_MODULE);
//        pb.append(tmpStr);
//        PacketSender ps = new PacketSender(pb.getPacket());
//        ps.start();
//        try {
//            ps.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }





    }


    private void DialogRadio(final int pos_){
        final CharSequence[] Sets= {"삭제","타이틀 변경","배치변경" };
//        final CharSequence[] PhoneModels = {"iPhone", "Nokia", "Android"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
//        alt_bld.setIcon(R.drawable.logo);
        alt_bld.setTitle("선택하세요");
        alt_bld.setSingleChoiceItems(Sets, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

               if(item==0) // 삭제
               {
                   deleList(pos_);
               }
               else if(item==1)// 타이틀 변경
               {
                   changeTItleName(pos_);
               }
               else if(item==2)// 배치변경
               {
                    changeArray(pos_);
               }
               else if(item==3)// ??
               {

               }
                dialog.cancel();
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    private void changeArray(int pos_) {


        tmpArrayedBeforeInt=pos_;

        tmpListOne = lists[pos_];

//        tmpListOne.titlename = lists[pos_].titlename;
//
//        tmpListOne.btname = lists[pos_].btname;
//
//        for(int i = 0 ; i < 7 ; i ++)
//        {
//            tmpListOne.temper2power2option3[i] = lists[pos_].temper2power2option3[i];
//        }
//        for(int i = 0 ; i < 5 ; i ++)
//        {
//            tmpListOne.bts[i] = lists[pos_].bts[i];
//        }


        changeCheckMode2();



    }

    private void changeTItleName(final int pos_) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("타이틀을 입력하세요");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_DialogText = input.getText().toString();

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

                String tmpStr = pref.getString( String.valueOf(pos_), "");
                String[]tmpStrArr = tmpStr.split("@");
                int []tmpIntArr = new int[7];
                char []tmpCharArr = new char[5];

                for(int j = 0 ; j < 7 ; j ++)
                {
                    tmpIntArr[j] = Integer.parseInt(tmpStrArr[j + 2]);
                }
                for(int j = 0 ; j < 5 ; j ++)
                {
                    tmpCharArr[j] = tmpStrArr[j+9].charAt(0);
                }
                lists[pos_]= new ListOne(tmpStrArr[0],m_DialogText,tmpIntArr,tmpCharArr);

                listAdapter.setTag(0);
                listAdapter.reflashList(lists);
                listAdapter.notifyDataSetChanged();


                try {
                    SharedPreferences pref3 = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = pref3.edit();

                    String saveStr = tmpStrArr[0] + "@" + m_DialogText + "@" +
                            tmpStrArr[2] + "@" +
                            tmpStrArr[3] + "@" +
                            tmpStrArr[4] + "@" +
                            tmpStrArr[5] + "@" +
                            tmpStrArr[6] + "@" +
                            tmpStrArr[7] + "@" +
                            tmpStrArr[8] + "@" +
                            tmpStrArr[9] + "@" +
                            tmpStrArr[10] + "@" +
                            tmpStrArr[11] + "@" +
                            tmpStrArr[12] + "@" +
                            tmpStrArr[13];

                    editor3.putString(String.valueOf(pos_), saveStr);
                    editor3.commit();

                }catch (Exception e){}
                isStartReady=1;
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    int sTi(String s)
    {
        return Integer.parseInt(s);
    }
    String iTs(int s)
    {
        return String.valueOf(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
    }

}
