package com.google.lenono.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.lenono.sellsortbarcommon.SellButtonToActivity;
import com.google.lenono.sellsortbarcommon.SellSortBar;
import com.google.lenono.sellsortbarcommon.SellSortBarActivity;
import com.google.lenono.tiaozaoproject.R;

/**
 * Created by lenono on 2016-07-17.
 */
public class SellFragment extends Fragment implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4,btn5;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sellfragment_item, null);
        initButtonDilog();
        return view;
    }
    public void initButtonDilog() {
        btn1 = (Button) view.findViewById(R.id.sell_fragment_dilog_btn1);
        btn2 = (Button) view.findViewById(R.id.sell_fragment_dilog_btn2);
        btn3 = (Button) view.findViewById(R.id.sell_fragment_dilog_btn3);
        btn4 = (Button) view.findViewById(R.id.sell_fragment_pinpai_btn);
        btn5= (Button) view.findViewById(R.id.sell_fragment_xiadan);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sell_fragment_dilog_btn1:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                TextView tv = new TextView(getContext());
                tv.setText("售卖标准");
                tv.setTextColor(Color.RED);
                tv.setTextSize(25);
                tv.setPadding(0, 20, 0, 20);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setCustomTitle(tv);
                View view = View.inflate(getContext(), R.layout.sell_fragment_dialog_item, null);
                builder.setView(view);
                builder.setNegativeButton(R.string.dialog_five, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case R.id.sell_fragment_dilog_btn2:
                Intent intent = new Intent(getContext(), SellSortBarActivity.class);
                startActivity(intent);
                break;
            case R.id.sell_fragment_dilog_btn3:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(getContext());
                TextView tv3 = new TextView(getContext());
                tv3.setText("售卖标准");
                tv3.setTextColor(Color.RED);
                tv3.setTextSize(25);
                tv3.setPadding(0, 20, 0, 20);
                tv3.setGravity(Gravity.CENTER_HORIZONTAL);
                builder3.setCustomTitle(tv3);
                builder3.setMessage("购买时实付金额在500元以上，非吊牌价格或标价。");
                builder3.setNegativeButton(R.string.dialog_five, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog3 = builder3.create();
                dialog3.setCanceledOnTouchOutside(true);
                dialog3.show();
                break;
            case R.id.sell_fragment_pinpai_btn:
                Intent intent1 = new Intent(getContext(), SellSortBarActivity.class);
                startActivity(intent1);
                break;
            case R.id.sell_fragment_xiadan:
                Intent intent2=new Intent(getContext(), SellButtonToActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
