package com.didi.business.polling;

import android.os.Handler;
import android.os.Message;

import com.third.lidroid.xutils.util.LogUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huhuajun on 2015/10/19.
 */
public class BusinessPollingImpl implements IBusinessPolling, Handler.Callback {

    //----------------关注的polling--------------//
    public static interface BusinessPollingObserver {
        void onPollingBeat();
    }

    public static class BusinessPollingObservable {
        public static void register(BusinessPollingObserver observer) {
            sObservers.add(observer);
        }

        public static void unregister(BusinessPollingObserver observer) {
            sObservers.remove(observer);
        }

        public static void notifyPollingBeat() {
            for (BusinessPollingObserver observer : sObservers) {
                if (observer == null) {
                    continue;
                }
                observer.onPollingBeat();
            }
        }
    }

    private static List<BusinessPollingObserver> sObservers = new LinkedList<BusinessPollingObserver>();

    //-------------业务控制------------------//

    /**
     * 轮训间隔
     */
    private int mPollingInterVal = 5 * 60 * 1000;
    private Handler mHandler;
    private Timer mTimer;
    private TimerTask mTimerTask;

    public BusinessPollingImpl() {
        mHandler = new Handler(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        LogUtils.d("--------->BusinessPollingImpl notifyPollingBeat");
        BusinessPollingObservable.notifyPollingBeat();
        return true;
    }

    @Override
    public void startPolling() {
        if (mTimer == null)
            mTimer = new Timer();
        if (mTimerTask != null)
            mTimerTask.cancel();
        mTimerTask = new BusinessPollingTask();
        mTimer.schedule(mTimerTask, mPollingInterVal, mPollingInterVal);
    }

    @Override
    public void stopPolling() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    public void setPollingInterVal(int pollingInterVal) {
        mPollingInterVal = pollingInterVal;
    }

    public int getPollingInterVal() {
        return mPollingInterVal;
    }

    class BusinessPollingTask extends TimerTask {

        @Override
        public void run() {
            Message message = Message.obtain();
            mHandler.sendMessage(message);
        }
    }


}
