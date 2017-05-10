package com.smartplus.smartplus;

/**
 * Created by seose on 2015-05-16.
 */
public class AirOne {

    public int[] nums;
    public String name;

    AirOne(String name_, int tmin,int tmax,int pmin,int pmax, int isReflash,int tReflash, int pReflash){

        nums = new int[7];
        nums[0] = tmin;
        nums[1] = tmax;
        nums[2] = pmin;
        nums[3] = pmax;
        nums[4] = isReflash;
        nums[5] = tReflash;
        nums[6] = pReflash;

        name = name_;
    }
}
