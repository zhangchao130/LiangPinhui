package com.google.lenono.tiaozaoproject;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.lenono.adapter.OrderAddressAdapter;
import com.google.lenono.service.GoodDetailService;
import com.google.lenono.sqlite.TZSQLiteDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderAddressActivity extends AppCompatActivity {
    @Bind(R.id.order_address_toolbar)
    Toolbar toolbar;
    @Bind(R.id.order_address_listview)
    ListView listview;

    private Handler handler;
    private TZSQLiteDao dao;
    public static List<HashMap<String, Object>> addressInfo;
    private OrderAddressAdapter adapter;
    private String user_id;
    private int currentPosition;

    final int EDIT=0x001;
    final int DEl=0x002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address);

        ButterKnife.bind(this);

        toolbar.setTitle("收货地址");
        toolbar.setNavigationIcon(R.drawable.home_recommend_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("position",0);
                setResult(200,intent);
                finish();
            }
        });

        dao=new TZSQLiteDao(this);
        registerForContextMenu(listview);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==2){
                    Log.i("aaa","-----address----"+addressInfo);
                    adapter.notifyDataSetChanged();
                    if(addressInfo.size()==0){
                        //背景设置下
                    }
                }
            }
        };

        Intent getIntent=getIntent();
        user_id=getIntent.getStringExtra("user_id");

        addressInfo=new ArrayList<>();
        adapter=new OrderAddressAdapter(this,addressInfo);
        listview.setAdapter(adapter);

        initListener();
    }

    public void initListener(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("aaa","position--"+position);
                Intent intent=new Intent();
                intent.putExtra("position",position);
                setResult(200,intent);
                finish();
            }
        });
    }

    public void initService(){
        Intent intent=new Intent(this, GoodDetailService.class);
        ServiceConnection serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                GoodDetailService.GoodDetailBinder binder= (GoodDetailService.GoodDetailBinder) iBinder;
                binder.getOrder(handler,dao,null,user_id);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    public void clickButton(View view){
        Intent intent=new Intent(this,AddressEditActivity.class);
        intent.putExtra("user_id",user_id);
        intent.putExtra("address_num",addressInfo.size());
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem menu1=menu.add(Menu.NONE,EDIT,Menu.NONE,"编辑");
        MenuItem menu2=menu.add(Menu.NONE,DEl,Menu.NONE,"删除");

        AdapterView.AdapterContextMenuInfo acmi= (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentPosition=acmi.position;

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String nickname=null;
        String phone_num=null;
        String address=null;
        String address_detail=null;

        HashMap<String,Object> map=addressInfo.get(currentPosition);
        nickname=map.get("nickname").toString();
        phone_num=map.get("phone_num").toString();
        address=map.get("address").toString();
        address_detail=map.get("address_detail").toString();

        switch (item.getItemId()){
            case EDIT:
                Intent intent=new Intent(this,AddressEditActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("address_num",addressInfo.size());

                intent.putExtra("nickname",nickname);
                intent.putExtra("phone_num",phone_num);
                intent.putExtra("address",address);
                intent.putExtra("address_detail",address_detail);
                intent.putExtra("type","edit");
                startActivity(intent);
                break;
            case DEl:
                dao.delete("user_address","id=? and nickname=? and phone_num=? and address=? and address_detail=?",new String[]{user_id,nickname,phone_num,address,address_detail});
                addressInfo.remove(currentPosition);
                boolean update=dao.updateOrder("user_info",new String[]{"address_num"},new String[]{addressInfo.size()+""},"id=?",new String[]{user_id});
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initService();
    }
}
