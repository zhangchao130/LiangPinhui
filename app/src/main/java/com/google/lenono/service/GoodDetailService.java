package com.google.lenono.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.lenono.common.goodDetail_model.GoodDetail;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailActivity;
import com.google.lenono.tiaozaoproject.GoodDetailOrderActivity;
import com.google.lenono.tiaozaoproject.OrderAddressActivity;
import com.google.lenono.utils.InternetUtils;
import com.google.lenono.utils.jsonUtils.JsonUtils_GoodDetailPage;

/**
 * Created by Administrator on 2016/7/27.
 */
public class GoodDetailService extends Service{
    private GoodDetailBinder binder=new GoodDetailBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class GoodDetailBinder extends Binder{
        public void getGoodDetail(final String goodUrl, final Handler handler){
            new Thread(){
                public void run(){
                    if(goodUrl!=null){
                        String jsonStr = InternetUtils.getWebString(goodUrl);
                        JsonUtils_GoodDetailPage json = new JsonUtils_GoodDetailPage();
                        GoodDetail goodDetail = json.getJsonStr(jsonStr);
                        GoodDetailActivity.goodDetail=goodDetail;
                        handler.sendEmptyMessage(1);
                    }else{
                        handler.sendEmptyMessage(2);
                    }
                }
            }.start();
        }

        public void insertOrder(final TZSQLiteDao dao, final String table,final String[] insertColumn, final String[] args){  //订单部分的服务
            new Thread(){
                public void run(){
                    dao.insertUserInfo(table,insertColumn,args);
                }
            }.start();

        }
//        public void updateOrder(final TZSQLiteDao dao, final String table, final String[] updateColumn, final String[] updateValue , final String selection, final String[] selectArgs){
//            new Thread(){
//                public void run(){
//                    dao.updateOrder(table,updateColumn,updateValue,selection,selectArgs);
//                }
//            }.start();
//        }

        public void getOrder(final Handler handler, final TZSQLiteDao dao, final String good_id, final String user_id){
            new Thread(){
                public void run(){
                    if (good_id!=null){
                        GoodDetailOrderActivity.goodParamList=dao.getUserInfo("user_order",new String[]{"id","good_id","good_url","good_name","good_img","good_size","good_price","good_order_num","good_info"},"good_id=?",new String[]{good_id},null);
                        GoodDetailOrderActivity.addressList=dao.getUserInfo("user_info",new String[]{"id","first_shop","address_num"},"id=?",new String[]{user_id},null);
                        handler.sendEmptyMessage(1);
                    }else if(good_id==null){
                        OrderAddressActivity.addressInfo.clear();
                        OrderAddressActivity.addressInfo.addAll(dao.getUserInfo("user_address",new String[]{"nickname","phone_num","address","address_detail",},"id=?",new String[]{user_id},null));
                        handler.sendEmptyMessage(2);
                    }
                }
            }.start();
        }
//        public void deleteOrder(final TZSQLiteDao dao, final String table, final String deleteWhere, final String[] deleteArgs){
//            new Thread(){
//                public void run(){
//                    dao.delete(table,deleteWhere,deleteArgs);
//                }
//            }.start();
//        }
    }
}
