package com.hsd.hamc.register;

/**
 * Created by apple on 16/10/10.
 */
public class RegisterConstract {
    public interface RegisterView{
         void onFinish();
         void onFail();
    }
    public interface  RegisterModel{
        void register(String user , String pwd );
    }
    public interface RegisterPresenter{

    }
}
