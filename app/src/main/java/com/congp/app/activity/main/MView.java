package com.congp.app.activity.main;

import com.congp.app.data.ResultApk;

/**
 * Created by congp on 11/29/2017.
 */

interface MView {
    void success(ResultApk resultApk);
    void fail(String s);
}

