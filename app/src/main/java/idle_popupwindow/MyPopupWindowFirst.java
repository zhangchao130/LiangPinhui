package idle_popupwindow;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.lenono.service.Idle_DownloadService;
import com.google.lenono.tiaozaoproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

;

/**
 * Created by Administrator on 2016/7/23.
 */
public class MyPopupWindowFirst extends PopupWindow {
   private Context context;
    private View menuView;
    private ListView listView,listView01;
    private List<String> rightlist;
    private TextView currentTextView ;
    private TextView tv;
    private int tag;

    private String[] left_sort={"全部","包袋","服饰","首饰","配饰","腕表","鞋靴","苹果设备"};
    private String[] packages={"全部包袋","单肩包","手提包","斜挎包","双肩包","公文包","化妆包","手包/手拿包","钱包/卡包","腰包/胸包","旅行箱/行李箱","机车包","其他"};
    private String[] clothes={"全部服饰","羽绒服","大衣","风衣","小西装","短外套","针织衫","衬衫","短裙","裤装","其他"};
    private String[] jewelry={"全部首饰","手镯/手链/手环","项链/吊坠","戒指/指环","耳坠/耳环/耳钉","胸针","首饰套装","发箍/发带...绳/发卡","其他"};
    private String[] acc={"全部配饰","腰带/腰链","太阳镜/眼镜","帽子","手套","披肩","围巾/丝巾/方巾","袖扣/领扣","钥匙扣/挂件","丝巾扣/腰带扣","其他"};
    private String[] watchs={"全部腕表","机械表","石英表","电子表","其他"};
    private String[] shoes={"全部鞋靴","靴子","高跟鞋","中跟鞋","平底鞋","运动鞋","凉鞋/拖鞋","其他"};
    private String[] apples={"全部苹果设备","iPhone","iPad","MacBook","Apple Watch","其他"};

