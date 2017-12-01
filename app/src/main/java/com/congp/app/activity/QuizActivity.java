package com.congp.app.activity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.congp.app.Database.Database;
import com.congp.app.R;
import com.congp.app.adapter.CauAdapter;
import com.congp.app.data.Cau;
import com.congp.app.data.CauHoi;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity {
    static final String DATABASE_NAME = "NgoaiKhoaPreTest.sqlite";
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<CauHoi> cauHoiList = new ArrayList<>();
    ArrayList<Cau> caus = new ArrayList<Cau>();
    private String giaithich;
    private int luaChon;
    private float lastTranslate = 0.0f;
    private int answer = 0;
    private int idCauhoi;
    private int soChuong;
    private int score = 0;
    private int i = 0;
    private int s;
    private int time = 30;
    private int prosetbar = 30;
    private int cout = 0;
    private String typeQuery = "";
    private Handler handler;
    private Timer timer;
    int dem = 0;
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.mydrawer)
    DrawerLayout mydrawer;
    @BindView(R.id.content_layout)
    FrameLayout contentLayout;
    @BindView(R.id.tv_so_cau)
    TextView tvSoCau;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.rb_a)
    RadioButton rbA;
    @BindView(R.id.rb_b)
    RadioButton rbB;
    @BindView(R.id.rb_c)
    RadioButton rbC;
    @BindView(R.id.rb_d)
    RadioButton rbD;
    @BindView(R.id.rb_e)
    RadioButton rbE;
    @BindView(R.id.imb_next)
    ImageButton imbNext;
    @BindView(R.id.imb_back)
    ImageButton imbBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        typeQuery = (String) bundle.get("typeQuery");
        updateDatabase();
        setQuestionView();
        startGame();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1000, 1000);

        CauAdapter adapter = new CauAdapter(this, caus);

        lvMenu.setAdapter(adapter);
        for (int i = 1; i <= cout; i++) {
            String title = "Câu " + i;
            caus.add(new Cau(title));
        }
        adapter.notifyDataSetChanged();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mydrawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                contentLayout.setTranslationX(slideOffset * drawerView.getWidth());
                mydrawer.bringChildToFront(drawerView);
                mydrawer.requestLayout();
            }
        };
        mydrawer.setDrawerListener(actionBarDrawerToggle);

        lvMenu.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    lvMenu.bringToFront();
                    mydrawer.requestLayout();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                i = position;
                setQuestionView();
            }
        });


    }


    private void updateDatabase() {
        int valueLuachon = 10;
        ContentValues values = new ContentValues();
        values.put("LuaChon", valueLuachon);
        SQLiteDatabase db = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        Cursor cursor = db.rawQuery(typeQuery, null);
        dem = cursor.getCount();
        for (int j = 0; j < dem; j++) {
            db.update("CauHoi", values, "id= ?", new String[]{j + 1 + ""});
        }
        cursor.close();
        db.close();
    }

    public void startGame() {
        handler = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what== 1 && time >= 0) {
                    progressbar.setProgress(prosetbar);
                    tvTime.setText(time + "s");
                    prosetbar--;
                    time--;

                } else {
                    setQuestionView();
                }


            }
        };
    }

    private void setQuestionView() {
        time = 30;
        prosetbar = 30;
        SQLiteDatabase db = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        Cursor cursor = db.rawQuery(typeQuery, null);

        cout = cursor.getCount();
        if(i<cout){
            cursor.moveToPosition(i);
            soChuong = cursor.getInt(1);
            idCauhoi = cursor.getInt(0);
            tvQuestion.setText(cursor.getString(2));
            rbA.setText(cursor.getString(4));
            rbB.setText(cursor.getString(5));
            rbC.setText(cursor.getString(6));
            rbD.setText(cursor.getString(7));
            rbE.setText(cursor.getString(8));
            giaithich = cursor.getString(9);
            luaChon = cursor.getInt(10);
            s = cursor.getInt(3);
            tvScore.setText(score + "");
            tvSoCau.setText(i + 1 + "");
            setChecked();
            i++;
            if (i > 1) {
                imbBack.setVisibility(View.VISIBLE);
            } else {
                imbBack.setVisibility(View.GONE);
            }
        }

        cursor.close();
        db.close();
    }

    private void backQuestion() {
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        i--;
        i--;
        setQuestionView();

    }

    public void nextQuestion() {
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        if (s == answer) {
            score++;
        }
        if (i < cout) {
            setQuestionView();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); //Your score
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }

    private void upDateAnswer() {
        int number = answer;
        ContentValues values = new ContentValues();
        values.put("LuaChon", number);
        SQLiteDatabase db = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        db.update("CauHoi", values, "id= ?", new String[]{idCauhoi + ""});
        db.close();
    }

    @OnClick({R.id.rb_a, R.id.rb_b, R.id.rb_c, R.id.rb_d, R.id.rb_e, R.id.imb_next, R.id.imb_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_a:
                answer = 1;
                rbA.setTextColor(getResources().getColor(R.color.colorAccent));
                rbB.setTextColor(getResources().getColor(R.color.b));
                rbC.setTextColor(getResources().getColor(R.color.b));
                rbD.setTextColor(getResources().getColor(R.color.b));
                rbE.setTextColor(getResources().getColor(R.color.b));
                upDateAnswer();
                break;
            case R.id.rb_b:
                answer = 2;
                rbA.setTextColor(getResources().getColor(R.color.b));
                rbB.setTextColor(getResources().getColor(R.color.colorAccent));
                rbC.setTextColor(getResources().getColor(R.color.b));
                rbD.setTextColor(getResources().getColor(R.color.b));
                rbE.setTextColor(getResources().getColor(R.color.b));

                upDateAnswer();
                break;
            case R.id.rb_c:
                answer = 3;
                rbA.setTextColor(getResources().getColor(R.color.b));
                rbB.setTextColor(getResources().getColor(R.color.b));
                rbC.setTextColor(getResources().getColor(R.color.colorAccent));
                rbD.setTextColor(getResources().getColor(R.color.b));
                rbE.setTextColor(getResources().getColor(R.color.b));
                upDateAnswer();
                break;
            case R.id.rb_d:
                answer = 4;
                rbA.setTextColor(getResources().getColor(R.color.b));
                rbB.setTextColor(getResources().getColor(R.color.b));
                rbC.setTextColor(getResources().getColor(R.color.b));
                rbD.setTextColor(getResources().getColor(R.color.colorAccent));
                rbE.setTextColor(getResources().getColor(R.color.b));
                upDateAnswer();
                break;
            case R.id.rb_e:
                answer = 5;
                rbA.setTextColor(getResources().getColor(R.color.b));
                rbB.setTextColor(getResources().getColor(R.color.b));
                rbC.setTextColor(getResources().getColor(R.color.b));
                rbD.setTextColor(getResources().getColor(R.color.b));
                rbE.setTextColor(getResources().getColor(R.color.colorAccent));
                upDateAnswer();
                break;
            case R.id.imb_next:
                nextQuestion();
                break;
            case R.id.imb_back:
                backQuestion();
                break;
        }
    }

    private void setChecked() {

        if (luaChon == 1) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbA.getId());
            rbA.setTextColor(getResources().getColor(R.color.colorAccent));
            rbB.setTextColor(getResources().getColor(R.color.b));
            rbC.setTextColor(getResources().getColor(R.color.b));
            rbD.setTextColor(getResources().getColor(R.color.b));
            rbE.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 2) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbB.getId());
            rbA.setTextColor(getResources().getColor(R.color.b));
            rbB.setTextColor(getResources().getColor(R.color.colorAccent));
            rbC.setTextColor(getResources().getColor(R.color.b));
            rbD.setTextColor(getResources().getColor(R.color.b));
            rbE.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 3) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbC.getId());
            rbA.setTextColor(getResources().getColor(R.color.b));
            rbB.setTextColor(getResources().getColor(R.color.b));
            rbC.setTextColor(getResources().getColor(R.color.colorAccent));
            rbD.setTextColor(getResources().getColor(R.color.b));
            rbE.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 4) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbD.getId());
            rbA.setTextColor(getResources().getColor(R.color.b));
            rbB.setTextColor(getResources().getColor(R.color.b));
            rbC.setTextColor(getResources().getColor(R.color.b));
            rbD.setTextColor(getResources().getColor(R.color.colorAccent));
            rbE.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 5) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbE.getId());
            rbA.setTextColor(getResources().getColor(R.color.b));
            rbB.setTextColor(getResources().getColor(R.color.b));
            rbC.setTextColor(getResources().getColor(R.color.b));
            rbD.setTextColor(getResources().getColor(R.color.b));
            rbE.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.clearCheck();
            rbA.setTextColor(getResources().getColor(R.color.b));
            rbB.setTextColor(getResources().getColor(R.color.b));
            rbC.setTextColor(getResources().getColor(R.color.b));
            rbD.setTextColor(getResources().getColor(R.color.b));
            rbE.setTextColor(getResources().getColor(R.color.b));
        }
    }


}
