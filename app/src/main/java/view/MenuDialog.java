package view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.highspace.notebook.R;

/**
 * Created by Administrator on 2016/12/11.
 */

public class MenuDialog extends Dialog {
    private static OnCollectListener mOnCollectListener;
    private static OnSettingListener mOnSettingListener;

    public void setmOnCollectListener(OnCollectListener mOnCollectListener) {
        this.mOnCollectListener = mOnCollectListener;
    }

    public void setmOnSettingListener(OnSettingListener mOnSettingListener) {
        this.mOnSettingListener = mOnSettingListener;
    }

    public MenuDialog(Context context) {
        super(context);
    }

    public MenuDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MenuDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static MenuDialog create(Context context) {
        MenuDialog menuDialog = new MenuDialog(context, R.style.DilogStyle);
        menuDialog.setContentView(R.layout.dialog_menu_view);
        TextView setting = (TextView) menuDialog.findViewById(R.id.menu_dialog_setting_tv);
        TextView collect = (TextView) menuDialog.findViewById(R.id.menu_dialog_collect_tv);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSettingListener != null) {
                    mOnSettingListener.onSetting();
                }
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCollectListener != null) {
                    mOnCollectListener.onCollect();
                }
            }
        });

        return menuDialog;
    }

    public interface OnSettingListener {
        public void onSetting();

    }

    public interface OnCollectListener {
        public void onCollect();
    }

}
