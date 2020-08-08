package com.mkleo.project.models.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {

    private MutableLiveData<Integer> mTimer = new MutableLiveData<>();

    public MutableLiveData<Integer> getTimer() {
        return mTimer;
    }
}
