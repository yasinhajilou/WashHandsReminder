package com.yasinhajiloo.washhandsreminder2.utils;

import com.yasinhajiloo.washhandsreminder2.constants.TimeConstants;

public class TimeDefinerString {
    private static String halfHour = "هر 30 دقیقه";
    private static String anHour = "هر 1 ساعت";
    private static String اanHourAndHalf = "هر 1 ساعت و نیم ";
    private static String twoHour = "هر 2 ساعت";
    private static String threeHour = "هر 3 ساعت";
    private static String noTime = "یادآوری تنظیم نشده";

    public static String getTimeDefiner(long time){
        if (time == TimeConstants.HALF_HOUR)
            return halfHour;
        if (time == TimeConstants.ONE_HOUR)
            return anHour;
        if (time == TimeConstants.ONE_AND_HALF_HOUR)
            return اanHourAndHalf;
        if (time == TimeConstants.TWO_HOUR)
            return twoHour;
        if (time == TimeConstants.THREE_HOUR)
            return threeHour;

        return noTime;
    }
}
