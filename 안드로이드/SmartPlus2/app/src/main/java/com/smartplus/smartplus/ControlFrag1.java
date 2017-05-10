package com.smartplus.smartplus;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class ControlFrag1 extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private Handler mHandler;
    private Runnable mRunnable;

    ImageView bt_frag1_tup;
    ImageView bt_frag1_tdown;
    ImageView bt_frag1_pup;
    ImageView bt_frag1_pdown;

    Switch sw_frag1_power;
    Switch sw_frag1_quick;

    TextView tv_frag1_temper;
    TextView tv_frag1_power;

    int minPower;
    int maxPower;
    int minTemper;
    int maxTemper;
    int curPower;
    int curTemper;

    char []arrC;

    int btA;
    int btB;
    int btC;
    int btD;
    int btE;
    String btName;

    boolean isOn;
    int isOnQuick;

    LinearLayout ll_frag1_ll;

    View view;

    boolean isInit;

    NotificationManager notificationManager ;

    boolean isToast = false;

    int limited = 0;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        arrC = new char[5];
        isOnQuick=0;
        view = inflater.inflate(R.layout.activity_control_frag1,container,false);

        bt_frag1_tup = (ImageView) view.findViewById(R.id.bt_frag1_tup);
        bt_frag1_tdown = (ImageView) view.findViewById(R.id.bt_frag1_tdown);
        bt_frag1_pup = (ImageView) view.findViewById(R.id.bt_frag1_pup);
        bt_frag1_pdown = (ImageView) view.findViewById(R.id.bt_frag1_pdown);
        sw_frag1_power = (Switch) view.findViewById(R.id.sw_frag1_power);
        sw_frag1_quick = (Switch)view.findViewById(R.id.sw_frag1_quick);
        tv_frag1_temper = (TextView) view.findViewById(R.id.tv_frag1_temper);
        tv_frag1_power = (TextView) view.findViewById(R.id.tv_frag1_power);
        ll_frag1_ll = (LinearLayout)view.findViewById(R.id.ll_frag1_ll);

        bt_frag1_tup.setOnClickListener(this);
        bt_frag1_tdown.setOnClickListener(this);
        bt_frag1_pup.setOnClickListener(this);
        bt_frag1_pdown.setOnClickListener(this);

        sw_frag1_power.setOnCheckedChangeListener(this);
        sw_frag1_quick.setOnCheckedChangeListener(this);
        getData();

        view.getContext().registerReceiver(broadcastReceiver, new IntentFilter("TAG_NOTI"));
        floatToast("ONCREATE");

        mRunnable = new Runnable() {
            @Override
            public void run() {
                limited=1;
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 500);

        return view;
    }

    void getData()
    {
        Log.i("PP", SC.PACK);
        String[]tmpStrArr = SC.PACK.split("@");

        SC.BT_NAME = btName = tmpStrArr[0];//블투이름


        /////////////////// ##전원여부## //////////////////
            	//^^






          PacketSender ps = new PacketSender(iTs (TAG.GET_IS_ON) );
          ps.start();

        try {
            ps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("ControlFag1", "is_on : " + ps.return_msg);

        if( Integer.parseInt(ps.return_msg) == 1) { // 0519
//        if( true)
            isOn = true;
        }
        else
            isOn=false;

        //////////////////////////////////////////////////


        if(isOn) {
            sw_frag1_power.setChecked(true);



            powerOn();

        }
        else
        {
            sw_frag1_power.setChecked(false);
            powerOff();
        }


       String tmpStr2 ;

        ps = new PacketSender( iTs( TAG.GET_SETTING ) );//pb.getPacket());
        ps.start();

        try {
            ps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tmpStr2 = ps.return_msg;

        Log.i("cfg1 return_msg : " ,ps.return_msg);

        String  [] tmpStr = tmpStr2.split("@");


//        if(isOn) {
//
//            minTemper = sTi(tmpStrArr[1]);
//            maxTemper =  sTi(tmpStrArr[2]);
//            minPower = sTi(tmpStrArr[3]);
//            maxPower = sTi(tmpStrArr[4]);

        SC.CURRENT_TEMPER =  curTemper = sTi(tmpStr[2]);
        SC.CURRENT_POWER = curPower = sTi(tmpStr[5]);

            tv_frag1_temper.setText(iTs(curTemper));
            tv_frag1_power.setText(iTs(curPower));
//
//        }




        //&&위에따라 구현해줘야함함
        btA = cTi(tmpStrArr[8].charAt(0)); //파워
        btB = cTi(tmpStrArr[9].charAt(0)); // tu
        btC = cTi(tmpStrArr[10].charAt(0)); //td
        btD = cTi(tmpStrArr[11].charAt(0)); //pu
        btE = cTi(tmpStrArr[12].charAt(0)); //pd

        Log.i("---", String.valueOf(btA));
        Log.i("---", String.valueOf(btB));
        Log.i("---", String.valueOf(btC));
        Log.i("---", String.valueOf(btD));
        Log.i("---", String.valueOf(btE));


    }

    int cTi(char c)
    {
        if(c=='A')
            return 1;
        if(c=='B')
            return 2;
        if(c=='C')
            return 3;
        if(c=='D')
            return 4;
        if(c=='E')
            return 5;
        return -1;
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
    public void onClick(View v) {


        if (v.getId() == R.id.bt_frag1_tup)
        {
            remoteTUp();

        } else if (v.getId() == R.id.bt_frag1_tdown)
        {
            remoteTDown();
        } else if (v.getId() == R.id.bt_frag1_pup)
        {
            remotePUp();

        } else if (v.getId() == R.id.bt_frag1_pdown)
        {
            remotePDown();

        }
    }

    void remoteTDown()
    {

        floatToast("TD");

        curTemper--;
        if(curTemper==minTemper-1)
            curTemper++;
        else
        {

            String tmpStr2 = String.valueOf( TAG.TEMPER_DOWN );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();



            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            ClientAsyncTask clientAST = new ClientAsyncTask();
//            clientAST.execute(new String[] { SC.tmpIP, SC.tmpPORT,"TEMPERDOWN" });

            //##온도센서 한칸 올리기 ^^
//            PacketBuilder pb = new PacketBuilder(TAG.SEND_COMMAND);
//            pb.append(btName);
//            pb.append(btC); //&&//td
//            PacketSender ps = new PacketSender(pb.getPacket());
//            ps.start();
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }


        SC.CURRENT_TEMPER = curTemper;
        tv_frag1_temper.setText(iTs(curTemper));
        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("현재 온도는 " + String.valueOf(curTemper) + "입니다."), Toast.LENGTH_SHORT).show();
        }
    }

    void remoteTUp()
    {
        floatToast("TU");
        curTemper++;

        if(curTemper==maxTemper+1)
            curTemper--;
        else
        {

            String tmpStr2 = String.valueOf( TAG.TEMPER_UP );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            ClientAsyncTask clientAST = new ClientAsyncTask();
//            clientAST.execute(new String[] { SC.tmpIP, SC.tmpPORT,"TEMPERUP" });


            //##온도센서 한칸 올리기 ^^
//            PacketBuilder pb = new PacketBuilder(TAG.SEND_COMMAND);
//            pb.append(btName);
//            pb.append(btB);//&&//tu
//            PacketSender ps = new PacketSender(pb.getPacket());
//            ps.start();
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
        SC.CURRENT_TEMPER = curTemper;
        tv_frag1_temper.setText(iTs(curTemper));
        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("현재 온도는 " + String.valueOf(curTemper) + "입니다."), Toast.LENGTH_SHORT).show();
        }

    }

    void remotePUp()
    {
        floatToast("PU");
        curPower++;

        if(curPower==maxPower+1)
            curPower--;
        else
        {

            String tmpStr2 = String.valueOf( TAG.POWER_UP );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            ClientAsyncTask clientAST = new ClientAsyncTask();
//            clientAST.execute(new String[] { SC.tmpIP, SC.tmpPORT,"POWERUP" });


            //##온도센서 한칸 올리기 ^^
//            PacketBuilder pb = new PacketBuilder(TAG.SEND_COMMAND);
//            pb.append(btName);
//            pb.append(btD);//&&//pu
//            PacketSender ps = new PacketSender(pb.getPacket());
//            ps.start();
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        SC.CURRENT_POWER = curPower;
        tv_frag1_power.setText(iTs(curPower));
        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("현재 세기는 " + String.valueOf(curPower) + "입니다."), Toast.LENGTH_SHORT).show();
        }

    }

    void remotePDown()
    {

        floatToast("PD");

        curPower--;

        if(curPower==minPower-1)
            curPower++;
        else
        {


            String tmpStr2 = String.valueOf( TAG.POWER_DOWN );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


//            ClientAsyncTask clientAST = new ClientAsyncTask();
//            clientAST.execute(new String[] { SC.tmpIP, SC.tmpPORT,"POWERDOWN" });


            //##온도센서 한칸 올리기 ^^
//            PacketBuilder pb = new PacketBuilder(TAG.SEND_COMMAND);
//            pb.append(btName);
//            pb.append(btE); //&&//pd
//            PacketSender ps = new PacketSender(pb.getPacket());
//            ps.start();
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        SC.CURRENT_POWER= curPower;
        tv_frag1_power.setText(iTs(curPower));
        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("현재 세기는 " + String.valueOf(curPower) + "입니다."), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.sw_frag1_quick) {

            if(isChecked==true)
            {
                startNotification();
                isOnQuick=1;
            }
            else
            {
                removeNotification();
                isOnQuick=0;
            }

        } else if (buttonView.getId() == R.id.sw_frag1_power) {
            if (isChecked == true) {
                powerOn();

            } else {
                //전원끔
                powerOff();


            }
        }
    }

    void powerOn()
    {
        isOn=true;
        floatToast("POWERON");


        if(limited==1) {



            String tmpStr2 = String.valueOf( TAG.POWER_ON );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            String tmpStr2 = String.valueOf( TAG.POWER_ON );
//
//            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
//            ps.start();
//
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


//            ClientAsyncTask clientAST = new ClientAsyncTask();
//            clientAST.execute(new String[] { SC.tmpIP, SC.tmpPORT,"POWERON" });

//            PacketBuilder pb = new PacketBuilder(TAG.SEND_COMMAND);
//            pb.append(btName);
//            pb.append(btA); //&&//poon
//        pb.append(curTemper);
//        pb.append(curPower);

//            PacketSender ps = new PacketSender(pb.getPacket());
//            ps.start();
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


/*            PacketBuilder pb2 = new PacketBuilder(TAG.SEND_CURRENT_INFO2);
            pb2.append(btName);
            pb2.append(1);
//            pb.append(btA); //&&//poff
            pb2.append(curTemper);
            pb2.append(curPower);*/

//            PacketSender ps2 = new PacketSender(pb2.getPacket());
//            ps2.start();
//            try {
//                ps2.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


        }








        ll_frag1_ll.setVisibility(View.VISIBLE);

        /////////////##현제 에어컨 온도 및 파워##///////////////////

        String tmpStr2 = String.valueOf( TAG.GET_SETTING );

        PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
        ps.start();

        try {
            ps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tmpStr2 = ps.return_msg;

        Log.i("cfg1 return_msg : " ,ps.return_msg);

        String  [] tmpStr = tmpStr2.split("@");

//        PacketBuilder pb2 = new PacketBuilder(TAG.GET_SETTING);
//        pb2.append(btName);
//        PacketSender ps2 = new PacketSender(pb2.getPacket());
//        ps2.start();
//        try {
//            ps2.join();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        //^^
//        Log.i("ControlFrag1", "GET_SETTING : " + ps2.return_msg);

//        String[]tmpStr = ps2.return_msg.split("@"); // 0519

//        String []tmpStr = {"1","1","1","1","1","1","1"};

//        if( tmpStr[5].compareTo("1")==0)
//            isInit=true;
//        else
//            isInit=false;

        ////////////////////////////////////////////////////////////


//        Log.i("AAAAAA", tmpStr[5] + "  " + tmpStr[6]);
//
            minTemper = sTi(tmpStr[0]);
            maxTemper = sTi(tmpStr[1]);
            minPower = sTi(tmpStr[3]);
            maxPower = sTi(tmpStr[4]);
            SC.CURRENT_TEMPER = curTemper = sTi(tmpStr[2]);
            SC.CURRENT_POWER = curPower = sTi(tmpStr[5]);
            tv_frag1_temper.setText(iTs(curTemper));
            tv_frag1_power.setText(iTs(curPower));

        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("제품이 가동되었습니다"), Toast.LENGTH_SHORT).show();
        }
    }

    void floatToast(String s)
    {
//        Toast.makeText(view.getContext(),s,100).show();
    }

    void powerOff()
    {
        isOn=false;
        floatToast("POWEROFF");

        if(limited==1) {
//            ClientAsyncTask clientAST = new ClientAsyncTask();
//            clientAST.execute(new String[] { SC.tmpIP, SC.tmpPORT,"POWEROFF" });

            /////////////##현제 에어컨 온도 및 파워 저장##///////////////////

            String tmpStr2 = String.valueOf( TAG.POWER_ON );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            PacketBuilder pb = new PacketBuilder(TAG.SEND_COMMAND);
//            pb.append(btName);
//            pb.append(btA); //&&//poff
////        pb.append(curTemper);
////        pb.append(curPower);
//
//            PacketSender ps = new PacketSender(pb.getPacket());
//            ps.start();
//            try {
//                ps.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


//            PacketBuilder pb2 = new PacketBuilder(TAG.SEND_CURRENT_INFO2);
//            pb2.append(btName);
//            pb2.append(0);
////            pb.append(btA); //&&//poff
//            pb2.append(curTemper);
//            pb2.append(curPower);
//
//            PacketSender ps2 = new PacketSender(pb2.getPacket());
//            ps2.start();
//            try {
//                ps2.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }




//        //##온도센서 한칸 올리기 ^^
//        PacketBuilder pb2 = new PacketBuilder(TAG.SEND_COMMAND);
//        pb2.append(btName);
//        pb2.append(btE);
//        pb2.append(SC.CURRENT_TEMPER);
//        pb2.append(SC.CURRENT_POWER);
//
//
//
//        PacketSender ps2 = new PacketSender(pb2.getPacket());
//        ps2.start();
//        try {
//            ps2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




        ll_frag1_ll.setVisibility(View.INVISIBLE);

        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("제품이 종료되었습니다"), Toast.LENGTH_SHORT).show();
        }

    }

    private void startNotification(){
        String ns = Context.NOTIFICATION_SERVICE;
         notificationManager =
                (NotificationManager) view.getContext().getSystemService(ns);

        Notification notification = new Notification(R.drawable.logo, null,
                System.currentTimeMillis());

        RemoteViews notificationView = new RemoteViews(view.getContext().getPackageName(),
                R.layout.quick_remote);

        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(view.getContext(), ControlFrag1.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(view.getContext(), 0,
                notificationIntent, 0);

        notification.contentView = notificationView;
        notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;

        //this is the intent that is supposed to be called when the
        //button is clicked
        Intent switchIntent1 = new Intent(view.getContext(), switchButtonListener.class);
        switchIntent1.setAction("A");
        Intent switchIntent2 = new Intent(view.getContext(), switchButtonListener.class);
        switchIntent2.setAction("B");
        Intent switchIntent3 = new Intent(view.getContext(), switchButtonListener.class);
        switchIntent3.setAction("C");
        Intent switchIntent4 = new Intent(view.getContext(), switchButtonListener.class);
        switchIntent4.setAction("D");
        Intent switchIntent5 = new Intent(view.getContext(), switchButtonListener.class);
        switchIntent5.setAction("E");
//        Intent switchIntent6 = new Intent(view.getContext(), switchButtonListener.class);
//        switchIntent6.setAction("F");


        PendingIntent pendingSwitchIntent1 = PendingIntent.getBroadcast(view.getContext(), 0,
                switchIntent1, 0);
        PendingIntent pendingSwitchIntent2 = PendingIntent.getBroadcast(view.getContext(), 0,
                switchIntent2, 0);
        PendingIntent pendingSwitchIntent3 = PendingIntent.getBroadcast(view.getContext(), 0,
                switchIntent3, 0);
        PendingIntent pendingSwitchIntent4 = PendingIntent.getBroadcast(view.getContext(), 0,
                switchIntent4, 0);
        PendingIntent pendingSwitchIntent5 = PendingIntent.getBroadcast(view.getContext(), 0,
                switchIntent5, 0);
//        PendingIntent pendingSwitchIntent6 = PendingIntent.getBroadcast(view.getContext(), 0,
//                switchIntent6, 0);


        notificationView.setOnClickPendingIntent(R.id.bt_quick_tu,
                pendingSwitchIntent1);
        notificationView.setOnClickPendingIntent(R.id.bt_quick_td,
                pendingSwitchIntent2);
        notificationView.setOnClickPendingIntent(R.id.bt_quick_pu,
                pendingSwitchIntent3);
        notificationView.setOnClickPendingIntent(R.id.bt_quick_pd,
                pendingSwitchIntent4);
        notificationView.setOnClickPendingIntent(R.id.bt_quick_po,
                pendingSwitchIntent5);
////        notificationView.setOnClickPendingIntent(R.id.bt_quick_po_off,
////                pendingSwitchIntent6);


        notificationManager.notify(1, notification);

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }

    private void removeNotification() {

        if(isOnQuick==1)
            notificationManager.cancel(1);
    }

    public static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().compareTo("A")==0)
            {
                Intent i = new Intent("TAG_NOTI");
                i.putExtra("TAG",1);
                context.sendBroadcast(i);
            }
            else if(intent.getAction().compareTo("B")==0)
            {
                Intent i = new Intent("TAG_NOTI");
                i.putExtra("TAG",2);
                context.sendBroadcast(i);
            }
            else if(intent.getAction().compareTo("C")==0)
            {
                Intent i = new Intent("TAG_NOTI");
                i.putExtra("TAG",3);
                context.sendBroadcast(i);
            }
            else if(intent.getAction().compareTo("D")==0)
            {
                Intent i = new Intent("TAG_NOTI");
                i.putExtra("TAG",4);
                context.sendBroadcast(i);
            }
            else if(intent.getAction().compareTo("E")==0)
            {
                Intent i = new Intent("TAG_NOTI");
                i.putExtra("TAG",5);
                context.sendBroadcast(i);
            }
