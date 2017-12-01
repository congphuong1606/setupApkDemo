package com.congp.app.data;

/**
 * Created by MyPC on 06/07/2017.
 */

public class Chapter {
    private int id;
    private String chapterName;

    public Chapter(int id, String chapterName) {
        this.id = id;
        this.chapterName = chapterName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
