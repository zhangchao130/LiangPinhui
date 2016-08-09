package com.google.lenono.tiaozaoproject;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.lenono.applicationpay.PayDemoActivity;
import com.google.lenono.applicationpay.PayResult;
import com.google.lenono.applicationpay.SignUtils;
import com.google.lenono.service.GoodDetailService;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodDetailOrderActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.gd_order_toolbar)
    Toolbar toolbar;
    @Bind(R.id.gd_order_address)
    LinearLayout ll_address;   //收货地址
    @Bind(R.id.gd_order_good_url)
    LinearLayout ll_good_url;   //收货地址
    @Bind(R.id.gd_order_good_img)
    ImageView iv_good_img;   //商品图片
    @Bind(R.id.gd_order_brand)
    TextView tv_brand;   //品牌
    @Bind(R.id.gd_order_good_size)
    TextView tv_good_size;   //尺码
    @Bind(R.id.gd_order_price)
    TextView tv_good_price;   //价钱
    @Bind(R.id.gd_order_single_price)
    TextView tv_good_single_price;   //小计价钱
    @Bind(R.id.gd_order_discount_price)
    TextView tv_good_discount_price;   //首单优惠
    @Bind(R.id.gd_order_edit)
    EditText et_good_info;  //备注
    @Bind(R.id.gd_order_num)
    TextView tv_order_num;   //订单号
    @Bind(R.id.gd_order_final_price)
    TextView tv_good_final_price;   //总计


    private TZSQLiteDao dao;
    public static List<HashMap<String, Object>> goodParamList;
    public static List<HashMap<String, Object>> addressList;
    private Handler handler;
    private String user_id;
    private View view2;

    // 商户PID 商户的签约 ID 必须 2088 开头
    public static final String PARTNER = "2088111278561763";
    // 商户收款账号 支付宝账号
    public static final String SELLER = "gaoyandingzhi@126.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANEClM9ja39OuhbiFcPYG8nUt19TIGvnBjC2CGMV3BKY2pTolVuicMfM0yyxvwtewe7Wkk+06Zl8fjgIWZS8SsfOeznQZbJq236CbcFYIhDsorDllDwQ0Uk409WSjaOCDJamOjGeQjYqy3D7v+z+Z48ZvCOPleX2h415mHQeHWVdAgMBAAECgYB6FrHqOr7uTIRzHXltPu1shi7fJeWIYhjBl3NqvbghvNvho8KrFkYez8yDDQj1kVJjOz+YA6t4lrn77RS2xw4+fRJgBy/LD9ILectaThysuFt84yKooSuFAv1AQKMeVXkpnFuzzBFtxyuRPtPUYXftSvEm/9BapFHGEVCuT7RvAQJBAP9yq18VFhPQAfngld9n0NwmCO33kdbFYqVIWBNKZdvVZIqwIvnmTqsgQacrvWutsWauukKT7VzySkht/uE63j0CQQDRdjgqx4H7SfMjkaZK5nJ6ptuFgR19HkakOJZSIM78Ot3PzfHcnfYuCRjs8lIEWmhYqj2FE+BcZ9cejphGuTWhAkB0XimBXBq9ldGAonXD2whDcbQ5q8EtJKgmgUlWKFs0hQaTQ1/7lZYa0Mv3uq5EwlCBZXGGaNsFr351dl5Y/jdFAkA6D2DmSsL22rqwo1DK9jHJWbMDwJRh+CBwqNbSERIOzGprjZR7KLXycMcd9tVRK5Y87YN7/dR1CLuSVshS4kfBAkAW6ls9/RlBA6gOpDuq+Qn4CZUng3h7OJsDgzCY95RtuMISJNuVFcGC/XVKB+urkyfhR/H7I8HIPXQtNJenH9f2";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(GoodDetailOrderActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(GoodDetailOrderActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(GoodDetailOrderActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(GoodDetailOrderActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail_order);

        ButterKnife.bind(this);
        dao = new TZSQLiteDao(GoodDetailOrderActivity.this);
        toolbar.setTitle("确认订单");
        toolbar.setNavigationIcon(R.drawable.home_recommend_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //判断下数据库中是否存有地址,若无用...布局,若有用...布局;
        //其他的用数据进行填充

        view2 = LayoutInflater.from(this).inflate(R.layout.gd_order_have_address, null);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    initOrder();
                }
            }
        };

        ll_good_url.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        initIntent();
    }

    public void initIntent() {
        Intent getIntent = getIntent();
        user_id = getIntent.getStringExtra("user_id");
        final String good_id = getIntent.getStringExtra("good_id");

        Intent intent = new Intent(this, GoodDetailService.class);
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                GoodDetailService.GoodDetailBinder binder = (GoodDetailService.GoodDetailBinder) iBinder;
                binder.getOrder(handler, dao, good_id, user_id);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void initOrder() {
        HashMap<String, Object> goodMap = goodParamList.get(0);
        String price = goodMap.get("good_price").toString();

        Picasso.with(this).load(goodMap.get("good_img").toString()).into(iv_good_img);
        tv_brand.setText(goodMap.get("good_name").toString());
        tv_good_size.setText("/" + goodMap.get("good_size").toString());
        tv_order_num.setText("订单号：" + goodMap.get("good_order_num").toString());
        tv_good_price.setText("￥" + price);
        tv_good_single_price.setText("￥" + price);

        HashMap<String, Object> addressMap = addressList.get(0);
        String firstOrder = addressMap.get("first_shop").toString();
        if (firstOrder.equals("true")) {
            tv_good_discount_price.setText("10");
            tv_good_final_price.setText("￥" + (Integer.parseInt(price) + 10));
            dao.updateOrder("user_info", new String[]{"first_shop"}, new String[]{"false"}, "id=?", new String[]{user_id});
        } else {
            tv_good_discount_price.setText("0");
            tv_good_final_price.setText("￥" + (Integer.parseInt(price) + 20));
        }

        initAddress(0);
    }

    public void initAddress(int position) {
        addressList=dao.getUserInfo("user_info",new String[]{"id","first_shop","address_num"},"id=?",new String[]{user_id},null);
        HashMap<String, Object> addressMap = addressList.get(0);
        Object addressObj = addressMap.get("address_num");

        View view=ll_address.getChildAt(0);
        if(view!=null){
            ll_address.removeView(view);
        }

        if (addressObj.toString().equals("0")) {
            View view1 = LayoutInflater.from(this).inflate(R.layout.gd_order_null_address, null);
            ll_address.addView(view1);
        } else {
            List<HashMap<String, Object>> addressInfo = dao.getUserInfo("user_address", new String[]{"nickname", "phone_num", "address", "address_detail"}, "id=?", new String[]{user_id}, null);
            HashMap<String, Object> map1 = addressInfo.get(position);

            String basicAddress = map1.get("address").toString();
            String detailAddress = map1.get("address_detail").toString();
            String nickName = map1.get("nickname").toString();
            String phone = map1.get("phone_num").toString();

            TextView tv_address_name = (TextView) view2.findViewById(R.id.gd_order_address_nickname);
            TextView tv_address_phone = (TextView) view2.findViewById(R.id.gd_order_address_phone);
            TextView tv_address_basic = (TextView) view2.findViewById(R.id.gd_order_address_basic);
            TextView tv_address_detail = (TextView) view2.findViewById(R.id.gd_order_address_detail);

            tv_address_name.setText(nickName);
            tv_address_phone.setText(phone);
            tv_address_basic.setText(basicAddress);
            tv_address_detail.setText(detailAddress);

            ll_address.addView(view2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gd_order_good_url:
                Intent intent = new Intent(this, GoodDetailActivity.class);
                intent.putExtra("goodsUrl", goodParamList.get(0).get("good_url").toString());
                startActivity(intent);
                break;
            case R.id.gd_order_address:
                Intent intent1 = new Intent(this, OrderAddressActivity.class);
                intent1.putExtra("user_id", user_id);
                startActivityForResult(intent1,100);
                break;
//            case R.id.gd_order_pay:
//                pay(view);
//                break;
        }

    }
    public void payClick(View view){
        pay(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==200){
            int position=data.getIntExtra("position",0);
            initAddress(position);
        }
    }
    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    finish();
                                }
                            }).show();
            return;
        }
        // 订单 下单时只有现实的是 测试的商品 和价格 。描述并没有被使用
        String orderInfo = getOrderInfo("商品名称", "该测试商品的详细描述", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(GoodDetailOrderActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

}
