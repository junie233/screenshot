package com.junie.screenshot.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.junie.screenshot.ScreenShot;
import com.junie.screenshot.allscreen.ScreenShotActivity;
import com.junie.screenshot.allscreen.ScreenShotLow;
import com.junie.screenshot.constants.ShotConstants;

/**
 * Created by niejun on 2018/1/18.
 */

public class ScreenShotHandler{

    private String TAG = ScreenShotHandler.class.getSimpleName();
    private ScreenShot.ShotListener shotListener;
    private ScreenShotReceiver screenShotReceiver;
    private ScreenShotLow screenShotLow = new ScreenShotLow();
    private ViewShotHandler viewShotHandler = new ViewShotHandler();


    public static final int REQUEST_MEDIA_PROJECTION = 10001;
    public Context context;


    public void startShotAllScreen(Context context,ScreenShot.ShotListener listener) {
        this.context = context;
        this.shotListener = listener;

        if( Build.VERSION.SDK_INT < 21) {
            if(context == null) {
                return;
            }
            if(screenShotReceiver == null) {
                screenShotReceiver = new ScreenShotReceiver();
                context.registerReceiver(screenShotReceiver, new IntentFilter(ShotConstants.ScreenShotAction));
            }
            Intent intent = new Intent(context, ScreenShotActivity.class);
            context.startActivity(intent);
        } else {
            Log.d(TAG,"5.0以下直接截屏，没有状态栏");
            screenShotLow.startShot(context,listener);
        }
    }


    public void startShotView(Context context, View view , ScreenShot.ShotListener listener) {
        if(view == null || context == null ){
            return;
        }
        viewShotHandler.startShot(context,view,listener);
    }




    private class ScreenShotReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null && !TextUtils.isEmpty(intent.getAction())
                    && intent.getAction().equals(ShotConstants.ScreenShotAction)) {

                Log.d(TAG,"完成截图");
                int type = intent.getIntExtra(ShotConstants.ShotType,0);
                String path = intent.getStringExtra(ShotConstants.ShotPath);
                switch (type) {
                    case ShotConstants.SHOT_AllSCREENT:
                        Log.d(TAG,"截取全屏截图");
                        shotListener.onShotFinish(path);
                        break;


                }
            }
        }
    }


}
