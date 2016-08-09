package com.google.lenono.common;

/**
 * Created by Administrator on 2016/7/16.
 */
public class Recommend {
    private String title;  //今日推荐
    private String icon;  //今日推荐前的小图标，以上这俩其实可以不要

    private String goods_id;
    private String price;
    private String recommendName;
    private String image;
    private String recommendUrl;

    public Recommend() {
    }

    public Recommend(String title, String icon, String goods_id, String price, String recommendName, String image, String recommendUrl) {
        this.title = title;
        this.icon = icon;
        this.goods_id = goods_id;
        this.price = price;
        this.recommendName = recommendName;
        this.image = image;
        this.recommendUrl = recommendUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    @Override
    public String toString() {
        return "Recommend{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", price='" + price + '\'' +
                ", recommendName='" + recommendName + '\'' +
                ", image='" + image + '\'' +
                ", recommendUrl='" + recommendUrl + '\'' +
                '}';
    }
}
