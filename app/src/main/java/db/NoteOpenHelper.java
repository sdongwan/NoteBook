package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/8.
 */

public class NoteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "note.db";
    private static final int DB_VERSION = 1;
    public static final String NOTE_TAB = "note";
    public static final String COLLECT_TAB = "collect";
    public static final String NOTE_DATE = "note_date";
    public static final String NOTE_CONTENT = "note_content";
    public static final String NOTE_IMG = "note_img";
    // public static final String NOTE_ID = "note_id";


    private static final String CREAT_NOTE_TABLE = " create table " + NOTE_TAB + " ( " +
            "id integer primary key autoincrement," +
            NOTE_DATE + " text, " +
            NOTE_CONTENT + " text," +
            NOTE_IMG + " text " +
            ");";

    private static final String CREAT_COLLECT_TABLE = " create table " + COLLECT_TAB + " ( " +
            "id integer primary key autoincrement," +
            NOTE_DATE + " text, " +
            NOTE_CONTENT + " text," +
            NOTE_IMG + " text " +
            ");";

    public NoteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_NOTE_TABLE);
        db.execSQL(CREAT_COLLECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: 2016/12/8 暂不更新
    }
}
