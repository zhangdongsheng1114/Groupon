package com.tarena.groupon.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tarena.groupon.R;

public class BusinesActivity extends AppCompatActivity {

    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busines);
        city = getIntent().getStringExtra("city");
    }
}
