package com.junie.screenshot;

import android.content.Context;
import android.view.View;

import com.junie.screenshot.handler.ScreenShotHandler;

import java.util.Queue;

/**
 * Created by niejun on 2018/1/18.
 */

public class ScreenShot {

    private static String TAG = ScreenShot.class.getSimpleName();
    private static ShotListener shotListener;
    private static ScreenShotHandler screenShotHandler = new ScreenShotHandler();



    /**
     * 截取全屏
     * @param context
     * @return
     */
    public static void shotAllScreen(Context context,String path, ShotListener listener) {
        screenShotHandler.startShotAllScreen(context,listener);
    }
    public final Queue queue = null;

    /**
     * 截取某个View
     * @param context
     * @return
     */
    public static void  shotView(Context context,View view, ShotListener listener) {
        //可能是普通view，webview，ScrollView，ListView
        String path = null;
        screenShotHandler.startShotView(context,view,listener);
    }



    public interface ShotListener {

        void onShotFinish(String path);

        void onError(String code,String msg);

    }





}
