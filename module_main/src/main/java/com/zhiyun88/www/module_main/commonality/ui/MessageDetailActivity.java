package com.zhiyun88.www.module_main.commonality.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.adapter.MessageAdapter;
import com.zhiyun88.www.module_main.commonality.adapter.MessageDetailAdapter;
import com.zhiyun88.www.module_main.commonality.bean.MessageBean;
import com.zhiyun88.www.module_main.commonality.bean.MessageDetailsBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageDetailActivity extends BaseActivity{

    private TopBarView topBarView;
    private MultipleStatusView multipleStatusView;
    private ImageView imageView;
    private TextView time;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_message_details);
        initView(savedInstanceState);
        setListener();
        processLogic(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        multipleStatusView = getViewById(R.id.multiplestatusview);
        topBarView = getViewById(R.id.topbarview);
        imageView = getViewById(R.id.details_image);
        time = getViewById(R.id.details_time);
        content = getViewById(R.id.details_context);
    }
    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        MessageDetailsBean messageDetailsBean = getIntent().getParcelableExtra("MessageBean");
        if (messageDetailsBean == null) {
            multipleStatusView.showError();
            return;
        }
        if (messageDetailsBean.getMessage_type() == 1) {
            topBarView.getCenterTextView().setText("系统详情");
            imageView.setImageResource(R.drawable.user_notify_main_system);
        }else if (messageDetailsBean.getMessage_type() == 2) {
            topBarView.getCenterTextView().setText("课程详情");
            imageView.setImageResource(R.drawable.user_notify_main_course);
        }else {
            topBarView.getCenterTextView().setText("任务详情");
            imageView.setImageResource(R.drawable.user_notify_main_task);
        }
        time.setText(getDateToString(messageDetailsBean.getApp_time_created_at(),"MM月dd日 HH:mm"));
        content.setText(Html.fromHtml(messageDetailsBean.getMessage_desc()));
        content.setLinkTextColor(Color.parseColor("#3a89f5"));
        content.setMovementMethod(LinkMovementMethod.getInstance());
        multipleStatusView.showContent();
    }
    public static String getDateToString(String milSecond, String pattern) {
        Date date = new Date(Long.parseLong(milSecond+"000"));
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