    public MyPopupWindowFirst(Context context, TextView tv) {
       this.context=context;
        this.tv=tv;
    }
//    初始化popupwindow
    public void initPopWindow(){
        menuView= LayoutInflater.from(context).inflate(R.layout.idle_popupwindow_first,null);
        listView=(ListView)menuView.findViewById(R.id.idle_popupwindow_first_leftlv);
        listView01=(ListView)menuView.findViewById(R.id.idle_popupwindow_first_rightlv);

        this.setContentView(menuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        this.setOutsideTouchable(true);
        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, Arrays.asList(left_sort));
        listView.setAdapter(arrayAdapter1);
        rightlist=new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,rightlist);
        listView01.setAdapter(arrayAdapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if (currentTextView!=null){
                   currentTextView.setTextColor(Color.parseColor("#000000"));
               }
                currentTextView=(TextView)view;

              switch (i){
                  case 0:
                      tag=0;
                      rightlist.clear();
                      break;
                  case 1:
                      tag=1;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(packages));

                      break;
                  case 2:
                      tag=2;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(clothes));
                      break;
                  case 3:
                      tag=3;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(jewelry));
                      break;
                  case 4:
                      tag=4;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(acc));
                      break;
                  case 5:
                      tag=5;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(watchs));
                      break;
                  case 6:
                      tag=6;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(shoes));
                      break;
                  case 7:
                     tag=7;
                      currentTextView.setTextColor(Color.parseColor("#ff0000"));
                      rightlist.clear();
                      rightlist.addAll(Arrays.asList(apples));
                      break;
              }
                arrayAdapter2.notifyDataSetChanged();
            }
        });
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                进行页面切换
                MyPopupWindowFirst.this.dismiss();
                tv.setText(adapterView.getItemAtPosition(i).toString());
                Intent intent=new Intent(context, Idle_DownloadService.class);
               if (tag==1){
                   switch(i){
                       case 0:
                           intent.putExtra("cate",2000);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 1:
                           intent.putExtra("cate",2001);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);

                           break;
                       case 2:
                           intent.putExtra("cate",2002);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 3:
                           intent.putExtra("cate",2003);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 4:
                           intent.putExtra("cate",2004);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 5:
                           intent.putExtra("cate",2005);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 6:
                           intent.putExtra("cate",2006);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 7:
                           intent.putExtra("cate",2007);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 8:
                           intent.putExtra("cate",2008);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 9:
                           intent.putExtra("cate",2009);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 10:
                           intent.putExtra("cate",2010);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 11:
                           intent.putExtra("cate",2011);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 12:
                           intent.putExtra("cate",2012);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;
                       case 13:
                           intent.putExtra("cate",2099);
                           intent.putExtra("brand",-1);
                           intent.putExtra("condition",-1);
                           break;

                   }
               }
                if (tag==2){
                    switch(i){
                        case 0:
                            intent.putExtra("cate",2100);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 1:
                            intent.putExtra("cate",2101);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);

                            break;
                        case 2:
                            intent.putExtra("cate",2102);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 3:
                            intent.putExtra("cate",2103);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 4:
                            intent.putExtra("cate",2104);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 5:
                            intent.putExtra("cate",2105);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 6:
                            intent.putExtra("cate",2106);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 7:
                            intent.putExtra("cate",2107);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 8:
                            intent.putExtra("cate",2108);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 9:
                            intent.putExtra("cate",2109);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 10:
                            intent.putExtra("cate",2110);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                    }
                }
                if (tag==3){
                    switch(i){
                        case 0:
                            intent.putExtra("cate",2200);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 1:
                            intent.putExtra("cate",2201);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);

                            break;
                        case 2:
                            intent.putExtra("cate",2202);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 3:
                            intent.putExtra("cate",2203);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 4:
                            intent.putExtra("cate",2204);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 5:
                            intent.putExtra("cate",2205);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 6:
                            intent.putExtra("cate",2206);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 7:
                            intent.putExtra("cate",2207);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 8:
                            intent.putExtra("cate",2208);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;

                    }
                }
                if (tag==4){
                    switch(i){
                        case 0:
                            intent.putExtra("cate",2300);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 1:
                            intent.putExtra("cate",2301);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);

                            break;
                        case 2:
                            intent.putExtra("cate",2302);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 3:
                            intent.putExtra("cate",2303);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 4:
                            intent.putExtra("cate",2304);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 5:
                            intent.putExtra("cate",2305);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 6:
                            intent.putExtra("cate",2306);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 7:
                            intent.putExtra("cate",2307);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 8:
                            intent.putExtra("cate",2308);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 9:
                            intent.putExtra("cate",2309);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 10:
                            intent.putExtra("cate",2310);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 11:
                            intent.putExtra("cate",2399);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                    }
                }
                if (tag==5){
                    switch(i){
                        case 0:
                            intent.putExtra("cate",2400);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 1:
                            intent.putExtra("cate",2401);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);

                            break;
                        case 2:
                            intent.putExtra("cate",2402);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 3:
                            intent.putExtra("cate",2403);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 4:
                            intent.putExtra("cate",2099);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;

                    }
                }
                if (tag==6){
                    switch(i){
                        case 0:
                            intent.putExtra("cate",2500);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 1:
                            intent.putExtra("cate",2501);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);

                            break;
                        case 2:
                            intent.putExtra("cate",2502);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 3:
                            intent.putExtra("cate",2403);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 4:
                            intent.putExtra("cate",2404);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 5:
                            intent.putExtra("cate",2405);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 6:
                            intent.putExtra("cate",2406);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 7:
                            intent.putExtra("cate",2407);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 8:
                            intent.putExtra("cate",2499);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;

                    }
                }
                if (tag==7){
                    switch(i){
                        case 0:
                            intent.putExtra("cate",2600);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 1:
                            intent.putExtra("cate",2601);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);

                            break;
                        case 2:
                            intent.putExtra("cate",2602);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 3:
                            intent.putExtra("cate",2603);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 4:
                            intent.putExtra("cate",2604);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                        case 5:
                            intent.putExtra("cate",2699);
                            intent.putExtra("brand",-1);
                            intent.putExtra("condition",-1);
                            break;
                    }
                }
                context.startService(intent);
            }
        });
    }
}