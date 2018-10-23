package com.zhiyun88.www.module_main.main.ui;

import android.os.Bundle;

import com.baijiahulian.BJVideoPlayerSDK;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.base.fragment.BaseFragment;
import com.wb.baselib.bean.Result;
import com.wb.baselib.bean.UserLoginBean;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.user.AppLoginUserInfoUtils;
import com.wb.baselib.view.BottomBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.BannerBean;
import com.zhiyun88.www.module_main.main.fragment.CourseFragment;
import com.zhiyun88.www.module_main.main.fragment.HomeFragment;
import com.zhiyun88.www.module_main.main.fragment.MineFragment;
import com.zhiyun88.www.module_main.main.fragment.TaskFragment;
import com.zhiyun88.www.module_main.main.fragment.TrainFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        UserLoginBean userLoginBean=new UserLoginBean();
        //配置下载用户身份
        userLoginBean.setUid(AppConfigManager.newInstance().getAppConfig().getFlgs());
        AppLoginUserInfoUtils.getInstance().saveLoginInfo(userLoginBean);
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        List<BaseFragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new TaskFragment());
        list.add(new CourseFragment());
        list.add(new TrainFragment());
        list.add(new MineFragment());
        BottomBarView bottomBarView = getViewById(R.id.main_main_btv);
        bottomBarView.setBottomNoIcon(R.drawable.main_home_no, R.drawable.main_task_no, R.drawable.main_course_no, R.drawable.main_train_no, R.drawable.main_user_no)//未选择的图标 必传
                .setBottomSelectIcon(R.drawable.main_home_sel, R.drawable.main_task_sel, R.drawable.main_course_sel, R.drawable.main_train_sel, R.drawable.main_user_sel)//选择的图标 必传
                .setBottomTitles(getString(R.string.main_home), getString(R.string.main_task), getString(R.string.main_course), getString(R.string.main_train), getString(R.string.main_mine)) //显示文字 必传
                .setFragments(list) //显示的gragment 必传
                .setBottomTextNoColor(getResources().getColor(R.color.main_text_grey_7c))//未选中的字体颜色 必传
                .bindFrament(getSupportFragmentManager());
        bottomBarView.setBottomTextSelectColor(getResources().getColor(R.color.main_text_blue_458));//选中的字体颜色 必传

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
