package com.tikrosoft.mcattestapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.tikrosoft.mcattestapp.Models.TitleChild;
import com.tikrosoft.mcattestapp.Models.TitleParent;
import com.tikrosoft.mcattestapp.R;
import com.tikrosoft.mcattestapp.ViewHolders.TitleChildViewHolder;
import com.tikrosoft.mcattestapp.ViewHolders.TitleParentViewHolder;

import java.util.List;

public class MyAdapter extends ExpandableRecyclerAdapter<TitleParentViewHolder, TitleChildViewHolder> {

    LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TitleParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_parent,viewGroup,false);
        return new TitleParentViewHolder(v);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.layout_child,viewGroup,false);
        return new TitleChildViewHolder(v);
    }

    @Override
    public void onBindParentViewHolder(TitleParentViewHolder titleParentViewHolder, int i, Object o) {
        TitleParent title = (TitleParent) o;
        titleParentViewHolder.textView.setText(title.getTitle());
    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder titleChildViewHolder, int i, Object o) {
        TitleChild title = (TitleChild) o;
        titleChildViewHolder.opt1.setText(title.getOption1());
        titleChildViewHolder.opt2.setText(title.getOption2());
    }
}




