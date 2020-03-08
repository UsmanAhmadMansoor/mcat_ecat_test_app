package com.tikrosoft.mcattestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tikrosoft.mcattestapp.Adapter.MyAdapter;

public class TestScoreActivity extends AppCompatActivity {

    TextView correct,incorrect,unanswered,marks;
    Button show,takeTest;

    myDBAdapter helper,mcqHelper,correctHelper,skippedHelper;
    myDBSecondAdapter helper2;

    Intent it;

    AlertDialog alertDialog;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_score);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Test Result");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        /*correct = (TextView) findViewById(R.id.correct);
        incorrect = (TextView) findViewById(R.id.incorrect);
        unanswered = (TextView) findViewById(R.id.skip);
        marks = (TextView) findViewById(R.id.marks);
        show = (Button) findViewById(R.id.show);
        takeTest = (Button) findViewById(R.id.take);

        helper = new myDBAdapter(this);

        helper2 = new myDBSecondAdapter(this);

        helper.deleteTimer();
        helper.deleteMCQHelper();
        helper.deleteCorrectOptionHelper();
        helper.deleteSkipedMCQHelper();

        helper2.deleteID();


        it = getIntent();
        correct.setText("Correct Answers : " + it.getStringExtra("true"));
        incorrect.setText("Wrong Answers : " + it.getStringExtra("false"));
        unanswered.setText("Unanswered Questions are : " + it.getStringExtra("skipped"));
        marks.setText("Marks Obtained : " + it.getStringExtra("marks"));

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestScoreActivity.this,ShowAnswersActivity.class);
                intent.putExtra("incorrect",it.getStringExtra("false"));
                startActivity(intent);
            }
        });

        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper2.deleteIncorrectMCQS();
                Intent intent = new Intent(TestScoreActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //onDestroy();
            }
        });*/


        AlertDialog.Builder builder = new AlertDialog.Builder(TestScoreActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.scoredialog, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);


        alertDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    finish();
                }
                return true;
            }

        });

        correct = (TextView) dialogView.findViewById(R.id.correct);
        incorrect = (TextView) dialogView.findViewById(R.id.incorrect);
        unanswered = (TextView) dialogView.findViewById(R.id.skip);
        marks = (TextView) dialogView.findViewById(R.id.marks);
        show = (Button) dialogView.findViewById(R.id.show);
        takeTest = (Button) dialogView.findViewById(R.id.take);

        helper = new myDBAdapter(this);

        helper2 = new myDBSecondAdapter(this);

        helper.deleteTimer();
        helper.deleteMCQHelper();
        helper.deleteCorrectOptionHelper();
        helper.deleteSkipedMCQHelper();

        helper2.deleteID();


        it = getIntent();
        correct.setText(it.getStringExtra("true"));
        incorrect.setText(it.getStringExtra("false"));
        unanswered.setText(it.getStringExtra("skipped"));
        marks.setText(it.getStringExtra("marks"));

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestScoreActivity.this,ShowAnswersActivity.class);
                intent.putExtra("incorrect",it.getStringExtra("false"));
                startActivity(intent);
            }
        });

        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper2.deleteIncorrectMCQS();
                Intent intent = new Intent(TestScoreActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        /*Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);*/
        //alertDialog.setCancelable(false);

    }

}


