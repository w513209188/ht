package com.zhiyun88.www.module_main;

import android.app.Application;

import com.baijiahulian.BJVideoPlayerSDK;

public class hApp {
    private static hApp mApp;
    public static hApp newInstance(){
        if (mApp==null){
            mApp=new hApp();
        }
        return mApp;
    }
    public void initVideoPlay(Application application){
        BJVideoPlayerSDK.getInstance().init(application);
    }
}
