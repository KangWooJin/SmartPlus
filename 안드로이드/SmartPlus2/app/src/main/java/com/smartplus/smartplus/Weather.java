package com.smartplus.smartplus;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by seose on 2015-05-19.
 */
public class Weather {

    String w1;
    String w2;
    String w3;
    String w4;
    String w5;
    String w6;
    String w7;

    public String getWeathehr()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        w1="맑음@11@26@"+ (cal.get(Calendar.MONTH)+1) +"@"+(cal.get(Calendar.DATE));
        w2="흐림@16@27@"+(cal.get(Calendar.MONTH)+1)+"@"+(cal.get(Calendar.DATE)+1);
        w3="맑음@17@29@"+(cal.get(Calendar.MONTH)+1)+"@"+(cal.get(Calendar.DATE)+2);
        w4="맑음@17@29@"+(cal.get(Calendar.MONTH)+1)+"@"+(cal.get(Calendar.DATE)+3);
        w5="흐림@18@29@"+(cal.get(Calendar.MONTH)+1)+"@"+(cal.get(Calendar.DATE)+4);
        w6="흐림@18@28@"+(cal.get(Calendar.MONTH)+1)+"@"+(cal.get(Calendar.DATE)+5);
        w7="흐림@16@23@"+(cal.get(Calendar.MONTH)+1)+"@"+(cal.get(Calendar.DATE)+6);

        String rst = w1+"@"+w2+"@"+w3+"@"+w4+"@"+w5+"@"+w6+"@"+w7;

        return rst;

    }


}
