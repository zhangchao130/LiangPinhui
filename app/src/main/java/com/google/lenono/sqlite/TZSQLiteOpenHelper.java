package com.google.lenono.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenono on 2016-07-18.
 */
public class TZSQLiteOpenHelper extends SQLiteOpenHelper {
    public TZSQLiteOpenHelper(Context context) {
        super(context, "tzsqlite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists banner(alias varchar(30) primary key," +  //banner表
                "image varchar(100)," +
                "bannerUrl varchar(100))");
        sqLiteDatabase.execSQL("create table if not exists dot(alias varchar(30) primary key," +     //dot表
                "dotUrl varchar(100)," +
                "image varchar(100)," +
                "title varchar(50))");
        sqLiteDatabase.execSQL("create table if not exists recommend(goods_id integer primary key," +  //recommend表
                "title varchar(50)," +
                "icon varchar(100)," +
                "price integer," +
                "recommendName varchar(100)," +
                "image varchar(100)," +
                "recommendUrl varchar(100))");
        sqLiteDatabase.execSQL("create table if not exists flashSale(goods_id integer primary key," +  //flashSale表
                "title varchar(50)," +
                "icon varchar(100)," +
                "subAction varchar(30)," +
                "image varchar(100)," +
                "begin varchar(100)," +
                "end varchar(100)," +
                "flashSaleUrl varchar(100))");
        sqLiteDatabase.execSQL("create table if not exists album(goods_id varchar(20) primary key," +  //album表
                "title varchar(50)," +
                "subTitle varchar(50)," +
                "tag varchar(30)," +
                "tag_bg varchar(100)," +
                "album_image varchar(100)," +
                "albumUrl varchar(100)," +
                "album_action_alias varchar(50)," +
                "price integer," +
                "name varchar(30)," +
                "image varchar(100)," +
                "itemUrl varchar(100))");
        sqLiteDatabase.execSQL("create table if not exists goods(id integer primary key," +  //闲置页的goods表
                "goods_name varchar(30)," +
                "brand_name varchar(30)," +
                "original_price varchar(20)," +
                "sell_price varchar(20)," +
                "cover varchar(100)," +
                "goodsUrl varchar(100)," +
                "is_onsale varchar(20))");
        sqLiteDatabase.execSQL("create table if not exists viewGoods(id integer primary key," +
                "jsonurl varchar(100)," +
                "cover varchar(50)," +
                "goods_name varchar(30)," +
                "mall_logo varchar(100)," +
                "ori_price_cn varchar(20)," +
                "brand_name varchar(30)," +
                "sell_price_cn varchar(20))");
        sqLiteDatabase.execSQL("create table if not exists user_info(id integer primary key," +  //以电话为id
                "first_shop varchar(20) default 'true'," +  //是否是首单,首单有优惠
                "address_num integer default '0')");
        sqLiteDatabase.execSQL("create table if not exists user_address(id integer," +  //以电话为id,地址表
                "nickname varchar(30)," +
                "phone_num integer," +
                "address varchar(100)," +   //市区地址
                "address_detail varchar(100))");   //详细地址
        sqLiteDatabase.execSQL("create table if not exists user_order(id integer," +  //以电话为id
                "good_id varchar(50) primary key," +
                "good_url varchar(100)," +
                "good_name varchar(50)," +
                "good_img varchar(50)," +
                "good_size varchar(20)," +
                "good_price varchar(20)," +
                "good_order_num integer," + //订单号
                "good_info varchar(50))");   //备注
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
