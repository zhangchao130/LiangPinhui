package idle_popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.ToggleButton;

import com.google.lenono.tiaozaoproject.R;

/**
 * Created by Administrator on 2016/7/24.
 */
public class MyPopupWindowFour extends PopupWindow implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{
    private Context context;
    private View menuView;
    private CheckBox cb_time,cb_hightolow,cb_lowtohigh;
    private ToggleButton tbtn_gov_sale,tbtn_saling;
    private Button btn_four;
    private CheckBox currentCheckBox;
    public MyPopupWindowFour(Context context ) {
        this.context=context;

    }
    public void initPopupWindow(){
        menuView= LayoutInflater.from(context).inflate(R.layout.idle_popupwindow_four,null);
        cb_time=(CheckBox) menuView.findViewById(R.id.idle_popupwindow_four_time_cb);
        cb_hightolow=(CheckBox)menuView.findViewById(R.id.idle_popupwindow_four_hightolowprice_cb);
        cb_lowtohigh=(CheckBox)menuView.findViewById(R.id.idle_popupwindow_four_lowtohighprice_cb);
        tbtn_gov_sale=(ToggleButton)menuView.findViewById(R.id.idle_popupwindow_four_gov_sale_tbtn);
        tbtn_saling=(ToggleButton)menuView.findViewById(R.id.idle_popupwindow_four_saling_tbtn);
        btn_four=(Button)menuView.findViewById(R.id.idle_popupwindow_four_btn);

        cb_time.setOnCheckedChangeListener(this);
        cb_hightolow.setOnCheckedChangeListener(this);
        cb_lowtohigh.setOnCheckedChangeListener(this);
        tbtn_gov_sale.setOnCheckedChangeListener(this);
        tbtn_saling.setOnCheckedChangeListener(this);

        btn_four.setOnClickListener(this);





        this.setContentView(menuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        this.setOutsideTouchable(true);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()){
//       在这里进行页面更新
            case R.id.idle_popupwindow_four_time_cb:
               if (b){
                   cb_lowtohigh.setChecked(false);
                   cb_hightolow.setChecked(false);
               }

                break;
            case R.id.idle_popupwindow_four_hightolowprice_cb:
                if (b){
                    cb_time.setChecked(false);
                    cb_lowtohigh.setChecked(false);
                }

                break;
            case R.id.idle_popupwindow_four_lowtohighprice_cb:
                if (b){
                    cb_time.setChecked(false);
                    cb_hightolow.setChecked(false);
                }

                break;
            case R.id.idle_popupwindow_four_gov_sale_tbtn:
                break;
            case R.id.idle_popupwindow_four_saling_tbtn:
                break;
        }
    }


    @Override
    public void onClick(View view) {

        MyPopupWindowFour.this.dismiss();
    }
}
