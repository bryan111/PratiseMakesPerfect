package com.bryan.example.framework.mvp.login;

/**
 * 这个类用来实现具体的界面显示内容
 * 
 * 持有Activity的LoginView(封装了Activity更新所需要的所有方法，再需要的时候去调用更新)
 * 
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishListener
{
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView)
    {
        super();
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String userName, String passWord)
    {
        loginInteractor.login(userName, passWord, this);
    }

    @Override
    public void onUserNameError()
    {
        loginView.setUserNameError();
        loginView.hideProgress();
    }

    @Override
    public void onPassWordError()
    {
        loginView.setUserPasswordWordError();
        loginView.hideProgress();

    }

    @Override
    public void onSuccess()
    {
        loginView.go2Home();
    }

}
