package com.google.lenono.common;

/**
 * Created by Administrator on 2016/7/16.
 */
public class Dot {
    private String alias;
    private String dotUrl;
    private String image;
    private String title;

    public Dot(String alias, String dotUrl, String image, String title) {
        this.alias = alias;
        this.dotUrl = dotUrl;
        this.image = image;
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDotUrl() {
        return dotUrl;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setDotUrl(String dotUrl) {
        this.dotUrl = dotUrl;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "alias='" + alias + '\'' +
                ", dotUrl='" + dotUrl + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
