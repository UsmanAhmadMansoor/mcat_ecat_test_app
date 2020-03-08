package com.tikrosoft.mcattestapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaperRecyclerViewAdapter extends RecyclerView.Adapter<PaperRecyclerViewAdapter.ViewHolder> {

    List<PaperData> data;
    PaperData current;
    Context context;
    int paperNo = 1;

    public PaperRecyclerViewAdapter(Context context, List<PaperData> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.paper_recycler_view_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PaperRecyclerViewAdapter.ViewHolder holder, final int position) {

        current=data.get(position);
        holder.paperItem.setText(String.valueOf(paperNo));
        paperNo++ ;
        holder.paperItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current=data.get(position);
                Intent it = new Intent(context,TestActivity.class);
                it.putExtra("paperId",current.paper_id);
                context.startActivity(it);

                //((PapersActivity)context).finish();
            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button paperItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            paperItem = (Button) itemView.findViewById(R.id.paperItem);

        }
    }

}

