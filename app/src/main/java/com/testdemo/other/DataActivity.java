package com.testdemo.other;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.testdemo.R;
import com.testdemo.adapter.AreaAdapter;
import com.testdemo.dao.AreaDao;
import com.testdemo.entity.Area;
import com.testdemo.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 06 11:11
 * @DESC：
 */

public class DataActivity extends Activity {
    Button query_btn,clear_btn,insert_btn;
    ListView area_lv;
    List<Area> areas = new ArrayList<>();
    AreaAdapter areaAdapter;
    AreaDao areaDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        areaDao = new AreaDao(this);
        initView();
        setListener();
    }

    private void initView(){
        clear_btn = (Button) findViewById(R.id.clear_btn);
        query_btn = (Button) findViewById(R.id.query_btn);
        insert_btn = (Button) findViewById(R.id.insert_btn);
        area_lv = (ListView) findViewById(R.id.area_lv);
        areaAdapter = new AreaAdapter(this,areas);
        area_lv.setAdapter(areaAdapter);
    }

    private void setListener(){
        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Area> areaList = areaDao.getList();
                if (null != areaList && areaList.size() > 0) {
                    areas.addAll(areaList);
                }
                areaAdapter.notifyDataSetChanged();
            }
        });
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areas.clear();
                areaAdapter.notifyDataSetChanged();
            }
        });
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Log.e("Mainactivity","-----------------开始执行");
            List<String> sqlList = DBHelper.getInstance().loadSql();
            Log.e("MainActivity","--------------读取完成，共耗时：" + (System.currentTimeMillis() - start) +"毫秒");
            if (null != sqlList) {
                Log.e("MainActivity","----------------size：" + sqlList.size());
                areaDao.deleteAll();
                areaDao.executeSql(sqlList);
            }
        }
    };
}
