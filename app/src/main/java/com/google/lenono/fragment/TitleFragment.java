package com.google.lenono.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.lenono.adapter.TitleCustormViewPagerAdatper;
import com.google.lenono.common.Album;
import com.google.lenono.common.FlashSale;
import com.google.lenono.common.Recommend;
import com.google.lenono.custorm.TitleCustormViewPager;
import com.google.lenono.memorycache.MemoryCache;
import com.google.lenono.memorycache.MemoryCacheManager;
import com.google.lenono.scaleInTransformer.ScaleInTransformer;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailActivity;
import com.google.lenono.tiaozaoproject.R;
import com.google.lenono.titletoactivity.TitleLineatLayoutActivity;
import com.google.lenono.titletoactivity.TitleListNoImageActivity;
import com.google.lenono.titletoactivity.TitleToActivity;
import com.google.lenono.titletoactivity.TitleToDayActivity;
import com.google.lenono.titletoactivity.TitleToFalshsale;
import com.google.lenono.titletoactivity.TitleWebViewActivity;
import com.google.lenono.utils.DensityUtil;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


/**
 * Created by lenono on 2016-07-17.
 */
public class TitleFragment extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private LinearLayout title_ll_new, title_ll_sell, title_ll_merchant, title_ll_discount;
    private SupportAnimator mAnimator;
    private ImageView iv_bottom_search, title_list_iv;
    private View view;
    private TextView tv;
    private ImageView[] points;
    private int currentIndex = 0;
    private ImageButton btn_left, btn_right;
    private ViewPager viewPager, viewPager_sale;
    private List<View> views = new ArrayList<View>();
    private int index = 0;
    private MyAdapter adapter;
    private boolean isTouch;
    public Handler handler, taDayHanlder;
    private TZSQLiteDao tzsqLiteDao;
    List<HashMap<String, Object>> recommendhs;
    List<HashMap<String, Object>> flashSalehs;
    List<HashMap<String, Object>> albumhs, albumBagshs, albumClothinghs, albumShoeshs, albumJewelryhs, albumAccessorieshs, albumWatcheshs, albumApplehs, albumSijinhs, albumJingdianhs, albumDiscounths, albumFjingdianhs, albumCoachhs, albumPeishihs, albumToryhs;
    private List<Recommend> recommendList;
    private List<FlashSale> flashSaleList;
    private List<Album> albumIdleList, albumBagsList, albumClothingList, albumShoesList, albumJewelryList, albumAccessoriesList, albumWatchesList, albumAppleList, albumSijinList, albumJingdianList, albumDiscountList, albumFjingdianList, albumCoachList, albumPeishiList, albumToryList;
    private TitleFalshaleAdapter titleFalshaleAdapter;
    String t1;
    ImageView idleIv,bagsIv,clothingIv,shoesIv,jewelryIv,accessoriesIv,watchesIv,appleIv,sijinIv,jingdianIv,discountIv,fjingdianIv,coachIv,peishiIv,toryIv;
    //下拉刷新
    private PullToRefreshScrollView mPullToRefreshScrollView;
    MemoryCacheManager memoryCacheManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.titlefragment_item, null);
        for (int i = 0; i < 3; i++) {
            View view = inflater.inflate(R.layout.title_custorm_taday_ref_vpitem, null);
            views.add(view);
        }
        initScrollView();
        initAnimation();
        initCustorm();

        initData();
        initTileLayout();
        return view;
    }


    public void initScrollView() {
        mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.title_fragment_scrollview);
        initDate();
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("刷新时间:" + t1);
    }

    public void initDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        t1 = format.format(d1);
    }

    public void initTileLayout() {
        title_ll_new = (LinearLayout) view.findViewById(R.id.title_ll_new);
        title_ll_sell = (LinearLayout) view.findViewById(R.id.title_ll_sell);
        title_ll_merchant = (LinearLayout) view.findViewById(R.id.title_ll_merchant);
        title_ll_discount = (LinearLayout) view.findViewById(R.id.title_ll_discount);
        bagsIv= (ImageView) view.findViewById(R.id.title_fragment_listview_bags);
        clothingIv= (ImageView) view.findViewById(R.id.title_fragment_listview_clothing);
        shoesIv= (ImageView) view.findViewById(R.id.title_fragment_listview_shoes);
        jewelryIv= (ImageView) view.findViewById(R.id.title_fragment_listview_jewelry);
        accessoriesIv= (ImageView) view.findViewById(R.id.title_fragment_listview_accessories);
        watchesIv= (ImageView) view.findViewById(R.id.title_fragment_listview_watches);
        appleIv= (ImageView) view.findViewById(R.id.title_fragment_listview_apple);

        idleIv= (ImageView) view.findViewById(R.id.title_fragment_listview_idle);
        sijinIv= (ImageView) view.findViewById(R.id.title_fragment_listview_sijin);
        jingdianIv= (ImageView) view.findViewById(R.id.title_fragment_listview_jingdian);
        discountIv= (ImageView) view.findViewById(R.id.title_fragment_listview_discount);
        fjingdianIv= (ImageView) view.findViewById(R.id.title_fragment_listview_fjingdian);
        coachIv= (ImageView) view.findViewById(R.id.title_fragment_listview_coach);
        peishiIv= (ImageView) view.findViewById(R.id.title_fragment_listview_peishi);
        toryIv= (ImageView) view.findViewById(R.id.title_fragment_listview_tory);

        title_ll_new.setOnClickListener(this);
        title_ll_sell.setOnClickListener(this);
        title_ll_merchant.setOnClickListener(this);
        title_ll_discount.setOnClickListener(this);
        idleIv.setOnClickListener(this);
        bagsIv.setOnClickListener(this);
        clothingIv.setOnClickListener(this);
        shoesIv.setOnClickListener(this);
        jewelryIv.setOnClickListener(this);
        accessoriesIv.setOnClickListener(this);
        watchesIv.setOnClickListener(this);
        appleIv.setOnClickListener(this);
        sijinIv.setOnClickListener(this);
        discountIv.setOnClickListener(this);
        jingdianIv.setOnClickListener(this);
        fjingdianIv.setOnClickListener(this);
        coachIv.setOnClickListener(this);
        peishiIv.setOnClickListener(this);
        toryIv.setOnClickListener(this);
    }

    //自定义viewpger 为首页页面上自动跳转的大图
    public void initCustorm() {
        final TitleCustormViewPager titleCustormViewPager = (TitleCustormViewPager) view.findViewById(R.id.titlefragment_curstormviewpager);
        int[] bitmaps = {R.drawable.gifts, R.drawable.men_400, R.drawable.taiyangjing_750_400, R.drawable.qingshe_cover_v2, R.drawable.shipin_cover_v3, R.drawable.gabr_cover};
        final List<ImageView> bitmapList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(bitmaps[i]);
            bitmapList.add(imageView);
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.title_viewpager_ll);
        points = new ImageView[bitmapList.size()];
        for (int j = 0; j < bitmapList.size(); j++) {
            points[j] = (ImageView) linearLayout.getChildAt(j);
            points[j].setEnabled(false);
        }
        Log.i("aaa", bitmapList.size() + "");
        points[currentIndex].setEnabled(true);
        TitleCustormViewPagerAdatper titleCustormViewPagerAdatper = new TitleCustormViewPagerAdatper(bitmapList);
        titleCustormViewPager.setAdapter(titleCustormViewPagerAdatper);
        titleCustormViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        isTouch = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isTouch = false;
                        break;
                }
                return false;
            }
        });
        titleCustormViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (position < 0 || position > bitmapList.size() + 1) {
                    return;
                }
                points[position].setEnabled(true);
                points[currentIndex].setEnabled(false);
                currentIndex = (position);
                bitmapList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), TitleToActivity.class);
                        intent.putExtra("position", position + "");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler = new Handler();

        new Thread() {
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isTouch) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (currentIndex < bitmapList.size() - 1) {
                                    points[currentIndex].setEnabled(false);
                                    titleCustormViewPager.setCurrentItem(++currentIndex);
                                    points[currentIndex].setEnabled(true);
                                } else {
                                    points[currentIndex].setEnabled(false);
                                    currentIndex = 0;
                                    titleCustormViewPager.setCurrentItem(currentIndex);
                                    points[currentIndex].setEnabled(true);
                                }
                            }
                        });
                    }
                }
            }
        }.start();
    }

    //searchView动画版
    public void initAnimation() {
        final CardView cardView = (CardView) view.findViewById(R.id.tzmain_searchview);
        cardView.setVisibility(View.INVISIBLE);
        tv = (TextView) view.findViewById(R.id.tzmain_searchview_tv);
        final FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        iv_bottom_search = (ImageView) view.findViewById(R.id.iv_bottom_search);
        iv_bottom_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimator != null && !mAnimator.isRunning()) {
                    mAnimator = mAnimator.reverse();
                    float curTranslationX = iv_bottom_search.getTranslationX();
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_search, "translationX", curTranslationX, 0);
                    animator.setDuration(1000);
                    mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            animator.start();
                        }

                        @Override
                        public void onAnimationEnd() {
                            mAnimator = null;
                            floatingActionButton.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel() {

                        }

                        @Override
                        public void onAnimationRepeat() {

                        }
                    });
                } else if (mAnimator != null) {
                    mAnimator.cancel();
                    return;
                } else {
                    int cx = cardView.getRight();
                    int cy = cardView.getBottom();
                    float curTranslationX = iv_bottom_search.getTranslationX();
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_search, "translationX", curTranslationX, cx / 2 - DensityUtil.dip2px(getContext(), 24));
                    animator.setDuration(1000);
                    float radius = r(cardView.getWidth(), cardView.getHeight());
                    mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cx / 2, cy - DensityUtil.dip2px(getContext(), 32), DensityUtil.dip2px(getContext(), 20), radius);
                    mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            animator.start();


                        }

                        @Override
                        public void onAnimationEnd() {

                        }

                        @Override
                        public void onAnimationCancel() {

                        }

                        @Override
                        public void onAnimationRepeat() {

                        }
                    });
                }

                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(1000);
                mAnimator.start();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
                iv_bottom_search.performClick();

            }
        });
    }

    //限时活动
    public void initFalshsale() {
        viewPager_sale = (ViewPager) view.findViewById(R.id.id_viewpager);

        viewPager_sale.setPageMargin(40);
        viewPager_sale.setOffscreenPageLimit(3);
        titleFalshaleAdapter = new TitleFalshaleAdapter(flashSaleList, getContext());
        viewPager_sale.setAdapter(titleFalshaleAdapter);
        viewPager_sale.setPageTransformer(true, new ScaleInTransformer());
        viewPager_sale.setCurrentItem(flashSaleList.size() * 100);
        Log.i("aaa", "清空flash" + flashSaleList.size());
    }

    class TitleFalshaleAdapter extends PagerAdapter {
        List<FlashSale> imageViews;
        Context context;

        public TitleFalshaleAdapter(List<FlashSale> imageViews, Context context) {
            this.imageViews = imageViews;
            this.context = context;

        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            Log.i("aaaa", "大小=" + imageViews.size());
            position %= imageViews.size();
            if (position < 0) {
                position = imageViews.size() + position;
            }
            ImageView view = new ImageView(getActivity());
            Picasso.with(getContext()).load(imageViews.get(position).getImage()).into(view);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            //add listeners here if necessary
            final int finalPosition = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!imageViews.get(finalPosition).getSubAction().toString().equals("show_web")) {
                        Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                        intent.putExtra("goodsUrl",imageViews.get(finalPosition).getFlashSaleUrl().toString());
                        startActivity(intent);
                    }else{
                        Intent intent=new Intent(getContext(), TitleWebViewActivity.class);
                        intent.putExtra("goodsUrl",imageViews.get(finalPosition).getFlashSaleUrl().toString());
                        intent.putExtra("name","VACYHOME小书包+红领巾套餐");
                        startActivity(intent);
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //今日推荐
    public void initTitleTodayref() {

        btn_left = (ImageButton) view.findViewById(R.id.title_custorm_taday_ref_btn_left);
        btn_right = (ImageButton) view.findViewById(R.id.title_custorm_taday_ref_btn_right);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.title_custorm_taday_ref_vp_ll);
        viewPager = (ViewPager) view.findViewById(R.id.title_custorm_taday_ref_vp);
        adapter = new MyAdapter(recommendList);
        viewPager.setAdapter(adapter);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static float r(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    //今天推荐 的button的左右 按钮切换图片 titilefragmtnLayout的点击跳转
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_custorm_taday_ref_btn_left:
                index = index - 1;
                Log.i("aaa", "leftindex" + index);
                if (index < 0) {
                    viewPager.setCurrentItem(2);
                    index = 2;
                } else {
                    viewPager.setCurrentItem(index);
                }
                break;
            case R.id.title_custorm_taday_ref_btn_right:
                index = index + 1;
                Log.i("aaa", "rightindex" + index);
                if (index > views.size() - 1) {
                    viewPager.setCurrentItem(0);
                    index = 0;
                } else {
                    viewPager.setCurrentItem(index);
                }
                break;
            case R.id.title_ll_new:
                Intent intent1 = new Intent(getContext(), TitleLineatLayoutActivity.class);
                intent1.putExtra("name", "最新上市");
                intent1.putExtra("url", "http://uuyichu.com/api/goods/album_v4/?alias=sortnew&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                intent1.putExtra("imgurl", "http://7xiekw.com2.z0.glb.qiniucdn.com/shangxin_banner_v5.jpg");
                intent1.putExtra("title", "");
                intent1.putExtra("content", "");
                startActivity(intent1);
                break;
            case R.id.title_ll_sell:
                Intent intent2 = new Intent(getContext(), TitleLineatLayoutActivity.class);
                intent2.putExtra("name", "官方寄卖");
                intent2.putExtra("url", "http://uuyichu.com/api/goods/album_v4/?alias=jimai&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=1");
                intent2.putExtra("imgurl", "http://7xiekw.com2.z0.glb.qiniucdn.com/jimai_banner_v2.jpg");
                intent2.putExtra("title", "官方寄卖 平台保真");
                intent2.putExtra("content", "所有宝贝由卖家提前发货至良品汇，已通过官方专业鉴定，100%正品保证，照片为良品汇官方实拍，拍下后可立即顺丰发货。");
                startActivity(intent2);
                break;
            case R.id.title_ll_merchant:
                Intent intent3 = new Intent(getContext(), TitleLineatLayoutActivity.class);
                intent3.putExtra("name", "认证商家");
                intent3.putExtra("url", "http://uuyichu.com/api/goods/album_v4/?alias=renzheng&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                intent3.putExtra("imgurl", "http://7xiekw.com2.z0.glb.qiniucdn.com/renzheng_banner.jpg");
                intent3.putExtra("title", "认证商家");
                intent3.putExtra("content", "【认证商家】商家实体门店、营业执照、店主身份已认证，并已缴纳保证金，让您买得更放心。");
                startActivity(intent3);
                break;
            case R.id.title_ll_discount:
                Intent intent4 = new Intent(getContext(), TitleLineatLayoutActivity.class);
                intent4.putExtra("name", "正在打折");
                intent4.putExtra("url", "http://uuyichu.com/api/goods/album_v4/?alias=xsth&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                intent4.putExtra("title", "折扣专区");
                intent4.putExtra("imgurl", "http://7xiekw.com2.z0.glb.qiniucdn.com/zhekou_banner.jpg");
                intent4.putExtra("content", "LOUIS VUITTON,CHANLE,GUESS,VIVIENNE WESTWOOD等多款商品正在折扣中，欢迎抢购");
                startActivity(intent4);
                break;
            case R.id.title_fragment_listview_bags:
                Intent intent5=new Intent(getContext(),TitleListNoImageActivity.class);
                intent5.putExtra("name","包袋");
                intent5.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_baodai&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent5);
                break;
            case R.id.title_fragment_listview_clothing:
                Intent intent6=new Intent(getContext(),TitleListNoImageActivity.class);
                intent6.putExtra("name","服饰");
                intent6.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_fushi&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent6);
                break;
            case R.id.title_fragment_listview_shoes:
                Intent intent7=new Intent(getContext(),TitleListNoImageActivity.class);
                intent7.putExtra("name","鞋履");
                intent7.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_xiexue&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent7);
                break;
            case R.id.title_fragment_listview_jewelry:
                Intent intent8=new Intent(getContext(),TitleListNoImageActivity.class);
                intent8.putExtra("name","首饰");
                intent8.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_shoushi&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent8);
                break;
            case R.id.title_fragment_listview_accessories:
                Intent intent9=new Intent(getContext(),TitleListNoImageActivity.class);
                intent9.putExtra("name","配饰");
                intent9.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_peishi&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent9);
                break;
            case R.id.title_fragment_listview_watches:
                Intent intent10=new Intent(getContext(),TitleListNoImageActivity.class);
                intent10.putExtra("name","腕表");
                intent10.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_wanbiao&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent10);
                break;
            case R.id.title_fragment_listview_apple:
                Intent intent11=new Intent(getContext(),TitleListNoImageActivity.class);
                intent11.putExtra("name","苹果");
                intent11.putExtra("url","http://uuyichu.com/api/goods/album_v4/?alias=home_hot_cate_apple&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=");
                startActivity(intent11);
                break;
        }
    }

    //今日推荐Adapter
    class MyAdapter extends PagerAdapter {
        List<Recommend> recommendL;

        public MyAdapter(List<Recommend> recommendL) {
            this.recommendL = recommendL;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            LinearLayout linearLayout1 = (LinearLayout) views.get(position).findViewById(R.id.title_custorm_taday_ref_item_ll1);
            LinearLayout linearLayout2 = (LinearLayout) views.get(position).findViewById(R.id.title_custorm_taday_ref_item_ll2);
            LinearLayout linearLayout3 = (LinearLayout) views.get(position).findViewById(R.id.title_custorm_taday_ref_item_ll3);
            ImageView iv1 = (ImageView) linearLayout1.findViewById(R.id.title_custorm_taday_ref_item_iv1);
            ImageView iv2 = (ImageView) linearLayout2.findViewById(R.id.title_custorm_taday_ref_item_iv2);
            ImageView iv3 = (ImageView) linearLayout3.findViewById(R.id.title_custorm_taday_ref_item_iv3);
            TextView tvname1 = (TextView) linearLayout1.findViewById(R.id.title_custorm_taday_ref_item_tvname1);
            TextView tvname2 = (TextView) linearLayout2.findViewById(R.id.title_custorm_taday_ref_item_tvname2);
            TextView tvname3 = (TextView) linearLayout3.findViewById(R.id.title_custorm_taday_ref_item_tvname3);
            TextView tvprice1 = (TextView) linearLayout1.findViewById(R.id.title_custorm_taday_ref_item_tvprice1);
            TextView tvprice2 = (TextView) linearLayout2.findViewById(R.id.title_custorm_taday_ref_item_tvprice2);
            TextView tvprice3 = (TextView) linearLayout3.findViewById(R.id.title_custorm_taday_ref_item_tvprice3);
            String price1 = recommendL.get(0 + 3 * position).getPrice().toString();
            String price2 = recommendL.get(1 + 3 * position).getPrice().toString();
            String price3 = recommendL.get(2 + 3 * position).getPrice().toString();
            String name1 = recommendL.get(0 + 3 * position).getRecommendName().toString();
            String name2 = recommendL.get(1 + 3 * position).getRecommendName().toString();
            String name3 = recommendL.get(2 + 3 * position).getRecommendName().toString();
            Log.i("aaa", "position=" + position);
            if (!recommendL.get(0 + 3 * position).getImage().isEmpty()) {
                Log.i("aaa", recommendL.get(0 + 3 * position).getImage());
                Picasso.with(getContext()).load(recommendL.get(0 + 3 * position).getImage()).resize(50, 100).centerCrop().into(iv1);
            }

            if (!recommendL.get(1 + 3 * position).getImage().isEmpty()) {
                Log.i("aaa", recommendL.get(1 + 3 * position).getImage());
                Picasso.with(getContext()).load(recommendL.get(1 + 3 * position).getImage()).resize(50, 100).centerCrop().into(iv2);
            }

            if (!recommendL.get(2 + 3 * position).getImage().isEmpty()) {
                Log.i("aaa", recommendL.get(2 + 3 * position).getImage());
                Picasso.with(getContext()).load(recommendL.get(2 + 3 * position).getImage()).resize(50, 100).centerCrop().into(iv3);
            }

            tvname1.setText(name1);
            tvname2.setText(name2);
            tvname3.setText(name3);
            tvprice1.setText(price1);
            tvprice2.setText(price2);
            tvprice3.setText(price3);
            container.addView(views.get(position));
            final int finalPositon = position;
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                    intent.putExtra("goodsUrl", recommendList.get(0 + 3 * position).getRecommendUrl().toString());
                    startActivity(intent);
                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                    intent.putExtra("goodsUrl", recommendList.get(1 + 3 * position).getRecommendUrl().toString());
                    startActivity(intent);
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                    intent.putExtra("goodsUrl", recommendList.get(2 + 3 * position).getRecommendUrl().toString());
                    startActivity(intent);
                }
            });
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //闲置循环
    private void initGalleryIdle() {
        //固定的图片
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_idle_ll);
        for (int x = 0; x < albumIdleList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumIdleList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumIdleList.get(x).getName().toString());
            tv1.setText("￥" + albumIdleList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumIdleList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumIdleList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumIdleList.clear();
        Log.i("aaa", "abidle" + albumIdleList.size());
    }

    private void initGalleryBags() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_bags_ll);
        for (int x = 0; x < albumBagsList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumBagsList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumBagsList.get(x).getName().toString());
            tv1.setText("￥" + albumBagsList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumBagsList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                    intent.putExtra("goodsUrl", albumBagsList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumBagsList.clear();
    }

    private void initGalleryClothing() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_clothing_ll);
        for (int x = 0; x < albumClothingList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumClothingList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumClothingList.get(x).getName().toString());
            tv1.setText("￥" + albumClothingList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumClothingList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumClothingList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumClothingList.clear();
    }

    private void initGalleryShoes() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_shoes_ll);
        for (int x = 0; x < albumShoesList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumShoesList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumShoesList.get(x).getName().toString());
            tv1.setText("￥" + albumShoesList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumShoesList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumShoesList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });

        }
