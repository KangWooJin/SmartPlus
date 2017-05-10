package com.smartplus.smartplus;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class ControlFrag3  extends Fragment {

    private Handler mHandler;
    private Runnable mRunnable;
    View view;
    ImageView iv_frag3_mic;
    ImageView iv_frag3_round;
    TextView tv_frag3_txt;
    String str="";



    static int isLived;

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

    boolean isToast = false;


    boolean isInit;

    int limited = 0;

    SpeechRecognizer recognizer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        isLived=1;
        arrC = new char[5];


        view = inflater.inflate(R.layout.activity_control_frag3, container, false);
        tv_frag3_txt = (TextView)view.findViewById(R.id.tv_frag3_txt);
        iv_frag3_round = (ImageView)view.findViewById(R.id.iv_frag3_round);
        iv_frag3_mic = (ImageView)view.findViewById(R.id.iv_frag3_mic);
        iv_frag3_mic.setImageDrawable(getResources().getDrawable(R.drawable.n_mic_off));
        iv_frag3_round.setVisibility(View.INVISIBLE);



        iv_frag3_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btClicked();


            }
        });


//
//        Weather w = new Weather();
//        tv.setText(w.getWeatherToday()+"\n"+w.getWeatherWeek());

        init();


        btClicked();

        getData();

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


        ///////////////////##전원여부##//////////////////
        //^^

//        PacketBuilder pb = new PacketBuilder(TAG.IS_ON);
//        pb.append(btName);
//
//        PacketSender ps = new PacketSender(pb.getPacket());
//        ps.start();
//
//        try {
//            ps.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        Log.i("ControlFag1", "is_on : " + ps.return_msg);

//        if( Integer.parseInt(ps.return_msg) == 1) { // 0519
////        if( true)
//            isOn = true;
//        }
//        else
//            isOn=false;

        //////////////////////////////////////////////////


        if(isOn) {



            powerOn();

        }
        else
        {
            powerOff();
        }



