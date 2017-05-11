package com.testdemo.view.seniorui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.testdemo.R;
import com.testdemo.seniorui.WaterFallChildView;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 11 13:41
 * @DESC：
 */

public class WaterFallView extends ViewGroup {

    /**
     * 每行显示的列数，如果不设置，则自左到右呈流式布局排布
     */
    private int columnCount = 3;
    private int currentPosition = 0;
    private List<List<WaterFallChildView>> columnChildViews = new ArrayList<>();


    public WaterFallView(Context context) {
        this(context,null);
    }

    public WaterFallView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterFallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.waterFall);
            columnCount = typedArray.getInt(R.styleable.waterFall_columnCount,3);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        currentPosition = 0;
        for (int i = 0; i < columnCount; i++) {
            columnChildViews.add(new ArrayList<WaterFallChildView>());
        }
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int measuredWidth = widthSpecSize;
        int measuredHeight = heightSpecSize;
        if (widthSpecMode != MeasureSpec.EXACTLY || heightSpecMode != MeasureSpec.EXACTLY) {
            int left = 0,top = 0,right = 0,bottom = 0;
            //上一行最低点
            int preMinViewHeight = 0;
            int currentLineWidth = 0;
            int currentLineMaxHeight = 0;
            int currentLineMinHeight = 0;
            //需要重新计算
            int childCount = getChildCount();
            int position = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                //当前childView的宽度
                int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                //当前childView的高度
                int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                //第一行的child从左往右
                if (i < columnCount) {
                    if (i == 0) {
                        left = lp.leftMargin;
                        right = childWidth;
                    }else {
                        left = currentLineWidth;
                        right = currentLineWidth + childWidth;
                    }
                    top = lp.topMargin;
                    bottom = childHeight;
                } else {
                    WaterFallChildView preView = getMinHeightView();
                    left = preView.left;
                    top = preView.bottom + lp.topMargin;
                    right = preView.right;
                    bottom = preView.bottom + childHeight;
                }
                //计算容器宽高
                if (i % columnCount == 0) {
                    measuredWidth = Math.max(measuredWidth,currentLineWidth);
                    measuredHeight += currentLineMaxHeight;
                    //这说明是另起一行
                    currentLineWidth = childWidth;
                    currentLineMaxHeight = childHeight;
                    currentLineMinHeight = childHeight;
                } else {
                    currentLineWidth += childWidth;
                    currentLineMaxHeight = Math.max(currentLineMaxHeight,childHeight);
                    currentLineMinHeight = Math.min(currentLineMinHeight,childHeight);
                    preMinViewHeight = Math.min(preMinViewHeight,childHeight);
                }
                WaterFallChildView waterFallChildView = new WaterFallChildView();
                waterFallChildView.view = childView;
                waterFallChildView.top = top;
                waterFallChildView.left = left;
                waterFallChildView.right = right;
                waterFallChildView.bottom = bottom;
                if (i < columnCount) {
                    if (position >= columnCount) {
                        position = 0;
                    }
                    columnChildViews.get(position).add(waterFallChildView);
                    position++;
                } else {
                    columnChildViews.get(currentPosition).add(waterFallChildView);
                }
            }
        }
        Log.e("WaterFallView","==================measuredWidth：" + measuredWidth + "，measuredHeight：" + measuredHeight);
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (columnChildViews.size() > 0) {
            for (int i = 0; i < columnChildViews.size(); i++) {
                List<WaterFallChildView> childViews = columnChildViews.get(i);
                for (WaterFallChildView childView : childViews) {
                    childView.view.layout(childView.left,childView.top,childView.right,childView.bottom);
                }
            }
        }
        columnChildViews.clear();
    }

    /**
     * 获取列表中高度最小的view，作为下一个view的依据
     * @return
     */
    private WaterFallChildView getMinHeightView() {
        Log.e("...........","columnChildViews：" + columnChildViews.size());
        int bottom = 0;
        WaterFallChildView resultView = null;
        for (int i = 0; i < columnCount; i++) {
            List<WaterFallChildView> childViews = columnChildViews.get(i);
            if (childViews.size() > 0) {
                WaterFallChildView childView = childViews.get(childViews.size() -1);
                if (i == 0) {
                    currentPosition = i;
                    bottom = childView.bottom;
                    resultView = childView;
                    continue;
                }
                if (bottom > childView.bottom) {
                    resultView = childView;
                    currentPosition = i;
                }
            }
        }
        return resultView;
    }
}
