package com.congp.app.adapter;

/**
 * Created by congp on 11/30/2017.
 */

public interface ItemClickListener<T> {
    void onClickItem(int position, T item);
}