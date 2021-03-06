package com.zhiyun88.www.module_main.course.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jungan.www.common_coorinator.CoordinatorTabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.adapter.CoordinatorPagerAdapter;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;
import com.zhiyun88.www.module_main.course.fragment.CommentListFrament;
import com.zhiyun88.www.module_main.course.fragment.CourseOutFragment;
import com.zhiyun88.www.module_main.course.fragment.WebViewFragment;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseInfoContranct;
import com.zhiyun88.www.module_main.course.mvp.presenter.CourseInfoPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程详情
 */
public class CourseInfoActivity extends MvpActivity<CourseInfoPresenter> implements CourseInfoContranct.CourseInfoView{

    private TopBarView course_tb;
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private ArrayList<Fragment> mFragments;
    private List<String> mTitles ;
    private ViewPager mViewPager;
    private MultipleStatusView multipleStatusView;
    private boolean isCourseTaskInfo;
    private TextView isbuy_tv;
    private  String courseId;
    private boolean isHaveBuy=false;
    private CourseOutFragment courseOutFragment;
    private CourseInfoBean courseInfoBean;
    @Override
    protected CourseInfoPresenter onCreatePresenter() {
        return new CourseInfoPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_course_courseinfo);
         courseId =getIntent().getStringExtra("courseId");
        isCourseTaskInfo=getIntent().getBooleanExtra("isCourseTaskInfo",false);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        if (courseId == null || "".equals(courseId)) {
            multipleStatusView.showError();
            return;
        }
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        course_tb=getViewById(R.id.course_tb);
        isbuy_tv=getViewById(R.id.isbuy_tv);
        mTitles=new ArrayList<>();
        mCoordinatorTabLayout = getViewById(R.id.coordinatortablayout);
        mFragments = new ArrayList<>();
        mViewPager = getViewById(R.id.vp);
        mPresenter.getCourseInfoData(courseId);
        if(isCourseTaskInfo){
            course_tb.getCenterTextView().setText("培训详情");
        }else {
            course_tb.getCenterTextView().setText("课程详情");
        }
    }
    private void initFragments(CourseInfoBean courseInfoBean) {
        mFragments.add(WebViewFragment.newInstcace(courseInfoBean.getInfo().getDetails()));
        courseOutFragment=CourseOutFragment.newInstcace(courseInfoBean.getChapter(),isCourseTaskInfo,courseInfoBean.getInfo().getTitle(),isHaveBuy);
        mFragments.add(courseOutFragment);
        mFragments.add(CommentListFrament.newInstance(courseInfoBean.getInfo().getId()));

    }
    private void initViewPager() {
            if(isCourseTaskInfo){
                mTitles.add("培训详情");
                mTitles.add("培训大纲");
                mTitles.add("培训评价");
             //   isbuy_tv.setText("立即报名");
            }else {
                mTitles.add("课程详情");
                mTitles.add("课程大纲");
                mTitles.add("课程评价");
             //   isbuy_tv.setText("加入学习");
            }
             mViewPager.setAdapter(new CoordinatorPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
            mViewPager.setOffscreenPageLimit(3);
    }
    @Override
    protected void setListener() {
        isbuy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.buyCourse(courseId);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCourseInfoData(courseId);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        course_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(TopBarView.ACTION_LEFT_BUTTON==action){
                    finish();
                }
            }
        });
    }
    @Override
    public void initCoorLayout(CourseInfoBean courseInfoBean) {
        LogTools.e(courseInfoBean.toString()+"------>>>");
        this.courseInfoBean=courseInfoBean;
        if(isCourseTaskInfo){
            courseOrTrain(courseInfoBean,"立即报名","已报名","已截止","已结束");
            mCoordinatorTabLayout
                    .setBackEnable(true)
                    .setClassFly(true)
                    .setCourceTitle(courseInfoBean.getInfo().getTitle())
                    .setStartCourseTime(courseInfoBean.getInfo().getStart_end_date())
                    .setStartCourseAddress(courseInfoBean.getInfo().getAddress())
                    .settalkTeacher(courseInfoBean.getInfo().getTeacher_name())
                    .setPxBm(courseInfoBean.getInfo().getDepartment())
                    .sethaveBm(courseInfoBean.getInfo().getApply_num())
                    .setsyl("（剩余"+courseInfoBean.getInfo().getSurplus_num()+"名额）")
                    .setupWithViewPager(mViewPager);
        }else {
            if(courseInfoBean.getInfo().getIs_buy().equals("0")){
                //未添加
                isbuy_state("加入学习",true,false,false);
            }else if(courseInfoBean.getInfo().getIs_buy().equals("1")){
                //已加入
                isbuy_state("已加入",false,false,true);
//                isbuy_tv.setVisibility(View.GONE);
            }
            mCoordinatorTabLayout
                    .setBackEnable(true)
                    .setClassFly(false)
                    .setTwoCourseName(courseInfoBean.getInfo().getTitle())
                    .setTwoDes(courseInfoBean.getInfo().getSubtitle())
                    .setTwollNum(courseInfoBean.getInfo().getApply_num()+"人学过 | "+courseInfoBean.getInfo().getPage_view()+"人浏览")
                    .setTwoTeacherName("主讲人："+courseInfoBean.getInfo().getTeacher_name())
                    .setupWithViewPager(mViewPager);
        }

        initFragments(courseInfoBean);
        initViewPager();

        GlideManager.getInstance().setCommonPhoto(mCoordinatorTabLayout.getImageView(),R.drawable.course_image,CourseInfoActivity.this,courseInfoBean.getInfo().getCover(),false);
    }

    private void courseOrTrain(CourseInfoBean courseInfoBean, String apply, String applied, String abort,String finished) {
        if(courseInfoBean.getInfo().getIs_buy().equals("0")){
            //未添加
            isbuy_state(apply,true,false,false);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("1")){
            //已加入
            isbuy_state(applied,false,true,true);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("2")){
            //已截止
            isbuy_state(abort,false,true,false);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("3")){
            //已结束
            isbuy_state(finished,false,true,false);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("4")){
            //已截止并报名
            isbuy_state(abort,false,true,true);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("5")){
            //已结束并报名
            isbuy_state(finished,false,true,true);
        }
    }

    private void isbuy_state(String apply,boolean enabled,boolean selected,boolean haveBuy) {
        isbuy_tv.setText(apply);
        isbuy_tv.setEnabled(enabled);
        isbuy_tv.setSelected(selected);
        isHaveBuy=haveBuy;
    }

    @Override
    public void joinSuccess(String msg) {
        courseInfoBean.getInfo().setIs_buy("1");
        if(isCourseTaskInfo){
            courseOrTrain(courseInfoBean,"立即报名","已报名","已截止","已结束");
        }else {
            isbuy_state("已报名",false,true,true);
        }
        showErrorMsg(msg);
        courseOutFragment.setisBuy(true);
    }

    @Override
    public void ShowLoadView() {
            multipleStatusView.showLoading();
    }

    @Override
    public void NoNetWork() {
        multipleStatusView.showNoNetwork();
    }

    @Override
    public void NoData() {
        multipleStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusView.showError();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void showErrorMsg(String msg) {
        showLongToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        multipleStatusView.showContent();
        initCoorLayout((CourseInfoBean) o);
    }
}
