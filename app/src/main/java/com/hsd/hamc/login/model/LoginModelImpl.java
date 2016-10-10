package com.hsd.hamc.login.model;

import com.hsd.hamc.App;
import com.hsd.hamc.login.LoginConstract;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.login.LoginListener;

/**
 * Created by apple on 16/10/9.
 */
public class LoginModelImpl implements LoginConstract.LoginModel {

    private LoginListener loginListener;

    @Override
    public void login(String usr, String pwd, LoginListener loginListener) {
        if(loginListener == null){
            new NullPointerException("loginListener" + "不可以为空" + "loginListener can't is null");
        }else{
            CommUser user = new CommUser();
            user.name = usr;
            user.id = usr;
            App.mCommSDK.loginToUmengServerBySelfAccount(App.mContext,user,loginListener);
        }
    }
}
