package com.highspace.notebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import util.SpUtil;

public class SettingSizeDialogActivity extends Activity implements View.OnTouchListener {
    private Button mCancelBtn;
    private LinearLayout mSmallLl;
    private LinearLayout mStandardLl;
    private LinearLayout mBigLl;
    private LinearLayout mSuperBigLl;
    private LinearLayout mEsBigLl;

    private RadioButton mSmallRdb;
    private RadioButton mStandardRdb;
    private RadioButton mBigRdb;
    private RadioButton mSuperBigRdb;
    private RadioButton mEspacialBigRdb;

    private List<RadioButton> radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcter_dialog);
        initView();
        initEvent();
    }

    private void initEvent() {
        mSmallLl.setOnTouchListener(this);
        mStandardLl.setOnTouchListener(this);
        mBigLl.setOnTouchListener(this);
        mSuperBigLl.setOnTouchListener(this);
        mEsBigLl.setOnTouchListener(this);
        mCancelBtn.setOnTouchListener(this);

    }

    private void initView() {
        radioButtons = new ArrayList<>();
        mSmallRdb = (RadioButton) findViewById(R.id.charcter_small_rdb);
        mStandardRdb = (RadioButton) findViewById(R.id.charcter_standard_rdb);
        mBigRdb = (RadioButton) findViewById(R.id.charcter_big_rdb);
        mSuperBigRdb = (RadioButton) findViewById(R.id.charcter_super_big_rdb);
        mEspacialBigRdb = (RadioButton) findViewById(R.id.charcter_espacial_big_rdb);
        radioButtons.add(mSmallRdb);
        radioButtons.add(mStandardRdb);
        radioButtons.add(mBigRdb);
        radioButtons.add(mSuperBigRdb);
        radioButtons.add(mEspacialBigRdb);
        mSmallLl = (LinearLayout) findViewById(R.id.charcter_small_ll);
        mStandardLl = (LinearLayout) findViewById(R.id.charcter_standard_ll);
        mBigLl = (LinearLayout) findViewById(R.id.charcter_big_ll);
        mSuperBigLl = (LinearLayout) findViewById(R.id.charcter_super_big_ll);
        mEsBigLl = (LinearLayout) findViewById(R.id.charcter_espcial_big_ll);
        mCancelBtn = (Button) findViewById(R.id.charcter_cancel_btn);

        recoveryBg(radioButtons);
        radioButtons.get(SpUtil.getInt(this, "checked", 1)).setChecked(true);
    }

    public void recoveryBg(List<RadioButton> radioButtons) {
        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setChecked(false);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (v.getId()) {
                case R.id.charcter_small_ll:
                    recoveryBg(radioButtons);
                    mSmallRdb.setChecked(true);
                    SpUtil.putInt(SettingSizeDialogActivity.this, "checked", 0);
                    Intent intent1 = new Intent();
                    intent1.putExtra(SettingActivity.CHARACTER_SIZE_FLAG, 13);
                    intent1.putExtra(SettingActivity.CHARACTER_NAME_FLAG, 0);
                    setResult(Activity.RESULT_OK, intent1);
                    finish();
                    break;
                case R.id.charcter_standard_ll:
                    recoveryBg(radioButtons);
                    mStandardRdb.setChecked(true);
                    SpUtil.putInt(SettingSizeDialogActivity.this, "checked", 1);
                    Intent intent2 = new Intent();
                    intent2.putExtra(SettingActivity.CHARACTER_SIZE_FLAG, 18);
                    intent2.putExtra(SettingActivity.CHARACTER_NAME_FLAG, 1);
                    setResult(Activity.RESULT_OK, intent2);
                    finish();
                    break;
                case R.id.charcter_big_ll:
                    recoveryBg(radioButtons);
                    mBigRdb.setChecked(true);
                    SpUtil.putInt(SettingSizeDialogActivity.this, "checked", 2);
                    Intent intent3 = new Intent();
                    intent3.putExtra(SettingActivity.CHARACTER_SIZE_FLAG, 23);
                    intent3.putExtra(SettingActivity.CHARACTER_NAME_FLAG, 2);
                    setResult(Activity.RESULT_OK, intent3);
                    finish();

                    break;
                case R.id.charcter_super_big_ll:
                    recoveryBg(radioButtons);
                    SpUtil.putInt(SettingSizeDialogActivity.this, "checked", 3);
                    mSuperBigRdb.setChecked(true);
                    Intent intent4 = new Intent();
                    intent4.putExtra(SettingActivity.CHARACTER_SIZE_FLAG, 28);
                    intent4.putExtra(SettingActivity.CHARACTER_NAME_FLAG, 3);
                    setResult(Activity.RESULT_OK, intent4);
                    finish();
                    break;
                case R.id.charcter_espcial_big_ll:
                    recoveryBg(radioButtons);
                    SpUtil.putInt(SettingSizeDialogActivity.this, "checked", 4);
                    mEspacialBigRdb.setChecked(true);
                    Intent intent5 = new Intent();
                    intent5.putExtra(SettingActivity.CHARACTER_SIZE_FLAG, 33);
                    intent5.putExtra(SettingActivity.CHARACTER_NAME_FLAG, 4);
                    setResult(Activity.RESULT_OK, intent5);
                    finish();
                    break;
                case R.id.charcter_cancel_btn:
                    finish();
                    break;

            }

        }
        return true;
    }

}
