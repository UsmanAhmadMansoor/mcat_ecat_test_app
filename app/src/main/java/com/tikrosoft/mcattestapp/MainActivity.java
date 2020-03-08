package com.tikrosoft.mcattestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView ECATcardView,MCATcardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Categories");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ECATcardView = (CardView) findViewById(R.id.EcardView);
        MCATcardView = (CardView) findViewById(R.id.McardView);

        ECATcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent it = new Intent(MainActivity.this,PapersActivity.class);
                it.putExtra("click","0");
                startActivity(it);
            }
        });

        MCATcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent it = new Intent(MainActivity.this,PapersActivity.class);
                it.putExtra("click","1");
                startActivity(it);
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }
}


