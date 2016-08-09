package com.google.lenono.common;

/**
 * Created by Administrator on 2016/7/16.
 */
public class FlashSale {
    private String title;  //限时活动
    private String icon;   //限时活动前的小图标,以上俩可以不要

    private String goods_id;  //goods_id为11的连接是个webView
    private String subAction;
    private String image;
    private String begin;
    private String end;
    private String flashSaleUrl;

    public FlashSale() {
    }

    public FlashSale(String title, String icon, String goods_id, String subAction, String image, String begin, String end, String flashSaleUrl) {
        this.title = title;
        this.icon = icon;
        this.goods_id = goods_id;
        this.subAction = subAction;
        this.image = image;
        this.begin = begin;
        this.end = end;
        this.flashSaleUrl = flashSaleUrl;
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

    public String getSubAction() {
        return subAction;
    }

    public void setSubAction(String subAction) {
        this.subAction = subAction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFlashSaleUrl() {
        return flashSaleUrl;
    }

    public void setFlashSaleUrl(String flashSaleUrl) {
        this.flashSaleUrl = flashSaleUrl;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    @Override
    public String toString() {
        return "FlashSale{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", subAction='" + subAction + '\'' +
                ", image='" + image + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", flashSaleUrl='" + flashSaleUrl + '\'' +
                '}';
    }
}
