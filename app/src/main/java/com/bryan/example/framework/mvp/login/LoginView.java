package com.bryan.example.framework.mvp.login;

/**
 * 这个方法封装了更新界面所有需要的方法,然后在Activity中实现
 * @author Administrator
 *
 */
public interface LoginView
{
    public void setUserNameError();
    public void setUserPasswordWordError();
    public void showProgress();
    public void hideProgress();
    public void go2Home();

}
