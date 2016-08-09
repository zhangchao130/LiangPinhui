package com.google.lenono.tiaozaoproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.lenono.sqlite.TZSQLiteDao;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressEditActivity extends AppCompatActivity {
    @Bind(R.id.address_toolbar)
    Toolbar toolbar;
    @Bind(R.id.address_name)
    TextView tv_name;
    @Bind(R.id.address_phone)
    TextView tv_phone;
    @Bind(R.id.address_basic)
    TextView tv_basic;
    @Bind(R.id.address_detail)
    TextView tv_detail;

    private TZSQLiteDao dao;
    private String user_id;
    private int address_num;
    private String basic_nickname,basic_phone_num,basic_address,basic_address_detail,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.home_recommend_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent=getIntent();
        user_id=intent.getStringExtra("user_id");
        address_num=intent.getIntExtra("address_num",0);

        type=intent.getStringExtra("type");

        if(type!=null&&type.equals("edit")){
            basic_nickname=intent.getStringExtra("nickname");
            basic_phone_num=intent.getStringExtra("phone_num");
            basic_address=intent.getStringExtra("address");
            basic_address_detail=intent.getStringExtra("address_detail");

            tv_name.setText(basic_nickname);
            tv_phone.setText(basic_phone_num);
            tv_basic.setText(basic_address);
            tv_detail.setText(basic_address_detail);
        }

        dao=new TZSQLiteDao(this);
    }

    public void clickButton(View view){
        String nickname=tv_name.getText().toString();
        String phone_num=tv_phone.getText().toString();
        String address=tv_basic.getText().toString();
        String address_detail=tv_detail.getText().toString();

        if(type!=null&&type.equals("edit")){
           boolean orderUpdate=dao.updateOrder("user_address",new String[]{"nickname","phone_num","address","address_detail"},new String[]{nickname,phone_num,address,address_detail},"id=? and nickname=? and phone_num=? and address=? and address_detail=?",new String[]{user_id,basic_nickname,basic_phone_num,basic_address,basic_address_detail});
            if(orderUpdate){
                finish();
            }else {
                Toast.makeText(this,"保存地址失败",Toast.LENGTH_SHORT).show();
            }
        }else{
            if(TextUtils.isEmpty(nickname)||TextUtils.isEmpty(phone_num)||TextUtils.isEmpty(address)||TextUtils.isEmpty(address_detail)){
                Toast.makeText(this,"请完善地址信息",Toast.LENGTH_SHORT).show();
            }else {
                boolean isOk=dao.insertUserInfo("user_address",new String[]{"id","nickname","phone_num","address","address_detail"},new String[]{user_id,nickname,phone_num,address,address_detail});
                boolean update=dao.updateOrder("user_info",new String[]{"address_num"},new String[]{address_num+1+""},"id=?",new String[]{user_id});
                if (isOk&&update){
                    finish();
                }else {
                    Toast.makeText(this,"保存地址失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
