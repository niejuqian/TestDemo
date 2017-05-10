package com.testdemo.view.seniorui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 10 10:47
 * @DESC：
 */

public class FlowLayout extends ViewGroup {

    /**
     * 每行包含的view
     */
    private List<List<View>> lineViewList = new ArrayList<>();

    /**
     * 行数以及每行所占的高度
     */
    private List<Integer> lineHeights = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量view大小
        //宽度测量模式
        int iWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        //高度测量模式
        int iHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        //建议宽度值
        int iWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        //建议高度值
        int iHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //viewgroup最终的宽度
        int measuredWidth = 0;
        //viewgroup最终的高度
        int measuredHeight = 0;

        //当前行的宽度
        int currentLineWidth = 0;
        //当前行的高度
        int currentLineHeight = 0;

        if (iWidthMode == MeasureSpec.EXACTLY && iHeightMode == MeasureSpec.EXACTLY) {
            measuredWidth = iWidthSpecSize;
            measuredHeight = iHeightSpecSize;
        } else {
            //需要测量子view
            int childCount = getChildCount();
            int iChildViewHeight = 0;
            int iChildViewWidth = 0;
            //每行包含的childView数量
            List<View> currentLineViews = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //如果childView设置了不显示，则，不做测量
                if (childView.getVisibility() == GONE) continue;
                //测量childView宽高
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                //获取childView的margin属性
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                //childView的宽度
                iChildViewWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                //childView的高度
                iChildViewHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                //如果当前行的宽度 + 当前childView的宽度 大于iWidthSpecSize 则需要换行了
                if (currentLineWidth + iChildViewWidth > iWidthSpecSize) {
                    /* 换行处理 */
                    lineHeights.add(currentLineHeight);
                    lineViewList.add(currentLineViews);

                    //累计高度 需要在加上当前行的高度
                    measuredHeight += currentLineHeight;

                    //最大宽度取最宽的一行的宽度
                    if (iWidthMode == MeasureSpec.AT_MOST) {
                        measuredWidth = Math.min(measuredWidth, currentLineWidth);
                    } else {
                        measuredWidth = Math.max(measuredWidth, currentLineWidth);
                    }

                    //当前行的宽度从头开始
                    currentLineWidth = iChildViewWidth;
                    currentLineHeight = iChildViewHeight;

                    //将childView添加到当前行的List中，换行时的childView是当前行的第一个childView
                    currentLineViews = new ArrayList<>();
                    currentLineViews.add(childView);
                } else {
                    /* 继续在本行追加 */

                    //当前行的宽度等于累计宽度+当前childView的宽度
                    currentLineWidth += iChildViewWidth;
                    //当前行的高度取最高childView的高度
                    currentLineHeight = Math.max(currentLineHeight, iChildViewHeight);
                    //将childView添加到当前行的List中
                    currentLineViews.add(childView);
                }
                //最后一行单独处理
                if (i == (childCount -1)) {
                    //最后一行
                    lineHeights.add(currentLineHeight);
                    lineViewList.add(currentLineViews);

                    //累计高度 需要在加上当前行的高度
                    measuredHeight += currentLineHeight;
                    //最大宽度取最宽的一行的宽度
                    measuredWidth = Math.max(measuredWidth, currentLineWidth);
                }
            }
        }
        //最终目的
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //此处的左上右下，相当于 childView的两个坐标点
        //left top为左上角坐标(x,y)
        //right bottom为右下角坐标(x,y)
        int left = 0, top = 0, right = 0, bottom = 0;
        int currentLeft = 0, currentTop = 0;
        Log.e("FlowLayout","lineHeights：" + lineHeights);
        Log.e("FlowLayout","lineViewList：" + lineViewList);
        //确定view位置
        if (lineHeights.size() > 0) {
            //屏幕实际宽度
            int windowWidth = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
            int lineSize = lineHeights.size();
            for (int i = 0; i < lineSize; i++) {
                List<View> lineViews = lineViewList.get(i);
                for (View childView : lineViews) {
                    MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                    //左上角的x坐标点，等于childView本身的marginLeft + 上一个（左边）childView右下角的x坐标
                    left = currentLeft + lp.leftMargin;
                    //左上角的y坐标点，等于childView本身的marginTop + 行数累加高度（每一行的高度累加）
                    top = currentTop + lp.topMargin;

                    //右下角的x坐标点，等于childView本身的宽度 + 左上角的x坐标点值
                    right = left + childView.getMeasuredWidth();
                    if (right >= windowWidth) {
                        right = windowWidth - lp.rightMargin;
                    }
                    //右下角的y坐标点，等于childView本身的高度 + 左上角的y坐标点值
                    bottom = top + childView.getMeasuredHeight();
                    //设置childView在布局中的位置
                    childView.layout(left,top,right,bottom);
                    //设置下开一个childView左上角x坐标点（不包含下一个childView的marginLeft）
                    currentLeft += (childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                }
                //换行后，left清零，从新开始
                currentLeft = 0;
                //换行后，高度需要累加
                currentTop += lineHeights.get(i);
            }
        }
        //处理完成后，需要清空，因为onMeasure可以onLayout可能会多次执行，导致数据异常
        lineHeights.clear();
        lineViewList.clear();
    }
}
