package com.bryan.example.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

/**
 * 所有Activity的基类 -可用来保存一些基本数据或者功用方法
 * 
 * @author Administrator
 * 
 */
public class BaseActivity extends Activity implements OnClickListener {

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	protected void go2Activity(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	protected void toast(String content) {
		Toast.makeText(BaseActivity.this, content, Toast.LENGTH_SHORT).show();
	}
	
	protected void print(String desc,String content){
	    Log.e(desc, content);
	}

}
