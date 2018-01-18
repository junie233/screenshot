package com.junie.screenshot.allscreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.junie.screenshot.ScreenShot;
import com.junie.screenshot.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by niejun on 2018/1/18.
 */

public class ScreenShotLow {

    private static final String TAG = ScreenShotLow.class.getSimpleName();

    public void startShot(Context context, ScreenShot.ShotListener listener) {
        if(! (context instanceof Activity)) {
            listener.onError("","");
            return;
        }
        Activity activity = (Activity)context;
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                File file = FileUtil.getImageFile(activity,"");

                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                Log.d("a7888", "截图完成: "+file.getPath());
                listener.onShotFinish(file.getPath());
            } catch (Exception e) {
                Log.e(TAG,"",e);
            }
        }


    }
}
