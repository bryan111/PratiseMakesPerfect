package com.bryan.example.function.touch;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;


/**
 * 自定义触摸事件转发
 * 
 * 避免直接在onTouchEvent（）方法中直接对触摸事件进行处理。调用dispatchTouchEvent（）可以使其
 * 像常规触摸事件一样处理目标试图的触摸事件。包括必要时的事件拦截
 */
public class RemoteScrollActivity extends BaseActivity implements OnTouchListener
{
    private TextView mTouchText;
    private HorizontalScrollView mScrollView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romate_scroll);
        mTouchText = (TextView) findViewById(R.id.text_touch);
        mScrollView = (HorizontalScrollView) findViewById(R.id.scroll_view);
        mTouchText.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        //如果需要的话可以修改事件的位置
        //这里我们将每个事件的垂直方向的位置设置为HorizontalScrollView的中间
        
        /**
         * 视图需要的事件位置都是相对于自己的坐标
         * 修改x/y的坐标。将y调整到HorizontalScrollView的中间，这样当手指向前向后滚动时，就好像在
         * HorizontalScrollView的中间滚动一样。然后调用dispatchTouchEvent将事件交给HorizontalScrollView处理
         */
//        event.setLocation(event.getX(), mScrollView.getHeight()/2);
        
        //将TextView上的每个事件转发到HorizontalScrollView
        mScrollView.dispatchTouchEvent(event);
        return true;
    }
    

}
