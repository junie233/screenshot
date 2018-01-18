package com.junie.screenshot.util;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by niejun on 2018/1/18.
 */

public class FileUtil {


    public static File getImageFile(Context context,String saveUrl){

        if (TextUtils.isEmpty(saveUrl)) {
            saveUrl = context.getExternalFilesDir("screenshot").getAbsoluteFile()
                    +
                    "/"
                    +
                    SystemClock.currentThreadTimeMillis() + ".png";
        }
        File imageFile = new File(saveUrl);

        if (!imageFile.exists()) {
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageFile;
    }




}
