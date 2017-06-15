package com.tarena.groupon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tarena.groupon.R;
import com.tarena.groupon.util.SPUtil;

public class SplashActivity extends AppCompatActivity {
    SPUtil spUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spUtil = new SPUtil(this);
        // 界面停留几秒钟
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 读取偏好设置文件中的值
                // 根据是否是第一次使用进行相应的界面跳转
                Intent intent;
                // spUtil.isFirst()
                if (true) {
                    //向新手指导页跳转
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    spUtil.setFirst(false);
                } else {
                    // 向主页面跳转
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
