package com.bryan.example.bmob.test;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser
{

    private String nick;
    private Integer number;

    public String getNick()
    {
        return nick;
    }

    public void setNick(String nick)
    {
        this.nick = nick;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

}
