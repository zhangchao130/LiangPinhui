package com.google.lenono.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.MenuPopupWindow;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.lenono.common.User_info;
import com.google.lenono.sellinfotoacitivuty.SellInfoToActivity;
import com.google.lenono.sellinfotoacitivuty.SellinfosellodorActivity;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.OrderAddressActivity;
import com.google.lenono.tiaozaoproject.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lenono on 2016-07-17.
 */
public class SellInfoFragment extends Fragment implements View.OnClickListener {
    Button btn1, btn2;
    TextView tv;
    CircleImageView circleImageView;
    TZSQLiteDao tzsqLiteDao;
    String tel;
    List<HashMap<String, Object>> telInfo;
    Handler handler;
    User_info info;
    View.OnClickListener listener;
    Intent intent;
    SharedPreferences spf;
    View view;
    LinearLayout idle, haitao, shoumai, jimai, fabu, shoucang, lishi, youhui, yue, address, sellinfo, feedback, updata, about;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sellinfofragment_item, null);
        initListener();
        tzsqLiteDao = new TZSQLiteDao(getContext());

        isFirstSMSLogin();
        selectHead();
        return view;
    }

    public void initListener() {
        btn1 = (Button) view.findViewById(R.id.sellinfo_btn1);
        btn2 = (Button) view.findViewById(R.id.sellinfo_btn2);
        tv = (TextView) view.findViewById(R.id.sellinfo_tv);
        circleImageView = (CircleImageView) view.findViewById(R.id.sellinfo_iv);
        btn2.setVisibility(View.INVISIBLE);
        btn2.setEnabled(false);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        idle = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_llidle);
        haitao = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_haitao);
        shoumai = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_shoumai);
        jimai = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_jimai);
        fabu = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_fabu);
        shoucang = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_shoucang);
        lishi = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_lishi);
        youhui = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_youhui);
        yue = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_yue);
        address = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_address);
        sellinfo = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_info);
        feedback = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_feedback);
        updata = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_update);
        about = (LinearLayout) view.findViewById(R.id.sellinfo_fragment_about);
        idle.setOnClickListener(this);
        haitao.setOnClickListener(this);
        shoumai.setOnClickListener(this);
        jimai.setOnClickListener(this);
        fabu.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        lishi.setOnClickListener(this);
        youhui.setOnClickListener(this);
        yue.setOnClickListener(this);
        address.setOnClickListener(this);
        sellinfo.setOnClickListener(this);
        feedback.setOnClickListener(this);
        updata.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300) {
            Log.i("aaaa", data.getStringExtra("tel"));
            btn1.setVisibility(View.INVISIBLE);
            btn1.setEnabled(false);
            btn2.setVisibility(View.VISIBLE);
            btn2.setEnabled(true);
            tel = data.getStringExtra("tel");
            tv.setText(tel);
            tzsqLiteDao.insertUserInfo("user_info", new String[]{"id"}, new String[]{tel});

        }
        if (requestCode == 101 && resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            //系统固定格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            saveBitmapToSDcard(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), "myphoto.png");
            circleImageView.setImageBitmap(bitmap);
        }
        if (requestCode == 102 && resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            //系统固定格式
            Bitmap bitmap = bundle.getParcelable("data");
            //   saveBitmapToSDcard(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), "myphoto.png");

            circleImageView.setImageBitmap(bitmap);
        }
    }

    private void isFirstSMSLogin() {

        spf = getActivity().getSharedPreferences("isFirstSMSLogin", Context.MODE_PRIVATE);
        final boolean flg = spf.getBoolean("isSMSLogin", false);
        if (!flg) {
            btn1.setVisibility(View.VISIBLE);
            btn1.setEnabled(true);
            btn2.setVisibility(View.INVISIBLE);
            btn2.setEnabled(false);
        } else {
            //隐藏button按钮
            Log.i("aaaa", "第二次登陆");
            btn1.setVisibility(View.INVISIBLE);
            btn1.setEnabled(false);
            btn2.setVisibility(View.VISIBLE);
            btn2.setEnabled(true);
            //从数据库根据号码拿数据
            final String oldTel = spf.getString("tel", null);
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 10) {
                        initdata();
                        tv.setText(info.getId());
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    telInfo = new ArrayList<HashMap<String, Object>>();
                    telInfo = tzsqLiteDao.getUserInfo("user_info", new String[]{"id"}, "id=?", new String[]{oldTel}, null);
                    handler.sendEmptyMessage(10);
                }
            }).start();
        }
    }

    public void initdata() {
        HashMap<String, Object> user_info = new HashMap<>();
        for (int i = 0; i < telInfo.size(); i++) {
            user_info = telInfo.get(i);
            info = new User_info();
            info.setId(user_info.get("id").toString());
        }
    }

    //选择头像
    public void selectHead() {

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View Menu = LayoutInflater.from(getContext()).inflate(R.layout.handware_popupwindow_item, null);
                MenuPopupWindow popupWindow = new MenuPopupWindow(getContext(), Menu, listener);
                popupWindow.showAtLocation(getView().findViewById(R.id.sellinfo_fragment_ll), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.menu_camera:
                        Log.i("aaa", "打开相机");
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 101);
                        break;
                    case R.id.menu_photo:
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 2);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 200);
                        intent.putExtra("outputY", 100);
                        intent.putExtra("result-data", true);
                        startActivityForResult(intent, 102);
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sellinfo_btn1:
                Intent intent1 = new Intent(getActivity(), SellInfoToActivity.class);
                startActivityForResult(intent1, 200);
                break;
            case R.id.sellinfo_btn2:
                SharedPreferences.Editor editor = spf.edit();
                editor.putBoolean("isSMSLogin", false);
                editor.putString("tel", null);
                editor.commit();
                tv.setText("");
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn2.setEnabled(false);
                btn1.setEnabled(true);
                break;
            case R.id.sellinfo_fragment_llidle:
                Intent intent = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent.putExtra("name", "闲置订单");
                startActivity(intent);
                break;
            case R.id.sellinfo_fragment_haitao:
                Intent intent2 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent2.putExtra("name", "海淘订单");
                startActivity(intent2);
                break;
            case R.id.sellinfo_fragment_shoumai:
                Intent intent3 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent3.putExtra("name", "售卖订单");
                startActivity(intent3);
                break;
            case R.id.sellinfo_fragment_jimai:
                Intent intent4 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent4.putExtra("name", "寄卖的宝贝");
                startActivity(intent4);
                break;
            case R.id.sellinfo_fragment_fabu:
                Intent intent5 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent5.putExtra("name", "发布的宝贝");
                startActivity(intent5);
                break;
            case R.id.sellinfo_fragment_shoucang:
                Intent intent6 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent6.putExtra("name", "我的收藏");
                startActivity(intent6);
                break;
            case R.id.sellinfo_fragment_lishi:
                Intent intent7 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent7.putExtra("name", "浏览历史");
                startActivity(intent7);
                break;
            case R.id.sellinfo_fragment_youhui:
                Intent intent8 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent8.putExtra("name", "优惠券");
                startActivity(intent8);
                break;
            case R.id.sellinfo_fragment_yue:
                Intent intent9 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent9.putExtra("name", "帐户余额");
                startActivity(intent9);
                break;
            case R.id.sellinfo_fragment_address:
                String telphone = tv.getText().toString();
                if (telphone != null) {
                    Intent intent10 = new Intent(getActivity(), OrderAddressActivity.class);
                    intent10.putExtra("user_id", telphone);
                    startActivity(intent10);
                }
                break;
            case R.id.sellinfo_fragment_feedback:
                Intent intent11 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent11.putExtra("name", "用户反馈");
                startActivity(intent11);
                break;
            case R.id.sellinfo_fragment_update:
                Intent intent12 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent12.putExtra("name", "检查更新");
                startActivity(intent12);
                break;
            case R.id.sellinfo_fragment_about:
                Intent intent13 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent13.putExtra("name", "关于");
                startActivity(intent13);
                break;
            case R.id.sellinfo_fragment_info:
                Intent intent14 = new Intent(getActivity(), SellinfosellodorActivity.class);
                intent14.putExtra("name", "身份信息");
                startActivity(intent14);
                break;
        }
    }

    class MenuPopupWindow extends PopupWindow {
        Button btn_open_camera, btn_open_photo, btn_cancel;

        public MenuPopupWindow(Context context, View menuView, View.OnClickListener listener) {
            super(context);
            btn_open_camera = (Button) menuView.findViewById(R.id.menu_camera);
            btn_open_photo = (Button) menuView.findViewById(R.id.menu_photo);
            btn_cancel = (Button) menuView.findViewById(R.id.menu_cancel);
            btn_open_photo.setOnClickListener(listener);
            btn_open_camera.setOnClickListener(listener);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            this.setContentView(menuView);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setFocusable(true);
            //this.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
            //设置点击其他部分是否能关闭掉 false不能点击窗口外围
            this.setOutsideTouchable(false);

        }
    }

    public void saveBitmapToSDcard(Bitmap bitmap, String path, String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String filepath = path + File.separator + fileName;
            File file = new File(filepath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                Log.i("aaa", filepath);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
