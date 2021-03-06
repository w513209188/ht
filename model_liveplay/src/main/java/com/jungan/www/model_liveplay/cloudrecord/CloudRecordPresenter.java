package com.jungan.www.model_liveplay.cloudrecord;

import com.baijiahulian.livecore.utils.LPErrorPrintSubscriber;

import com.jungan.www.model_liveplay.activity.LiveRoomRouterListener;
import com.jungan.www.model_liveplay.utils.RxUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by wangkangfei on 17/5/8.
 */

public class CloudRecordPresenter implements CloudRecordContract.Presenter {
    private LiveRoomRouterListener liveRoomRouterListener;
    private Subscription subscriptionOfCloudRecord;

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        this.liveRoomRouterListener = liveRoomRouterListener;
    }

    @Override
    public void subscribe() {
        subscriptionOfCloudRecord = liveRoomRouterListener.getLiveRoom().getObservableOfCloudRecordStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LPErrorPrintSubscriber<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            if (liveRoomRouterListener.isTeacherOrAssistant()) {
                                liveRoomRouterListener.navigateToCloudRecord(true);
                            } else {
                                liveRoomRouterListener.navigateToCloudRecord(false);
                            }
                        } else {
                            liveRoomRouterListener.navigateToCloudRecord(false);
                        }
                    }
                });
    }

    @Override
    public void unSubscribe() {
        RxUtils.unSubscribe(subscriptionOfCloudRecord);
    }

    @Override
    public void destroy() {
        liveRoomRouterListener = null;
    }

    @Override
    public void cancelCloudRecord() {
        liveRoomRouterListener.getLiveRoom().requestCloudRecord(false);
    }
}
