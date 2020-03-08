package com.tikrosoft.mcattestapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.tikrosoft.mcattestapp.R;

public class TitleChildViewHolder extends ChildViewHolder {

    public TextView opt1;
    public TextView opt2;

    public TitleChildViewHolder(View itemView) {
        super(itemView);

        opt1 = (TextView) itemView.findViewById(R.id.opt1);
        opt2 = (TextView) itemView.findViewById(R.id.opt2);
    }
}

