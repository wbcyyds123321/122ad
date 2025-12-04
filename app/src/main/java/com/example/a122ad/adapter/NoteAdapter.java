package com.example.a122ad.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a122ad.R;
import com.example.a122ad.model.Note;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 笔记适配器，用于将笔记数据显示在RecyclerView中
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> noteList;
    private OnNoteClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // 构造函数
    public NoteAdapter(Context context, List<Note> noteList, OnNoteClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        try {
            Note note = noteList.get(position);
            // 安全设置标题，避免空值
            String title = note.getTitle();
            holder.titleTextView.setText(title != null ? title : "无标题");
            
            // 安全设置内容，避免空值
            String content = note.getContent();
            holder.contentTextView.setText(content != null ? content : "");
            
            // 安全设置时间戳，避免空值
            if (note.getUpdatedAt() != null) {
                try {
                    holder.timestampTextView.setText(dateFormat.format(note.getUpdatedAt()));
                } catch (Exception e) {
                    holder.timestampTextView.setText("");
                }
            } else {
                holder.timestampTextView.setText("");
            }
            
            // 安全设置分类，避免空值
            String category = note.getCategory();
            holder.categoryTextView.setText(category != null ? category : "");
            
            // 设置图片
            String imagePath = note.getImagePath();
            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    // 使用缩略图优化性能，这里简单实现，实际应使用Glide/Picasso
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.noteImageView.setImageBitmap(myBitmap);
                    holder.noteImageView.setVisibility(View.VISIBLE);
                } else {
                    holder.noteImageView.setVisibility(View.GONE);
                }
            } else {
                holder.noteImageView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            // 捕获所有异常，避免因单个项目数据问题导致整个列表显示失败
            holder.titleTextView.setText("");
            holder.contentTextView.setText("");
            holder.timestampTextView.setText("");
            holder.categoryTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return noteList != null ? noteList.size() : 0;
    }

    // 更新数据列表
    public void updateList(List<Note> newNotes) {
        this.noteList = newNotes;
        notifyDataSetChanged();
    }

    // 内部ViewHolder类
    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView titleTextView;
        TextView contentTextView;
        TextView timestampTextView;
        TextView categoryTextView;
        ImageView noteImageView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title);
            contentTextView = itemView.findViewById(R.id.note_content);
            timestampTextView = itemView.findViewById(R.id.note_timestamp);
            categoryTextView = itemView.findViewById(R.id.note_category);
            noteImageView = itemView.findViewById(R.id.note_image);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onNoteClick(noteList.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                listener.onNoteLongClick(noteList.get(getAdapterPosition()), getAdapterPosition());
                return true;
            }
            return false;
        }
    }

    // 接口定义点击事件
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
        void onNoteLongClick(Note note, int position);
    }
}