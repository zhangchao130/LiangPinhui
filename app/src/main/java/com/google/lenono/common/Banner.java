package com.google.lenono.common;

/**
 * Created by Administrator on 2016/7/16.
 */
public class Banner {
    private String bannerUrl;
    private String image;
    private String alias;

    public Banner(String bannerUrl, String image, String alias) {
        this.bannerUrl = bannerUrl;
        this.image = image;
        this.alias = alias;
    }

    public Banner() {
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getImage() {
        return image;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerUrl='" + bannerUrl + '\'' +
                ", image='" + image + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
