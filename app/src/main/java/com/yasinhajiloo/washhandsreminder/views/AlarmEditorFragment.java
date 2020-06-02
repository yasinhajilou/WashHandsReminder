package com.yasinhajiloo.washhandsreminder.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yasinhajiloo.washhandsreminder.R;
import com.yasinhajiloo.washhandsreminder.SharedViewModel;
import com.yasinhajiloo.washhandsreminder.databinding.FragmentAlarmEditorDialogBinding;
import com.yasinhajiloo.washhandsreminder.utils.MySharedPreferenceConstants;
import com.yasinhajiloo.washhandsreminder.utils.TimeConstants;
import com.yasinhajiloo.washhandsreminder.utils.TimeDefinerString;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlarmEditorFragment extends BottomSheetDialogFragment {
    private FragmentAlarmEditorDialogBinding mBinding;
    private SharedViewModel mSharedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAlarmEditorDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setUpClickListeners();
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(MySharedPreferenceConstants.sharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mSharedViewModel.getAlarmStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mBinding.tvStatus.setText("روشن");
                    mBinding.tvStatus.setTextColor(getResources().getColor(R.color.colorAlarmOn));
                } else {
                    mBinding.tvStatus.setText("خاموش");
                    mBinding.tvStatus.setTextColor(getResources().getColor(R.color.colorAlarmOff));
                }
            }
        });

        mSharedViewModel.getDataTime().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                resetAllBg();
                mBinding.tvTime.setText(TimeDefinerString.getTimeDefiner(aLong));
                if (aLong == TimeConstants.HALF_HOUR)
                    mBinding.llOne.setBackgroundResource(R.drawable.time_bg_shape);
                if (aLong == TimeConstants.ONE_HOUR)
                    mBinding.llTwo.setBackgroundResource(R.drawable.time_bg_shape);
                if (aLong == TimeConstants.ONE_AND_HALF_HOUR)
                    mBinding.llThree.setBackgroundResource(R.drawable.time_bg_shape);
                if (aLong == TimeConstants.TWO_HOUR)
                    mBinding.llFour.setBackgroundResource(R.drawable.time_bg_shape);
                if (aLong == TimeConstants.THREE_HOUR)
                    mBinding.llFive.setBackgroundResource(R.drawable.time_bg_shape);

            }
        });
    }

    private void setUpClickListeners() {
        mBinding.llOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedViewModel.setDataTime(TimeConstants.HALF_HOUR);

            }
        });

        mBinding.llTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedViewModel.setDataTime(TimeConstants.ONE_HOUR);
            }
        });


        mBinding.llThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedViewModel.setDataTime(TimeConstants.ONE_AND_HALF_HOUR);
            }
        });


        mBinding.llFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedViewModel.setDataTime(TimeConstants.TWO_HOUR);

            }
        });


        mBinding.llFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedViewModel.setDataTime(TimeConstants.THREE_HOUR);
            }
        });
    }

    private void resetAllBg() {
        mBinding.llOne.setBackgroundResource(0);
        mBinding.llTwo.setBackgroundResource(0);
        mBinding.llThree.setBackgroundResource(0);
        mBinding.llFour.setBackgroundResource(0);
        mBinding.llFive.setBackgroundResource(0);

    }

}