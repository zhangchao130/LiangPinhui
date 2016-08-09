package com.google.lenono.common.goodDetail_model;

/**
 * Created by Administrator on 2016/7/22.
 */
public class Brand {
    private String code;  //品牌id
    private String name;   //品牌名
    private String logo_url;   //品牌图片地址
    private String description;   //品牌描述

    public Brand(String code, String name, String logo_url, String description) {
        this.code = code;
        this.name = name;
        this.logo_url = logo_url;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
