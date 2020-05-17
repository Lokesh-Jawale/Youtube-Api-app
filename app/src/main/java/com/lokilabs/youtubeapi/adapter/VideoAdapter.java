package com.lokilabs.youtubeapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lokilabs.youtubeapi.R;
import com.lokilabs.youtubeapi.listArray.ListContentProvider;
import com.lokilabs.youtubeapi.youtube.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<ListContentProvider> mlist;

    public VideoAdapter(Context mContext, ArrayList<ListContentProvider> mlist){
        this.mlist = mlist;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewDes;
        TextView textViewDate;
        ImageView imageThamb;
        LinearLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            this.textViewDes = (TextView) itemView.findViewById(R.id.textViewDes);
            this.textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            this.imageThamb = (ImageView) itemView.findViewById(R.id.imageThumb);
            this.parentLayout = (LinearLayout)itemView.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_post_layout, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        TextView textViewTitle = viewHolder.textViewTitle;
        TextView textViewDes = viewHolder.textViewDes;
        TextView textViewDate = viewHolder.textViewDate;
        ImageView imageThamb = viewHolder.imageThamb;

        final ListContentProvider listContentProvider = mlist.get(i);

        if(listContentProvider.getDescription().length() > 100){
            listContentProvider.setDesText(listContentProvider.getDescription().substring(0,50).concat(" ..."));
        }

        textViewTitle.setText(listContentProvider.getTitleText());
        textViewDate.setText(listContentProvider.getDateText());
        textViewDes.setText(listContentProvider.getDesText());

        //TODO: download image through url
        Picasso.with(mContext).load(listContentProvider.getThumbnail()).into(imageThamb);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("title_text",listContentProvider.getTitleText());
                intent.putExtra("des_text",listContentProvider.getDescription());
                intent.putExtra("thumbnail_url",listContentProvider.getThumbnail());
                intent.putExtra("videoId",listContentProvider.getVideoId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
