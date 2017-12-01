package com.congp.app.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.congp.app.R;
import com.congp.app.activity.QuizActivity;
import com.congp.app.data.Chapter;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    ArrayList<Chapter> chapters = new ArrayList<>();

    public ChapterAdapter(ArrayList<Chapter> chapters, Context context, int layout) {
        this.chapters = chapters;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.tvIdChapter.setText(String.valueOf(chapter.getId()));
        holder.tvNameChapter.setText(chapter.getChapterName().toString());
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdChapter;
        TextView tvNameChapter;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvIdChapter = (TextView) itemView.findViewById(R.id.tv_id_chapter);
            tvNameChapter = (TextView) itemView.findViewById(R.id.tv_name_chapter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context;
                    context = view.getContext();
                    Intent intent = new Intent(context, QuizActivity.class);
                    String typeQuery = "";
                    String sochuong = tvIdChapter.getText().toString();
                    switch (sochuong) {
                        case "1":
                            typeQuery = "select * from CauHoi where SoChuong = 1 ";
                            break;
                        case "2":
                            typeQuery = "select * from CauHoi where SoChuong = 2 ";
                            break;
                        case "3":
                            typeQuery = "select * from CauHoi where SoChuong = 3 ";
                            break;
                        case "4":
                            typeQuery = "select * from CauHoi where SoChuong = 4 ";
                            break;
                        case "5":
                            typeQuery = "select * from CauHoi where SoChuong = 5 ";
                            break;
                        case "6":
                            typeQuery = "select * from CauHoi where SoChuong = 6 ";
                            break;
                        case "7":
                            typeQuery = "select * from CauHoi where SoChuong = 7 ";
                            break;
                        case "8":
                            typeQuery = "select * from CauHoi where SoChuong = 8 ";
                            break;
                        case "9":
                            typeQuery = "select * from CauHoi where SoChuong = 9 ";
                            break;
                        case "10":
                            typeQuery = "select * from CauHoi where SoChuong = 10 ";
                            break;
                        case "11":
                            typeQuery = "select * from CauHoi where SoChuong = 11 ";
                            break;
                        case "12":
                            typeQuery = "select * from CauHoi where SoChuong = 12 ";
                            break;
                        case "13":
                            typeQuery = "select * from CauHoi where SoChuong = 13 ";
                            break;
                    }
                    intent.putExtra("typeQuery", typeQuery);
                    context.startActivity(intent);
                }
            });

        }
    }
}
