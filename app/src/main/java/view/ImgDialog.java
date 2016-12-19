package view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.highspace.notebook.R;

/**
 * Created by Administrator on 2016/12/9.
 */

public class ImgDialog extends Dialog {

    private static OnTakePhotoListener onTakePhotoListener;
    private static OnPhotoListener onPhotoListener;


    public void setOnTakePhotoListener(OnTakePhotoListener onTakePhotoListener) {
        this.onTakePhotoListener = onTakePhotoListener;
    }


    public void setOnPhotoListener(OnPhotoListener onPhotoListener) {
        this.onPhotoListener = onPhotoListener;
    }

    public interface OnTakePhotoListener {
        public void onTakePhoto();


    }

    public interface OnPhotoListener {
        public void onPhoto();

    }

    protected ImgDialog(@NonNull Context context) {
        super(context);
    }

    protected ImgDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ImgDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static ImgDialog createDialog(Context context) {

        final ImgDialog imgDialog = new ImgDialog(context, R.style.DilogStyle);
       // View dilogView = LayoutInflater.from(context).inflate(R.layout.dialog_img_view, null);
        //imgDialog.addContentView(dilogView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));//不能弹出对话框，点进去看注解
        //imgDialog.setContentView(dilogView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));//imgDialog.setContentView(dilogView)与这个效果一样
        imgDialog.setContentView(R.layout.dialog_img_view);  //可以弹出,布局正确
        // imgDialog.setContentView(dilogView);//可以弹出，布局与R.layout.dialog_view不符合
        //     <item name="android:background">#ffffff</item>  在style文件中加入这个会导致dialog布局不是圆角
        imgDialog.show();

        TextView takePhoto = (TextView) imgDialog.findViewById(R.id.select_dialog_take_photo_tv);
        TextView photo = (TextView) imgDialog.findViewById(R.id.select_dialog_photo_tv);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTakePhotoListener != null) {
                    onTakePhotoListener.onTakePhoto();
                    imgDialog.dismiss();
                }
            }
        });



        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPhotoListener != null) {
                    onPhotoListener.onPhoto();
                    imgDialog.dismiss();
                }
            }
        });


        return imgDialog;


    }


}
