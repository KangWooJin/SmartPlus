package com.smartplus.smartplus;

/**
 * Created by seose on 2015-05-17.
 */
public class ListOne {

    String btname;
    String titlename;
    int []temper2power2option3;
    char []bts;

    public ListOne()
    {
        titlename = "NONE";
    }

    public ListOne(int i)
    {
        titlename = "전구";
    }

    public ListOne(String btname_,String titlename_,int []temper2power2option3_, char []bts_)
    {
        btname=btname_;
        titlename=titlename_;

        if(SC.IS_LIGHT==true)
            return;

        temper2power2option3= new int[7];


        for(int i = 0 ; i < 7  ; i ++)
        {
            temper2power2option3[i] = temper2power2option3_[i];
        }

        bts = new char[5];

        for(int i = 0 ; i < 5 ; i ++)
        {
            bts[i] = bts_[i];
        }
    }


}
