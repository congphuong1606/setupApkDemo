package com.congp.app.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.congp.app.Database.Database;
import com.congp.app.R;
import com.congp.app.adapter.ChapterAdapter;
import com.congp.app.data.Chapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListChapterActivity extends AppCompatActivity {
    static final String DATABASE_NAME = "NgoaiKhoaPreTest.sqlite";
    ArrayList<Chapter> chapters = new ArrayList<>();
    SQLiteDatabase database;
    ChapterAdapter chapterAdapter;
    @BindView(R.id.rcv_list_chapter)
    RecyclerView rcvListChapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        ButterKnife.bind(this);

        layoutManager = new GridLayoutManager(ListChapterActivity.this, 1);
        rcvListChapter.setLayoutManager(layoutManager);
        rcvListChapter.setHasFixedSize(true);
        chapterAdapter = new ChapterAdapter(chapters,ListChapterActivity.this, R.layout.row_chapter);
        rcvListChapter.setAdapter(chapterAdapter);
        readData();

    }

    private void readData() {
        database = Database.initDatabase(ListChapterActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from Chuong ", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            chapters.add(new Chapter(id, name));
        }
        chapterAdapter.notifyDataSetChanged();
    }
}
