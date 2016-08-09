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

import java.util.Arrays;

/**
 * Created by Administrator on 2016/7/23.
 */
public class MyPopupWindowThird extends PopupWindow{
    private Context context;
    private View menuView;
    private ListView listView;
    private TextView tv;
    private TextView currentTextView;
    String [] old={"不限成色","全新","99成新","95成新","9成新","85成新","8成新"};
    public MyPopupWindowThird(Context context, TextView tv){
        this.context=context;
        this.tv=tv;
    }
    public void initPopupWindow(){
        menuView= LayoutInflater.from(context).inflate(R.layout.idle_popupwindow_third,null);
        listView=(ListView)menuView.findViewById(R.id.idle_popupwindow_third_lv);

        this.setContentView(menuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        this.setOutsideTouchable(true);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, Arrays.asList(old));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentTextView!=null){
                    currentTextView.setTextColor(Color.parseColor("#000000"));
                }
                currentTextView=(TextView)view;
//                在这里进行页面操作
                Intent intent=new Intent(context, Idle_DownloadService.class);

                switch (i){
                    case 0:
                         intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",-1);
                        break;
                    case 1:
                        currentTextView.setTextColor(Color.RED);
                        intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",1);
                        break;
                    case 2:
                        currentTextView.setTextColor(Color.RED);
                        intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",0);
                        break;
                    case 3:
                        currentTextView.setTextColor(Color.RED);
                        intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",5);
                        break;
                    case 4:
                        currentTextView.setTextColor(Color.RED);
                        intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",3);
                        break;
                    case 5:
                        currentTextView.setTextColor(Color.RED);
                        intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",6);
                        break;
                    case 6:
                        currentTextView.setTextColor(Color.RED);
                        intent.putExtra("cate",-1);
                        intent.putExtra("brand",-1);
                        intent.putExtra("condition",4);
                        break;

                }
                context.startService(intent);
                MyPopupWindowThird.this.dismiss();
                if (i!=0){
                    tv.setText(adapterView.getItemAtPosition(i).toString());
                }else {
                    tv.setText("成色");
                }

            }
        });
    }
}
