package com.bryan.example.framework.mvp.main;

import java.util.List;

public interface MainView
{
    
    public void showProgress();
    public void hideProgress();
    /**设置数据源*/
    public void setItem(List<String> items);
    /**呈现点击position*/
    public void showMessage(String message);
}
