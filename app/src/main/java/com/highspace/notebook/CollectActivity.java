package com.highspace.notebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.NoteAdapter;
import bean.NoteBean;
import db.NoteHelper;
import db.NoteOpenHelper;

public class CollectActivity extends AppCompatActivity {
    private static final int EDIT_REQUEST_CODE = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteAdapter mNoteAdapter;
    private List<NoteBean> noteBeanList;
    private TextView mCollectEmptyView;

    private int mClickPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initEvent();

    }

    private void initEvent() {
        noteBeanList = covertNotes(this);
        if (noteBeanList.size() == 0) {
            mCollectEmptyView.setVisibility(View.VISIBLE);
        }
        mNoteAdapter = new NoteAdapter(this, noteBeanList);
        mRecyclerView.setAdapter(mNoteAdapter);
        if (SettingActivity.pLayout) {
            mLayoutManager = new LinearLayoutManager(this);
        } else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        // TODO: 2016/12/13  
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mNoteAdapter.setOnRcyClickListener(new NoteAdapter.OnRcyClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(CollectActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.IS_NEW_CREATE, false);
                intent.putExtra(EditActivity.NOTE_BEAN, mNoteAdapter.getItem(position));
                CollectActivity.this.startActivityForResult(intent, EDIT_REQUEST_CODE);
                mClickPosition = position;

            }
        });


    }

    public List<NoteBean> covertNotes(Context context) {
        List<NoteBean> noteBeanList = NoteHelper.getInstance(context).queryNote(NoteOpenHelper.COLLECT_TAB);
        List<NoteBean> result = new ArrayList<>();
        for (int i = noteBeanList.size() - 1; i >= 0; i--) {
            result.add(noteBeanList.get(i));
        }
        return result;
    }


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.collect_note_rcy);
        mCollectEmptyView = (TextView) findViewById(R.id.collect_empty_view);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (SettingActivity.pLayout) {
            mLayoutManager = new LinearLayoutManager(this);
        } else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        if (mRecyclerView != null)
            mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == EditActivity.EDIT_RESULT_CODE) {

                NoteBean noteBean = data.getParcelableExtra(EditActivity.RESULT_INTENT);
                if (mNoteAdapter != null) {
                    mNoteAdapter.insertNote(noteBean, NoteOpenHelper.COLLECT_TAB);
                    mCollectEmptyView.setVisibility(View.GONE);
                }

            } else if (resultCode == EditActivity.UPDATE_RESULT_CODE) {
                if (mNoteAdapter != null) {
                    NoteBean noteBean = data.getParcelableExtra(EditActivity.RESULT_INTENT);
                    mNoteAdapter.updateNote(mClickPosition, noteBean, NoteOpenHelper.COLLECT_TAB);
                }

            } else if (resultCode == EditActivity.DELETE_RESULT_CODE) {

                NoteBean noteBean = data.getParcelableExtra(EditActivity.RESULT_INTENT);
                NoteHelper.getInstance(this).deleteNote(noteBean, NoteOpenHelper.COLLECT_TAB);
                mNoteAdapter.deleteNote(mClickPosition, mCollectEmptyView);
            }
        }


    }

}