//        albumShoesList.clear();
    }

    private void initGalleryjewelry() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_jewelry_ll);
        for (int x = 0; x < albumJewelryList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumJewelryList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumJewelryList.get(x).getName().toString());
            tv1.setText("￥" + albumJewelryList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumJewelryList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumJewelryList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });

        }
//        albumJewelryList.clear();
    }

    private void initGalleryaccessories() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_accessories_ll);
        for (int x = 0; x < albumAccessoriesList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumAccessoriesList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumAccessoriesList.get(x).getName().toString());
            tv1.setText("￥" + albumAccessoriesList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumAccessoriesList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumAccessoriesList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });

        }
//        albumAccessoriesList.clear();
    }

    private void initGallerywatches() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_watches_ll);
        for (int x = 0; x < albumWatchesList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumWatchesList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumWatchesList.get(x).getName().toString());
            tv1.setText("￥" + albumWatchesList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumWatchesList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumWatchesList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumWatchesList.clear();
    }

    private void initGalleryapple() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_apple_ll);
        for (int x = 0; x < albumAppleList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumAppleList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumAppleList.get(x).getName().toString());
            tv1.setText("￥" + albumAppleList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumAppleList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumAppleList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });

        }
//        albumAppleList.clear();
    }

    private void initGallerysijin() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_sijin_ll);
        for (int x = 0; x < albumSijinList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumSijinList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumSijinList.get(x).getName().toString());
            tv1.setText("￥" + albumSijinList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumSijinList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumSijinList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumSijinList.clear();
    }

    private void initGalleryjingdian() {

        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_jingdian_ll);
        for (int x = 0; x < albumJingdianList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumJingdianList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumJingdianList.get(x).getName().toString());
            tv1.setText("￥" + albumJingdianList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumJingdianList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumJingdianList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumJingdianList.clear();
    }

    private void initGallerydiscount() {

        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_discount_ll);
        for (int x = 0; x < albumDiscountList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumDiscountList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumDiscountList.get(x).getName().toString());
            tv1.setText("￥" + albumDiscountList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumDiscountList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumDiscountList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumDiscountList.clear();
    }

    private void initGalleryfjingdian() {

        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_fjingdian_ll);
        for (int x = 0; x < albumFjingdianList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumFjingdianList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumFjingdianList.get(x).getName().toString());
            tv1.setText("￥" + albumFjingdianList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumFjingdianList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumFjingdianList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumFjingdianList.clear();
    }

    private void initGallerycoach() {
        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_coach_ll);
        for (int x = 0; x < albumCoachList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumCoachList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumCoachList.get(x).getName().toString());
            tv1.setText("￥" + albumCoachList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumCoachList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumCoachList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumCoachList.clear();
    }

    private void initGallerypeishi() {

        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_peishi_ll);
        for (int x = 0; x < albumPeishiList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumPeishiList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumPeishiList.get(x).getName().toString());
            tv1.setText("￥" + albumPeishiList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumPeishiList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumPeishiList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumPeishiList.clear();
    }

    private void initGallerytory() {

        layoutInflater = LayoutInflater.from(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.title_fragment_listview_tory_ll);
        for (int x = 0; x < albumToryList.size(); x++) {
            View view = layoutInflater.inflate(R.layout.horizontalscrollview_item, linearLayout, false);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_item_activity);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_hs);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_hs);
            TextView tv1 = (TextView) view.findViewById(R.id.tv1_item_hs);
            Picasso.with(getContext()).load(albumToryList.get(x).getImage()).resize(60, 40).centerCrop().into(iv);
            tv.setText(albumToryList.get(x).getName().toString());
            tv1.setText("￥" + albumToryList.get(x).getPrice().toString());
            linearLayout.addView(view);
            final int finalX = x;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("aaaa",albumToryList.get(finalX).getItemUrl().toString());
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);

                    intent.putExtra("goodsUrl", albumToryList.get(finalX).getItemUrl().toString());
                    startActivity(intent);
                }
            });
        }
