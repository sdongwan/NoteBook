package com.highspace.notebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import bean.NoteBean;
import db.NoteHelper;
import db.NoteOpenHelper;
import util.DateUtl;
import view.DeleteDialog;
import view.ImgDialog;

public class EditActivity extends AppCompatActivity {

    public static final String IS_NEW_CREATE = "isNewCreate";
    public static final String NOTE_BEAN = "notebean";
    public static final int EDIT_RESULT_CODE = 2;
    public static final int UPDATE_RESULT_CODE = 3;
    public static final int DELETE_RESULT_CODE = 4;
    public static int PHOTO_REQUEST_CODE = 5;
    public static int TAKE_PHOTO_REQUEST_CODE = 6;
    public static final String RESULT_INTENT = "result_intent";
    private boolean isNewCreate;

    private NoteBean mNoteBean;

    private ImageButton mErrorIB;
    private ImageButton mRightIB;
    private TextView mDateTv;
    private EditText mContentEt;
    private String noteDate;
    protected TextView mToDOTv;
    protected TextView mImgTv;
    protected TextView mShareTv;
    protected TextView mCollectTv;
    protected TextView mDeleteTv;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Intent intent = getIntent();
        isNewCreate = intent.getBooleanExtra(IS_NEW_CREATE, true);
        mNoteBean = new NoteBean();
        noteDate = DateUtl.getCurrentTime();
        if (isNewCreate) {
        } else {
            mNoteBean = intent.getParcelableExtra(NOTE_BEAN);
            // noteDate = NoteHelper.getInstance(this).queryNote()
        }
        initView();
        initEvent();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mContentEt.setTextSize(SettingActivity.pCharcterSize);

    }

    private void initView() {
        mToDOTv = (TextView) findViewById(R.id.edit_todo_tv);
        mImgTv = (TextView) findViewById(R.id.edit_img_tv);
        mErrorIB = (ImageButton) findViewById(R.id.edit_error_ib);
        mRightIB = (ImageButton) findViewById(R.id.edit_right_ib);
        mDateTv = (TextView) findViewById(R.id.edit_time_tv);
        mContentEt = (EditText) findViewById(R.id.edit_edit_et);
        mShareTv = (TextView) findViewById(R.id.edit_share_tv);
        mCollectTv = (TextView) findViewById(R.id.edit_collect_tv);
        mDeleteTv = (TextView) findViewById(R.id.edit_delete_tv);
        if (isNewCreate) {
            mDateTv.setText(noteDate);
        } else {
            mDateTv.setText(mNoteBean.getNoteDate());
            mContentEt.setText(mNoteBean.getNoteContent());
            mContentEt.setSelection(mNoteBean.getNoteContent().length());
            // TODO: 2016/12/8 getTimeFromDB
        }
    }

    private void initEvent() {
        mImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImgDialog imgDialog = ImgDialog.createDialog(EditActivity.this);
                imgDialog.setOnTakePhotoListener(new ImgDialog.OnTakePhotoListener() {
                    @Override
                    public void onTakePhoto() {
                        // Toast.makeText(EditActivity.this, "takephoto", Toast.LENGTH_SHORT);
                        Intent intent = new Intent();
                        intent.setAction("android.media.action.IMAGE_CAPTURE");
                        intent.addCategory("android.intent.category.DEFAULT");
                        File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myNoteImg/");
                        if (!fileDir.exists()) {
                            fileDir.mkdir();
                        }
                        File photo = new File(fileDir, +System.currentTimeMillis() + ".png");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));//将拍照后图片保存到指定目录
                        Toast.makeText(EditActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/myNoteImg/" + System.currentTimeMillis() + ".png", Toast.LENGTH_SHORT).show();
                        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
                    }
                });

                imgDialog.setOnPhotoListener(new ImgDialog.OnPhotoListener() {
                    @Override
                    public void onPhoto() {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PHOTO_REQUEST_CODE);

                    }
                });


            }
        });

        mContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNoteBean.setNoteContent(s.toString());
                mNoteBean.setNoteDate(noteDate);
                mNoteBean.setNoteImg("");

            }
        });


        mToDOTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/12/9
            }
        });

        mContentEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mErrorIB.setVisibility(View.VISIBLE);
                    mRightIB.setVisibility(View.VISIBLE);
                    mImgTv.setVisibility(View.VISIBLE);
                    mToDOTv.setVisibility(View.VISIBLE);

                    mShareTv.setVisibility(View.GONE);
                    mCollectTv.setVisibility(View.GONE);
                    mDeleteTv.setVisibility(View.GONE);
                } else {
                    mErrorIB.setVisibility(View.GONE);
                    mRightIB.setVisibility(View.GONE);
                    mImgTv.setVisibility(View.GONE);
                    mToDOTv.setVisibility(View.GONE);

                    mShareTv.setVisibility(View.VISIBLE);
                    mCollectTv.setVisibility(View.VISIBLE);
                    mDeleteTv.setVisibility(View.VISIBLE);

                }
            }
        });
        mErrorIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRightIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/12/8
                Intent result = new Intent();
                if (isNewCreate) {
                    if (mNoteBean.getNoteContent() != null && !mNoteBean.getNoteContent().equals("")) {
                        result.putExtra(RESULT_INTENT, mNoteBean);
                        setResult(EDIT_RESULT_CODE, result);
                    }

                } else {
                    if (mNoteBean.getNoteContent() != null && !mNoteBean.getNoteContent().equals("")) {
                        result.putExtra(RESULT_INTENT, mNoteBean);
                        setResult(UPDATE_RESULT_CODE, result);
                    }


                }
                onBackPressed();
            }
        });

        mShareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mNoteBean.getNoteContent());
                startActivity(Intent.createChooser(intent, "分享到......"));
            }
        });
        mDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteDialog deleteDialog = DeleteDialog.create(EditActivity.this);
                deleteDialog.setmOnDeleteListener(new DeleteDialog.OnDeleteListener() {
                    @Override
                    public void onDelete() {
                        Intent intent = new Intent();
                        intent.putExtra(RESULT_INTENT, mNoteBean);
                        setResult(DELETE_RESULT_CODE, intent);
                        onBackPressed();
                    }
                });

                deleteDialog.show();
/*
设置deletedialog的宽度
 */
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = deleteDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth() * 0.9); //设置宽度
                deleteDialog.getWindow().setAttributes(lp);

            }
        });
        mCollectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNoteBean.getNoteContent().equals("")) {
                    NoteHelper.getInstance(EditActivity.this).insertNote(mNoteBean, NoteOpenHelper.COLLECT_TAB);
                    Toast.makeText(EditActivity.this, "添加收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditActivity.this, "添加收藏失败啦", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if (c != null) {
                c.moveToFirst();
            }
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            if (mContentEt != null && mNoteBean != null) {
                insertImg(EditActivity.this, imagePath, mNoteBean.getNoteContent(), mContentEt);
            }

            Toast.makeText(EditActivity.this, imagePath, Toast.LENGTH_SHORT).show();
            // TODO: 2016/12/9  
            c.close();
        } else if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            Toast.makeText(EditActivity.this, "TAKEPHOTO  OK", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertImg(Context mContext, String imagePath, String content, EditText editText) {

        SpannableString spanString = new SpannableString(content + "\n" + " ");
        Drawable drawable = Drawable.createFromPath(imagePath);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(imageSpan, spanString.length() - 1, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        editText.setText(spanString);
        editText.setSelection(spanString.length());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.anim_top_to_bottom);
        finish();

    }


}
