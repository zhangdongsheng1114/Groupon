package com.tarena.groupon.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tarena.groupon.R;

import butterknife.BindView;


public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.vp_man)
    ViewPager viewPager;
    My
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }
}
