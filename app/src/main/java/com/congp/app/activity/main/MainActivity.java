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
    Button button;
    private static ProgressDialog pDialog;
    private ResultApk resultApk;
    public static final int progress_bar_type = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Presenter presenter;
    private int flag;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = ((Button) findViewById(R.id.btn_lague));
        reset = ((Button) findViewById(R.id.btn_reset));

        LanguageUtils.loadLocale();
        button.setText(getString(R.string.chang_lag));

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        presenter = new Presenter(this);
        verifyStoragePermissions(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLanguageScreen();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getApk();
            }
        });

    }

    public void openLanguageScreen() {
        Intent intent = new Intent(MainActivity.this, ChangeLanguageActivity.class);
        startActivityForResult(intent, Constants.RequestCode.CHANGE_LANGUAGE);
    }

    private void updateViewByLanguage() {
        button.setText(getString(R.string.chang_lag));
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage(getResources().getString(R.string.loading));
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    @Override
    public void success(ResultApk resultApk) {
        this.resultApk = resultApk;
        if (resultApk.getIsshowwap().equals("1")) {
            String path = Environment.getExternalStorageDirectory()
                    + "/Download/" + resultApk.getAppid() + ".apk";
            File file = new File(path);
            if (file.exists()) {
                getPackage(path);
            } else {
                showDialogUpdate();
            }
        } else if (resultApk.getIsshowwap().equals("2")) {
            fail("");
        }
    }

    private void showDialogUpdateFail() {
        String fail = getResources().getString(R.string.update_fail);
        String btnCancelS = getResources().getString(R.string.cancel);
        String btnContinuteS = getResources().getString(R.string.continute);
        final Dialog dialogUpdateFail = new Dialog(this);
        dialogUpdateFail.setContentView(R.layout.dialog_update_fail);
        TextView tvUpdateFail = (TextView) dialogUpdateFail.findViewById(R.id.tv_text_update_fail);
        Button btnUpdateCancel = (Button) dialogUpdateFail.findViewById(R.id.btn_update_cancel);
        Button btnUpdateContinute = (Button) dialogUpdateFail.findViewById(R.id.btn_update_continute);
        tvUpdateFail.setText(fail);
        btnUpdateCancel.setText(btnCancelS);
        btnUpdateContinute.setText(btnContinuteS);
        dialogUpdateFail.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogUpdateFail.setCancelable(false);
        btnUpdateContinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileFromURL().execute(resultApk.getWapurl());
                dialogUpdateFail.dismiss();
            }
        });
        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateFail.dismiss();
            }
        });
        dialogUpdateFail.show();
    }

    private void showDialogUpdate() {
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
                new DownloadFileFromURL().execute(resultApk.getWapurl());
                dialogUpdate.dismiss();
            }
        });
        dialogUpdate.show();
    }


    private void getPackage(String path) {
        final PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, 0);
        if(info!=null){
            String packageName = info.packageName;
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                startActivity(launchIntent);
                finish();
            } else {
                installApk(path);
            }
        }else {
            File file = new File(path);
            file.delete();
            presenter.getApk();
        }

    }

    @Override
    public void fail(String s) {
        String fail = getResources().getString(R.string.fail);
        String btnCancelS = getResources().getString(R.string.cancel);
        String btnContinuteS = getResources().getString(R.string.continute);
        final Dialog dialogUpdateFail = new Dialog(this);
        dialogUpdateFail.setContentView(R.layout.dialog_update_fail);
        TextView tvUpdateFail = (TextView) dialogUpdateFail.findViewById(R.id.tv_text_update_fail);
        Button btnUpdateCancel = (Button) dialogUpdateFail.findViewById(R.id.btn_update_cancel);
        Button btnUpdateContinute = (Button) dialogUpdateFail.findViewById(R.id.btn_update_continute);
        tvUpdateFail.setText(fail);
        btnUpdateCancel.setText(btnCancelS);
        btnUpdateContinute.setText(btnContinuteS);
        dialogUpdateFail.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogUpdateFail.setCancelable(false);
        btnUpdateContinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getApk();
                dialogUpdateFail.dismiss();
            }
        });
        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateFail.dismiss();
            }
        });
        dialogUpdateFail.show();
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            flag = 0;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                String a = Environment
                        .getExternalStorageDirectory()
                        + "/Download/" + resultApk.getAppid() + ".apk";
                OutputStream output = new FileOutputStream(a);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                installApk(a);
                flag = 0;

            } catch (Exception e) {
                flag = 1;
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {

            dismissDialog(progress_bar_type);
            if (flag == 1) {
                showDialogUpdateFail();
            }
        }

    }

    private void installApk(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.CHANGE_LANGUAGE:
                if (resultCode == RESULT_OK) {
                    updateViewByLanguage();
                }
                break;
        }
    }

}
