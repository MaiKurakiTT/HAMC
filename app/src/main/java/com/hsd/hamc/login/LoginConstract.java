package com.hsd.hamc.login;

import com.umeng.comm.core.login.AbsLoginImpl;
import com.umeng.comm.core.login.LoginListener;

/**
 * Created by apple on 16/10/9.
 */
public class LoginConstract {

    public interface LoginView{
        public void toComment();
        public void toRegister();
        public void toFindPwd();
        public void onFinish();

    }
    public interface LoginPresenter{
        public void login(String usr,String pwd);
    }
    public interface LoginModel{
        public void login(String usr, String pwd , LoginListener loginListener);
    }

}
