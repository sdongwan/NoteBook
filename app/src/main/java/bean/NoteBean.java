package bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/8.
 */

public class NoteBean implements Parcelable {

    private String noteDate;
    private String noteContent;
    private long noteId=-1;
    private String noteImg;

    protected NoteBean(Parcel in) {
        noteDate = in.readString();
        noteContent = in.readString();
        noteId = in.readLong();
        noteImg = in.readString();
    }

    public static final Creator<NoteBean> CREATOR = new Creator<NoteBean>() {
        @Override
        public NoteBean createFromParcel(Parcel in) {
            return new NoteBean(in);
        }

        @Override
        public NoteBean[] newArray(int size) {
            return new NoteBean[size];
        }
    };

    public String getNoteImg() {
        return noteImg;
    }

    public void setNoteImg(String noteImg) {
        this.noteImg = noteImg;
    }


    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public NoteBean() {

    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteDate);
        dest.writeString(noteContent);
        dest.writeLong(noteId);
        dest.writeString(noteImg);
    }
}
