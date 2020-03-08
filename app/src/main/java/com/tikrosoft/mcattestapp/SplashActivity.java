package com.tikrosoft.mcattestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    myDBAdapter helper;
    myDBSecondAdapter helper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        helper = new myDBAdapter(this);
        helper2 = new myDBSecondAdapter(this);

        if(!helper.getTimer().equals("")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
            alertDialogBuilder.setMessage("There is already a Test saved by user , Do you want to continue with previous or start a new one ? ");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Continue",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent it = new Intent(SplashActivity.this,TestActivity.class);
                            startActivity(it);
                            finish();
                        }
                    });
            alertDialogBuilder.setNegativeButton("Start New Test", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    helper.deleteTimer();
                    helper.deleteMCQHelper();
                    helper.deleteCorrectOptionHelper();
                    helper.deleteSkipedMCQHelper();
                    helper2.deleteID();
                    helper2.deleteIncorrectMCQS();
                    Intent it = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(it);
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else{
            Intent it = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(it);
            finish();
        }

    }
}


