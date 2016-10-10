package com.hsd.hamc.login.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsd.hamc.App;
import com.hsd.hamc.R;
import com.hsd.hamc.login.LoginConstract;
import com.hsd.hamc.login.presenter.LoginPresenterImpl;


/**
 * Created by apple on 16/10/9.
 */
public class LoginActivity extends Activity implements View.OnClickListener,LoginConstract.LoginView{


    private EditText usr;
    private EditText pwd;
    private RelativeLayout commit;
    private RelativeLayout forgot;
    private TextView register;

    private LoginConstract.LoginPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lg_ri);
        findView();

    }

    private void findView() {
        usr = (EditText) findViewById(R.id.login_usr);
        pwd = (EditText) findViewById(R.id.login_pwd);
        commit = (RelativeLayout) findViewById(R.id.login_submit);
        forgot = (RelativeLayout) findViewById(R.id.login_forgot_pwd);
        register = (TextView) findViewById(R.id.login_register);

        commit.setOnClickListener(this);
        forgot.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        System.out.println("onClick");
        int id = v.getId();
        switch (id){
            case R.id.login_submit :
                System.out.println("onClick login_submit");
                if(invidate()){
                    presenter = new LoginPresenterImpl(this);
                    presenter.login(usr.getText().toString(),pwd.getText().toString());
                }else{
                    Toast.makeText(this,R.string.notnull,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_forgot_pwd:
                toFindPwd();
                break;
            case R.id.login_register:
                toRegister();
                break;
        }
    }

    @Override
    public void toComment() {
        App.mCommSDK.openCommunity(App.mContext);
        this.onFinish();
    }

    @Override
    public void toRegister() {
        // TODO: 16/10/9  跳转到注册页面
        Toast.makeText(this,"toRegister",Toast.LENGTH_SHORT);
    }

    @Override
    public void toFindPwd() {
        // TODO: 16/10/9 跳转到密码找回页面
        Toast.makeText(this,"toFindPwd",Toast.LENGTH_SHORT);
    }

    @Override
    public void onFinish() {
        this.finish();
    }

    //校验数据有效性
    public boolean invidate(){
        boolean flag = false;
        System.out.println(usr.getText().toString());
        if(usr.getText().toString().equals("") || pwd.getText().toString().equals("")){
            return flag;
        }else{
            flag = true;
        }
        return flag;
    }
}
