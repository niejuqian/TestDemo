package com.testdemo.recyclerview;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

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

public class StaggeredGridLayoutManagerActivity extends Activity {
    private RecyclerView myRecyclerView;
    private List<TestBean> list = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_staggered);
        initData();
        myRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(list, RecyclerViewEnum.STAGGERED_GRID);
        recyclerViewAdapter.setListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, TestBean testBean) {
                Log.e("recyclerview","position：" + position  + "，bean：" + testBean);
            }
        });
        myRecyclerView.setAdapter(recyclerViewAdapter);
        myRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
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
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;//间距，单位dp
        public SpaceItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int itemCount = recyclerViewAdapter.getItemCount();
            int pos = parent.getChildAdapterPosition(view);
            Log.d("SpaceItemDecoration", "itemCount>>" +itemCount + ";Position>>" + pos);

            outRect.top = 0;
            outRect.bottom = 0;
            outRect.right = space;
            outRect.left = 0;
        }
    }
}
