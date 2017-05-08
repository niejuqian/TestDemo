package com.testdemo.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.util.DevicesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 03 11:17
 * @DESC：
 */

public class CameraActivity extends BaseAppCompatActivity {
    private static final String IMAGE_FILE_NAME = "output_img.jpg";
    private static final int CAMERA_REQ_CODE = 1;
    private static final int OPEN_ALBUMREQ_CODE = 2;
    private Uri imageUri;
    @BindView(R.id.camera_iv)
    ImageView camera_iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_camera);
    }

    @OnClick(R.id.choise_camera_btn)
    void choise(){
        //相册选择
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        }else {
            //申请权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    /**
     * 打开相册
     */
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,OPEN_ALBUMREQ_CODE);
    }


    /**
     * 4.4以前版本处理方式
     */
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        displayImage(uri.getPath());
    }

    /**
     * 4.4以后版本处理方式
     */
    @TargetApi(19)
    private void handleImageKitKat(Intent data){
        Uri uri = data.getData();
        Log.e(TAG,"-----------scheme：" + uri.getScheme());
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            displayImage(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，则直接获取图片路径即可
            handleImageBeforeKitKat(data);
        }else if (DocumentsContract.isDocumentUri(this,uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                displayImage(uri,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                displayImage(contentUri,null);
            }
        }else{
            Toast.makeText(this,"图片类型错误",Toast.LENGTH_SHORT).show();
        }
    }

    private void displayImage(final Uri uri, final String selection) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
                    if (null != cursor) {
                        while (cursor.moveToFirst()) {
                            subscriber.onNext(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                            break;
                        }
                        cursor.close();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                subscriber.onCompleted();
            }
        }).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String imagePath) {
                Bitmap bitmap = null;
                if (!TextUtils.isEmpty(imagePath)){
                    bitmap = BitmapFactory.decodeFile(imagePath);
                }
                return bitmap;
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onStart() { camera_iv.setImageBitmap(null); }
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(CameraActivity.this,"异常鸟~~",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(Bitmap bitmap) {
                Log.e(TAG,"--------------------------ddddd");
                if (null != bitmap) {
                    camera_iv.setImageBitmap(bitmap);
                }
            }
        });
    }

    /**
     * 根据图片路径，直接显示图片
     * @param imagePath
     */
    private void displayImage(final String imagePath) {
        Log.e(TAG,"-----------imagePath：" + imagePath);
        if (TextUtils.isEmpty(imagePath)) return;
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(CameraActivity.this,"异常鸟~~",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Bitmap bitmap) {
                if (null != bitmap) {
                    camera_iv.setImageBitmap(bitmap);
                }
            }
        });
    }

    @OnClick(R.id.camera_btn)
    void camera(){
        //拍照
        File outputImage = new File(getExternalCacheDir(),IMAGE_FILE_NAME);
        try {
            //如果存在，则先删除
            if (outputImage.exists()){
                outputImage.delete();
            }
            //创建一个新文件
            outputImage.createNewFile();
            if (Build.VERSION.SDK_INT >= 24) {
                //7.x以上使用这种取值方式
                imageUri = FileProvider.getUriForFile(this, DevicesUtils.getPackageName()+ ".provider",outputImage);
            }else {
                //7.0系统以下使用这种取值方式
                imageUri = Uri.fromFile(outputImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //调用系统相机拍照
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,CAMERA_REQ_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQ_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        camera_iv.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e(TAG,"----------取消拍照");
                }
                break;
            case OPEN_ALBUMREQ_CODE:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4.x以上版本如此处理
                        handleImageKitKat(data);
                    } else {
                        //4.4以下版本如此处理
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else{
                Toast.makeText(this,"未开启权限",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
