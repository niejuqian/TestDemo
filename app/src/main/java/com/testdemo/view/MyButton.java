package com.testdemo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 02 09:36
 * @DESC：自定义Button
 */

public class MyButton extends Button {
    String TAG = MyButton.class.getSimpleName();
    String className;
    public MyButton(Context context) {
        this(context,null,0);
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public void initView(final Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
        className = typedArray.getString(R.styleable.MyButton_className);
        Log.e(TAG,"-----------className：" + className);
        typedArray.recycle();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(className)) {
                    Toast.makeText(context,"类名不存在",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Class c = Class.forName(className);
                        if (c != null) {
                            context.startActivity(new Intent(context,c));
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
