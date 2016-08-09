package idle_popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.lenono.adapter.MyPopupWindowSecondAdapter;
import com.google.lenono.service.Idle_DownloadService;
import com.google.lenono.tiaozaoproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public class MyPopupWindowSecond extends PopupWindow{
    private Context context;
    private View menuView;
    private ListView listView;
    private Button btn;
    private TextView currentTextView;
    private TextView tv;
    private List<HashMap<String,Object>> data;


    public MyPopupWindowSecond(Context context, TextView tv) {
        this.context = context;
        this.tv = tv;
    }
    public void initPopupWindow(){
        menuView= LayoutInflater.from(context).inflate(R.layout.idle_popupwindow_second,null);
        listView=(ListView) menuView.findViewById(R.id.idle_popupwindow_second_lv);

        this.setContentView(menuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        this.setOutsideTouchable(true);

        data=new ArrayList<>();
        HashMap<String,Object> map1=new HashMap<>();
        map1.put("img", R.drawable.hot_cate_all);
        map1.put("name","不限品牌");
        HashMap<String,Object> map2=new HashMap<>();
        map2.put("img", R.drawable.brand_icon_polo);
        map2.put("name","Polo Ralph lauren|拉夫·劳伦");
        HashMap<String,Object> map3=new HashMap<>();
        map3.put("img", R.drawable.brand_icon_miumiu);
        map3.put("name","Miu Miu|缪缪");
        HashMap<String,Object> map4=new HashMap<>();
        map4.put("img", R.drawable.brand_icon_michaelkors);
        map4.put("name","Michael Kors|迈克高仕");
        HashMap<String,Object> map5=new HashMap<>();
        map5.put("img", R.drawable.brand_icon_louisvuitton);
        map5.put("name","Louies Vuitton");
        HashMap<String,Object> map6=new HashMap<>();
        map6.put("img", R.drawable.brand_icon_chloe);
        map6.put("name","Chloe|蔻依");
        HashMap<String,Object> map7=new HashMap<>();
        map7.put("img", R.drawable.brand_icon_coach);
        map7.put("name","COACH|蔻驰");
        HashMap<String,Object> map8=new HashMap<>();
        map8.put("img", R.drawable.brand_icon_burberry);
        map8.put("name","Burberry|博柏利");
        HashMap<String,Object> map9=new HashMap<>();
        map9.put("img", R.drawable.hot_cate_more);
        map9.put("name","更多品牌");
        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);
        data.add(map6);
        data.add(map7);
        data.add(map8);
        data.add(map9);

        MyPopupWindowSecondAdapter myPopupWindowSecondAdapter=new MyPopupWindowSecondAdapter(context,data);
        listView.setAdapter(myPopupWindowSecondAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              if (currentTextView!=null){
                  currentTextView.setTextColor(Color.parseColor("#000000"));
              }
                currentTextView=(TextView) view.findViewById(R.id.idle_popupwindow_second_adapter_tv);
//                在这里进行页面操作
                Intent intent=new Intent(context, Idle_DownloadService.class);
                switch (i){
                    case 0:
                        currentTextView.setTextColor(Color.RED);

                        break;
                    case 1:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 2:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 3:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 4:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 5:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 6:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 7:
                        currentTextView.setTextColor(Color.RED);
                        break;
                    case 8:
                        currentTextView.setTextColor(Color.RED);
                        break;
                }
                MyPopupWindowSecond.this.dismiss();
                if (i!=0){
                    tv.setText(data.get(i).get("name").toString());

                }
            }
        });
    }
}
