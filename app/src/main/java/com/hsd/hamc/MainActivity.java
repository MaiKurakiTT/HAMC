package com.hsd.hamc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.hsd.hamc.video.view.VideoPlayerActivity;
import com.hsd.hamc.video.view.widget.VideoTouchView;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.AbsLoginImpl;
import com.umeng.comm.core.login.LoginListener;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.demo.VideoSubtitleList;

public class MainActivity extends Activity {

    CommunitySDK mCommSDK;
    private String path = "dt591.com/111.mp4";
    private VideoView view;

    private VideoTouchView videoView;
    private RelativeLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_test);
        mCommSDK = CommunityFactory.getCommSDK(this);
        if (!LibsChecker.checkVitamioLibs(this))
            return;

        fragment = (RelativeLayout) findViewById(R.id.frame);
        videoView = (VideoTouchView) findViewById(R.id.videoView);
        videoView.setmActivity(this);
        videoView.setPath(path);
        videoView.getmVideoView().setVideoPath(path);
//        loadVitamio();

        //finish();


//        view = (VideoView) findViewById(R.id.video);
//        view.setVideoPath("http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/movie.mp4");
//        view.start();




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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return videoView.onTouchEvent(event);
    }

    private void loadVitamio() {

        if (!LibsChecker.checkVitamioLibs(this))
            return;
        System.out.print("loadVitamio before");
        videoView.setmActivity(this);
        videoView.setPath("www.dt591.com/111.mp4");
//        Intent intent = new Intent(this,VideoPlayerActivity.class);
//        startActivity(intent);

        System.out.print("loadVitamio ");
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