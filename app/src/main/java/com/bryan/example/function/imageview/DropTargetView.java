package com.bryan.example.function.imageview;

import cn.bmob.v3.a.a.This;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;

/**
 * 据题解释看示例代码大全150
 * @author bryan
 *
 */
public class DropTargetView extends ImageView implements OnDragListener
{

    private boolean mDropped;

    public DropTargetView(Context context)
    {
        super(context);
        init();
    }

    public DropTargetView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public DropTargetView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        // 我们必须设置一个有效的监听器来接收DragEvent
        setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event)
    {
        PropertyValuesHolder pvhX,pvhY;
        switch (event.getAction())
        {
        case DragEvent.ACTION_DRAG_STARTED:
            //通过收缩视图响应新的拖动动作
            pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.5f);
            pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.5f);
            ObjectAnimator.ofPropertyValuesHolder(this,pvhX,pvhY).start();
            //新的拖动行为会清空当前的放置图片
            setImageDrawable(null);
            mDropped = false;
            break;
        case DragEvent.ACTION_DRAG_ENDED:
            //拖动结束时，如果没有找到放置目标，视图则恢复到原来的大小
            if (!mDropped){
                pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f);
                pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f);
                ObjectAnimator.ofPropertyValuesHolder(this,pvhX,pvhY).start();
                mDropped = false;
            }
          
            break;
        case DragEvent.ACTION_DRAG_ENTERED:
            //拖动进入到边界区域时，视图会稍微放大一点
            pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.75f);
            pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.75f);
            ObjectAnimator.ofPropertyValuesHolder(this,pvhX,pvhY).start();
            break;
        case DragEvent.ACTION_DRAG_EXITED:
            //拖动离开视图时，视图会恢复到之前的大小
            pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.5f);
            pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.5f);
            ObjectAnimator.ofPropertyValuesHolder(this,pvhX,pvhY).start();
            break;
        case DragEvent.ACTION_DROP:
            //拖动后放置时会有一段简短的关键帧动画并将视图的图片设置为拖动事件传入的drawable图片
            //这个动画会收缩一下视图然后再还原
            Keyframe frame0 = Keyframe.ofFloat(0f, 0.75f);
            Keyframe frame1 = Keyframe.ofFloat(0.5f, 0f);
            Keyframe frame2 = Keyframe.ofFloat(1f, 0.75f);
            
            pvhX = PropertyValuesHolder.ofKeyframe("scaleX", frame0,frame1,frame2);
            pvhY = PropertyValuesHolder.ofKeyframe("scaleY", frame0,frame1,frame2);
            ObjectAnimator.ofPropertyValuesHolder(this, pvhX,pvhY).start();
            //DragEvent中传递的object设置我们的图片
            setImageDrawable((Drawable)event.getLocalState());
            //我们设置放置标识让ENDED动画不再运行
            mDropped = true;
            break;
        default:
            //忽略不感兴趣的事件
           return false;
        }
        //处理感兴趣的事件
        return true;
    }

}
