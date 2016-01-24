package com.bryan.example.datasave;

import android.os.Bundle;
import android.view.View;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.datasave.assets.AssetsActivity;
import com.bryan.example.datasave.file.ExternalDsActivity;
import com.bryan.example.datasave.file.InternalDsActivity;
import com.bryan.example.datasave.simple.DsSimpleActivity;
import com.example.pratisemeanperfect.R;

/**
 * 各种情况的数据存储进行的总结
 * 
 * @author bryan
 * 
 */
public class DataSaveMainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datasava_main);
		findViewById(R.id.simple).setOnClickListener(this);
		findViewById(R.id.internal).setOnClickListener(this);
		findViewById(R.id.external).setOnClickListener(this);
		findViewById(R.id.assets).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.simple:
			go2Activity(DsSimpleActivity.class);
			break;
		case R.id.internal:
			go2Activity(InternalDsActivity.class);
			break;
		case R.id.external:
			go2Activity(ExternalDsActivity.class);
			break;
		case R.id.assets:
			go2Activity(AssetsActivity.class);
			break;
		default:
			break;
		}
	}

}
