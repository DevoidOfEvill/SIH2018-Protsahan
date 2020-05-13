package com.mota.tribal.protsahan.Schemes.View;

import android.app.Application;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class ViewClickHelperApp extends Application {

    private final List<WeakReference<ReadyListener>> mListeners = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void addListener(@NonNull ReadyListener listener) {
        mListeners.add(new WeakReference<>(listener));
        listener.onReady();
    }

    public void removeListener(ReadyListener listener) {
        for (WeakReference<ReadyListener> wRef : mListeners) {
            if (wRef.get() == listener) {
                mListeners.remove(wRef);
                break;
            }
        }
    }

    public interface ReadyListener {
        void onReady();
    }

}
