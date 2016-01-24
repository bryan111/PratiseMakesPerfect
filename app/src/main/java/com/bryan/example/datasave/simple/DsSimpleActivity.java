package com.bryan.example.datasave.simple;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * 问题：简单而低开销的方式持久存储保存一些基本数据，以便离线时也能显示这些数据
 * 方法：使用SP进行数据存储，比文件读写高效
 * 
 * 1-sp_将数据以XML形式存储在应用程序数据存储区
 * 
 * 2-如何保存一组相关联的内容（如已经登陆的用户）呢？当该用户退出时将需要删除他所保存的所有数据。
 * 如果将这些数据单独创建SP，则但用户退出时则只需要调用formStore.edit().clear();即可
 * 
 * @author bryan
 * 
 */
public class DsSimpleActivity extends BaseActivity {

	private EditText emailEt;
	private EditText messageEt;
	private Button submitBtn;
	private CheckBox ageCb;
	private SharedPreferences formStore;

	private boolean commitSuccess = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple);

		emailEt = (EditText) findViewById(R.id.email);
		messageEt = (EditText) findViewById(R.id.message);
		ageCb = (CheckBox) findViewById(R.id.age);
		submitBtn = (Button) findViewById(R.id.commit);
		submitBtn.setOnClickListener(this);

		// 创建或获取首选项对象
		formStore = getPreferences(MODE_PRIVATE);// 以Activity命名获得/创建一个SP
		// formStore = getSharedPreferences("spName",MODE_PRIVATE);//获得/创建自己命名的SP
		// formStore =PreferenceManager.getDefaultSharedPreferences(this);//获得系统命名的SP
		// 在不同的Activity中可以访问同一个SP，只需要同一个SP的名字即可访问。因为SP的创建时单实例对象。所以在不同的Activity可以去修改同一个SP文件中的内同
		//参数MODE_PRIVATE  表示只能创建这个SP的应用程序才能访问这个sp文件，其他应用程序访问不了
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 还原表单数据
		emailEt.setText(formStore.getString("email", ""));
		messageEt.setText(formStore.getString("message", ""));
		ageCb.setChecked(formStore.getBoolean("age", false));
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (commitSuccess) {
			// 当成功发送了之后就不再保存表单数据
			formStore.edit().clear();// 清楚表单数据
		} else {
			// 还没发送成功就保存表单数据，下次回到这个界面之后就恢复数据
			// 保存表单数据
			SharedPreferences.Editor editor = formStore.edit();
			editor.putString("email", emailEt.getText().toString().trim());
			editor.putString("message", messageEt.getText().toString().trim());
			editor.putBoolean("age", ageCb.isChecked());
			editor.apply();
		}
	}

	@Override
	public void onClick(View v) {

		// 发送信息
		// 发送成功之后就清除SP中的内容，将标志位设置为成功
		commitSuccess = true;
		// formStore.edit().clear();
		finish();
	}
}
