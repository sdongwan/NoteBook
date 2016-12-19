package com.highspace.notebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.NoteAdapter;
import bean.NoteBean;
import db.NoteHelper;
import db.NoteOpenHelper;
import util.SpUtil;
import view.MenuDialog;

public class MainActivity extends Activity {

    public static final int EDIT_REQUEST_CODE = 0;
    private TextView mMenu;
    private TextView mNote;
    private TextView mEmptyView;
    private RecyclerView mNoteRcy;

    private List<NoteBean> noteBeanList;
    private NoteAdapter mNoteAdapter;

    private RecyclerView.LayoutManager mLayoutManager;


    private int mClickPosition = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();
        initEvent();

    }


    private void initView() {
        mEmptyView = (TextView) findViewById(R.id.empty_view);
        mMenu = (TextView) findViewById(R.id.home_bottom_menu_tv);
        mNote = (TextView) findViewById(R.id.home_bottom_note_tv);
        mNoteRcy = (RecyclerView) findViewById(R.id.home_note_rcy);
        noteBeanList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SettingActivity.pLayout) {
            mLayoutManager = new LinearLayoutManager(this);
        } else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        if (mNoteRcy != null)
            mNoteRcy.setLayoutManager(mLayoutManager);
    }

    private void initEvent() {


        noteBeanList = covertNotes(this);
        if (noteBeanList.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        mNoteAdapter = new NoteAdapter(this, noteBeanList);
        mNoteRcy.setAdapter(mNoteAdapter);
        if (SpUtil.getInt(this, "layout", 0) == 0) {
            SettingActivity.pLayout = true;
            mLayoutManager = new LinearLayoutManager(this);
        } else {
            SettingActivity.pLayout = false;
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        mNoteRcy.setLayoutManager(mLayoutManager);
        // TODO: 2016/12/13  
        //mNoteRcy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); 

        mNoteAdapter.setOnRcyClickListener(new NoteAdapter.OnRcyClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.IS_NEW_CREATE, false);
                intent.putExtra(EditActivity.NOTE_BEAN, mNoteAdapter.getItem(position));
                MainActivity.this.startActivityForResult(intent, EDIT_REQUEST_CODE);
                mClickPosition = position;

            }
        });


        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MenuDialog menuDialog = MenuDialog.create(MainActivity.this);
                menuDialog.show();
                menuDialog.setmOnCollectListener(new MenuDialog.OnCollectListener() {
                    @Override
                    public void onCollect() {
                        startActivity(new Intent(MainActivity.this, CollectActivity.class));
                        menuDialog.dismiss();
                    }
                });

                menuDialog.setmOnSettingListener(new MenuDialog.OnSettingListener() {
                    @Override
                    public void onSetting() {
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        menuDialog.dismiss();
                    }
                });

            }
        });


        mNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.IS_NEW_CREATE, true);
                startActivityForResult(intent, EDIT_REQUEST_CODE);

            }
        });
    }

    public List<NoteBean> covertNotes(Context context) {
        List<NoteBean> noteBeanList = NoteHelper.getInstance(context).queryNote(NoteOpenHelper.NOTE_TAB);
        List<NoteBean> result = new ArrayList<>();
        for (int i = noteBeanList.size() - 1; i >= 0; i--) {
            result.add(noteBeanList.get(i));
        }
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == EditActivity.EDIT_RESULT_CODE) {

                NoteBean noteBean = data.getParcelableExtra(EditActivity.RESULT_INTENT);
                if (mNoteAdapter != null) {
                    mNoteAdapter.insertNote(noteBean, NoteOpenHelper.NOTE_TAB);
                    mEmptyView.setVisibility(View.GONE);
                }

            } else if (resultCode == EditActivity.UPDATE_RESULT_CODE) {
                if (mNoteAdapter != null) {
                    NoteBean noteBean = data.getParcelableExtra(EditActivity.RESULT_INTENT);
                    mNoteAdapter.updateNote(mClickPosition, noteBean, NoteOpenHelper.NOTE_TAB);
                    //NoteHelper.getInstance(MainActivity.this).updateNote(noteBean);

                }

            } else if (resultCode == EditActivity.DELETE_RESULT_CODE) {
                NoteBean noteBean = data.getParcelableExtra(EditActivity.RESULT_INTENT);
                if (noteBean.getNoteId() == -1) {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                } else {
                    NoteHelper.getInstance(this).deleteNote(noteBean, NoteOpenHelper.NOTE_TAB);
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                mNoteAdapter.deleteNote(mClickPosition, mEmptyView);
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (SettingActivity.pLayout) {
            SpUtil.putInt(this, "layout", 0);  // 0 list  ,1  grid
        } else {
            SpUtil.putInt(this, "layout", 1);
        }

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_bottom_to_top, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
