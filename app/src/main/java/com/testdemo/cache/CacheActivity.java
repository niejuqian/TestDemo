package com.testdemo.cache;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.testdemo.other.ACache;
import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import java.util.Random;

import butterknife.BindView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 20 09:59
 * @DESC：
 */

public class CacheActivity extends BaseAppCompatActivity {
    @BindView(R.id.read_cache_btn)
    Button read_cache_btn;

    @BindView(R.id.write_cache_btn)
    Button write_cache_btn;

    @BindView(R.id.clear_cache_btn)
    Button clear_cache_btn;

    @BindView(R.id.cache_content_tv)
    TextView cache_content_tv;

    Random random = new Random();
    ACache aCache;
    private static String KEY = "key";
    public static String CONTENT_NORMAL = "我是内容";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_cache);
        aCache = ACache.get(this);
    }

    private void setListener(){
        read_cache_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cache_content_tv.setText(getContent());
            }
        });
        write_cache_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContent();
            }
        });
        clear_cache_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
    }



    public String getContent(){
        try {
            //return CacheManger.getInstance().get(KEY);
            return aCache.getAsString(KEY);
        }catch (Exception e) {
            Log.e("MainActivity","异常鸟：" + e.getMessage());
        }
        return "没取到值";
    }

    public void setContent(){
        try {
            String content = CONTENT_NORMAL + random.nextInt(200);
            Log.e("MainActivity", "设置内容：" + content);
            //CacheManger.getInstance().put(KEY, content , 1000 * 60);
            aCache.put(KEY, content, 60);
        }catch (Exception e) {
            Log.e("MainActivity", "设置内容异常鸟：" + e.getMessage());
        }
    }

    public void remove(){
        Log.e("MainActivity","清除缓存");
        aCache.remove(KEY);
    }
}
