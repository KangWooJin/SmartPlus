package com.smartplus.smartplus;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by 11 on 2016-05-11.
 */
public class PacketSender extends Thread {
    public static String serverIP = SC.tmpIP; // ex: 192.168.0.100
    public static int serverPort = SC.tmpPORT; // ex: 5555
    private String msg;
    public String return_msg="";
    String TAG  = "PacketSender";

    public PacketSender(){}
    public PacketSender(String _msg){

        Log.i("MY SEND THIS"  , _msg);
        this.msg = _msg;
    }
    public PacketSender(Inet4Address inet4Address, int serverPort){
        this.serverIP = inet4Address.toString();
        this.serverPort = serverPort;
    }
    public void setMsg(String _msg){
        this.msg = _msg;
    }

    @Override
    public void run() {

        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            Socket socket = new Socket(serverAddr, serverPort);
                try {


                PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);


                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println(msg);
                out.flush();

                return_msg = in.readLine();

//                while(( return_msg = in.readLine() ) != null ) {
//                    Log.i(TAG,"MSG 수신");
//                    break;
//                }

            } catch(Exception e) {
                Log.e(TAG,  e.toString());
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}