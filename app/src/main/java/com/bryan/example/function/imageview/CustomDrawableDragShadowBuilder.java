package com.bryan.example.function.imageview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.DragShadowBuilder;

/**
 * 这个类主要负责拖动时的图片效果
 * 
 * @author bryan
 *
 */
public class CustomDrawableDragShadowBuilder extends DragShadowBuilder
{
    private Drawable mDrawable;

    /**
     * 
     * @param view
     * @param drawable  拖动时的可见副本
     */
    public CustomDrawableDragShadowBuilder(View view, Drawable drawable)
    {
        super(view);
        // 设置Drawable并使用一个绿色的过滤器
        mDrawable = drawable;
        mDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
    }

    /**
     * DragShadowBuilder初始化的时候会调用这个方法。
     */
    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint)
    {
        // 拖动时图片阴影的填充大小,设置为图片的宽高*2
        shadowSize.x = mDrawable.getIntrinsicWidth() * 2;
        shadowSize.y = mDrawable.getIntrinsicHeight() * 2;

        // 设置触摸点相对于阴影的位置
        shadowTouchPoint.x = mDrawable.getIntrinsicWidth() / 2;
        shadowTouchPoint.y = mDrawable.getIntrinsicHeight() / 2;

        mDrawable.setBounds(new Rect(0, 0, shadowSize.x, shadowSize.y));
    }

    @Override
    public void onDrawShadow(Canvas canvas)
    {
        // 在提供的Canvas上绘制阴影
        mDrawable.draw(canvas);
    }

}
