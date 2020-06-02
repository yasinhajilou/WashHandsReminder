package com.yasinhajiloo.washhandsreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Long> mDataTime;
    private MutableLiveData<Boolean> mAlarmStatus;
    public SharedViewModel() {
        mDataTime = new MutableLiveData<>();
        mAlarmStatus = new MutableLiveData<>();
    }


    public void setDataTime(long time){
        mDataTime.setValue(time);
    }

    public LiveData<Long> getDataTime() {
        return mDataTime;
    }


    public void setAlarmStatus(boolean status){
        mAlarmStatus.setValue(status);
    }

    public LiveData<Boolean> getAlarmStatus(){
        return mAlarmStatus;
    }

}
