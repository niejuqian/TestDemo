package com.testdemo.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.testdemo.R;

import static com.testdemo.share.MainActivity.mTencent;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 13 17:50
 * @DESC：
 */

public class ShareQQActivity extends Activity {

    private String TAG = ShareQQActivity.class.getSimpleName();
    Button shareQQBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_qq);
        shareQQBtn = (Button) findViewById(R.id.share_qq_btn);
        shareQQBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQQ();
            }
        });
    }
    public void shareQQ() {
        final Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://c.y.qq.com/v8/playsong.html?songid=109325260&songmid=000kuo2H2xJqfA&songtype=0&source=mqq&_wv=1");
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "分享标题");
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://ws.stream.qqmusic.qq.com/C100000kuo2H2xJqfA.m4a?fromtag=0");
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "乔紫乔");
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "三享通");
        //标识该消息的来源应用，值为应用名称+AppId。
//        bundle.putString(Constants.PARAM_APP_SOURCE, share.getSite());
        //调用sdk
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(ShareQQActivity.this, bundle, qqShareListener);
            }
        });
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e(TAG, "------------onComplete：" + o);
        }

        @Override
        public void onError(UiError uiError) {
            Log.e(TAG, "------------onError：" + uiError);
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "------------onCancel" );
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Log.e(TAG,"-----------------------onActivityResult");
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        }
    }
}
