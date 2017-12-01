package com.congp.app.activity.main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.congp.app.activity.MaiActivity;
import com.congp.app.activity.UpdateActivity;
import com.congp.app.common.Constants;
import com.congp.app.common.LanguageUtils;

import com.congp.app.R;
import com.congp.app.data.ResultApk;
import com.congp.app.activity.ChangeLanguageActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements MView {




    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Presenter presenter;
    private int flag;
    private Button reset;
    private boolean isSettup=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset = ((Button) findViewById(R.id.btn_reset));
        LanguageUtils.loadLocale();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        presenter = new Presenter(this);
        verifyStoragePermissions(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getApk();
            }
        });

    }



    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE
            );
            verifyStoragePermissions(this);
        } else {
            presenter.getApk();
        }
    }




    @Override
    public void success(ResultApk resultApk) {
        if (resultApk.getIsshowwap().equals("1")) {
            showDialogUpdate(resultApk);
        } else if (resultApk.getIsshowwap().equals("2")) {
            fail("");
        }else  {
            fail("");
        }
    }



    private void showDialogUpdate(ResultApk resultApk) {
        String updateVi = getResources().getString(R.string.confirm_update);
        String btnUpdateS = getResources().getString(R.string.yes);
        final Dialog dialogUpdate = new Dialog(this);
        dialogUpdate.setContentView(R.layout.dialog_update);
        TextView tvUpdate = (TextView) dialogUpdate.findViewById(R.id.tv_text_update);
        TextView btnUpdate = (Button) dialogUpdate.findViewById(R.id.btn_update);
        tvUpdate.setText(updateVi);
        btnUpdate.setText(btnUpdateS);
        dialogUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogUpdate.setCancelable(false);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("name",resultApk.getAppname()+"");
                intent.putExtra("link",resultApk.getWapurl()+"");
                startActivity(intent);
                finish();
                dialogUpdate.dismiss();
            }
        });
        dialogUpdate.show();
    }



    @Override
    public void fail(String s) {
        startActivity(new Intent(this, MaiActivity.class));
        finish();
    }






}
