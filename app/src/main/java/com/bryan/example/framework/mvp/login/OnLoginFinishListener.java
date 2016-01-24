package com.bryan.example.framework.mvp.login;
/**
 * 一个回调函数，当登陆完成的时候调用
 * 里面封装了所有可能的成果
 * @author Administrator
 *
 */
public interface OnLoginFinishListener
{
    /**用户名有误*/
    public void onUserNameError();
    /**密码有误*/
    public void onPassWordError();
    /**成功登陆*/
    public void onSuccess();
    

}
