package com.bryan.example.utils;

import android.R.integer;

public class MathUtil
{
    /**
     * 计算两点间的角度
     */
    public static int getDegreesIntTwoDots(float x0,float y0,float x1 , float y1){
        //计算两根手指间的角度
        float deltaX = x0 -x1;
        float deltaY = y0 - y1;
        //弧度
        double radians = Math.atan(deltaX/deltaY);
        //转换成角度
        int degrees = (int) (radians * 180 /Math.PI);
        return degrees;
    }

}
