package whereit.com.ratingapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by Desenvolvimento on 02/06/17.
 */

public class RateSPManager {

    private static final String LAUNCH_TIMES_KEY = "launch_times_key";
    private static final int LAUNCH_TIMES = 3;
    private static final String TIME_KEY = "time_key";
    private static final int DAYS_DELAY = 3 * (24*60*60*1000);



    private static SharedPreferences getSP(Context context)
    {
        return context.getSharedPreferences("preferences",Context.MODE_PRIVATE);

    }

    public static void updateLaunchTimes(Context context)
    {
        SharedPreferences sp = getSP(context);
        sp.edit().putInt(LAUNCH_TIMES_KEY,0).apply();
    }

    public static void updateLaunchTimes(Context context, Bundle savedInstanceState)
    {
        if(savedInstanceState !=null)
            return;
        SharedPreferences sp = getSP(context);
        int launchTimes = sp.getInt(LAUNCH_TIMES_KEY,0);
        sp.edit().putInt(LAUNCH_TIMES_KEY,launchTimes++).apply();

    }

    public static void updateTime(Context context)
    {
        SharedPreferences sp = getSP(context);
        sp.edit().putLong(TIME_KEY,System.currentTimeMillis() + DAYS_DELAY).apply();

    }

    private static boolean isLaunchTimesValid(Context context)
    {
        SharedPreferences sp = getSP(context);
        int launchTimes = sp.getInt(LAUNCH_TIMES_KEY,0);

        return launchTimes > 0 && launchTimes % LAUNCH_TIMES == 0;
    }

    private static boolean isTimeValid(Context context)
    {
        SharedPreferences sp = getSP(context);
        Long time = sp.getLong(TIME_KEY,0);

        if(time == 0)
        {
            updateTime(context);
            time = sp.getLong(TIME_KEY,0);
        }
        return time < System.currentTimeMillis();
    }



}
