package com.smartplus.smartplus;

import java.util.ArrayList;

/**
 * Created by seose on 2015-05-16.
 */
public class AirList {

    public ArrayList<AirOne> airs;

    public AirList()
    {
        airs = new ArrayList<AirOne>();
        airs.add(new AirOne("CUSTOM"                 ,0,0,0,0,0,0,0));
        airs.add(new AirOne("AF15HVZQ1WK"  ,18,28,1,10,1,22,5));      /* ,22,32,1,10,1,27,5));*/
        airs.add(new AirOne("AF15FVVQ1WK"  ,18,28,1,10,1,22,5));      /* ,18,28,1,10,1,22,5));*/
        airs.add(new AirOne("AF12FCZZ1EH"  ,18,28,1,10,1,22,5));      /* ,25,35,1,3,1,26,2));*/
        airs.add(new AirOne("AF-23FVAM1EE" ,18,28,1,10,1,22,5));      /*  ,22,32,1,5,1,25,3));*/
        airs.add(new AirOne("AF16J7970WFK" ,18,28,1,10,1,22,5));      /*  ,15,35,1,10,1,25,5));*/



    }



}
