package com.bryan.example.function.touch;

import android.R.integer;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class PanScrollView extends FrameLayout
{
    /**
     * 急滑控件
     */
    private Scroller mScroller;
    /**
     * 这个是干啥用的？
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 上一个移动事件的位置
     */
    private float mLastTouchX, mLastTouchY;
    /**
     * 拖动的临界值
     */
    private int mTouchSlop;
    /**
     * 急滑的速度
     */
    private int mMaximumVelocity, mMinmumVelocity;
    /**
     * 拖动锁
     */
    private boolean mDragging = false;

    public PanScrollView(Context context)
    {
        super(context);
        init(context);
    }

    public PanScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public PanScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        // 获得触摸临界值的系统常量
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinmumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    /**
     * 覆写MeasureChild的实现来保证子视图尽可能大 默认实现会强制一些子视图和该视图一样大
     */
    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec)
    {
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

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

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            // 这个方法会在ViewGroup绘制时调用
            // 我们使这个方法保证急滑动画的完成
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
            // 在动画完成前会一直绘制
            postInvalidate();
        }
    }

    /**
     * 覆写SctollTo方法一进行每个滚动请求的边界检查
     */
    @Override
    public void scrollTo(int x, int y)
    {
        // 我们依赖View.scrollBy调用scrollTo
        if (getChildCount() > 0)
        {

            View child = getChildAt(0);
            x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), child.getWidth());
            y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
            if (x != getScrollX() || y != getScrollY())
            {
                super.scrollTo(x, y);
            }
        }
    }

    /**
     * 监控传递给子视图的触摸事件，并且一旦确定拖曳就进行拦截 如果子视图是可交互的（如按钮）。那么已然允许子视图接受触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            // 终止所有正在进行的急滑动画
            if (!mScroller.isFinished())
            {
                mScroller.abortAnimation();
            }
            // 还原速度跟踪器
            mVelocityTracker.clear();
            mVelocityTracker.addMovement(ev);
            // 保存初始触点
            mLastTouchX = ev.getX();
            mLastTouchY = ev.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float x = ev.getX();
            final float y = ev.getY();

            final int yDiff = (int) Math.abs(y - mLastTouchY);
            final int xDiff = (int) Math.abs(x - mLastTouchX);
            // 检查x或y上的距离是否适合拖曳
            if (yDiff > mTouchSlop || xDiff > mTouchSlop)
            {
                mDragging = true;
                mVelocityTracker.addMovement(ev);
                // 我们自己开始捕捉事件
                return true;
            }
            break;

        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP:
            mDragging = false;
            mVelocityTracker.clear();
            break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 将我们接收到的所有触摸事件传给检测器处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mVelocityTracker.addMovement(event);

        switch (event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            // 我们已经保存了初始触点，但如果这里发现有子视图没有捕捉事件，还是需要返回true的
            return true;
        case MotionEvent.ACTION_MOVE:
            final float x = event.getX();
            final float y = event.getY();
            float deltaX = mLastTouchX - x;
            float deltaY = mLastTouchY - y;
            // 检查各个方向时间上的临界值
            if ((Math.abs(deltaY) > mTouchSlop || Math.abs(deltaX) > mTouchSlop) && !mDragging)
            {
                mDragging = true;
            }

            if (mDragging)
            {
                // 滚动试图
                scrollBy((int) deltaX, (int) deltaY);
                // 更新最后一个触摸事件
                mLastTouchX = x;
                mLastTouchY = y;

            }

            break;
        case MotionEvent.ACTION_CANCEL:
            mDragging = false;
            // 终止所有进行的急滑动画
            if (mScroller.isFinished())
            {
                mScroller.abortAnimation();
            }
            break;
        case MotionEvent.ACTION_UP:
            mDragging = false;
            // 计算当前的速度，如果高于最小临界值，则启动一个急滑
            mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
            int velocityX = (int) mVelocityTracker.getXVelocity();
            int velocityY = (int) mVelocityTracker.getYVelocity();
            if (Math.abs(velocityX) > mMinmumVelocity || Math.abs(velocityY) > mMinmumVelocity)
            {
                fling(-velocityX, -velocityY);
            }
            break;
        }
        return super.onTouchEvent(event);
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
