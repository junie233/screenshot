package com.junie.screenshot.allscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.junie.screenshot.constants.ShotConstants;
import com.junie.screenshot.listener.ShotListener;
import com.junie.screenshot.util.FileUtil;
import com.junie.screenshot.util.ScreenUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by niejun on 2018/1/18.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenShotManager {

    private static final String TAG = ScreenShotManager.class.getSimpleName();

    private Context context;
    private ShotListener shotListener;
    private MediaProjection mMediaProjection;
    private ImageReader mImageReader;
    private VirtualDisplay mVirtualDisplay;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScreenShotManager(Context context, Intent intent, ShotListener shotListener) {
        this.context = context;
        this.shotListener =shotListener;
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        mMediaProjection = mediaProjectionManager.getMediaProjection(Activity.RESULT_OK,intent);
        mImageReader = ImageReader.newInstance(
                ScreenUtils.getScreenWidth(),
                ScreenUtils.getScreenHeight(),
                PixelFormat.RGBA_8888,//此处必须和下面 buffer处理一致的格式 ，RGB_565在一些机器上出现兼容问题。
                1);
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                ScreenUtils.getScreenWidth(),
                ScreenUtils.getScreenHeight(),
                Resources.getSystem().getDisplayMetrics().densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startScreenShot() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Image image = mImageReader.acquireLatestImage();
                                    SaveImage(image);
                                }
                            },300);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void SaveImage(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        //每个像素的间距
        int pixelStride = planes[0].getPixelStride();
        //总的间距
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height,
                Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        image.close();
        String saveUrl = null;
        File imageFile =  FileUtil.getImageFile(context,saveUrl);
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG,"bitmap null");
        }
        notifyShotFinished(imageFile.getPath());
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }


    private void notifyShotFinished(String path) {
        Log.d(TAG,"截图完成，path"+path);
        Intent intent = new Intent(ShotConstants.ScreenShotAction);
        intent.putExtra(ShotConstants.ShotType,ShotConstants.SHOT_AllSCREENT);
        intent.putExtra(ShotConstants.ShotPath,path);
        context.sendBroadcast(intent);
    }


    public void release() {

        if(mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }

        if(mImageReader != null) {
            mImageReader.close();
            mImageReader = null;
        }

        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
        }

        if (shotListener != null) {
            shotListener = null;
        }

    }


}