//        albumToryList.clear();
    }


    public void initData() {
        taDayHanlder = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    initTitleTodayref();
                    initFalshsale();
                    initGalleryIdle();
                    initGalleryBags();
                    initGalleryClothing();
                    initGalleryShoes();
                    initGalleryapple();
                    initGalleryaccessories();
                    initGalleryjewelry();
                    initGallerywatches();
                    initGallerysijin();
                    initGalleryjingdian();
                    initGallerydiscount();
                    initGalleryfjingdian();
                    initGallerycoach();
                    initGallerypeishi();
                    initGallerytory();
                    clearhs();
                    Log.i("aaa", "清空hs" + albumBagshs.size());

                }
            }
        };
        initList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                recommendhs = tzsqLiteDao.getTiaoZao("recommend", new String[]{"recommendName", "price", "image", "recommendUrl"}, null, null, null);
                for (int i = 0; i < recommendhs.size(); i++) {
                    HashMap<String, Object> data = recommendhs.get(i);
                    Recommend recommend = new Recommend();
                    recommend.setPrice(data.get("price").toString());
                    recommend.setImage(data.get("image").toString());
                    recommend.setRecommendName(data.get("recommendName").toString());
                    recommend.setRecommendUrl(data.get("recommendUrl").toString());
                    recommendList.add(recommend);
                }
                flashSalehs = tzsqLiteDao.getTiaoZao("flashSale", new String[]{"image", "subAction", "flashSaleUrl"}, null, null, null);
                Log.i("aaa", "fhs" + flashSalehs.size());
                for (int j = 0; j < flashSalehs.size(); j++) {
                    HashMap<String, Object> fdata = flashSalehs.get(j);
                    FlashSale flashSale = new FlashSale();
                    flashSale.setImage(fdata.get("image").toString());
                    flashSale.setFlashSaleUrl(fdata.get("flashSaleUrl").toString());
                    flashSale.setSubAction(fdata.get("subAction").toString());
                    flashSaleList.add(flashSale);
                }
                Log.i("aaa", "添加flash" + flashSaleList.size());
                Log.i("aaa", "添加前flashhs" + albumhs.size());
                albumhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"jimai"}, null);
                Log.i("aaa", "添加flashhs" + albumhs.size());
                for (int m = 0; m < albumhs.size(); m++) {
                    HashMap<String, Object> adata = albumhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    Log.i("aaa", album.toString());
                    albumIdleList.add(album);
                }
                Log.i("aaa", "添加flash" + albumIdleList.size());
                albumBagshs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_baodai"}, null);
                for (int m = 0; m < albumBagshs.size(); m++) {
                    HashMap<String, Object> adata = albumBagshs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumBagsList.add(album);
                }

                albumClothinghs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_fushi"}, null);
                for (int m = 0; m < albumClothinghs.size(); m++) {
                    HashMap<String, Object> adata = albumClothinghs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumClothingList.add(album);
                }
                albumShoeshs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_xiexue"}, null);
                for (int m = 0; m < albumShoeshs.size(); m++) {
                    HashMap<String, Object> adata = albumShoeshs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumShoesList.add(album);
                }
                albumJewelryhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_shoushi"}, null);
                for (int m = 0; m < albumJewelryhs.size(); m++) {
                    HashMap<String, Object> adata = albumJewelryhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumJewelryList.add(album);
                }
                albumAccessorieshs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_peishi"}, null);
                for (int m = 0; m < albumAccessorieshs.size(); m++) {
                    HashMap<String, Object> adata = albumAccessorieshs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumAccessoriesList.add(album);
                }
                albumWatcheshs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_wanbiao"}, null);
                for (int m = 0; m < albumWatcheshs.size(); m++) {
                    HashMap<String, Object> adata = albumWatcheshs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumWatchesList.add(album);
                }
                albumApplehs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"home_hot_cate_apple"}, null);
                for (int m = 0; m < albumApplehs.size(); m++) {
                    HashMap<String, Object> adata = albumApplehs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumAppleList.add(album);
                }
                albumSijinhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"xiarisijin"}, null);
                for (int m = 0; m < albumSijinhs.size(); m++) {
                    HashMap<String, Object> adata = albumSijinhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumSijinList.add(album);
                }
                albumJingdianhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"chanel"}, null);
                for (int m = 0; m < albumJingdianhs.size(); m++) {
                    HashMap<String, Object> adata = albumJingdianhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumJingdianList.add(album);
                }
                albumDiscounths = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"xsth"}, null);
                for (int m = 0; m < albumDiscounths.size(); m++) {
                    HashMap<String, Object> adata = albumDiscounths.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumDiscountList.add(album);
                }
                albumFjingdianhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"balenciaga"}, null);
                for (int m = 0; m < albumFjingdianhs.size(); m++) {
                    HashMap<String, Object> adata = albumFjingdianhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumFjingdianList.add(album);
                }
                albumCoachhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"subject_coach"}, null);
                for (int m = 0; m < albumCoachhs.size(); m++) {
                    HashMap<String, Object> adata = albumCoachhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumCoachList.add(album);
                }
                albumPeishihs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"jewelryqingshe"}, null);
                for (int m = 0; m < albumPeishihs.size(); m++) {
                    HashMap<String, Object> adata = albumPeishihs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumPeishiList.add(album);
                }
                albumToryhs = tzsqLiteDao.getTiaoZao("album", new String[]{"price", "image", "name","itemUrl"}, "album_action_alias =?", new String[]{"torynew"}, null);
                for (int m = 0; m < albumToryhs.size(); m++) {
                    HashMap<String, Object> adata = albumToryhs.get(m);
                    Album album = new Album();
                    album.setImage(adata.get("image").toString());
                    album.setPrice(adata.get("price").toString());
                    album.setName(adata.get("name").toString());
                    album.setItemUrl(adata.get("itemUrl").toString());
                    albumToryList.add(album);
                }
                taDayHanlder.sendEmptyMessage(1);
            }
        }).start();
    }


    public void initList() {
        tzsqLiteDao = new TZSQLiteDao(getContext());
        recommendList = new ArrayList<>();
        recommendhs = new ArrayList<>();
        flashSalehs = new ArrayList<>();
        albumhs = new ArrayList<>();
        albumBagshs = new ArrayList<>();
        albumClothinghs = new ArrayList<>();
        albumShoeshs = new ArrayList<>();
        albumJewelryhs = new ArrayList<>();
        albumAccessorieshs = new ArrayList<>();
        albumWatcheshs = new ArrayList<>();
        albumApplehs = new ArrayList<>();
        albumSijinhs = new ArrayList<>();
        albumJingdianhs = new ArrayList<>();
        albumDiscounths = new ArrayList<>();
        albumFjingdianhs = new ArrayList<>();
        albumCoachhs = new ArrayList<>();
        albumPeishihs = new ArrayList<>();
        albumToryhs = new ArrayList<>();

        flashSaleList = new ArrayList<>();
        albumIdleList = new ArrayList<>();
        albumBagsList = new ArrayList<>();
        albumClothingList = new ArrayList<>();
        albumShoesList = new ArrayList<>();
        albumJewelryList = new ArrayList<>();
        albumAccessoriesList = new ArrayList<>();
        albumWatchesList = new ArrayList<>();
        albumAppleList = new ArrayList<>();
        albumSijinList = new ArrayList<>();
        albumJingdianList = new ArrayList<>();
        albumDiscountList = new ArrayList<>();
        albumFjingdianList = new ArrayList<>();
        albumCoachList = new ArrayList<>();
        albumPeishiList = new ArrayList<>();
        albumToryList = new ArrayList<>();
    }

    public void clearhs() {
        recommendhs.clear();
        flashSalehs.clear();
        albumhs.clear();
        albumBagshs.clear();
        albumClothinghs.clear();
        albumShoeshs.clear();
        albumJewelryhs.clear();
        albumAccessorieshs.clear();
        albumWatcheshs.clear();
        albumApplehs.clear();
        albumSijinhs.clear();
        albumJingdianhs.clear();
        albumDiscounths.clear();
        albumFjingdianhs.clear();
        albumCoachhs.clear();
        albumPeishihs.clear();
        albumToryhs.clear();

    }
}