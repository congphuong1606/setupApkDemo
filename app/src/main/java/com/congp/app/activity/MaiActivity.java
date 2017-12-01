package com.congp.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.congp.app.R;
import com.congp.app.activity.main.MainActivity;
import com.congp.app.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MaiActivity extends AppCompatActivity {
    String typeQuery;
    @BindView(R.id.btn_tuchon)
    Button btnTuchon;
    @BindView(R.id.btn_chonchuong)
    Button btnChonchuong;
    @BindView(R.id.btn_luyentap)
    Button btnLuyentap;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mai);
        ButterKnife.bind(this);
        button = ((Button) findViewById(R.id.btn_lague));
        button.setText(getString(R.string.chang_lag));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLanguageScreen();
            }
        });
    }
    public void openLanguageScreen() {
        Intent intent = new Intent(MaiActivity.this, ChangeLanguageActivity.class);
        startActivityForResult(intent, Constants.RequestCode.CHANGE_LANGUAGE);
    }

    private void updateViewByLanguage() {
        button.setText(getString(R.string.chang_lag));
        btnTuchon.setText(getResources().getString(R.string.option));
        btnChonchuong.setText(getResources().getString(R.string.chapter));
        btnLuyentap.setText(getResources().getString(R.string.fast));
    }
    @OnClick({R.id.btn_tuchon, R.id.btn_chonchuong, R.id.btn_luyentap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tuchon:
                typeQuery = "select * from CauHoi";
                Intent intent=new Intent(MaiActivity.this, QuizActivity.class);
                intent.putExtra("typeQuery", typeQuery);
                startActivity(intent);
                break;
            case R.id.btn_chonchuong:
                Intent i=new Intent(MaiActivity.this, ListChapterActivity.class);
                startActivity(i);
                break;
            case R.id.btn_luyentap:
                typeQuery = "select * from CauHoi";
                Intent intent1=new Intent(MaiActivity.this, QuizActivity.class);
                intent1.putExtra("typeQuery", typeQuery);
                startActivity(intent1);
                break;
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
