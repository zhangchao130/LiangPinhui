package com.google.lenono.common;

/**
 * Created by Administrator on 2016/7/17.
 */
public class Album {
    private String title;
    private String subTitle;
    private String tag;
    private String tag_bg;
    private String album_image;
    private String albumUrl;
    private String album_action_alias;

    private String goods_id;
    private String price;
    private String name;
    private String image;
    private String itemUrl;

    public Album() {
    }

    public Album(String title, String subTitle, String tag, String tag_bg, String album_image, String albumUrl, String album_action_alias, String goods_id, String price, String name, String image, String itemUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.tag = tag;
        this.tag_bg = tag_bg;
        this.album_image = album_image;
        this.albumUrl = albumUrl;
        this.album_action_alias=album_action_alias;
        this.goods_id=goods_id;
        this.price = price;
        this.name = name;
        this.image = image;
        this.itemUrl = itemUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag_bg() {
        return tag_bg;
    }

    public void setTag_bg(String tag_bg) {
        this.tag_bg = tag_bg;
    }

    public String getAlbum_image() {
        return album_image;
    }

    public void setAlbum_image(String album_image) {
        this.album_image = album_image;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAlbum_action_alias() {
        return album_action_alias;
    }

    public void setAlbum_action_alias(String album_action_alias) {
        this.album_action_alias = album_action_alias;
    }

    @Override
    public String toString() {
        return "Album{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", tag='" + tag + '\'' +
                ", tag_bg='" + tag_bg + '\'' +
                ", album_image='" + album_image + '\'' +
                ", albumUrl='" + albumUrl + '\'' +
                ", album_action_alias='" + album_action_alias + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", itemUrl='" + itemUrl + '\'' +
                '}';
    }
}
