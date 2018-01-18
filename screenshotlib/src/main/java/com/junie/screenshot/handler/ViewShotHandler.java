package com.junie.screenshot.handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.junie.screenshot.ScreenShot;

/**
 * Created by niejun on 2018/1/18.
 */

public class ViewShotHandler{

    private View view;
    private Context context;
    private ScreenShot.ShotListener listener;


    public void startShot(Context context, View view , ScreenShot.ShotListener listener) {
        if(view == null) {
            return;
        }
        this.context = context;
        this.view = view;
        this.listener = listener;

        if( view instanceof ListView) {
            handleListView(view);
        } else if(view instanceof ScrollView) {
            handleScrollView(view);
        } else if(view instanceof WebView) {
            handleWebView(view);
        } else {
            handleOtherView();
        }





    }

    private void handleOtherView() {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        String path = saveBitmap(bitmap);
        listener.onShotFinish(path);
    }

    private String saveBitmap(Bitmap bitmap) {

        bitmap.recycle();
        bitmap = null;
        return null;
    }

    private void handleWebView(View view) {
        if(!(view instanceof WebView)) {
            return;
        }
        WebView webView = (WebView)view;
        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        String path = saveBitmap(bmp);
        listener.onShotFinish(path);
    }

    private void handleScrollView(View view) {
        if(!(view instanceof ScrollView)) {
            return;
        }
        ScrollView scrollView = (ScrollView)view;
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        String path = saveBitmap(bitmap);
        listener.onShotFinish(path);
    }



    //fixme 跟scrollview 一样？？
    private void handleListView(View view) {
        if(!(view instanceof ListView)) {
            return;
        }
        ListView listView = (ListView)view;
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < listView.getChildCount(); i++) {
            h += listView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        listView.draw(canvas);
        String path = saveBitmap(bitmap);
        listener.onShotFinish(path);
    }


}
