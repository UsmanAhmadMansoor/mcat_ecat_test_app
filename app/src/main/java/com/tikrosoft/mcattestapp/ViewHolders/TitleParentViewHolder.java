package com.tikrosoft.mcattestapp.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.tikrosoft.mcattestapp.R;

public class TitleParentViewHolder extends ParentViewHolder {

    public TextView textView;
    ImageButton imageButton;

    public TitleParentViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.parentId);
        imageButton = (ImageButton) itemView.findViewById(R.id.ib);


    }
}