//            else if(intent.getAction().compareTo("F")==0)
//            {
//                Intent i = new Intent("TAG_NOTI");
//                i.putExtra("TAG",6);
//                context.sendBroadcast(i);
//            }



        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

//        if(isOn==true) {
//            PacketBuilder pb2 = new PacketBuilder(TAG.SEND_CURRENT_INFO2);
//            pb2.append(btName);
//            pb2.append(2);
////            pb.append(btA); //&&//poff
//            pb2.append(curTemper);
//            pb2.append(curPower);
//
//            PacketSender ps2 = new PacketSender(pb2.getPacket());
//            ps2.start();
//            try {
//                ps2.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        removeNotification();
        view.getContext().unregisterReceiver(broadcastReceiver);




    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tmpIntet = intent.getIntExtra("TAG",0);

            isToast=true;
            if(tmpIntet == 1)
            {
                remoteTUp();
            }
            else if(tmpIntet == 2)
            {
                remoteTDown();

            }
            else if(tmpIntet == 3)
            {
                remotePUp();

            }
            else if(tmpIntet == 4)
            {
                remotePDown();

            }
            else if(tmpIntet == 5)
            {

                sw_frag1_power.setChecked( !sw_frag1_power.isChecked() );
//                if(sw_frag1_power.isChecked()==true)
//                    powerOff(1);
//                else
//                    powerOn(1);
            }
//            else if(tmpIntet == 6)
//            {
//                powerOff();
//            }

        }
    };




}