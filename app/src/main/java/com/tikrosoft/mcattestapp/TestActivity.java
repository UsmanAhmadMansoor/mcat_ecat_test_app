package com.tikrosoft.mcattestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    String optionNo[] = new String[1000];


    Button[] optButton;
    TextView timer,mcq,mcqNo;
    LinearLayout linearLayout;
    //RadioGroup radioGroup;
    //RadioButton opt1,opt2,opt3,opt4;
    Button skip,next,save;

    int buttonFlag = 0,alreadySelected=-1,optionSelected,j=0;

    String paperId;
    int[] buttonIDs;

    JSONArray jsonArray;
    JSONObject jsonObject;

    List<PaperData> list = new ArrayList<PaperData>();
    List<PaperData> list2;
    List<PaperData> skippedMCQs = new ArrayList<PaperData>();
    PaperData current;
    PaperData listData;

    int mcqPointer,optionPointer = 0 , skipPointer = 0 , sPointer=0;

    String correctValue = "";
    int Correct,Incorrect,Unanswered,correctMarks;
    boolean textViewClick=false,skipButtonPressed = false , userskipped = false , skippedAttempt = false;

    CountDownTimer countDownTimer;

    long totalTime = 60000;
    long TimeLeft;

    myDBAdapter helper,optionHelper,correctHelper,skipHelper;
    myDBSecondAdapter helper2;

    RequestURL requestURL;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        requestURL = new RequestURL();

        timer = (TextView) findViewById(R.id.testTimer);
        mcq = (TextView) findViewById(R.id.mcq);
        mcqNo = (TextView) findViewById(R.id.mcqNo);
        optButton = new Button[4];
        buttonIDs = new int[]{R.id.opt1, R.id.opt2, R.id.opt3, R.id.opt4};

        linearLayout = (LinearLayout) findViewById(R.id.ll);

        /*radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        opt1 = (RadioButton) findViewById(R.id.option1);
        opt2 = (RadioButton) findViewById(R.id.option2);
        opt3 = (RadioButton) findViewById(R.id.option3);
        opt4 = (RadioButton) findViewById(R.id.option4);*/

        skip = (Button) findViewById(R.id.skip);
        //next = (Button) findViewById(R.id.next);
        save = (Button) findViewById(R.id.save);

        helper = new myDBAdapter(this);
        optionHelper = new myDBAdapter(this);
        correctHelper = new myDBAdapter(this);
        helper2 = new myDBSecondAdapter(this);
        skipHelper = new myDBAdapter(this);

        //next.setOnClickListener(nextButtonClickedListner);

        CountdownTimer();

        Intent it = getIntent();
        paperId = it.getStringExtra("paperId");

        //LoadMCQS("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQS.php");
        LoadMCQS(requestURL.loadMcqURL);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // When skip clicked , save the mcq_id of current mcq (that user wants to skip) on somewhere storage (List or SQLite etc.)
                userskipped = true;
                //skippedAttempt = false;



                //problem in bwlow line,,,,current.mcq gives logical error
                current.mcq = mcq.getText().toString();
                skipHelper.saveSkippedMCQ(String.valueOf(skipPointer+1),current.mcq_id,current.mcq);
                //Log.d("skipTest",skipHelper.getSkipTestData());
                Log.d("skipTest",skipHelper.getSkipMCQ(String.valueOf(skipPointer+1)));
                skipPointer++;


                listData = new PaperData();

                listData.mcq_id = current.mcq_id;
                listData.mcq = current.mcq;

                skippedMCQs.add(listData);

                skipButtonPressed = true;
                //radioGroup.clearCheck();
                Unanswered = Unanswered + 1;
                showNextMCQ();
                //nextButtonClickedListner.onClick(view);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onPause();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
                alertDialogBuilder.setMessage("Do you want to save your test ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                countDownTimer.cancel();
                                helper.saveTimer(String.valueOf(TimeLeft),String.valueOf(mcqPointer-1),String.valueOf(paperId),
                                        String.valueOf(Correct),String.valueOf(Incorrect),String.valueOf(correctMarks),
                                        String.valueOf(Unanswered),String.valueOf(userskipped),String.valueOf(skippedAttempt),
                                        String.valueOf(skipPointer),String.valueOf(sPointer));
                                for(int i=0;i<skippedMCQs.size();i++ ){
                                    helper2.saveID(String.valueOf(i),String.valueOf(skippedMCQs.get(i).mcq_id));
                                }

                                //Toast.makeText(TestActivity.this,String.valueOf(TimeLeft),Toast.LENGTH_LONG).show();
                                //Intent it = new Intent(TestActivity.this,MainActivity.class);
                                //startActivity(it);

                                PapersActivity.getInstance().finish();
                                finish();
//
                            }
                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

    private void showNextMCQ(){

        for (int i = 0; i < optButton.length; i++) {
            optButton[i].setEnabled(false);
            //optButton[i].setBackgroundResource(R.drawable.textviewborder);
        }
        skip.setEnabled(false);
        //next.setEnabled(false);
        if (!textViewClick && !skipButtonPressed)
        {
            Toast.makeText(TestActivity.this,"Please Tap Your Option", Toast.LENGTH_LONG).show();
            for (int i = 0; i < optButton.length; i++) {
                optButton[i].setEnabled(true);
            }
            skip.setEnabled(true);
            //next.setEnabled(true);
        }
        else
        {
            textViewClick = false;
            LoadCorrectMCQOptionFromLocalDB();
            //LoadCorrectAnswer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadCorrectAnswer.php",current.mcq_id);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < optButton.length; i++) {
                        if(optionSelected == i && !skipButtonPressed){
                            if(optButton[i].getText().toString().equals(correctValue)){
                                correctMarks = correctMarks + 5;
                                Correct = Correct + 1;
                            }else {
                                correctMarks = correctMarks - 1;
                                Incorrect = Incorrect + 1;
                                //helper2.saveIncorrectMCQData(String.valueOf(Incorrect), current.mcq,String.valueOf(correctValue),optButton[i].getText().toString());
                                helper2.saveIncorrectMCQData(String.valueOf(Incorrect), mcq.getText().toString(),String.valueOf(correctValue),optButton[i].getText().toString());
                            }
                        }
                    }
                    skipButtonPressed = false;
                    if(mcqPointer < list.size()){
                        current = list.get(mcqPointer);
                        //Log.d("Current MCQ",current.mcq);
                        mcq.setText(current.mcq);
                        mcqNo.setText(mcqPointer+1 + "\\" + "220");
                        mcqPointer = mcqPointer + 1;

                        //LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php",current.mcq_id);
                        //LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php");

                        LoadMCQOptionFromLocalDB();
                        //LoadCorrectMCQOptionFromLocalDB();

                    }else if(userskipped){
                        if(!skippedAttempt){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setMessage("Are you want to solve the skipped questions ? ");
                            alertDialogBuilder.setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            //LoadSkippedMCQS("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadSkippedMCQ.php",skippedMCQs.get(skipPointer).mcq_id);

                                            LoadSkippedMCQFromLocalDB();

                                            //skipPointer++;
                                            //mcq.setText(skippedMCQs.get(skipPointer).);
                                            skippedAttempt = true;
                                            Unanswered--;
                                        }
                                    });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    countDownTimer.cancel();
                                    Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                                    it.putExtra("true",String.valueOf(Correct));
                                    it.putExtra("false",String.valueOf(Incorrect));
                                    it.putExtra("skipped",String.valueOf(Unanswered));
                                    it.putExtra("marks",String.valueOf(correctMarks));
                                    startActivity(it);
                                    finish();
                                }
                            });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else{
                            if(sPointer < skippedMCQs.size()){
                                //LoadSkippedMCQS("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadSkippedMCQ.php",skippedMCQs.get(skipPointer).mcq_id);

                                LoadSkippedMCQFromLocalDB();
                                //skipPointer++;
                                Unanswered--;
                            }else{
                                countDownTimer.cancel();
                                Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                                it.putExtra("true",String.valueOf(Correct));
                                it.putExtra("false",String.valueOf(Incorrect));
                                it.putExtra("skipped",String.valueOf(Unanswered));
                                it.putExtra("marks",String.valueOf(correctMarks));
                                startActivity(it);
                                finish();
                            }
                        }
                    }else{
                        countDownTimer.cancel();
                        Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                        it.putExtra("true",String.valueOf(Correct));
                        it.putExtra("false",String.valueOf(Incorrect));
                        it.putExtra("skipped",String.valueOf(Unanswered));
                        it.putExtra("marks",String.valueOf(correctMarks));
                        startActivity(it);
                        finish();
                    }
                    for (int i = 0; i < optButton.length; i++) {
                        optButton[i].setEnabled(true);
                    }
                    skip.setEnabled(true);
                    //next.setEnabled(true);
                }
            },500);
        }
    }


    /*private View.OnClickListener nextButtonClickedListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < optButton.length; i++) {
                optButton[i].setEnabled(false);
                optButton[i].setBackgroundResource(R.drawable.textviewborder);
            }
            skip.setEnabled(false);
            next.setEnabled(false);
            if (!textViewClick && !skipButtonPressed)
            {
                Toast.makeText(TestActivity.this,"Please Tap Your Option", Toast.LENGTH_LONG).show();
                for (int i = 0; i < optButton.length; i++) {
                    optButton[i].setEnabled(true);
                }
                skip.setEnabled(true);
                next.setEnabled(true);
            }
            else
            {
                textViewClick = false;
                LoadCorrectAnswer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadCorrectAnswer.php",current.mcq_id);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < optButton.length; i++) {
                            if(optionSelected == i && !skipButtonPressed){
                                if(optButton[i].getText().toString().equals(correctValue)){
                                    correctMarks = correctMarks + 5;
                                    Correct = Correct + 1;
                                }else {
                                    correctMarks = correctMarks - 1;
                                    Incorrect = Incorrect + 1;
                                }
                            }
                        }
                        skipButtonPressed = false;
                        if(mcqPointer < list.size()){
                            current = list.get(mcqPointer);
                            //Log.d("Current MCQ",current.mcq);
                            mcq.setText(current.mcq);
                            mcqNo.setText(mcqPointer+1 + "\\" + "220");
                            mcqPointer = mcqPointer + 1;
                            LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php",current.mcq_id);
                        }else if(userskipped){
                            if(!skippedAttempt){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
                                alertDialogBuilder.setMessage("Are you want to solve the skipped questions ? ");
                                alertDialogBuilder.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                LoadSkippedMCQS("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadSkippedMCQ.php",skippedMCQs.get(skipPointer).mcq_id);
                                                skippedAttempt = true;
                                                Unanswered--;
                                            }
                                        });
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        countDownTimer.cancel();
                                        Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                                        it.putExtra("true",String.valueOf(Correct));
                                        it.putExtra("false",String.valueOf(Incorrect));
                                        it.putExtra("skipped",String.valueOf(Unanswered));
                                        it.putExtra("marks",String.valueOf(correctMarks));
                                        startActivity(it);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }else{
                                if(skipPointer < skippedMCQs.size()){
                                    LoadSkippedMCQS("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadSkippedMCQ.php",skippedMCQs.get(skipPointer).mcq_id);
                                    Unanswered--;
                                }else{
                                    countDownTimer.cancel();
                                    Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                                    it.putExtra("true",String.valueOf(Correct));
                                    it.putExtra("false",String.valueOf(Incorrect));
                                    it.putExtra("skipped",String.valueOf(Unanswered));
                                    it.putExtra("marks",String.valueOf(correctMarks));
                                    startActivity(it);
                                }
                            }
                        }else{
                            countDownTimer.cancel();
                            Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                            it.putExtra("true",String.valueOf(Correct));
                            it.putExtra("false",String.valueOf(Incorrect));
                            it.putExtra("skipped",String.valueOf(Unanswered));
                            it.putExtra("marks",String.valueOf(correctMarks));
                            startActivity(it);
                        }
                        for (int i = 0; i < optButton.length; i++) {
                            optButton[i].setEnabled(true);
                        }
                        skip.setEnabled(true);
                        next.setEnabled(true);
                    }
                },2000);
            }
        }
    };*/

    private void LoadMCQS(String url){

        String p = helper.getPointer();
        String id = helper.getPaperId();
        String c = helper.getCorrect();
        String in = helper.getIncorrect();
        String cMarks = helper.getCorrectMarks();
        String uAnswer = helper.getUnanswered();
        String uskip = helper.getUserSkiped();
        String sAttempt = helper.getSkipAttempt();
        String sPointer = helper.getSkipPointer();
        if(p.equals("") && id.equals("")){
            mcqPointer = 0;
            Correct = 0;
            Incorrect = 0;
            Unanswered = 0;
            correctMarks = 0;
            skipPointer = 0;
        }else{
            //list = new ArrayList<PaperData>();
            paperId = id;
            mcqPointer = Integer.parseInt(p);
            Correct = Integer.parseInt(c);
            Incorrect = Integer.parseInt(in);

            correctMarks = Integer.parseInt(cMarks);
            Unanswered = Integer.parseInt(uAnswer);
            userskipped = Boolean.parseBoolean(uskip);
            skippedAttempt = Boolean.parseBoolean(sAttempt);
            skipPointer = Integer.parseInt(sPointer);

            for(int i=0;i<Unanswered;i++ ){
                //Log.d("Usman",helper2.getID(String.valueOf(i)));
                listData = new PaperData();
                listData.mcq_id = helper2.getID(String.valueOf(i));
                skippedMCQs.add(listData);
            }

        }
        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        PaperData listData = new PaperData();
                        listData.mcq_id = jsonObject.getString("mcq_id");
                        listData.mcq = jsonObject.getString("mcq");
                        list.add(listData);
                        //Log.d("MCQ",jsonObject.getString("mcq_id") + " " + jsonObject.getString("mcq"));
                    }
                    current = list.get(mcqPointer);
                    mcq.setText(current.mcq);
                    mcqNo.setText(mcqPointer+1 + "\\" + "220");
                    mcqPointer = mcqPointer + 1;
                    //Log.d("testLog" , current.mcq_id);

                    /*Thread timer= new Thread()
                    {
                        public void run()
                        {
                            try
                            { sleep(2000); }
                            catch (InterruptedException e)
                            { e.printStackTrace(); }
                            finally
                            {LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php",current.mcq_id);}
                        }
                    };
                    timer.start();*/
                    //LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php",current.mcq_id);

                    //LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php");
                    LoadMCQOptions(requestURL.loadMcqOptionURL);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(signInActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("paper_id",paperId);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void LoadMCQOptions(String url){

        //list2 = new ArrayList<PaperData>();
        //optionPointer = 0;
        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        optionNo[i] = jsonObject.getString("opt_no");
                        String id = jsonObject.getString("mcq_id");
                        String option = jsonObject.getString("option_name");
                        /*listData = new PaperData();
                        listData.option_name = option;
                        list2.add(listData);*/
                        optionHelper.saveMCQoptions(optionNo[i],id,option);
                        //optionHelper.saveMCQoptions(id,option);
                        //Log.d("TestOption",id + " " + option);
                    }

                    /*for (int i = 0; i < optButton.length; i++) {
                        optButton[i] = (Button) findViewById(buttonIDs[i]);
                        optButton[i].setBackgroundResource(R.drawable.textviewborder);
                        optButton[i].setText(list2.get(optionPointer).option_name);
                        optButton[i].setOnClickListener((View.OnClickListener) TestActivity.this);
                        optionPointer = optionPointer + 1 ;
                    }*/

                    Log.d("getTest",optionHelper.getTestData());

                    LoadMCQOptionFromLocalDB();
                    //LoadCorrectAnswer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadCorrectAnswer.php",current.mcq_id);

                    //LoadCorrectAnswer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadCorrectAnswer.php");
                    LoadCorrectAnswer(requestURL.loadCorrectAnswerURL);
                    //current = list2.get(0);
                    //opt1.setText(current.option_name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(signInActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        })/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mcq_id",mcqID);
                return params;
            }
        }*/;
        requestQueue.add(stringRequest);
    }


    private void LoadCorrectAnswer(String url){
        //final String[] correctOption = new String[1];
        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    /*jsonObject = jsonArray.getJSONObject(0);
                    correctValue = jsonObject.getString("option_name");
                    Log.d("Correct MCQ",correctValue);*/
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("mcq_id");
                        String option = jsonObject.getString("option_name");
                        //correctValue = option;
                        correctHelper.saveCorrectMCQoption(id,option);
                        Log.d("TestOption",id + " " + option);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(signInActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        })/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mcq_id",mcqID);
                return params;
            }
        }*/;
        requestQueue.add(stringRequest);
        //return correctOption[0];
    }


    private void LoadMCQOptionFromLocalDB(){
        list2 = new ArrayList<PaperData>();
        optionPointer = 0;

        for (int i = 0; i < 4; i++) {
            //String option = optionHelper.getMCQOption(String.valueOf(i+1),current.mcq_id);
            String option = optionHelper.getMCQOption(optionNo[i],current.mcq_id);
            //Log.d("loadTest",optionNo[i] + " " + option);
            listData = new PaperData();
            listData.option_name = option;
            list2.add(listData);
        }

        for (int i = 0; i < optButton.length; i++) {
            optButton[i] = (Button) findViewById(buttonIDs[i]);
            optButton[i].setBackgroundResource(R.drawable.textviewborder);
            optButton[i].setText(list2.get(optionPointer).option_name);
            optButton[i].setOnClickListener((View.OnClickListener) TestActivity.this);
            optionPointer = optionPointer + 1 ;
        }
    }

    private void LoadCorrectMCQOptionFromLocalDB(){
        correctValue = correctHelper.getCorrectMCQOption(current.mcq_id);
    }


    //LoadSkippedMCQS("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadSkippedMCQ.php",skippedMCQs.get(skipPointer).mcq_id);

    private void LoadSkippedMCQS(String url, final String mcq_id){

        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    //for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(0);
                    //PaperData listData = new PaperData();
                    //listData.mcq_id = jsonObject.getString("mcq_id");
                    //listData.mcq = jsonObject.getString("mcq");
                    //skippedMCQs.add(listData);
                    //Log.d("MCQ",jsonObject.getString("mcq_id") + " " + jsonObject.getString("mcq"));
                    mcq.setText(jsonObject.getString("mcq"));
                    //}
                    current = skippedMCQs.get(skipPointer);
                    skipPointer++;
                    //mcq.setText(current.mcq);
                    //mcqPointer = mcqPointer + 1;

                    //LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php",mcq_id);

                    //LoadMCQOptions("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadMCQOptions.php");
                    LoadMCQOptions(requestURL.loadMcqOptionURL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(signInActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mcq_id",mcq_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void LoadSkippedMCQFromLocalDB() {

        mcq.setText(skipHelper.getSkipMCQ(String.valueOf(sPointer+1)));
        Log.d("test2",skipHelper.getSkipMCQ(String.valueOf(sPointer+1)));
        current.mcq_id = skipHelper.getSkipMCQid(String.valueOf(sPointer+1));
        LoadMCQOptionFromLocalDB();
        sPointer++;
    }



    public void CountdownTimer() {
        String t = helper.getTimer();
        if(t.equals("") || t == null){
            totalTime = 9000000;
        }else{
            totalTime = Long.parseLong(t);

        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countDownTimer = new CountDownTimer(totalTime, 1000) {

                    @SuppressLint("DefaultLocale")
                    public void onTick(long millisUntilFinished) {
                        //secondsLeft = millisUntilFinished / 1000;
                        //timer.setText(String.valueOf(secondsLeft));
                        TimeLeft = millisUntilFinished;
                        timer.setText(String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(millisUntilFinished)%60,
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60));
                    }
                    public void onFinish() {
                        Intent it = new Intent(TestActivity.this,TestScoreActivity.class);
                        it.putExtra("true",String.valueOf(Correct));
                        it.putExtra("false",String.valueOf(Incorrect));
                        it.putExtra("skipped",String.valueOf(Unanswered));
                        startActivity(it);
                        finish();
                    }
                }.start();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        textViewClick = true;
        for(int i=0;i<4;i++){
            if(optButton[i].getId() == view.getId()){
                optButton[i].setBackgroundResource(R.drawable.textvieworangebackground);
                optionSelected = i;
                showNextMCQ();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
        //alertDialogBuilder.setMessage("Are you Sure , You want to Quit your Test ?");
        alertDialogBuilder.setMessage(R.string.quitMessage);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        helper.deleteTimer();
                        helper.deleteMCQHelper();
                        helper.deleteCorrectOptionHelper();
                        helper.deleteSkipedMCQHelper();
                        helper2.deleteID();
                        helper2.deleteIncorrectMCQS();

                        //Toast.makeText(TestActivity.this,String.valueOf(TimeLeft),Toast.LENGTH_LONG).show();
                        //Intent it = new Intent(TestActivity.this,MainActivity.class);
                        //startActivity(it);

                        finish();
//
                    }
                });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                /*Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);*/

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    /*@Override
    public void onClick(View view) {
        textViewClick = true;
        //skip.setEnabled(false);
        for(int i=0;i<4;i++){

            if(optButton[i].getId() == view.getId()){
                    optButton[i].setBackgroundResource(R.drawable.textvieworangebackground);
                    if(alreadySelected != -1 && alreadySelected != i){
                        optButton[alreadySelected].setBackgroundResource(R.drawable.textviewborder);
                    }
                    alreadySelected = i;

                /*if(optButton[i].getText().toString().equals(correctValue)){
                    correctMarks = correctMarks + 5;
                    Correct = Correct + 1;
                    optButton[i].setBackgroundResource(R.drawable.textviewgreenbackground);
                }else{
                    correctMarks = correctMarks - 1;
                    Incorrect = Incorrect + 1;
                    optButton[i].setBackgroundResource(R.drawable.textviewredbackground);
                }*/
            //}//(else { optButton[i].setOnClickListener(null); }

            //}
        //}

}



