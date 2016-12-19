package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.highspace.notebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */

public class TextSizeDialog extends Dialog implements View.OnTouchListener {

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

    private static Context mContext;


    public TextSizeDialog(Context context) {
        super(context);
    }

    public TextSizeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    protected TextSizeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_text_size_view);
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
    }

    public static TextSizeDialog create(Context context) {
        TextSizeDialog textSizeDialog = new TextSizeDialog(context, R.style.DilogStyle);
        mContext = context;
        return textSizeDialog;
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

                    dismiss();
                    break;
                case R.id.charcter_standard_ll:
                    recoveryBg(radioButtons);
                    mStandardRdb.setChecked(true);

                    dismiss();
                    break;
                case R.id.charcter_big_ll:
                    recoveryBg(radioButtons);
                    mBigRdb.setChecked(true);

                    dismiss();
                    break;
                case R.id.charcter_super_big_ll:
                    recoveryBg(radioButtons);
                    mSuperBigRdb.setChecked(true);

                    dismiss();
                    break;
                case R.id.charcter_espcial_big_ll:
                    recoveryBg(radioButtons);
                    mEspacialBigRdb.setChecked(true);

                    dismiss();
                    break;

                case R.id.charcter_cancel_btn:
                    dismiss();
                    break;

            }

        }
        return true;
    }


}
