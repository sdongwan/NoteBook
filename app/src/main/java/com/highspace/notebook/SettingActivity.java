package com.highspace.notebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import util.SpUtil;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public static final int CHARCTER_REQUEST_CODE = 0;
    public static final String CHARACTER_SIZE_FLAG = "character_size";
    public static final String CHARACTER_NAME_FLAG = "character_name";
    public static final String LAYOUT_SP_KEY = "layout_key";
    private RadioButton mListRb;
    private RadioButton mGridRb;
    private LinearLayout mCharcterLl;
    public static int pCharcterSize = 18;
    public static boolean pLayout = true;//true list,false grid
    private List<String> mNamesList;
    private TextView mCharacterNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initEvent();


    }

    private void initView() {
        mListRb = (RadioButton) findViewById(R.id.setting_list_rb);
        mGridRb = (RadioButton) findViewById(R.id.setting_grid_rb);
        mCharacterNameTv = (TextView) findViewById(R.id.setting_character_name_tv);
        mCharcterLl = (LinearLayout) findViewById(R.id.setting_charcter_ll);
        mNamesList = new ArrayList<>();
        mNamesList.add("小");
        mNamesList.add("标准");
        mNamesList.add("大");
        mNamesList.add("超大");
        mNamesList.add("特大");


        int position = SpUtil.getInt(SettingActivity.this, "name", 1);
        String name = mNamesList.get(position);
        mCharacterNameTv.setText(name);

        if (pLayout) {
            mListRb.setChecked(true);
        } else {
            mGridRb.setChecked(true);
        }


    }

    private void initEvent() {
        mGridRb.setOnCheckedChangeListener(this);
        mListRb.setOnCheckedChangeListener(this);

        mCharcterLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SettingActivity.this, SettingSizeDialogActivity.class), CHARCTER_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(SettingActivity.this, "resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        Toast.makeText(SettingActivity.this, "restart", Toast.LENGTH_SHORT).show();
        super.onRestart();
    }


    @Override
    protected void onPause() {
        Toast.makeText(SettingActivity.this, "paused", Toast.LENGTH_SHORT).show();
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHARCTER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            pCharcterSize = data.getIntExtra(CHARACTER_SIZE_FLAG, 18);
            int position = data.getIntExtra(CHARACTER_NAME_FLAG, 1);
            String name = mNamesList.get(position);
            mCharacterNameTv.setText(name);
            SpUtil.putInt(SettingActivity.this, "name", position);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.setting_list_rb:
                if (isChecked) {
                    pLayout = true;
                }
                break;
            case R.id.setting_grid_rb:
                if (isChecked) {
                    pLayout = false;
                }
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
