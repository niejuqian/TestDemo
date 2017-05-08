package com.testdemo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.Area;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 06 11:16
 * @DESC：
 */

public class AreaAdapter extends CommonAdapter<Area>{
    public AreaAdapter(Context context, List<Area> list) {
        super(context, list, R.layout.adapter_area);
    }

    @Override
    public void convert(ViewHolder viewHolder, Area item, int position) {
        TextView city_name = viewHolder.getView(R.id.city_name);
        TextView city_code = viewHolder.getView(R.id.city_code);
        city_name.setText(item.getCity_name());
        city_code.setText(item.getCity_code()+"");
    }
}
