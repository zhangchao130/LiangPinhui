package com.google.lenono.common.goodDetail_model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class GoodDetail {   //这个类不需要建表
    private String good_id;
    private String original_price;
    private List<HashMap<String,String>> detail_params;   //商品概述
    private String[] goods_pics;    //浏览图片
    private String sell_price;
    private Brand brand;
    private String purity_desc; //商品概述前语
    private String publisher_desc;   //卖家寄语
    private String img_h;   //图片尺寸
    private String img_w;
    private String wx_kefu;  //客服微信
    private String goods_name;   //商品名
    private From_user user;  //商家信息
    private String is_onsale;  //是否在售
    private String condition_dis;  //成色

    public GoodDetail(String good_id, String original_price, List<HashMap<String,String>> detail_params, String[] goods_pics, String sell_price, Brand brand, String purity_desc, String publisher_desc, String img_h, String img_w, String wx_kefu, String goods_name, From_user user, String is_onsale, String condition_dis) {
        this.good_id = good_id;
        this.original_price = original_price;
        this.detail_params = detail_params;
        this.goods_pics = goods_pics;
        this.sell_price = sell_price;
        this.brand = brand;
        this.purity_desc = purity_desc;
        this.publisher_desc = publisher_desc;
        this.img_h = img_h;
        this.img_w = img_w;
        this.wx_kefu = wx_kefu;
        this.goods_name = goods_name;
        this.user = user;
        this.is_onsale = is_onsale;
        this.condition_dis = condition_dis;
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public List<HashMap<String, String>> getDetail_params() {
        return detail_params;
    }

    public void setDetail_params(List<HashMap<String, String>> detail_params) {
        this.detail_params = detail_params;
    }

    public String[] getGoods_pics() {
        return goods_pics;
    }

    public void setGoods_pics(String[] goods_pics) {
        this.goods_pics = goods_pics;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getPurity_desc() {
        return purity_desc;
    }

    public void setPurity_desc(String purity_desc) {
        this.purity_desc = purity_desc;
    }

    public String getPublisher_desc() {
        return publisher_desc;
    }

    public void setPublisher_desc(String publisher_desc) {
        this.publisher_desc = publisher_desc;
    }

    public String getImg_h() {
        return img_h;
    }

    public void setImg_h(String img_h) {
        this.img_h = img_h;
    }

    public String getImg_w() {
        return img_w;
    }

    public void setImg_w(String img_w) {
        this.img_w = img_w;
    }

    public String getWx_kefu() {
        return wx_kefu;
    }

    public void setWx_kefu(String wx_kefu) {
        this.wx_kefu = wx_kefu;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public From_user getUser() {
        return user;
    }

    public void setUser(From_user user) {
        this.user = user;
    }

    public String getCondition_dis() {
        return condition_dis;
    }

    public String getIs_onsale() {
        return is_onsale;
    }

    public void setIs_onsale(String is_onsale) {
        this.is_onsale = is_onsale;
    }

    public void setCondition_dis(String condition_dis) {
        this.condition_dis = condition_dis;
    }

    @Override
    public String toString() {
        return "GoodDetail{" +
                "good_id='" + good_id + '\'' +
                ", original_price='" + original_price + '\'' +
                ", detail_params=" + detail_params +
                ", goods_pics=" + Arrays.toString(goods_pics) +
                ", sell_price='" + sell_price + '\'' +
                ", brand=" + brand +
                ", purity_desc='" + purity_desc + '\'' +
                ", publisher_desc='" + publisher_desc + '\'' +
                ", img_h='" + img_h + '\'' +
                ", img_w='" + img_w + '\'' +
                ", wx_kefu='" + wx_kefu + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", user=" + user +
                ", is_onsale='" + is_onsale + '\'' +
                ", condition_dis='" + condition_dis + '\'' +
                '}';
    }

    public String getDiscount(int type) {
        String content = null;
        int original = Integer.parseInt(original_price);
        int sell = Integer.parseInt(sell_price);
        int save = original - sell;
        int discount = save * 100 / original;
        switch (type) {
            case 1:   //标题下打了几折
                float saveDis = (100 - discount) / 10;
                content = saveDis + "折";
                break;
            case 2:   //标题下省了多少钱
                content = "省￥" + save;
                break;
            case 3:   //正下方节省百分比
                content = "节省了" + discount + "%";
                break;
        }

        return content;
    }

}
