package com.didi.business.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * Listen network state change
 */
public class BusinessNetworkStateReceiver extends BroadcastReceiver {

    public static interface NetworkObserver {
        void onNetworkStateChange(boolean connected);
    }

    public static class NetworkObservable {
        public static void register(NetworkObserver observer) {
            sObservers.add(observer);
        }

        public static void unregister(NetworkObserver observer) {
            sObservers.remove(observer);
        }

        public static void notifyNetworkChange(boolean connected) {
            for (NetworkObserver observer : sObservers) {
                if (observer == null) {
                    continue;
                }
                observer.onNetworkStateChange(connected);
            }
        }
    }

    private static List<NetworkObserver> sObservers = new LinkedList<NetworkObserver>();

    private boolean mLastState;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            return;
        }

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (!mLastState) {
                // network on
                NetworkObservable.notifyNetworkChange(true);
            }
            mLastState = true;
        } else {
            if (mLastState) {
                //network off
                NetworkObservable.notifyNetworkChange(false);
            }
            mLastState = false;
        }
    }
}
