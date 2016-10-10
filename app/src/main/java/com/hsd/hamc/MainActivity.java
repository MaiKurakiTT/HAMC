package com.hsd.hamc;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.AbsLoginImpl;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.sdkmanager.LoginSDKManager;

public class MainActivity extends Activity {

    CommunitySDK mCommSDK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommSDK = CommunityFactory.getCommSDK(this);
        setContentView(R.layout.layout_lg_ri);
//        Button b1 = (Button)this.findViewById(R.id.button1);
//        b1.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                LoginSDKManager.getInstance().addAndUse(new XXXLoginImpl());
//                mCommSDK.openCommunity(MainActivity.this);
//
//            }});


    }

    public class XXXLoginImpl extends AbsLoginImpl {
        @Override
        protected void onLogin(Context context, LoginListener listener) {
            // 注意用户id、昵称、source是必填项
            CommUser loginedUser = new CommUser("57ce7ad4ea77f787ff01bd83"); // 用户id
            loginedUser.name = "简单爱"; // 用户昵称
            loginedUser.source = Source.QQ;
            loginedUser.gender = CommUser.Gender.FEMALE;// 用户性别
            loginedUser.level = 1; // 用户等级，非必须字段
            loginedUser.score = 0;// 积分，非必须字段
            // 登录完成回调给社区SDK，200代表登录成功
            listener.onComplete(200, loginedUser);
        }
    }
}

