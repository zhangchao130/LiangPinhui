package com.google.lenono.tiaozaoproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.lenono.adapter.GoodDetailListViewAdapter;
import com.google.lenono.common.goodDetail_model.Brand;
import com.google.lenono.common.goodDetail_model.From_user;
import com.google.lenono.common.goodDetail_model.GoodDetail;
import com.google.lenono.fragment.GoodDetailViewpagerPicFragment;
import com.google.lenono.service.GoodDetailService;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.utils.InternetUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class GoodDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private GoodDetailListViewAdapter adapter;   //listview的adapter
    private Handler handler;
    public static GoodDetail goodDetail;
    private GoodDetailService.GoodDetailBinder binder;

    private List<HashMap<String, String>> good_params;
    private String wx_kefu = null;
    private String gdUrl = null;
    private ImageView[] imgDot;
    private int currentIndex;
    private boolean isConnectService = false;
    private ServiceConnection serviceConnection;
    private String errorInfo = null;
    private String[] orderInfo;
    private TZSQLiteDao dao;

    @Bind(R.id.gd_activity_return_btn)
    FloatingActionButton btn_return;
    @Bind(R.id.gd_activity_share_btn)
    FloatingActionButton btn_share;
    @Bind(R.id.gd_good_title)
    TextView tv_good_title;
    @Bind(R.id.gd_discount)
    TextView tv_good_discount;
    @Bind(R.id.gd_save)
    TextView tv_good_save;
    @Bind(R.id.gd_sell_price)
    TextView tv_sell_price;
    @Bind(R.id.gd_original_price)
    TextView tv_original_price;
    @Bind(R.id.gd_user_img)
    CircleImageView iv_user_img;
    @Bind(R.id.gd_user_name)
    TextView tv_user_name;
    @Bind(R.id.gd_user_detail)
    TextView tv_user_detail;
    @Bind(R.id.gd_user_desc)
    TextView tv_uesr_desc;
    @Bind(R.id.gd_purity_desc)
    TextView tv_purity_desc;  //详细情况
    @Bind(R.id.gd_brand_pic)
    ImageView iv_brand_pic;
    @Bind(R.id.gd_brand_name)
    TextView tv_brand_name;
    @Bind(R.id.gd_brand_desc)
    TextView tv_brand_desc;
    @Bind(R.id.gd_bottom_ll)
    LinearLayout ll_bottom;  //微信客服那点，添加监听
    @Bind(R.id.gd_viewpager_dot_ll)
    LinearLayout ll_dot;  //小白点所在的LinearLayout
    @Bind(R.id.gd_saler_info)
    LinearLayout ll_saler;
    @Bind(R.id.gd_bottom_now_price)
    TextView tv_bottom_now_price;
    @Bind(R.id.gd_bottom_before_price)
    TextView tv_bottom_before_price;
    @Bind(R.id.gd_bottom_discount)
    TextView tv_bottom_discount;
    @Bind(R.id.gd_bottom_btn)
    Button btn_bottom_order; //订单按钮
    @Bind(R.id.gd_viewpager)
    ViewPager viewPager;
    @Bind(R.id.gd_gooddetail_listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);

        ButterKnife.bind(this);  //绑定id
        tv_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  //文字中间加横线
        tv_bottom_before_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        good_params = new ArrayList<>();
        adapter = new GoodDetailListViewAdapter(this, good_params);
        listView.setAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    initData(goodDetail);
                } else {
                    errorInfo = "地址错误";
                }
            }
        };

        dao = new TZSQLiteDao(getApplicationContext());
        //加监听
        btn_return.setOnClickListener(this);
        ll_bottom.setOnClickListener(this);
        ll_saler.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        ShareSDK.initSDK(this);

        if (InternetUtils.isConnect(this)) {
            initIntent("goods");
        } else {
            errorInfo = "网络无连接";
            Toast.makeText(this, errorInfo, Toast.LENGTH_SHORT).show();
        }
    }

    public void initData(GoodDetail goodDetail) {
        List<HashMap<String, String>> goodParams_list = goodDetail.getDetail_params();
        good_params.clear();
        good_params.addAll(goodParams_list);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView);

        String[] goods_pics = goodDetail.getGoods_pics();   //浏览图片
        From_user user = goodDetail.getUser();
        Brand brand = goodDetail.getBrand();

        String good_id = goodDetail.getGood_id();
        String size = null;
        for (HashMap<String, String> map : goodParams_list) {
            String name = map.get("key");
            if (name.equals("尺码") || name.equals("规格") || name.equals("型号")) {
                size = map.get("value");
            }

        }
        orderInfo = new String[8];
        orderInfo[1] = good_id;
        orderInfo[2] = "http://uuyichu.com/api/goods/detail_v4/" + good_id;
        orderInfo[3] = goodDetail.getGoods_name();
        orderInfo[4] = goods_pics[0];  //商品图片
        orderInfo[5] = size;
        orderInfo[6] = goodDetail.getSell_price();

        tv_good_title.setText(goodDetail.getGoods_name());
        tv_good_discount.setText(goodDetail.getDiscount(1));
        tv_good_save.setText(goodDetail.getDiscount(2));
        tv_sell_price.setText("￥" + goodDetail.getSell_price());
        tv_original_price.setText("￥" + goodDetail.getOriginal_price());
        Picasso.with(this).load(user.getHeadimg()).into(iv_user_img);
        tv_user_name.setText(user.getNickname());
        tv_user_detail.setText("共发布了" + user.getPublish_count() + "件商品,售出" + user.getSold_count() + "件");
        tv_uesr_desc.setText(goodDetail.getPublisher_desc());
        tv_purity_desc.setText(goodDetail.getPurity_desc());
        Picasso.with(this).load(brand.getLogo_url()).into(iv_brand_pic);
        tv_brand_name.setText(brand.getName());
        tv_brand_desc.setText(brand.getDescription());
        wx_kefu = goodDetail.getWx_kefu();
        tv_bottom_now_price.setText("￥" + goodDetail.getSell_price());
        tv_bottom_before_price.setText("￥" + goodDetail.getOriginal_price());
        tv_bottom_discount.setText(goodDetail.getDiscount(3));

        if (!goodDetail.getIs_onsale().equals("true")) {  //判断售出状态
            btn_bottom_order.setText("已售出");
            btn_bottom_order.setBackgroundColor(Color.parseColor("#cccccc"));
            btn_bottom_order.setTextColor(Color.WHITE);
        } else {
            btn_bottom_order.setOnClickListener(this);
        }

        initViewpagerPic(goods_pics);
    }

    public void initIntent(final String info) {
        Intent getIntent = getIntent();
        gdUrl = getIntent.getStringExtra("goodsUrl");

        Intent intent = new Intent(this, GoodDetailService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                binder = (GoodDetailService.GoodDetailBinder) iBinder;
                if (info.equals("goods")) {
                    binder.getGoodDetail(gdUrl, handler);
                } else if (info.equals("order")) {
                    binder.insertOrder(dao, "user_order", new String[]{"id", "good_id", "good_url", "good_name", "good_img", "good_size", "good_price", "good_order_num"}, orderInfo);
                }
                isConnectService = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    public void initViewpagerPic(String[] goods_pics) {
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), goods_pics));

        for (int i = 0; i < goods_pics.length; i++) {   //加载小圆点
            View view = LayoutInflater.from(this).inflate(R.layout.gd_viewpager_dot, null);
            ll_dot.addView(view);
        }

        imgDot = new ImageView[goods_pics.length];  //设置小圆点的初始化
        for (int i = 0; i < imgDot.length; i++) {
            imgDot[i] = (ImageView) ll_dot.getChildAt(i);
            imgDot[i].setEnabled(false);
        }
        currentIndex = 0;
        imgDot[currentIndex].setEnabled(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imgDot[position].setEnabled(true);
                imgDot[currentIndex].setEnabled(false);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gd_activity_return_btn:
                finish();
                break;
            case R.id.gd_bottom_ll:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (wx_kefu != null) {
                    builder.setMessage("您的专属客服是：" + wx_kefu + "。将她加为微信好友即可为您服务（微信号已复制，可直接粘贴使用）");
                } else {
                    builder.setMessage("请稍候...");
                }
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("aaa", "确定");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                //复制内容到剪切板
                ClipboardManager cmb = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);  //已过时，但为了兼容低版本使用这个包下的;
                cmb.setText(goodDetail.getWx_kefu());
                break;
            case R.id.gd_bottom_btn:
                //判断登录状态，若登陆了进行跳转,并开启服务进行数据库插入操作，若未登录，则吐司吧
                //若登录判断下该订单是否已在数据库中
                SharedPreferences spf = getSharedPreferences("isFirstSMSLogin", Context.MODE_PRIVATE);
                boolean flag = spf.getBoolean("isSMSLogin", false);
                if (flag) {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    int second = c.get(Calendar.SECOND);

                    String phoneNum = spf.getString("tel", null);
                    orderInfo[0] = phoneNum;
                    orderInfo[1] = orderInfo[1] + "_" + phoneNum;
                    orderInfo[7] = year + "" + month + "" + day + "" + hour + "" + minute + "" + second;
                    initIntent("order");

                    Intent intent = new Intent(this, GoodDetailOrderActivity.class);
                    intent.putExtra("user_id", phoneNum);
                    intent.putExtra("good_id", orderInfo[1]);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.gd_saler_info:
                //跳转商户Activity
                break;
            case R.id.gd_activity_share_btn:
                OnekeyShare oks = new OnekeyShare();
               //关闭sso授权
                oks.disableSSOWhenAuthorize();
                 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("臻品汇");
                // titleUrl是标题的网络链接，QQ和QQ空间等使用
                oks.setTitleUrl("http://uuyichu.com/");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("臻品汇,使一切事物都变得有价值");
                oks.setComment("臻品汇,使一切事物都变得有价值");
                oks.show(this);
                break;
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {   //动态给定listview的高度
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;

        if (listAdapter == null) {
            return;
        }
        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);// listItem.measure(0, 0);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    class MyAdapter extends FragmentPagerAdapter {   //viewpager适配器
        private String[] pics;

        public MyAdapter(FragmentManager fm, String[] pics) {
            super(fm);
            this.pics = pics;
        }

        @Override
        public Fragment getItem(int position) {
            GoodDetailViewpagerPicFragment good_pic_fragment = new GoodDetailViewpagerPicFragment(GoodDetailActivity.this, pics[position]);
            return good_pic_fragment;
        }

        @Override
        public int getCount() {
            return pics.length;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConnectService) {
            unbindService(serviceConnection);
        }
    }
}
