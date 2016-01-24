package com.bryan.example.function.touch;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * ListView嵌套ViewPager，防止ListView的滑动事件截至了ViewPager的监听，用于处理嵌套布局所产生的事件拦截
 * 
 * 
 * @author bryan
 *
 */
public class DisallowActivity extends BaseActivity implements OnPageChangeListener
{
    private static final String[] ITEMS =
    { "item 1", "item 2", "item 3", "item 4", "item 5", "item 6", "item 7", "item 8", "item 9", "item 1", "item 2",
            "item 3", "item 4", "item 5", "item 6", "item 7", "item 8", "item 9" };

    private ViewPager mViewPager;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        // 作为ListView的高度，ViewPager必须有固定的高度

        mViewPager.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.head_height)));

        // 监听分页状态变化以禁止父视图触摸
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(new HeaderAdapter(this));

        // 创建垂直滚动列表
        mListView = new ListView(this);
        // 添加页面调度程序作为列表头部
        mListView.addHeaderView(mViewPager);
        // 添加列表项目
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITEMS));
        setContentView(mListView);

    }

    @Override
    public void onPageScrollStateChanged(int arg0)
    {
        // 当ViewPager滚动时调用，禁止ListView触摸拦截
        // 从而使它不能接管并尝试垂直滚动
        // 必须为每个要重写的手势设置此标志
        // ViewPager.SCROLL_STATE_IDLE ViewPager静止时的状态
        boolean isScrolling = arg0 != ViewPager.SCROLL_STATE_IDLE;
        /**
         * 表示是否需要调用onInterceptTouchEvent来判断是否拦截
         * true.表示不允许调用onInterceptTouchEvent(),所有父类的方法都不会进行拦截
         * ，从而保证事件能够被正确的传递给子控件
         */
        mListView.requestDisallowInterceptTouchEvent(isScrolling);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
    }

    @Override
    public void onPageSelected(int arg0)
    {
    }

    private static class HeaderAdapter extends PagerAdapter
    {

        private Context context;

        public HeaderAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount()
        {
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            // 创建新的页面视图
            TextView tv = new TextView(context);
            tv.setText(String.format("page %d", position + 1));
            tv.setBackgroundColor((position % 2 == 0) ? Color.RED : Color.GREEN);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.BLACK);

            // 添加此位置的视图，返回此位置的对象
            container.addView(tv);
            return tv;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            View page = (View) object;
            container.removeView(page);
        }

    }

}
