package com.google.lenono.common.goodDetail_model;

/**
 * Created by Administrator on 2016/7/22.
 */
public class From_user {
    private String nickname;  //商户名
    private String id;   //进入商家页面所需id：http://uuyichu.com/api/goods/user_v4/?user_id=<id>&page=1
    private String authenticated;  //是否认证
    private String publish_count;   //已发布商品数
    private String sold_count;   //已售商品数
    private String headimg;  //头像图片
    private String user_url;//商家地址

    public From_user(String nickname, String id, String authenticated, String publish_count, String sold_count, String headimg, String user_url) {
        this.nickname = nickname;
        this.id = id;
        this.authenticated = authenticated;
        this.publish_count = publish_count;
        this.sold_count = sold_count;
        this.headimg = headimg;
        this.user_url = user_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(String authenticated) {
        this.authenticated = authenticated;
    }

    public String getPublish_count() {
        return publish_count;
    }

    public void setPublish_count(String publish_count) {
        this.publish_count = publish_count;
    }

    public String getSold_count() {
        return sold_count;
    }

    public void setSold_count(String sold_count) {
        this.sold_count = sold_count;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    @Override
    public String toString() {
        return "From_user{" +
                "nickname='" + nickname + '\'' +
                ", id='" + id + '\'' +
                ", authenticated='" + authenticated + '\'' +
                ", publish_count='" + publish_count + '\'' +
                ", sold_count='" + sold_count + '\'' +
                ", headimg='" + headimg + '\'' +
                ", user_url='" + user_url + '\'' +
                '}';
    }
}
