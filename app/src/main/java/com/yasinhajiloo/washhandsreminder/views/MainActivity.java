package com.yasinhajiloo.washhandsreminder.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.yasinhajiloo.washhandsreminder.AlarmReceiver;
import com.yasinhajiloo.washhandsreminder.SharedViewModel;
import com.yasinhajiloo.washhandsreminder.databinding.ActivityMainBinding;
import com.yasinhajiloo.washhandsreminder.constants.AlarmMode;
import com.yasinhajiloo.washhandsreminder.utils.MyAlarmManager;
import com.yasinhajiloo.washhandsreminder.constants.MySharedPreferenceConstants;
import com.yasinhajiloo.washhandsreminder.constants.TimeConstants;
import com.yasinhajiloo.washhandsreminder.utils.TimeDefinerString;

public class MainActivity extends AppCompatActivity {

    private AlarmMode mAlarmMode;
    private ActivityMainBinding mBinding;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private float ANIM_ON_START = 0f;
    private float ANIM_ON_END = 0.43f;
    private float ANIM_OFF_START = 0.5f;
    private float ANIM_OFF_END = 1f;

    private long savedTime;
    private int PENDING_ID = 9876;

    private SharedViewModel mSharedViewModel;
    private AlarmManager alarmManager;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        mSharedPreferences = getSharedPreferences(MySharedPreferenceConstants.sharedPreferenceName, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        alarmManager = MyAlarmManager.getAlarmManager(this);

        mSharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        savedTime = mSharedPreferences.getLong(MySharedPreferenceConstants.KEY_LONG_TIME, 0);
        mSharedViewModel.setDataTime(savedTime);

        //checking for existing alarm
        if (MyAlarmManager.getPendingIntent(this, PENDING_ID, PendingIntent.FLAG_NO_CREATE) != null) {
            mAlarmMode = AlarmMode.ON;
            mSharedViewModel.setAlarmStatus(true);
            animateSwitchToggle(ANIM_ON_START, ANIM_ON_END);
        } else {
            mAlarmMode = AlarmMode.OFF;
            mSharedViewModel.setAlarmStatus(false);
            animateSwitchToggle(ANIM_OFF_START, ANIM_OFF_END);
        }


        mSharedViewModel.getDataTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mBinding.tvMainStatus.setText(TimeDefinerString.getTimeDefiner(aLong));
                if (aLong > 0) {
                    if (status) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + aLong, aLong, MyAlarmManager.getPendingIntent(getApplicationContext(), PENDING_ID , PendingIntent.FLAG_UPDATE_CURRENT));
                        else
                            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + aLong, aLong, MyAlarmManager.getPendingIntent(getApplicationContext(), PENDING_ID , PendingIntent.FLAG_UPDATE_CURRENT));
                    }
                }
                //save last selected time
                mEditor.putLong(MySharedPreferenceConstants.KEY_LONG_TIME, aLong);
                mEditor.apply();
                savedTime = aLong;
            }
        });

        mSharedViewModel.getAlarmStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                status = aBoolean;
            }
        });

        mBinding.animSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAlarmSwitch();
            }
        });
        mBinding.ivAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmEditorFragment alarmEditorFragment = new AlarmEditorFragment();
                alarmEditorFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });

        mBinding.ivGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/yasinhajiloo/WashHandsReminder");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mBinding.ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(Uri.parse("bazaar://details?id=" + getApplicationContext().getPackageName()));
                intent.setPackage("com.farsitel.bazaar");
                startActivity(intent);
            }
        });
    }

    private void handleAlarmSwitch() {
        switch (mAlarmMode) {
            // current state it's on so we should turn off the switch
            case ON:
                Toast.makeText(this, "Turning off", Toast.LENGTH_SHORT).show();
                animateSwitchToggle(ANIM_OFF_START, ANIM_OFF_END);
                mAlarmMode = AlarmMode.OFF;
                mSharedViewModel.setDataTime(0);
                mSharedViewModel.setAlarmStatus(false);
                if (alarmManager != null)
                    alarmManager.cancel(MyAlarmManager.getPendingIntent(getApplicationContext(), PENDING_ID , PendingIntent.FLAG_UPDATE_CURRENT));
                break;

            // current state it's off so we should turn on the switch
            case OFF:
                animateSwitchToggle(ANIM_ON_START, ANIM_ON_END);
                mAlarmMode = AlarmMode.ON;
                if (savedTime > 0) {
                    mSharedViewModel.setAlarmStatus(true);
                    mSharedViewModel.setDataTime(savedTime);
                } else {
                    mSharedViewModel.setAlarmStatus(true);
                    mSharedViewModel.setDataTime(TimeConstants.ONE_HOUR);
                }
                break;
        }
    }

    private void animateSwitchToggle(float start, float end) {
        mBinding.animSwitch.setMinAndMaxProgress(start, end);
        mBinding.animSwitch.playAnimation();
    }

}