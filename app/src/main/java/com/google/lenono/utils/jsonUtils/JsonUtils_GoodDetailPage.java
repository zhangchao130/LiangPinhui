package com.google.lenono.utils.jsonUtils;

import com.google.lenono.common.goodDetail_model.Brand;
import com.google.lenono.common.goodDetail_model.From_user;
import com.google.lenono.common.goodDetail_model.GoodDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class JsonUtils_GoodDetailPage {
    private String user_url1="http://uuyichu.com/api/goods/user_v4/?user_id=";   //商家详情跳转页
    private String user_url2="&page=1";
    private List<HashMap<String,String>> detail_params_list;
    private String[] goods_pics_array;
    private GoodDetail goodDetail;

    public GoodDetail getJsonStr(String jsonStr){
        detail_params_list=new ArrayList<>();
        try {
            JSONObject basicObj=new JSONObject(jsonStr);

            JSONObject detail=basicObj.getJSONObject("detail");
            JSONObject goods_obj=detail.getJSONObject("goods_obj");

            //goods_obj里内容
            String good_id=goods_obj.getString("id");
            String original_price=goods_obj.getString("original_price");
            JSONArray detail_params=goods_obj.getJSONArray("detail_params");
            JSONArray goods_pics=goods_obj.getJSONArray("goods_pics");
            String sell_price=goods_obj.getString("sell_price");
            JSONObject brand_obj=goods_obj.getJSONObject("brand_obj");
            String purity_desc=goods_obj.getString("purity_desc");
            String  publisher_desc=goods_obj.getString("publisher_desc");
            JSONObject img_size=goods_obj.getJSONObject("img_size");
            String wx_kefu=goods_obj.getString("wx_kefu");
            String goods_name=goods_obj.getString("goods_name");
            JSONObject from_user=goods_obj.getJSONObject("from_user");
            String is_onsale=goods_obj.getString("is_onsale");
            String condition_dis=goods_obj.getString("condition_dis");

            for(int i=0;i<detail_params.length();i++){   //detail_params里内容，商品概述
                HashMap<String,String> map=new HashMap<>();
                JSONObject obj=detail_params.getJSONObject(i);

                String key=obj.getString("key");
                String value=obj.getString("value");
                map.put("key",key);
                map.put("value",value);
                detail_params_list.add(map);
            }

            goods_pics_array=new String[goods_pics.length()];   //商品图片，goods_pics内容
            for(int j=0;j<goods_pics.length();j++){
                goods_pics_array[j]=goods_pics.getString(j);
            }

            String code=brand_obj.getString("code");    //品牌详情，brand_obj内容
            String name=brand_obj.getString("name");
            String logo_url=brand_obj.getString("logo_url");
            String description=brand_obj.getString("description");
            Brand brand=new Brand(code,name,logo_url,description);

            String img_h=img_size.getString("h");    //图片尺寸，img_size内容,这俩也可以不要
            String img_w=img_size.getString("w");

            String nickname=from_user.getString("nickname");   //商家信息，from_user内容
            String id=from_user.getString("id");
            String authenticated=from_user.getString("authenticated");
            String publish_count=from_user.getString("publish_count");
            String headimg=from_user.getString("headimg");
            String sold_count=from_user.getString("sold_count");
            String user_url=user_url1+id+user_url2;
            From_user user=new From_user(nickname,id,authenticated,publish_count,sold_count,headimg,user_url);

            goodDetail=new GoodDetail(good_id,original_price,detail_params_list,goods_pics_array,sell_price,brand,purity_desc,publisher_desc, img_h,img_w,wx_kefu,goods_name,user,is_onsale,condition_dis);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return goodDetail;
    }

}
