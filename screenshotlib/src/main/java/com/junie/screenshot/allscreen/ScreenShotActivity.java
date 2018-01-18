package com.junie.screenshot.allscreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Window;

import com.junie.screenshot.listener.ShotListener;

/**
 * Created by niejun on 2018/1/18.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenShotActivity extends Activity{

    private static String TAG = ScreenShotActivity.class.getSimpleName();
    public static final int REQUEST_MEDIA_PROJECTION = 10001;
    private ScreenShotManager shotManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setDimAmount(0f);
        requestScreenShot();
    }

    public void requestScreenShot() {
        Log.d(TAG,"请求权限");
        startActivityForResult(
                ((MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE)).createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION
        );
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {
                if (resultCode == -1 && data != null) {
                     shotManager = new ScreenShotManager(ScreenShotActivity.this,data,new ShotListener() {
                        @Override
                        public void onShotFinish() {

                        }
                    });
                    shotManager.startScreenShot();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(shotManager != null) {
            shotManager.release();
            shotManager = null;
        }
    }
}
