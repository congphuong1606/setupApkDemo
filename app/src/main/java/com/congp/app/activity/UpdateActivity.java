package com.congp.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.congp.app.R;
import com.congp.app.activity.main.MainActivity;
import com.congp.app.data.ResultApk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.provider.Telephony.ServiceStateTable.AUTHORITY;

public class UpdateActivity extends AppCompatActivity {
    private static ProgressDialog pDialog;
    private ResultApk resultApk;
    public static final int progress_bar_type = 0;
    private String path;
    private String link;
    private int flag;
    private boolean isSettup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        String appName=getIntent().getStringExtra("name");
        link=getIntent().getStringExtra("link");
        path = Environment.getExternalStorageDirectory()
                + "/Download/" + appName + ".apk";
        new UpdateActivity.DownloadFileFromURL().execute(link);
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
                new UpdateActivity.DownloadFileFromURL().execute(link);
                dialogUpdateFail.dismiss();
            }
        });
        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateFail.dismiss();
                startActivity(new Intent(UpdateActivity.this, MaiActivity.class));
                isSettup=false;
                finish();
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
                OutputStream output = new FileOutputStream(path);
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
            }else {
                installApk(path);
            }
        }

    }
    private void installApk(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(this, "com.your.package.fileProvider", file);
                    intent.setDataAndType(contentUri, type);
                } else {
                    intent.setDataAndType(Uri.fromFile(file), type);
                }
                isSettup=true;
                startActivityForResult(intent, 01);
            } catch (ActivityNotFoundException anfe) {
                Toast.makeText(this, "No activity found to open this attachment.", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSettup){
            startActivity(new Intent(this, MaiActivity.class));
            isSettup=false;
            finish();
        }else {

        }
    }

}
