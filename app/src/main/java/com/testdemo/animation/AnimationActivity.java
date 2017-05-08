package com.testdemo.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 03 28 11:03
 * @DESC：
 */

public class AnimationActivity extends Activity {
    private String TAG = AnimationActivity.class.getSimpleName();
    private ImageView star_iv,open_iv;
    private Button scale_btn,alpha_btn,tran_btn,rotate_btn;
    private Animation alphaAnimation,scaleAnimation,translateAnimation,rotateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        star_iv = (ImageView) findViewById(R.id.star_iv);
        open_iv = (ImageView) findViewById(R.id.open_iv);
        scale_btn = (Button) findViewById(R.id.scale_btn);
        alpha_btn = (Button) findViewById(R.id.alpha_btn);
        tran_btn = (Button) findViewById(R.id.tran_btn);
        rotate_btn = (Button) findViewById(R.id.rotate_btn);
        initAlphaAnimation();
        initScaleAnimation();
        initTranslateAnimation();
        initRotateAnimation();
        setListener();
    }
    private void setListener(){
        scale_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_iv.startAnimation(scaleAnimation);
            }
        });
        alpha_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_iv.startAnimation(alphaAnimation);
            }
        });
        tran_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_iv.startAnimation(translateAnimation);
            }
        });
        rotate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open_iv.startAnimation(rotateAnimation);
                groupAnimation();
            }
        });
    }

    /**
     * 获取透明动画
     * @return
     */
    public void initAlphaAnimation(){
        //fromAlpha //开始透明度
        // toAlpha //结束透明度
        alphaAnimation = new AlphaAnimation(0.1f,0.8f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(5);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG,"1-----------------------onAnimationStart");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG,"1-----------------------onAnimationEnd");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG,"1-----------------------onAnimationRepeat");

            }
        });
    }

    /**
     * 获取缩放动画
     * @return
     */
    public void initScaleAnimation(){
        //第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
        //第二个参数toX为动画结束时 X坐标上的伸缩尺寸
        //第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
        //第四个参数toY为动画结束时Y坐标上的伸缩尺寸
        /*说明:
                以上四种属性值
                0.0表示收缩到没有
                1.0表示正常无伸缩
                值小于1.0表示收缩
                值大于1.0表示放大
        */
        //第五个参数pivotXType为动画在X轴相对于物件位置类型
        //第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
        //第七个参数pivotXType为动画在Y轴相对于物件位置类型
        //第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置

        scaleAnimation = new ScaleAnimation(0.5f, 1.4f, 0.5f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);//执行时间
        scaleAnimation.setRepeatCount(30);//设置重复次数
        scaleAnimation.setRepeatMode(Animation.REVERSE);//设置放大、缩小效果
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG,"-----------------------onAnimationStart");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG,"-----------------------onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG,"-----------------------onAnimationRepeat");

            }
        });
    }

    public void initTranslateAnimation(){
        //第一个参数fromXDelta为动画起始时 X坐标上的移动位置
        //第二个参数toXDelta为动画结束时 X坐标上的移动位置
        //第三个参数fromYDelta为动画起始时Y坐标上的移动位置
        //第四个参数toYDelta为动画结束时Y坐标上的移动位置
        translateAnimation = new TranslateAnimation(0,100,0,80);
        translateAnimation.setDuration(3000);
        translateAnimation.setRepeatCount(5);
        translateAnimation.setRepeatMode(Animation.REVERSE);//设置放大、缩小效果
    }


    public void initRotateAnimation(){
        //第一个参数fromDegrees为动画起始时的旋转角度
        //第二个参数toDegrees为动画旋转到的角度
        //第三个参数pivotXType为动画在X轴相对于物件位置类型
        //第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
        //第五个参数pivotXType为动画在Y轴相对于物件位置类型
        //第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        rotateAnimation = new RotateAnimation(0.0f, +1080.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);//
    }

    public void groupAnimation(){
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);
        open_iv.startAnimation(animationSet);
    }
}
