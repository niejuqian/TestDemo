package com.testdemo.md;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.adapter.FruitAdapter;
import com.testdemo.entity.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 05 16:21
 * @DESC：
 */

public class MaterialDesignActivity extends BaseAppCompatActivity implements View.OnClickListener{
    @BindView(R.id.my_tool_bar)
    Toolbar my_tool_bar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.my_fabtn)
    FloatingActionButton my_fabtn;

    @BindView(R.id.md_recycler_vew)
    RecyclerView md_recycler_vew;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    CircleImageView nav_header_img;
    TextView mail_tv;
    TextView username_tv;
    List<Fruit> fruitList = new ArrayList<>();
    FruitAdapter fruitAdapter;
    Fruit[] fruits = new Fruit[]{
            new Fruit("banana",R.mipmap.banana),
            new Fruit("cherry",R.mipmap.cherry),
            new Fruit("grape",R.mipmap.grape),
            new Fruit("orange",R.mipmap.orange),
            new Fruit("pear",R.mipmap.pear),
            new Fruit("pineapple",R.mipmap.pineapple),
            new Fruit("strawberry",R.mipmap.strawberry),
            new Fruit("watermelon",R.mipmap.watermelon)
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_md);
        setSupportActionBar(my_tool_bar);
        initData();
        initView();
        setListener();
    }

    /**
     * 初始化view
     */
    private void initView(){
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        RelativeLayout headerView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_nav_header,null);
        nav_header_img = (CircleImageView) headerView.findViewById(R.id.nav_header_img);
        mail_tv = (TextView) headerView.findViewById(R.id.mail_tv);
        username_tv = (TextView) headerView.findViewById(R.id.username_tv);
        nav_view.addHeaderView(headerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        md_recycler_vew.setLayoutManager(gridLayoutManager);
        fruitAdapter = new FruitAdapter(fruitList);
        fruitAdapter.setItemClickListener(new FruitAdapter.ItemClickListener() {
            @Override
            public void onClick(Fruit fruit) {
                //查看水果详细信息
                Intent intent = new Intent(MaterialDesignActivity.this,FruitDetailActivity.class);
                intent.putExtra("fruit",fruit);
                startActivity(intent);
            }
        });
        md_recycler_vew.setAdapter(fruitAdapter);
        refresh_layout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void setListener(){
        nav_header_img.setOnClickListener(this);
        mail_tv.setOnClickListener(this);
        username_tv.setOnClickListener(this);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        Toast.makeText(MaterialDesignActivity.this,"click is nav_call",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MaterialDesignActivity.this,"click is nav_friends",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MaterialDesignActivity.this,"click is nav_location",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(MaterialDesignActivity.this,"click is nav_mail",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(MaterialDesignActivity.this,"click is nav_task",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        my_fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MaterialDesignActivity.this,"click is fabtn",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(1, TimeUnit.SECONDS)
                        .doOnNext(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                initData();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<Long>() {

                            @Override
                            public void onCompleted() {
                                refresh_layout.setRefreshing(false);
                                fruitAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {

                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"click is Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"click is Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"click is Settings",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mail_tv:
                Toast.makeText(this,"click is mail_tv",Toast.LENGTH_SHORT).show();
                break;
            case R.id.username_tv:
                Toast.makeText(this,"click is username_tv",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_header_img:
                Toast.makeText(this,"click is nav_header_img",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initData(){
        fruitList.clear();
        Random random = new Random();
        for (int i = 0; i < 49; i++) {
            fruitList.add(fruits[random.nextInt(fruits.length)]);
        }
    }
}