//        if(isOn) {
//
//            minTemper = sTi(tmpStrArr[1]);
//            maxTemper = sTi(tmpStrArr[2]);
//            minPower = sTi(tmpStrArr[3]);
//            maxPower = sTi(tmpStrArr[4]);
//
//            curPower = sTi(tmpStrArr[7]);
//            curTemper = sTi(tmpStrArr[6]);
//
//            tv_frag1_temper.setText(iTs(curTemper));
//            tv_frag1_power.setText(iTs(curPower));
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

    private void btClicked() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "org.androidtown.voice.recognizer");
        iv_frag3_mic.setVisibility(View.VISIBLE);
        iv_frag3_round.startAnimation(new CameraAnim());
        iv_frag3_round.setVisibility(View.VISIBLE);
        iv_frag3_mic.setImageDrawable(getResources().getDrawable(R.drawable.n_mic));
        tv_frag3_txt.setText("");
        str="";
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(isLived==1) {
                    iv_frag3_mic.setImageDrawable(getResources().getDrawable(R.drawable.n_mic_off));

                    iv_frag3_round.setVisibility(View.INVISIBLE);

                    analyStr();
                }
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 3500);

        recognizer.startListening(intent);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //do nothing, just let it tick
            }

            public void onFinish() {
                if(isLived==1)
                    recognizer.stopListening();
            }
        }.start();

    }

    void analyStr()
    {
        if(str.contains("켜줘") ||str.contains("켜주세요") ||str.contains("켜") || str.contains("틀어줘"))
        {
            powerOn();
            Toast.makeText(view.getContext(), "에어컨 전원을 가동합니다", Toast.LENGTH_SHORT).show();
        }
        else if(str.contains("꺼") || str.contains("꺼줘")|| str.contains("꺼주세요")|| str.contains("종료"))
        {
            powerOff();
            Toast.makeText(view.getContext(), "에어컨 전원을 종료합니다", Toast.LENGTH_SHORT).show();
        }
        else if(str.contains("온도") && (str.contains("올려")|| str.contains("업")|| str.contains("증가")|| str.contains("높혀")))
        {
            remoteTUp();
            Toast.makeText(view.getContext(), "에어컨 온도를 증가합니다", Toast.LENGTH_SHORT).show();
        }
        else if(str.contains("온도") && (str.contains("내려")|| str.contains("다운")|| str.contains("감소")|| str.contains("낮춰")))
        {
            remoteTDown();
            Toast.makeText(view.getContext(), "에어컨 온도를 감소합니다", Toast.LENGTH_SHORT).show();
        }
        else if(str.contains("파워") && (str.contains("올려")|| str.contains("업")|| str.contains("증가")|| str.contains("높혀")))
        {
            remotePUp();
            Toast.makeText(view.getContext(), "에어컨 파워를 증가합니다", Toast.LENGTH_SHORT).show();
        }
        else if(str.contains("파워") && (str.contains("내려")|| str.contains("다운")|| str.contains("감소")|| str.contains("낮춰")))
        {
            remotePDown();
            Toast.makeText(view.getContext(), "에어컨 파워를 감소합니다", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(view.getContext(), "발음이 좋지 않습니다", Toast.LENGTH_SHORT).show();
    }

    void powerOn()
    {
        isOn=true;
        floatToast("POWERON");

        Log.i("AAAAAAAA", "BBBBBBB");

        if(limited==1) {


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
//            pb.append(btA); //&&//poon
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
//
//
//            PacketBuilder pb2 = new PacketBuilder(TAG.SEND_CURRENT_INFO2);
//            pb2.append(btName);
//            pb2.append(1);
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


        /////////////##현제 에어컨 온도 및 파워##///////////////////

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
//
//
//        //^^
//        Log.i("ControlFrag1", "GET_SETTING : " + ps2.return_msg);
//
//        String[]tmpStr = ps2.return_msg.split("@"); // 0519

//        String []tmpStr = {"1","1","1","1","1","1","1"};

//        if( tmpStr[5].compareTo("1")==0)
//            isInit=true;
//        else
//            isInit=false;

        ////////////////////////////////////////////////////////////

//        Log.i("AAAAAA", tmpStr[5] + "  " + tmpStr[6]);
//
//        minTemper = sTi(tmpStr[0]);
//        maxTemper = sTi(tmpStr[1]);
//        minPower = sTi(tmpStr[2]);
//        maxPower = sTi(tmpStr[3]);

        minTemper = 22;//sTi(tmpStrArr[1]);
        maxTemper = 32;// sTi(tmpStrArr[2]);
        minPower = 1;//sTi(tmpStrArr[3]);
        maxPower = 10;//sTi(tmpStrArr[4]);

        curPower = 10;//sTi(tmpStrArr[7]);
        curTemper = 23;

//        SC.CURRENT_TEMPER = curTemper = sTi(tmpStr[5]);
//        SC.CURRENT_POWER = curPower = sTi(tmpStr[6]);

        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("제품이 가동되었습니다"), Toast.LENGTH_SHORT).show();
        }
    }

    void floatToast(String s)
    {
//        Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
    }

    void powerOff()
    {
        isOn=false;
        floatToast("POWEROFF");

        if(limited==1) {


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
//
//
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


        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("제품이 종료되었습니다"), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onDestroyView() {
        Log.d(this.getClass().getSimpleName(), "onDestroyView()");

        if(isOn==true) {
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
        }

        recognizer.cancel();
        recognizer.destroy();

        isLived=0;
        super.onDestroyView();
    }


    private void init() {

        recognizer = SpeechRecognizer.createSpeechRecognizer(view.getContext());
        recognizer.setRecognitionListener(new VoiceRecognitionListener());
    }

    private void stopRecognizer() {
        Log.d("Frag3", "stopRecognizer() called.");

        recognizer.stopListening();

    }


    private class VoiceRecognitionListener implements RecognitionListener {
        @Override
        public void onBeginningOfSpeech() {
//            Toast.makeText(view.getContext(), "말씀하세요", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {
//            Toast.makeText(view.getContext(), "인식이 끝났습니다.", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(int error) {
//            Toast.makeText(view.getContext(), "다시 시도하세요", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(view.getContext(), "말씀하세요", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onResults(Bundle results) {
            Log.d("ConrtrolFrag3", "onResults() called.");

            ArrayList<String> outStringList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (outStringList != null) {
                println("음성인식된 결과물 갯수 : " + outStringList.size());
                for (int i = 0; i < outStringList.size(); i++) {
                    String aItem = outStringList.get(i);
                    println("    결과물 #" + i + " : " + aItem);
                }
            }

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }
    }

    private void println(String msg) {
//        tv_frag3_txt.append(msg + "\n");
        str+=msg;
    }


    void remoteTDown()
    {

        floatToast("TD");

        curTemper--;
        if(curTemper==minTemper-1)
            curTemper++;
        {

            String tmpStr2 = String.valueOf( TAG.TEMPER_DOWN );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();



            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
        {

            String tmpStr2 = String.valueOf( TAG.TEMPER_UP );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

        {
            //##온도센서 한칸 올리기 ^^

            String tmpStr2 = String.valueOf( TAG.POWER_UP );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
        {


            String tmpStr2 = String.valueOf( TAG.POWER_DOWN );

            PacketSender ps = new PacketSender( tmpStr2 );//pb.getPacket());
            ps.start();

            try {
                ps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


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
        if(isToast)
        {
            isToast = false;
            Toast.makeText(view.getContext(), String.valueOf("현재 세기는 " + String.valueOf(curPower) + "입니다."), Toast.LENGTH_SHORT).show();
        }
    }










    int sTi(String s)
    {
        return Integer.parseInt(s);
    }
    String iTs(int s)
    {
        return String.valueOf(s);
    }


}

