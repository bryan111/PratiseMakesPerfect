package com.bryan.example.framework.mvp.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * Activity类其实是只跟Presenter类进行交互的。
 * 
 * presenter这个类相当于一个中间层，专门负责界面数据的呈现。Activity所有数据的呈现都经由这个类来完成。
 * 包括点击等事件也是由这个类来完成。在Activity中持有Presenter这个实现类的子类
 * 。借由LoginView(封装了Activity呈现数据所有需要的方法) 传到Presenter的子类中，然后去更新界面的数据。
 * 这样Activity就只负责去呈现数据，不包含任何的业务逻辑
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivity implements LoginView {
	private EditText mUserNameEt;
	private EditText mPassWordEt;
	private ProgressBar mProgressBar;
	private LoginPresenter loginPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mUserNameEt = (EditText) findViewById(R.id.username);
		mPassWordEt = (EditText) findViewById(R.id.password);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);
		findViewById(R.id.button).setOnClickListener(this);

		loginPresenter = new LoginPresenterImpl(this);

	}

	@Override
	public void setUserNameError() {
		mUserNameEt.setError("User Name cannot be empty");
	}

	@Override
	public void setUserPasswordWordError() {
		mPassWordEt.setError("password cannot be empty");
	}

	@Override
	public void showProgress() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void go2Home() {
		go2Activity(com.bryan.example.framework.mvp.main.MyMainActivity.class);
		finish();
	};

	@Override
	public void onClick(View v) {
		loginPresenter.validateCredentials(mUserNameEt.getText().toString()
				.trim(), mPassWordEt.getText().toString().trim());

	}

}
