package com.bryan.example.function.touch;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

public class PanScrollActivity extends BaseActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PanGestureScrollView scrollView = new PanGestureScrollView(this);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 5; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.ic_launcher);
            //让每个视图可以足够大来请求滚动
            layout.addView(iv, new LinearLayout.LayoutParams(1000, 500));
        }
        
        scrollView.addView(layout);
        setContentView(scrollView);
    }

}
