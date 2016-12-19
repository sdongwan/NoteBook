package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.highspace.notebook.R;

/**
 * Created by Administrator on 2016/12/13.
 */

public class DeleteDialog extends Dialog {
    private static OnDeleteListener mOnDeleteListener;

    public void setmOnDeleteListener(OnDeleteListener mOnDeleteListener) {
        this.mOnDeleteListener = mOnDeleteListener;
    }

    public DeleteDialog(Context context) {
        super(context);
    }

    public DeleteDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DeleteDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_view);




        Button cancelBtn = (Button) findViewById(R.id.delete_dialog_cancel_btn);
        Button deleteBtn = (Button) findViewById(R.id.delete_dialog_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDeleteListener != null) {
                    mOnDeleteListener.onDelete();
                    dismiss();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do null
                dismiss();
            }
        });


    }

    public static DeleteDialog create(Context context) {

        DeleteDialog deleteDialog = new DeleteDialog(context, R.style.DilogStyle);



        return deleteDialog;

    }


    public interface OnDeleteListener {

        public void onDelete();
    }

}

/*

  Button cancelBtn = (Button) deleteDialog.findViewById(R.id.delete_dialog_cancel_btn);
        Button deleteBtn = (Button) deleteDialog.findViewById(R.id.delete_dialog_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDeleteListener != null) {
                    mOnDeleteListener.onDelete();
                    deleteDialog.dismiss();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do null
                deleteDialog.dismiss();
            }
        });




 */
