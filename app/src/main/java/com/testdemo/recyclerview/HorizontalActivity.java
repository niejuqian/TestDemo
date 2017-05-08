package com.testdemo.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.testdemo.R;
import com.testdemo.adapter.RecyclerViewAdapter;
import com.testdemo.entity.RecyclerViewEnum;
import com.testdemo.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

import static com.testdemo.util.StringUtils.getRandomLenName;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 10:39
 * @DESC：
 */

public class HorizontalActivity extends Activity {
    private RecyclerView myRecyclerView;
    private List<TestBean> list = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_staggered);
        initData();
        myRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(list, RecyclerViewEnum.HORIZONTAL);
        recyclerViewAdapter.setListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, TestBean testBean) {
                Log.e("recyclerview","position：" + position  + "，bean：" + testBean);
            }
        });
        myRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initData(){
        for (int i = 0; i < 5; i++) {
            list.add(new TestBean(R.mipmap.chongzhi,getRandomLenName("好地方")));
            list.add(new TestBean(R.mipmap.dianpu,getRandomLenName("多少个")));
            list.add(new TestBean(R.mipmap.fanli,getRandomLenName("申达股份dds")));
            list.add(new TestBean(R.mipmap.hongbao,getRandomLenName("的股份等")));
            list.add(new TestBean(R.mipmap.tixian,getRandomLenName("发说说")));
            list.add(new TestBean(R.mipmap.tuikuan,getRandomLenName("都是恩恩")));
        }
    }
}
