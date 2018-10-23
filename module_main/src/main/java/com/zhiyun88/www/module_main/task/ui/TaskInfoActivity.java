package com.zhiyun88.www.module_main.task.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.task.adapter.TaskInfoListAdapter;
import com.zhiyun88.www.module_main.task.bean.TaskData;
import com.zhiyun88.www.module_main.task.bean.TaskInfoListBean;
import com.zhiyun88.www.module_main.task.mvp.contranct.TaskInfoContranct;
import com.zhiyun88.www.module_main.task.mvp.presenter.TaskInfoPresenter;

import java.util.ArrayList;
import java.util.List;

public class TaskInfoActivity extends MvpActivity<TaskInfoPresenter> implements TaskInfoContranct.TaskInfoView {
    private ListView mListView;
    private MultipleStatusView multiplestatusview;
    private List<TaskData> taskDataLists;
    private TaskInfoListAdapter mAdapter;
    private TextView end_time_tv,task_pross_tv,task_status_tv;
    private TopBarView task_tb;
    private String taskId;
    @Override
    protected TaskInfoPresenter onCreatePresenter() {
        return new TaskInfoPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_task_taskinfolist);
        multiplestatusview=getViewById(R.id.multiplestatusview);
        taskId = getIntent().getStringExtra("taskId");
        if (taskId == null || "".equals(taskId)) {
            multiplestatusview.showError();
            return;
        }
        multiplestatusview.showContent();
        multiplestatusview.showLoading();
        mListView=getViewById(R.id.task_infolist_lv);
        end_time_tv=getViewById(R.id.end_time_tv);
        task_pross_tv=getViewById(R.id.task_pross_tv);
        task_tb=getViewById(R.id.task_tb);
        task_status_tv=getViewById(R.id.task_status_tv);
        taskDataLists=new ArrayList<>();
        mAdapter=new TaskInfoListAdapter(taskDataLists,this);
        mListView.setAdapter(mAdapter);
        mPresenter.getTaskInfoList(taskId);
    }

    @Override
    protected void setListener() {
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getTaskInfoList(taskId);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskData taskData= (TaskData) parent.getItemAtPosition(position);
                Intent intent=new Intent(TaskInfoActivity.this, CourseInfoActivity.class);
                if(taskData.getType().equals("1")||taskData.getType().equals("4")){
                    intent.putExtra("courseId",taskData.getId());
                    intent.putExtra("isCourseTaskInfo",false);
                    startActivity(intent);
                }else if(taskData.getType().equals("2")){
                    showErrorMsg("期待中...");
                }else if(taskData.getType().equals("3")){
                    showErrorMsg("期待中...");
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        task_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(TopBarView.ACTION_LEFT_BUTTON==action){
                    finish();
                }
            }
        });
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {

    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {
        TaskInfoListBean taskInfoListBean= (TaskInfoListBean) o;
        if(taskInfoListBean.getTask_info()==null){

        }else {
            end_time_tv.setText(taskInfoListBean.getTask_info().getEnd_date()+"结束");
            int complete = (int) taskInfoListBean.getTask_info().getComplete();
            task_pross_tv.setText("完成"+complete+"%");
            if(taskInfoListBean.getTask_info().getTask_states().equals("1")){
                task_status_tv.setText("未开始");
            }else if(taskInfoListBean.getTask_info().getTask_states().equals("2")){
                task_status_tv.setText("已结束");
            }else{
                task_status_tv.setText("进行中");
            }
            task_tb.getCenterTextView().setText(taskInfoListBean.getTask_info().getName());
        }
        taskDataLists.addAll(taskInfoListBean.getTask_data());
        mAdapter.notifyDataSetChanged();
        multiplestatusview.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}