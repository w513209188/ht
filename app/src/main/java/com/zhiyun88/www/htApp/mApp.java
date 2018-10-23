package com.zhiyun88.www.htApp;

import android.app.Application;

import com.wb.baselib.BaseApplication;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.log.LogTools;
import com.zhiyun88.www.module_main.hApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mApp extends BaseApplication {
    @Override
    public String getRootPackAge() {
        return "wangbo";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hApp.newInstance().initVideoPlay(this);
    }
}
