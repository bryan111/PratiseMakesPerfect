package com.bryan.example.framework.mvp.main;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

public class MyMainActivity extends BaseActivity implements MainView, OnItemClickListener
{
    private ProgressBar mProgressBar;
    private ListView mListView;
    private MainPresentor mainPresentor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mainPresentor = new MainPresentorImpl(this);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mainPresentor.onResume();// 给界面数据赋值
    }

    @Override
    public void showProgress()
    {
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);

    }

    @Override
    public void hideProgress()
    {
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItem(List<String> items)
    {
        mListView.setAdapter(new ArrayAdapter(MyMainActivity.this, android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void showMessage(String message)
    {
        Toast.makeText(MyMainActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        mainPresentor.onItemClicked(position);
    }

}
