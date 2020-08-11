package com.mkleo.project.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

public class LiveDataHolder {

    private final Map<String, MutableLiveData<?>> mHolder;

    public LiveDataHolder() {
        mHolder = new HashMap<>();
    }

    /**
     * 持有livedata
     *
     * @param key
     * @return
     */
    public  <T> void holdLiveData(@NonNull String key) {
        synchronized (mHolder) {
            MutableLiveData<T> liveData = new MutableLiveData<>();
            mHolder.put(key, liveData);
        }
    }

    /**
     * 获取livedata
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> MutableLiveData<T> getLiveData(@NonNull String key) {
        synchronized (mHolder) {
            return (MutableLiveData<T>) mHolder.get(key);
        }
    }

    /**
     * 释放资源
     *
     * @param owner
     */
    public void release(LifecycleOwner owner) {
        //解绑livedata
        synchronized (mHolder) {
            //解绑
            for (Map.Entry<String, MutableLiveData<?>> entry : mHolder.entrySet()) {
                entry.getValue().removeObservers(owner);
            }
            //清除livedata
            mHolder.clear();
        }
    }
}
