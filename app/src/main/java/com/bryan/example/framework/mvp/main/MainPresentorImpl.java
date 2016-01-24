package com.bryan.example.framework.mvp.main;

import java.util.List;

public class MainPresentorImpl implements MainPresentor, OnFinishListener
{
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresentorImpl(MainView mainView)
    {
        super();
        this.mainView = mainView;
        mainInteractor = new MainInteractorImpl();
    }

    @Override
    public void onFinish(List<String> items)
    {
        mainView.setItem(items);
        mainView.hideProgress();

    }

    @Override
    public void onResume()
    {
        mainView.showProgress();
        mainInteractor.findItem(this);// 加载数据

    }

    @Override
    public void onItemClicked(int position)
    {
        mainView.showMessage(position + "");

    }

}
