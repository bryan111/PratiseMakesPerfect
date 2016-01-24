package com.bryan.example.view.drawer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * 侧滑菜单
 * 
 * @author bryan
 *
 */
public class DrawerActivity extends BaseActivity implements OnItemClickListener
{

    private static final String[] ITEMS =
    { "White", "Red", "Green", "Blue" };
    private static final int[] COLORS =
    { Color.WHITE, 0xffe51c23, 0xff2598ec, 0xff5677fc };

    private DrawerLayout mDrawerContainer;
    // 布局中的跟布局内容
    private View mMainContent;
    // 左侧滑动Drawer
    private ListView drawerLeft;
    // 右侧滑动Drawer
    private LinearLayout drawerRight;
    /** ActionBar的开关对象 */
    private ActionBarDrawerToggle mDrawerToggle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        mDrawerContainer = (DrawerLayout) findViewById(R.id.container);
        drawerLeft = (ListView) findViewById(R.id.drawer_main);
        mMainContent = findViewById(R.id.container_root);
        findViewById(R.id.open).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                openOrCloseDrawer();
            }
        });
        // /**
        // * 开关指示器也必须是Drawer侦听器 因此扩展该侦听器以侦听事件自身
        // */
        // mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerContainer,
        // R.drawable.ic_launcher, R.string.open, R.string.close) {
        // @Override
        // public void onDrawerOpened(View drawerView) {
        // super.onDrawerOpened(drawerView);
        // // 更新选项菜单
        // }
        //
        // @Override
        // public void onDrawerStateChanged(int newState) {
        // super.onDrawerStateChanged(newState);
        // // 更新选项菜单
        // }
        //
        // @Override
        // public void onDrawerClosed(View drawerView) {
        // super.onDrawerClosed(drawerView);
        // // 更新选项菜单
        // }
        // };

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITEMS);
        drawerLeft.setAdapter(adapter);

        drawerLeft.setOnItemClickListener(this);

        // mDrawerContainer.setDrawerListener(mDrawerToggle);
        // 在ActionBar中启用home按钮动作
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        // getActionBar().setHomeButtonEnabled(true);
    }

    // @Override
    // protected void onPostCreate(Bundle savedInstanceState) {
    // super.onPostCreate(savedInstanceState);
    // // 在框架还原任意实例状态之后同步Drawer的状态
    // mDrawerToggle.syncState();
    // }

    // @Override
    // public void onConfigurationChanged(Configuration newConfig) {
    // super.onConfigurationChanged(newConfig);
    // // 在更改任意配置时更新状态
    // mDrawerToggle.onConfigurationChanged(newConfig);
    // }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // // 创建ActionBar动作
    // getMenuInflater().inflate(R.menu.main, menu);
    // return true;
    // }
    //
    // @Override
    // public boolean onPrepareOptionsMenu(Menu menu) {
    //
    // // 基于主Drawer的状态显示动作选项,当侧滑菜单显示的时候隐藏ActionBar菜单
    // boolean isOpen = mDrawerContainer.isDrawerVisible(drawerLeft);
    // menu.findItem(R.id.action_delete).setVisible(!isOpen);
    // menu.findItem(R.id.action_settings).setVisible(!isOpen);
    // return super.onPrepareOptionsMenu(menu);
    //
    // }
    //
    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    //
    // // 首先让Drawer在事件处有一个缺口，从而处理Home按钮事件
    // if (mDrawerToggle.onOptionsItemSelected(item)) {
    // // 如果这是一个Drawer开关，我们需要更新选项菜单
    // // 但必须等到下一次循环遍历Drawer状态改变时再更新
    // mDrawerContainer.post(new Runnable() {
    //
    // @Override
    // public void run() {
    // // 更新选项菜单
    // supportInvalidateOptionMenu();
    // }
    // });
    // // 不再执行下去
    // return true;
    // }
    //
    // switch (item.getItemId()) {
    // case R.id.action_settings:
    // // 设置动作
    // return true;
    // case R.id.action_delete:
    // // 删除动作
    // return true;
    //
    // default:
    // return super.onOptionsItemSelected(item);
    // }
    //
    // }
    //
    // /**
    // * 更新选项菜单
    // */
    // protected void supportInvalidateOptionMenu() {
    // // TODO Auto-generated method stub
    //
    // }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // 更新住内容的背景色
        mMainContent.setBackgroundColor(COLORS[position]);
        // 手动关闭Drawer
        mDrawerContainer.closeDrawer(drawerLeft);
    }

    /**
     * 打开左滑菜单
     */
    public void openOrCloseDrawer()
    {
        if (mDrawerContainer != null)
        {
            if (mDrawerContainer.isDrawerOpen(Gravity.START))
            {
                mDrawerContainer.closeDrawers();

            }
            else
            {
                mDrawerContainer.openDrawer(Gravity.START);
            }

        }
    }

}
