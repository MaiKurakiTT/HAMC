package com.hsd.hamc.login.presenter;

import com.hsd.hamc.login.LoginConstract;
import com.hsd.hamc.login.activity.LoginActivity;
import com.hsd.hamc.login.model.LoginModelImpl;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.login.LoginListener;

/**
 * Created by apple on 16/10/9.
 */
public class LoginPresenterImpl implements LoginConstract.LoginPresenter {

    public LoginConstract.LoginView view;

    public LoginConstract.LoginModel model ;


    public LoginPresenterImpl(LoginActivity activity) {
        view = activity;
        model = new LoginModelImpl();
    }

    @Override
    public void login(String usr, String pwd) {
        LoginListener listener = new LoginListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int i, CommUser commUser) {
                if (ErrorCode.NO_ERROR==i) {
                    view.toComment();
                }
            }
        };
       model.login(usr,pwd,listener);
    }
}
