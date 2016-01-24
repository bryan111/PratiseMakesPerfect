package com.bryan.example.view.slidingtablayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.widget.slidingtablayout.SlidingTabLayout;
import com.bryan.example.widget.slidingtablayout.SlidingTabLayout.TabColorizer;
import com.example.pratisemeanperfect.R;

public class ActionTabsActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        SlidingTabLayout tabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        pager.setAdapter(new TabsPagerAdapter(this));
        
        /**
         * SlidingTabLayout 与ViewPager关联，继承选项卡标题和滚动跟踪行为
         */
        tabLayout.setViewPager(pager);
        tabLayout.setCustomTabColorizer(new TabColorizer()
        {
            
            @Override
            public int getIndicatorColor(int position)
            {
                /**
                 * 显示在每个选项卡位置下方的颜色
                 */
                return Color.WHITE;
            }
            
            @Override
            public int getDividerColor(int position)
            {
                /**
                 * 透明以隐藏分割线，不同选项卡间的分隔线
                 */
                return 0xffffffff;
            }
        });
        
    }

    /**
     * 简单的Adapter，用于显示带有静态图片的页面视图
     */
    private static class TabsPagerAdapter extends PagerAdapter
    {

        private Context mContext;

        public TabsPagerAdapter(Context context)
        {
            super();
            this.mContext = context;
        }

        /**
         * SlidingTabLayout要求此方法定义每个选项卡将显示的文本
         */

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
            case 0:
                return "Primary";
            case 1:
                return "Secondary";
            case 2:
                return "Tertisry";
            case 3:
                return "Quaternary";
            case 4:
                return "Quinary";
            default:
                return "";
            }
        }

        @Override
        public int getCount()
        {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            ImageView pageView = new ImageView(mContext);
            pageView.setScaleType(ImageView.ScaleType.CENTER);
            pageView.setImageResource(R.drawable.ic_launcher);
            container.addView(pageView);
            return pageView;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

    }

}
