package com.bryan.example.function.touch;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 通过GestureDector自定义ViewGroup
 * 
 * android提供了大量有用的临界值常量，这个值可以根据设备的分辨率进行缩放。这些常量都保存在ViewConfiguration中
 * ViewConfiguration.get(context).getScaledDoubleTapSlop();
 * 
 * @author bryan
 *
 */
public class PanGestureScrollView extends FrameLayout
{
    /**
     * 用来快速的处理手势事件
     */
    private GestureDetector mDetector;
    /**
     * 这个是用来干什么的
     */
    private Scroller mScroller;

    /**
     * 最后位移事件的位置
     */
    private float mInitialX, mInitialY;
    /**
     * 拖曳阈值
     */
    private int mTouchSlop;
    /**
     * 处理所有触摸事件的监听器
     */
    private SimpleOnGestureListener mListener = new SimpleOnGestureListener()
    {

        public boolean onDown(MotionEvent e)
        {
            /**
             * 取消当前的急滑动画
             */
            if (!mScroller.isFinished())
            {
                mScroller.abortAnimation();
            }
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            // 调用一个辅助方法来启动滚动动画
            fling((int) -velocityX / 3, (int) -velocityY / 3);
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            // 任意视图都可以调用他的ScrollBy（）进行滚动
            scrollBy((int) distanceX, (int) distanceY);
            return true;
        }

    };
    

    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            // 会在ViewGroup绘制时调用
            // 我们使用这个方法保证急滑动画的顺利完成
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();

            if (getChildCount() > 0)
            {
                View child = getChildAt(0);
                x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), child.getWidth());
                y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
                if (x != oldX || y != oldY)
                {
                    scrollTo(x, y);
                }
            }

        }
        // 在动画完成前会一直绘制
        postInvalidate();
    };

    /**
     * 覆写scrollTo()方法进行每个滚动请求的边界检查
     */
    @Override
    public void scrollTo(int x, int y)
    {
        // 我们依赖View.scrollBy调用scrollTo
        if (getChildCount() > 0)
        {
            View child = getChildAt(0);
            x = clamp(x, getWidth() - getPaddingLeft() - getPaddingRight(), child.getWidth());
            y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
            if (x != getScaleX() || y != getScaleY())
            {
                super.scrollTo(x, y);
            }
        }
    }

    /**
     * 监控传递给子视图的触摸事件，并且一旦确定拖曳就进行拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            mInitialX = ev.getX();
            mInitialY = ev.getY();
            //将按下事件传给手势检测器，这样当/如果拖曳开始就有了上下文
            mDetector.onTouchEvent(ev);
            break;
        case MotionEvent.ACTION_MOVE:
            //为什么这边要用final修饰
            final float x = ev.getX();
            final float y = ev.getY();
            final int yDiff = (int) Math.abs(y - mInitialY);
            final int xDiff = (int) Math.abs(x - mInitialX);
            //检查x或y上的距离是否适合拖曳
            if (yDiff > mTouchSlop || xDiff > mTouchSlop)
            {
                //开始捕捉事件
                return true;
            }
            break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    
    /**
     * 将我们所接受的所有触摸事件传递给检测器处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mDetector.onTouchEvent(event);
    }

    public PanGestureScrollView(Context context)
    {
        super(context);
        init(context);
    }

    public PanGestureScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PanGestureScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    private void init(Context context)
    {
        mDetector = new GestureDetector(context, mListener);
        mScroller = new Scroller(context);

        /**
         * 获得触摸临界值的系统常量,滑动多少算滑动
         * android提供了大量有用的临界值常量，这个值可以根据设备的分辨率进行缩放。这些常量都保存在ViewConfiguration中
         */
        mTouchSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
    }

    /**
     * 覆写MeasureChild的实现来保证生成的子视图尽可能大 默认实现会强制一些子视图和该视图一样大
     */
    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec)
    {
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * 这个方法是干什么用的？
     */
    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
            int parentHeightMeasureSpec, int heightUsed)
    {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.leftMargin + lp.rightMargin,
                MeasureSpec.UNSPECIFIED);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.topMargin + lp.bottomMargin,
                MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * 初始化Scroller和开始重新绘制的实用方法
     */
    public void fling(int velocityX, int velocityY)
    {
        if (getChildCount() > 0)
        {
            int height = getHeight() - getPaddingBottom() - getPaddingTop();
            int width = getWidth() - getPaddingLeft() - getPaddingRight();

            int bottom = getChildAt(0).getHeight();
            int right = getChildAt(0).getWidth();
            mScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, 0, Math.max(0, right - width), 0,
                    Math.max(0, bottom - height));
            invalidate();
        }
    }

    /**
     * 用来进行边界检查的辅助使用方法
     */
    public int clamp(int n, int my, int child)
    {
        if (my >= child || n < 0)
        {
            // 子视图超过了父视图的边界或者小于父视图，不能滚动
            return 0;
        }

        if ((my + n) > child)
        {
            // 请求的滚动超出了子视图的右边界
            return child - my;
        }

        return n;
    }

}
