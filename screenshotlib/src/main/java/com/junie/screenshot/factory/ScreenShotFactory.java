package com.junie.screenshot.factory;

import android.content.Context;

import com.junie.screenshot.handler.ShotHandler;

/**
 * Created by niejun on 2018/1/18.
 */

public class ScreenShotFactory {

    public static int SHOT_AllSCREENT = 0;
    public static int SHOT_VIEW = 1;

    private ShotHandler shotHandler;


    public ShotHandler getShotHandler(Context context, int shotType) {



        return shotHandler;
    }
}
