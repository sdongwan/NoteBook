package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UnknownFormatConversionException;

import bean.NoteBean;

/**
 * Created by Administrator on 2016/12/8.
 */

public class NoteHelper {
    private static NoteHelper sNoteHelper;
    private NoteOpenHelper mNoteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    private NoteHelper(Context context) {
        mNoteOpenHelper = new NoteOpenHelper(context);
        mSqLiteDatabase = mNoteOpenHelper.getWritableDatabase();
    }

    public static NoteHelper getInstance(Context context) {
        if (sNoteHelper == null) {
            sNoteHelper = new NoteHelper(context);

        }
        return sNoteHelper;
    }


    public long insertNote(NoteBean noteBean, String tabName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.NOTE_DATE, noteBean.getNoteDate());
        contentValues.put(NoteOpenHelper.NOTE_CONTENT, noteBean.getNoteContent());
        //contentValues.put(NoteOpenHelper.NOTE_ID, noteBean.getNoteId());
        long result = mSqLiteDatabase.insert(tabName, null, contentValues);
        if (result == -1) {
            throw new UnsupportedOperationException("插入数据失败");
        }
        return result;
    }

    public void deleteNote(NoteBean noteBean, String tabName) {
        int result = mSqLiteDatabase.delete(tabName, " id = ?", new String[]{noteBean.getNoteId() + ""});
        if (result < 0) {
            throw new UnsupportedOperationException("删除数据失败");
        }
    }


    public long updateNote(NoteBean noteBean, String tabName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.NOTE_DATE, noteBean.getNoteDate());
        contentValues.put(NoteOpenHelper.NOTE_CONTENT, noteBean.getNoteContent());
        contentValues.put(NoteOpenHelper.NOTE_IMG, noteBean.getNoteImg());
        // contentValues.put(NoteOpenHelper.NOTE_ID, noteBean.getNoteId());
        int delete = mSqLiteDatabase.delete(tabName, " id " + " = ?", new String[]{noteBean.getNoteId() + ""});

        long insert = mSqLiteDatabase.insert(NoteOpenHelper.NOTE_TAB, null, contentValues);
        if (delete < 0 || insert < 0) {
            throw new UnknownFormatConversionException("更新失败");
        }

        return insert;


    }


    public List<NoteBean> queryNote(String tabName) {
        Cursor cursor = mSqLiteDatabase.rawQuery("select * from " + tabName, null);
        List<NoteBean> noteBeanList = new ArrayList<>();
        while (cursor.moveToNext()) {
            NoteBean noteBean = new NoteBean();
            noteBean.setNoteDate(cursor.getString(cursor.getColumnIndex(NoteOpenHelper.NOTE_DATE)));
            noteBean.setNoteContent(cursor.getString(cursor.getColumnIndex(NoteOpenHelper.NOTE_CONTENT)));
            noteBean.setNoteId(cursor.getInt(cursor.getColumnIndex("id")));
            noteBean.setNoteImg(cursor.getString(cursor.getColumnIndex(NoteOpenHelper.NOTE_IMG)));
            // noteBean.setNoteId(cursor.getInt(cursor.getColumnIndex(NoteOpenHelper.NOTE_ID)));
            noteBeanList.add(noteBean);
        }
        return noteBeanList;


    }


}
