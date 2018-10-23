package com.zhiyun88.www.htApp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.utils.ToActivityUtil;
import com.zhiyun88.www.module_main.main.ui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private Button jj_bt,hr_bt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        jj_bt=this.findViewById(R.id.jj_bt);
        hr_bt=this.findViewById(R.id.hr_bt);
        jj_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> httpCodeOff = new ArrayList<>();
                httpCodeOff.add(201);

                AppConfig appConfig = new AppConfig.Bulider()
                        .setMaxPage(10)
                        .setHttpCodeOff(httpCodeOff)
                        .bulider();
                AppConfigManager.newInstance().setAppConfig(appConfig);
                LogTools.setDebug(true);

                Map<String,String> map=new HashMap<>();
                map.put("Authorization","bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC90ZXN0LXB4Lmh1YXR1LmNvbVwvYXBpXC9hcHBcL2NoZWNrQ2FzIiwiaWF0IjoxNTQwMTc1NDk1LCJleHAiOjE1NDA0Nzc4OTUsIm5iZiI6MTU0MDE3NTQ5NSwianRpIjoiT3FBT2ZNODFDcVVMbFRJMyIsInN1YiI6MzA4NTgsInBydiI6ImI5MTI3OTk3OGYxMWFhN2JjNTY3MDQ4N2ZmZjAxZTIyODI1M2ZlNDgifQ.oGBDJI5T5JV67raVQMx0Y7oN6VsNpfp2_ZZdh4dRt7o");
                HttpConfig.HttpConfigBuilder httpConfig = new HttpConfig.HttpConfigBuilder()
                        .setmBaseUrl("http://test-px.huatu.com")
                        .setmMapHeader(map)
                        .setmIsUseLog(true);
                HttpConfig.newInstanceBuild(httpConfig);
                ToActivityUtil.newInsance().toNextActivity(TestActivity.this, MainActivity.class);
            }
        });
        hr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> httpCodeOff = new ArrayList<>();
                httpCodeOff.add(201);

                AppConfig appConfig = new AppConfig.Bulider()
                        .setMaxPage(10)
                        .setHttpCodeOff(httpCodeOff)
                        .bulider();
                AppConfigManager.newInstance().setAppConfig(appConfig);
                LogTools.setDebug(true);

                Map<String,String> map=new HashMap<>();
                map.put("Authorization","bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC90ZXN0LXB4Lmh1YXR1LmNvbVwvYXBpXC9hcHBcL2NoZWNrQ2FzIiwiaWF0IjoxNTQwMTg4NDU5LCJleHAiOjE1NDA0OTA4NTksIm5iZiI6MTU0MDE4ODQ1OSwianRpIjoiSm0xemtXaklxV25jZE9sWSIsInN1YiI6MzA4NjAsInBydiI6ImI5MTI3OTk3OGYxMWFhN2JjNTY3MDQ4N2ZmZjAxZTIyODI1M2ZlNDgifQ.gsFRPko_dC9aZF7th5C3eR25rQVFFID0Ne6IVdnaEMw");
                HttpConfig.HttpConfigBuilder httpConfig = new HttpConfig.HttpConfigBuilder()
                        .setmBaseUrl("http://test-px.huatu.com")
                        .setmMapHeader(map)
                        .setmIsUseLog(true);
                HttpConfig.newInstanceBuild(httpConfig);
                ToActivityUtil.newInsance().toNextActivity(TestActivity.this, MainActivity.class);
            }
        });
    }
}
