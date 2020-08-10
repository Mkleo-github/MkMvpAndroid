package com.mkleo.project.models.livedata;

import androidx.lifecycle.MutableLiveData;

public class LiveDataFactory {

    public static <T> MutableLiveData<T> newLiveData() {
        return new MutableLiveData<>();
    }
}
