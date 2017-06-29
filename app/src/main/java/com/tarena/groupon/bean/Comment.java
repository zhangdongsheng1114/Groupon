package com.tarena.groupon.bean;

import java.util.Arrays;

/**
 * Created by tarena on 2017/6/27.
 */

public class Comment {

    String avatar; // 头像
    String name; // 网名
    String date; // 发布评论的日期
    String rating; // 评分
    String price; // 价格
    String content; // 评论的正文
    String[] imgs; // 配图（最多就选三张）

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCntent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", rating='" + rating + '\'' +
                ", price='" + price + '\'' +
                ", cntent='" + content + '\'' +
                ", imgs=" + Arrays.toString(imgs) +
                '}';
    }
}
