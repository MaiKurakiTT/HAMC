package com.hsd.hamc;

import android.app.Application;
import android.content.Context;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;

/**
 * Created by apple on 16/10/9.
 */
public class App extends Application{

    public static CommunitySDK mCommSDK;
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mCommSDK = CommunityFactory.getCommSDK(this);
        mContext = getApplicationContext();
    }
}
