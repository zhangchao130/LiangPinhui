package com.google.lenono.common;

/**
 * Created by lenono on 2016-07-28.
 */
public class ViewGoods {
    private String cover;
    private String goods_name;
    private String sell_price_cn;
    private String ori_price_cn;
    private String mall_logo;
    private String id;
    private String jsonUrl;
    private String brand_name;

    @Override
    public String toString() {
        return "ViewGoods{" +
                "cover='" + cover + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", sell_price_cn='" + sell_price_cn + '\'' +
                ", ori_price_cn='" + ori_price_cn + '\'' +
                ", mall_logo='" + mall_logo + '\'' +
                ", id='" + id + '\'' +
                ", jsonUrl='" + jsonUrl + '\'' +
                ", brand_name='" + brand_name + '\'' +
                '}';
    }

    public ViewGoods() {
    }

    public ViewGoods(String cover, String goods_name, String sell_price_cn, String ori_price_cn, String mall_logo, String id, String jsonUrl, String brand_name) {
        this.cover = cover;
        this.goods_name = goods_name;
        this.sell_price_cn = sell_price_cn;
        this.ori_price_cn = ori_price_cn;
        this.mall_logo = mall_logo;
        this.id = id;
        this.jsonUrl = jsonUrl;
        this.brand_name = brand_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSell_price_cn() {
        return sell_price_cn;
    }

    public void setSell_price_cn(String sell_price_cn) {
        this.sell_price_cn = sell_price_cn;
    }

    public String getOri_price_cn() {
        return ori_price_cn;
    }

    public void setOri_price_cn(String ori_price_cn) {
        this.ori_price_cn = ori_price_cn;
    }

    public String getMall_logo() {
        return mall_logo;
    }

    public void setMall_logo(String mall_logo) {
        this.mall_logo = mall_logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
}
