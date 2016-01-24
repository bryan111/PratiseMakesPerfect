package com.bryan.example.function.imageview;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * 据题解释看示例代码大全150
 * @author bryan
 *
 */
public class DragTouchActivity extends BaseActivity implements OnLongClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_target);
        // 为每个ImageView关联长按监听器
        findViewById(R.id.img1).setOnLongClickListener(this);
        findViewById(R.id.img2).setOnLongClickListener(this);
        findViewById(R.id.img3).setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v)
    {
        DragShadowBuilder shadowBuilder = new CustomDrawableDragShadowBuilder(v,((ImageView) v).getDrawable());
        // 开始拖动，将View的图片作为本地状态传递出去(第三个参数，然后在onDrag（）方法中可以获得此状态)
        v.startDrag(null, shadowBuilder, ((ImageView) v).getDrawable(), 0);
        return true;
    }

}
