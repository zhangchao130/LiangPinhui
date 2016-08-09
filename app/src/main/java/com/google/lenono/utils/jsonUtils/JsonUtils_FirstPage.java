package com.google.lenono.utils.jsonUtils;

import android.util.Log;

import com.google.lenono.common.Album;
import com.google.lenono.common.Banner;
import com.google.lenono.common.Dot;
import com.google.lenono.common.FlashSale;
import com.google.lenono.common.Recommend;
import com.google.lenono.common.ViewGoods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/16.
 * 这里边获取url时可以根据action提出来个方法
 */
public class JsonUtils_FirstPage {
    private String show_album1 = "http://uuyichu.com/api/goods/album_v4/?alias=";     //action的类别不同，地址不同
    private String show_album2 = "&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=1";
    private String show_ht_list = "http://uuyichu.com/haibaicai/spu/list?page=1&alias=";
    private String show_detail = "http://uuyichu.com/api/goods/detail_v4/";
    private String show_ht_detail = "http://uuyichu.com/haibaicai/spu/detail/";
    private String view_goods = "http://uuyichu.com/api/goods/detail_v4/";
    private String view2_goods = "http://uuyichu.com/haibaicai/spu/detail/";
    private List<Banner> bannerList;
    private List<Dot> dotList;
    private List<Recommend> recommendList;
    private List<FlashSale> flashSaleList;
    private List<ViewGoods> viewGoodsList;

    private HashMap<String, List<Album>> map;
    private int album_tag = 1;

