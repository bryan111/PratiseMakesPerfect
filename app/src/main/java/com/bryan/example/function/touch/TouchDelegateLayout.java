package com.bryan.example.function.touch;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.widget.CheckBox;
import android.widget.FrameLayout;

/**
 * 有时候程序中的视图或者触摸目标很小，所以手指很难按倒。
 * 使用TouchDelegate制定一个矩形区域来向小视图转发触摸事件。
 * （特定区域侦测到触摸事件后会将该事件转发给指定的子视图。）
 * @author bryan
 *
 */
public class TouchDelegateLayout extends FrameLayout
{
    private CheckBox mButton;
    
    public TouchDelegateLayout(Context context)
    {
        super(context);
        init(context);
    }
    
    public TouchDelegateLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }
    
    public TouchDelegateLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        //创建一个很小的子视图，我们要将触摸事件转发给这个视图
        mButton = new CheckBox(context);
        mButton.setText("Tap Anywhere");
        FrameLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(mButton,lp);
    }
    
    /**
     * TouchDelegate会将该视图（父视图）的某个特定矩形区域作为代理区域，将所有的触摸事件转发给CheckBox(小的子视图)。
     * 这里，矩形区域即为父视图的全部大小
     * 
     * 这个过程必须在视图确定了大小之后再进行，这样才能知道矩形有多大。所以选择了在
     * onSizeChange()中添加代理区域
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        if (w != oldw || h != oldh)
        {
            //将该视图的整个区域作为代理区域
            Rect bounds = new Rect(0, 0, w, h);
            TouchDelegate delegate = new TouchDelegate(bounds, mButton);//添加代理
            setTouchDelegate(delegate);
        }
    }

   
    

}
