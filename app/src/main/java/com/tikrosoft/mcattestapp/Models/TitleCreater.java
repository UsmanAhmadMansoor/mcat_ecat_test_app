package com.tikrosoft.mcattestapp.Models;

import android.content.Context;
import android.util.Log;

import com.tikrosoft.mcattestapp.myDBSecondAdapter;

import java.util.ArrayList;
import java.util.List;

public class TitleCreater {

    static TitleCreater titleCreater;
    List<TitleParent> titleParents;
    //int Incorrect;
    myDBSecondAdapter helper2;

    public TitleCreater(Context context,int incorrect) {
        helper2 = new myDBSecondAdapter(context);
        titleParents = new ArrayList<>();
        for(int i = 0 ;i<incorrect ;i++){
            //TitleParent title = new TitleParent(String.format("Caller #%d",i));
            TitleParent title = new TitleParent(helper2.getIncorrectMCQ(String.valueOf(i+1)));
            titleParents.add(title);
        }
    }

    public static TitleCreater get(Context context,int incorrect){
        //if(titleCreater == null){
            titleCreater = new TitleCreater(context,incorrect);
        //}
        return titleCreater;
    }

    public List<TitleParent> getAll(){
        return titleParents;
    }
}



