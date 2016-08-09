package com.google.lenono.utils.jsonUtils;

import android.util.Log;

import com.google.lenono.common.Goods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class JsonUtils_SecondPage {
    private String url="http://uuyichu.com/api/goods/detail_v4/";  //页面跳转地址：http://uuyichu.com/api/goods/detail_v4/<id>
    private List<Goods> goodsList;

    public List<Goods> getJsonStr(String jsonStr){
        try {
            goodsList=new ArrayList<>();
            JSONObject basicObj=new JSONObject(jsonStr);
            String page_sel=basicObj.getString("page_sel");
            JSONArray goods_list=basicObj.getJSONArray("goods_list");

            //以下是goods_list里内容
            for(int i=0;i<goods_list.length();i++){
                JSONObject object=goods_list.getJSONObject(i);  //获得数组里的对象
                String goods_name=object.getString("goods_name");  //获取商品名称
                String brand_name=object.getString("brand_name");  //品牌名
                String original_price=object.getString("original_price");   //商品原始价钱
                String sell_price=object.getString("sell_price");   //现售价格
                String cover=object.getString("cover");   //图片地址
                String id=object.getString("id");  //网页跳转所需id
                String is_onsale=object.getString("is_onsale");

//                String brand_id=object.getString("brand_id");  //品牌id
//                String cate_id=object.getString("cate_id");   //类别id
//                String condition_dis=object.getString("condition_dis");  //成色情况

                String goodsUrl=url+id;

                Goods goods=new Goods(id,goods_name,brand_name,original_price,sell_price,cover,goodsUrl,is_onsale);
                goodsList.add(goods);

                Log.i("goods",goods.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return goodsList;
    }
}
