package com.tarena.groupon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.tarena.groupon.R;
import com.tarena.groupon.app.MyApp;
import com.tarena.groupon.bean.BusinessBean;
import com.tarena.groupon.util.DistanceUtil;
import com.tarena.groupon.util.HttpUtil;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tarena on 2017/6/26.
 */

public class BusinessAdapter extends MyBaseAdapter<BusinessBean.Business> {
    public BusinessAdapter(Context context, List<BusinessBean.Business> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        BusinessAdapter.ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_busines_layout, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BusinessBean.Business item = getItem(i);
        HttpUtil.loadImage(item.getPhoto_url(), viewHolder.ivPic);
        String name = item.getName().substring(0, item.getName().indexOf("("));
        if (!TextUtils.isEmpty(item.getBranch_name())) {
            name = name + "(" + item.getBranch_name() + ")";
        }
        viewHolder.tvName.setText(name);

        int[] stars = new int[]{R.drawable.movie_star10, R.drawable.movie_star20,
                R.drawable.movie_star30, R.drawable.movie_star35,
                R.drawable.movie_star40, R.drawable.movie_star45, R.drawable.movie_star50};
        Random random = new Random();
        int idx = random.nextInt(7);
        viewHolder.ivRating.setImageResource(stars[idx]);

        int price = random.nextInt(100) + 50;
        viewHolder.tvPrice.setText("￥" + price + "/人");
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < item.getRegions().size(); j++) {
            if (j == 0) {
                sb.append(item.getRegions().get(j));
            } else {
                sb.append("/").append(item.getRegions().get(j));
            }
        }

        sb.append(" ");

        for (int j = 0; j < item.getCategories().size(); j++) {
            if (j == 0) {
                sb.append(item.getCategories().get(j));
            } else {
                sb.append("/").append(item.getCategories().get(j));
            }
        }
        viewHolder.tvInfo.setText(sb.toString());


        if (MyApp.myLocation != null) {
//            double distance = DistanceUtil.getDistance(item.getLongitude(), item.getLatitude(),
//                    MyApp.myLocation.longitude, MyApp.myLocation.latitude);
            double distance = DistanceUtil.getDistance(new LatLng(item.getLatitude(),item.getLatitude()),MyApp.myLocation);
            viewHolder.tvDistance.setText(distance+"米");
        } else {
            viewHolder.tvDistance.setText("");
        }

        return view;
    }

    public class ViewHolder {
        @BindView(R.id.iv_business_item)
        ImageView ivPic;
        @BindView(R.id.tv_business_item_name)
        TextView tvName;
        @BindView(R.id.iv_business_item_rating)
        ImageView ivRating;
        @BindView(R.id.tv_business_item_avg_price)
        TextView tvPrice;
        @BindView(R.id.tv_business_item_info)
        TextView tvInfo;
        @BindView(R.id.tv_business_item_distance)
        TextView tvDistance;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
