package com.testdemo.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 02 09:31
 * @DESC：运行时权限
 */

public class PermissionActivity extends BaseAppCompatActivity {
    private static final int PERSSION_CALL_PHONE_RECODE = 101;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_permission);
    }
    @OnClick(R.id.call_btn)
    void call(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            //还未申请权限，需要提示申请权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERSSION_CALL_PHONE_RECODE);
        } else {
            //已经申请权限，可以拨打电话了
            callPhone();
        }
    }

    private void callPhone() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERSSION_CALL_PHONE_RECODE:
                boolean isPerssion = (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                if (isPerssion) {
                    callPhone();
                } else {
                    Toast.makeText(this,"你还未申请权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
