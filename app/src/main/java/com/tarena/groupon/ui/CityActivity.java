package com.tarena.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.tarena.groupon.R;
import com.tarena.groupon.adapter.CityAdapter;
import com.tarena.groupon.app.MyApp;
import com.tarena.groupon.bean.CityBean;
import com.tarena.groupon.bean.CitynameBean;
import com.tarena.groupon.util.DBUtil;
import com.tarena.groupon.util.HttpUtil;
import com.tarena.groupon.util.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tarena on 2017/6/16.
 */

public class CityActivity extends Activity {

    @BindView(R.id.rv_city_cities)
    RecyclerView recyclerView;
    // 适配器
    CityAdapter adapter;
    // 数据源
    List<CitynameBean> datas;

    DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        dbUtil = new DBUtil(this);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        // 初始化数据源，适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        datas = new ArrayList<>();
        adapter = new CityAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_list_cities, recyclerView, false);
        adapter.addHeaderView(headerView);
        adapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                CitynameBean citynameBean = datas.get(position);
//                Toast.makeText(CityActivity.this, citynameBean.getCityName(), Toast.LENGTH_SHORT).show();
                String city = citynameBean.getCityName();
                Intent data = new Intent();
                data.putExtra("city", city);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        // 从内存缓存中读取城市数据
        if (MyApp.citynameBeenList != null && MyApp.citynameBeenList.size() > 0) {
            adapter.addAll(MyApp.citynameBeenList, true);
            Log.d("TAG", "城市数据从内存中缓存中加载: ");
            return;
        }

        // 从数据库中读取城市数据
        List<CitynameBean> list = dbUtil.query();
        if (list != null && list.size() > 0) {
            adapter.addAll(list, true);

            MyApp.citynameBeenList = list;
            Log.d("TAG", "城市数据从数据库中加载: ");
            return;
        }

        // 调用HttpUtil获取城市信息
        HttpUtil.getCitiesByRetrofit(new Callback<CityBean>() {
            @Override
            public void onResponse(Call<CityBean> call, Response<CityBean> response) {
                CityBean cityBean = response.body();
                //"全国，上海，杭州，北京，其它城市..."
                List<String> list = cityBean.getCities();
                // 根据List<String> 创建一个List<CitynameBean>
                // 将List<CitynameBean>放到RecyclerView中显示
                final List<CitynameBean> citynameBeanList = new ArrayList<CitynameBean>();
                for (String name : list) {
                    if (!name.equals("全国") && !name.equals("其它城市") && !name.equals("点评实验室")) {
                        CitynameBean citynameBean = new CitynameBean();
                        citynameBean.setCityName(name);
                        citynameBean.setPyName(PinYinUtil.getPinYin(name));
                        citynameBean.setLetter(PinYinUtil.getLetter(name));
                        citynameBeanList.add(citynameBean);
                    }
                }

                Collections.sort(citynameBeanList, new Comparator<CitynameBean>() {
                    @Override
                    public int compare(CitynameBean citynameBean, CitynameBean t1) {
                        return citynameBean.getPyName().compareTo(t1.getPyName());
                    }
                });

                adapter.addAll(citynameBeanList, true);
                Log.d("TAG", "城市名称数据从网络中加载: ");
                // 将数据缓存起来
                MyApp.citynameBeenList = citynameBeanList;
                // 向数据库中写入城市数据
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        long start = System.currentTimeMillis();
                        dbUtil.insertBatch(citynameBeanList);
                        Log.d("TAG", "写入数据完毕，耗时: " + (System.currentTimeMillis() - start));
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable throwable) {

            }
        });
    }

    @OnClick(R.id.tv_city_search)
    public void jumpTo(View view) {
        Intent intent = new Intent(CityActivity.this, SearchActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            // data中取出搜索后点击的城市名称
//            Intent data2 = new Intent();
//            String city = data.getStringExtra("city");
//            data2.putExtra("city",city);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
