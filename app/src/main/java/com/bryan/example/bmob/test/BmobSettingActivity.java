package com.bryan.example.bmob.test;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

import com.bryan.example.base.BaseActivity;
/**
 * Bmob基本搭建
 * @author bryan
 *
 */
public class BmobSettingActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("这是BMOB测试页");
        tv.setGravity(Gravity.CENTER);
        setContentView(tv);
        Bmob.initialize(this, Config.APP_ID);
        insertData();
        signUpData();
    }
    
    /**
     * 注册一个用户
     */
    private void signUpData(){
        final User user = new User();
        user.setUsername("用户1");
        user.setPassword("123456");
        user.setNick("bryan");
        user.setNumber(123);
        user.signUp(this, new SaveListener()
        {
            
            @Override
            public void onSuccess()
            {
                toast("数据添加成功,返回的ObjectID为:"+user.getObjectId());
            }
            
            @Override
            public void onFailure(int arg0, String arg1)
            {
               toast("数据创建失败"+arg1);
            }
        });
    }

    /**
     * 添加一条数据
     */
    private void insertData()
    {
        final Person person = new Person();
        person.name = "bryan";
        person.address = "福建省厦门市湖里区";
        person.save(this, new SaveListener()
        {
            
            @Override
            public void onSuccess()
            {
                toast("数据添加成功,返回的ObjectID为:"+person.getObjectId());
            }
            
            @Override
            public void onFailure(int arg0, String arg1)
            {
               toast("数据创建失败"+arg1);
            }
        });
        
    }
    
    
    

}
