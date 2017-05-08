package com.testdemo.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.adapter.PhoneAdapter;
import com.testdemo.entity.PhoneInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 02 11:53
 * @DESC：联系人
 */

public class ContactActivity extends BaseAppCompatActivity {
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.phone_list_rv)
    RecyclerView phoneListRv;

    PhoneAdapter phoneAdapter;
    private List<PhoneInfo> phoneInfos = new ArrayList<>();
    private Cursor cursor;
    private PhoneInfo selectInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_contact);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        phoneListRv.setLayoutManager(layoutManager);
        phoneAdapter = new PhoneAdapter(phoneInfos);
        phoneAdapter.setOnItemClickListener(new PhoneAdapter.OnItemClickListener() {
            @Override
            public void onCLick(PhoneInfo info) {
                Log.e(TAG,"-----phone：" + info);
                selectInfo = info;
                //拨打电话
                callPermission();
            }
        });
        phoneListRv.setAdapter(phoneAdapter);
        permission();
    }

    @OnTextChanged(value = R.id.search_et,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onChange(){
        filterPhone();
    }

    /**
     * 获取空布局
     * @param desc
     * @return
     */
    private View getEmptyView(String desc){
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty,null);
        TextView emptyDesc = (TextView) emptyView.findViewById(R.id.empty_desc_tv);
        if (!TextUtils.isEmpty(desc)){
            emptyDesc.setText(desc);
        }
        return emptyView;
    }

    private void callPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            call();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},102);
        }
    }

    private void call(){
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + selectInfo.getPhone()));
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void permission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            getPhoneList();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},101);
        }
    }

    public void filterPhone(){
        final String key = searchEt.getText().toString();
        if (phoneInfos.size() > 0 && !TextUtils.isEmpty(key)) {
            Observable.from(phoneInfos)
                    .filter(new Func1<PhoneInfo, Boolean>() {
                        @Override
                        public Boolean call(PhoneInfo info) {
                            return info.getName().contains(key);
                        }
                    }).toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<PhoneInfo>>() {
                        @Override
                        public void call(List<PhoneInfo> phoneInfos) {
                            if (null != phoneInfos) {
                                phoneAdapter.notifyData(phoneInfos);
                            }
                        }
                    });
        }else{
            phoneAdapter.notifyData(phoneInfos);
        }
    }

    /**
     * 读取联系人
     */
    public void getPhoneList(){
        Observable.create(new Observable.OnSubscribe<PhoneInfo>() {
            @Override
            public void call(Subscriber<? super PhoneInfo> subscriber) {
                cursor = getPhoneCursor();
                if (null != cursor) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        subscriber.onNext(new PhoneInfo(phone,name));
                    }
                }
                subscriber.onCompleted();
            }
        })
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<PhoneInfo>>() {
            @Override
            public void onStart() {
                phoneInfos.clear();
                cursor = null;
            }

            @Override
            public void onCompleted() {
                if (null != cursor) {
                    cursor.close();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<PhoneInfo> list) {
                if (list != null) {
                    phoneInfos.addAll(list);
                }
                phoneAdapter.notifyData(phoneInfos);
            }
        });
    }

    private Cursor getPhoneCursor(){
        String[] params = null;
        String selection = null;
        /*String key = searchEt.getText().toString();
        if (!TextUtils.isEmpty(key)) {
            params = new String[]{"%" + key + "%"};
            selection = "display_name = ?";
        }*/
        return getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getPhoneList();
            } else {
                Toast.makeText(this,"权限未开启",Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                call();
            } else {
                Toast.makeText(this,"权限未开启",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
