package com.testdemo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.testdemo.BaseAppCompatActivity;
import com.testdemo.MyApplication;
import com.testdemo.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 09 10:08
 * @DESC：
 */

public class UMShareActivity extends BaseAppCompatActivity{
    @BindView(R.id.root_layout)
    LinearLayout root_layout;
    @BindView(R.id.hender_img)
    CircleImageView hender_img;
    @BindView(R.id.nickname_tv)
    TextView nickname_tv;
    @BindView(R.id.gender_tv)
    TextView gender_tv;
    @BindView(R.id.uid_tv)
    TextView uid_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_umshare);
        setListener();
    }

    private void setListener(){
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        for (int i = 0; i < root_layout.getChildCount(); i++) {
            View view = root_layout.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(buttonClickListener);
            }
        }
    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qq_auth_login_btn:
                    //使用QQ进行第三方登录
                    MyApplication.getShareAPI().getPlatformInfo(UMShareActivity.this, SHARE_MEDIA.QQ,umAuthListener);
                    break;
            }
        }
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.e(TAG,"-----------data：" + data + "，action：" + action);
            wrapperResult(data);
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyApplication.getShareAPI().onActivityResult(requestCode,resultCode,data);
    }

    private void wrapperResult(Map<String,String> data) {
        if (null != data && data.size() > 0) {
            setTextViewValue(nickname_tv,data.get("screen_name"),"昵称");
            setTextViewValue(gender_tv,data.get("gender"),"性别");
            setTextViewValue(uid_tv,data.get("uid"),"UID");
            Glide.with(this).load(data.get("profile_image_url")).into(hender_img);
        }
    }

    private void setTextViewValue(TextView tv, String text,String title) {
        if (null == text) {
            text = "";
        }
        tv.setText(title+ "：" + text);
    }
}
