package com.tikrosoft.mcattestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.tikrosoft.mcattestapp.Adapter.MyAdapter;
import com.tikrosoft.mcattestapp.Models.TitleChild;
import com.tikrosoft.mcattestapp.Models.TitleCreater;
import com.tikrosoft.mcattestapp.Models.TitleParent;

import java.util.ArrayList;
import java.util.List;

public class ShowAnswersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int incorrect;

    myDBSecondAdapter helper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_answers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Incorrect MCQ's with Answers");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        helper2 = new myDBSecondAdapter(this);

        Intent it = getIntent();
        incorrect = Integer.parseInt(it.getStringExtra("incorrect"));

        //Log.d("Inc", String.valueOf(incorrect));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter mAdapter = new MyAdapter(this,initData());
        mAdapter.setParentClickableViewAnimationDefaultDuration();
        mAdapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(mAdapter);
    }

    private List<ParentObject> initData() {
        TitleCreater titleCreater = TitleCreater.get(this,incorrect);
        List<TitleParent> titles = titleCreater.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        int i =1;
        for(TitleParent title:titles){
            List<Object> childlist = new ArrayList<>();
            //childlist.add(new TitleChild("Add to Contacts","Send Message"));
            childlist.add(new TitleChild(helper2.getIncorrectMCQOption(String.valueOf(i)),helper2.getCorrectMCQOption(String.valueOf(i))));
            //Log.d("usman",incorrect + " " + helper2.getIncorrectMCQOption(String.valueOf(i)) + " " + helper2.getCorrectMCQOption(String.valueOf(i)));
            title.setChildObjectList(childlist);
            parentObject.add(title);
            i++;
        }
        return parentObject;
    }

}

