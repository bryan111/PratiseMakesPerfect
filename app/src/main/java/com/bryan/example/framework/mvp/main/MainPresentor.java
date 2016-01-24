package com.bryan.example.framework.mvp.main;

public interface MainPresentor
{
    /**什么时候需要这样去调用呢？*/
    public void onResume();
    /**封装ListView的点击事件*/
    public void onItemClicked(int position);
}
