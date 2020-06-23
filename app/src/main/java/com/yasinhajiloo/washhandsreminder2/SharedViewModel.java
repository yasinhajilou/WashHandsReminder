package com.yasinhajiloo.washhandsreminder2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
