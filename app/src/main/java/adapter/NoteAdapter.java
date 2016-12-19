package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.highspace.notebook.R;

import java.util.List;

import bean.NoteBean;
import db.NoteHelper;

/**
 * Created by Administrator on 2016/12/8.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {
    private Context context;
    private List<NoteBean> noteBeanList;

    private OnRcyClickListener onRcyClickListener;


    public NoteAdapter(Context context, List<NoteBean> noteBeanList) {
        this.context = context;
        this.noteBeanList = noteBeanList;
    }

    public void insertNote(NoteBean noteBean, String tabName) {
        noteBeanList.add(0, noteBean);
        notifyDataSetChanged();
        long insert = NoteHelper.getInstance(context).insertNote(noteBean, tabName);
        noteBean.setNoteId(insert);
    }

    public void updateNote(int position, NoteBean noteBean, String tabName) {
        long index = NoteHelper.getInstance(context).updateNote(noteBean, tabName);
        noteBean.setNoteId(index);
        noteBeanList.remove(position);
        noteBeanList.add(0, noteBean);
        notifyDataSetChanged();

    }

    public void deleteNote(int position, TextView emptyView) {
        if (noteBeanList.size() > 0) {
            noteBeanList.remove(position);
        }
        if (noteBeanList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();

    }

    public void setOnRcyClickListener(OnRcyClickListener onRcyClickListener) {
        this.onRcyClickListener = onRcyClickListener;
    }

    public interface OnRcyClickListener {
        public void onClick(int position);
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_home_rcy, parent, false);
        return new NoteVH(itemView);
    }

    @Override
    public void onBindViewHolder(NoteVH holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRcyClickListener != null) {
                    onRcyClickListener.onClick(position);

                }

            }
        });
        String noteDate[] = noteBeanList.get(position).getNoteDate().split("\\s+");
        holder.noteTitle.setText(noteBeanList.get(position).getNoteContent());
        holder.noteDate.setText(noteDate[1]);
//        holder.noteDate.setText("好大");
    }

    public NoteBean getItem(int position) {

        return noteBeanList.get(position);
    }

    @Override
    public int getItemCount() {
        return noteBeanList.size();
    }

    class NoteVH extends RecyclerView.ViewHolder {
        protected TextView noteDate;
        protected TextView noteTitle;

        public NoteVH(View itemView) {
            super(itemView);
            noteDate = (TextView) itemView.findViewById(R.id.rcy_item_date);
            noteTitle = (TextView) itemView.findViewById(R.id.rcy_item_content);
        }
    }
}
