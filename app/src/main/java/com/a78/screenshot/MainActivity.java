package com.a78.screenshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.junie.screenshot.ScreenShot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private Button screenShotBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenShotBtn = (Button)findViewById(R.id.screenshot_btn);
        screenShotBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.screenshot_btn:
                ScreenShot.shotAllScreen(this, "", new ScreenShot.ShotListener() {
                    @Override
                    public void onShotFinish(String path) {
                        Toast.makeText(MainActivity.this,"成功："+path,Toast.LENGTH_LONG).show();
                        Log.d(TAG,"截图完成："+path);
                    }

                    @Override
                    public void onError(String code, String msg) {

                    }
                });
        }
    }
}