    public void getJsonStr(String jsonStr) {    //这是总的获取json
        try {
            map = new HashMap<>();

            JSONObject basicObj = new JSONObject(jsonStr);
            JSONArray home_datas = basicObj.getJSONArray("home_datas");
            for (int i = 0; i < home_datas.length(); i++) {
                JSONObject object = home_datas.getJSONObject(i);
                String type = object.getString("type");
                String name = object.getString("name");
                if (name.equals("banner")) {
                    setBannerList(object);
                } else if (name.equals("dot")) {
                    setDotList(object);
                } else if (name.equals("recommend")) {
                    setRecommmendList(object);
                } else if (name.equals("flashsale")) {
                    setFlashSale(object);
                } else if (name.equals("album")) {
                    setAlbum(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setViewGoods(String jsonUrl, String key) {
        viewGoodsList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonUrl);
            if (!key.equals("5")) {
                JSONArray goods_list = object.getJSONArray("goods_list");
                for (int i = 0; i < goods_list.length(); i++) {
                    JSONObject object1 = goods_list.getJSONObject(i);
                    String original_price = object1.getString("original_price");
                    String id = object1.getString("id");
                    String sell_price = object1.getString("sell_price");
                    String cover = object1.getString("cover");
                    String goods_name = object1.getString("goods_name");
                    String brand_name = object1.getString("brand_name");
                    String jsonurl = view_goods + id;
                    ViewGoods viewGoods = new ViewGoods();
                    viewGoods.setCover(cover);
                    viewGoods.setGoods_name(goods_name);
                    viewGoods.setId(id);
                    viewGoods.setOri_price_cn(original_price);
                    viewGoods.setSell_price_cn(sell_price);
                    viewGoods.setJsonUrl(jsonurl);
                    viewGoods.setBrand_name(brand_name);
                    viewGoodsList.add(viewGoods);
                }
            } else {
                JSONArray spu_list = object.getJSONArray("spu_list");
                for (int j = 0; j < spu_list.length(); j++) {
                    JSONObject object1 = spu_list.getJSONObject(j);
                    JSONObject object2 = object1.getJSONObject("price_details");
                    String original_price = object2.getString("ori_price_cn");
                    String sell_price = object2.getString("sell_price_cn");
                    String id = object1.getString("sid");
                    String mall_logo = object1.getString("mall_logo");
                    String cover = object1.getString("cover");
                    String goods_name = object1.getString("name");
                    String jsonurl = view2_goods + id;
                    ViewGoods viewGoods = new ViewGoods();
                    viewGoods.setCover(cover);
                    viewGoods.setGoods_name(goods_name);
                    viewGoods.setId(id);
                    viewGoods.setOri_price_cn(original_price);
                    viewGoods.setSell_price_cn(sell_price);
                    viewGoods.setMall_logo(mall_logo);
                    viewGoods.setJsonUrl(jsonurl);
                    viewGoodsList.add(viewGoods);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<ViewGoods> getViewGoodsList() {
        return viewGoodsList;
    }

    public void setBannerList(JSONObject object) {
        String bannerUrl = null;
        bannerList = new ArrayList<>();
        try {
            JSONArray contents = object.getJSONArray("contents");
            for (int i = 0; i < contents.length(); i++) {
                JSONObject bannerObj = contents.getJSONObject(i);

                JSONObject action = bannerObj.getJSONObject("action");
                String image = bannerObj.getString("image");   //图片地址

                String subAction = action.getString("action");
                String alias = action.getString("alias");    //别名,即类型


                if (subAction.equals("show_album")) {
                    bannerUrl = show_album1 + alias + show_album2;   //该banner的连接
                } else if (subAction.equals("show_ht_list")) {
                    bannerUrl = show_ht_list + alias;
                }

                Banner banner = new Banner(bannerUrl, image, alias);
                Log.i("banner", banner.toString());
                bannerList.add(banner);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setDotList(JSONObject object) {
        String dotUrl = null;
        dotList = new ArrayList<>();
        try {
            JSONArray contents = object.getJSONArray("contents");
            for (int i = 0; i < contents.length(); i++) {
                JSONObject dotObj = contents.getJSONObject(i);

                JSONObject action = dotObj.getJSONObject("action");
                String image = dotObj.getString("image");   //图片地址
                String title = dotObj.getString("title");   //标题

                String subAction = action.getString("action");
                String alias = action.getString("alias");    //别名,即类型

                if (subAction.equals("show_album")) {
                    dotUrl = show_album1 + alias + show_album2;   //该dot的连接
                }

                Dot dot = new Dot(alias, dotUrl, image, title);
                Log.i("dot", "dot------" + dot.toString());
                dotList.add(dot);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Dot> getDotList() {
        return dotList;
    }

    public void setRecommmendList(JSONObject object) {
        String recommendUrl = null;
        recommendList = new ArrayList<>();
        try {
            String title = object.getString("title");  //总标题:今日推荐
            String icon = object.getString("icon");    //图标地址,实际上这个图片本地有
            JSONArray contents = object.getJSONArray("contents");
            for (int i = 0; i < contents.length(); i++) {
                JSONObject recommendObj = contents.getJSONObject(i);

                JSONObject action = recommendObj.getJSONObject("action");
                String price = recommendObj.getString("price");
                String recommendName = recommendObj.getString("name");
                String image = recommendObj.getString("image");   //图片地址

                String subAction = action.getString("action");
                String goods_id = action.getString("goods_id");//商品编号

                if (subAction.equals("show_detail")) {
                    recommendUrl = show_detail + goods_id;   //该商品的连接
                }

                Recommend recommend = new Recommend(title, icon, goods_id, price, recommendName, image, recommendUrl);
                Log.i("recommend", "recommend------" + recommend.toString());
                recommendList.add(recommend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Recommend> getRecommendList() {
        return recommendList;
    }

    public void setFlashSale(JSONObject object) {
        String flashSaleUrl = null;
        flashSaleList = new ArrayList<>();
        try {
            String title = object.getString("title");  //总标题:今日推荐
            String icon = object.getString("icon");    //图标地址,实际上这个图片本地有
            JSONArray contents = object.getJSONArray("contents");
            for (int i = 0; i < contents.length(); i++) {
                JSONObject flashSaleObj = contents.getJSONObject(i);

                JSONObject action = flashSaleObj.getJSONObject("action");
                JSONObject time_limit = flashSaleObj.getJSONObject("time_limit");   //限时活动的时间
                String image = flashSaleObj.getString("image");   //图片地址

                //这里获取action里的内容
                String subAction = action.getString("action");
                //这里获取time_limit里的内容
                String begin = time_limit.getString("begin");
                String end = time_limit.getString("end");

                String goods_id = null;
                if (subAction.equals("show_detail")) {
                    goods_id = action.getString("goods_id");//商品编号
                    flashSaleUrl = show_detail + goods_id;   //该商品的连接
                } else if (subAction.equals("show_web")) {
                    goods_id = String.valueOf(11);
                    flashSaleUrl = action.getString("url");   //这个应该是直接给出了地址，用webView应该就可以
                }

                FlashSale flashSale = new FlashSale(title, icon, goods_id, subAction, image, begin, end, flashSaleUrl);
                Log.i("flashSale", "flashSale------" + flashSale.toString());
                flashSaleList.add(flashSale);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<FlashSale> getFlashSaleList() {
        return flashSaleList;
    }

    public void setAlbum(JSONObject object) {
        String albumUrl = null;
        String itemUrl = null;
        try {
            List<Album> list = new ArrayList<>();

            JSONObject album = object.getJSONObject("album");
            JSONArray items = object.getJSONArray("items");

            //以下是album的内容
//                    String album_title=album.getString("title");   //若这两个值不为空，应显示在图片正中央.这个标题即下边的album_action_title
            String album_sub_title = album.getString("sub_title");     //子标题
            String tag = album.getString("tag");   //左上角标志
            String tag_bg = album.getString("tag_bg");  //标志背景色，不带#
            String album_image = album.getString("image");   //大图片地址
            JSONObject album_action = album.getJSONObject("action");
            //以下是album_action的内容
            String album_action_action = album_action.getString("action");  //根据此来判断连接地址
            String album_action_alias = album_action.getString("alias");   //以这个为map的key
            String album_action_title = album_action.getString("title");  //唯一的不同
            if (album_action_action.equals("show_album")) {
                albumUrl = show_album1 + album_action_alias + show_album2;
            } else if (album_action_action.equals("show_ht_list")) {
                albumUrl = show_ht_list + album_action_alias;
            }
            Log.i("album", "albumUrl-----" + albumUrl);
            //以下是items的内容
            for (int i = 0; i < items.length(); i++) {    //这块与前边的代码有重复,还是有不同之处
                JSONObject itemObj = items.getJSONObject(i);

                JSONObject action = itemObj.getJSONObject("action");
                String price = itemObj.getString("price");    //商品价格
                String name = itemObj.getString("name");     //商品品牌名
                String image = itemObj.getString("image");  //商品图片地址

                String subAction = action.getString("action");
                String goods_id = action.getString("goods_id");

                if (subAction.equals("show_detail")) {
                    itemUrl = show_detail + goods_id;
                } else if (subAction.equals("show_ht_detail")) {
                    itemUrl = show_ht_detail + goods_id;
                }
                Log.i("album", "itemUrl-----" + itemUrl);

                Album album1 = new Album(album_action_title, album_sub_title, tag, tag_bg, album_image, albumUrl, album_action_alias, album_tag + "_" + goods_id, price, name, image, itemUrl);
                list.add(album1);
            }
            Log.i("album", album_action_title + "----------" + list.size());
            map.put(album_action_alias, list);
            album_tag++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, List<Album>> getAlbum() {
        return map;
    }
}
