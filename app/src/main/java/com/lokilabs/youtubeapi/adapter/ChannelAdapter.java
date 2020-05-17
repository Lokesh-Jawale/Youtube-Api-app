package com.lokilabs.youtubeapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lokilabs.youtubeapi.R;
import com.lokilabs.youtubeapi.listArray.ListContentProvider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> {

    Context mcontext;
    ArrayList<ListContentProvider> mlist;

    public ChannelAdapter(Context mcontext, ArrayList<ListContentProvider> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView channelTitle;
        TextView des;
        ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.channelTitle = (TextView)itemView.findViewById(R.id.textViewTitle2);
            this.des = (TextView)itemView.findViewById(R.id.textViewDes2);
            this.thumbnail = (ImageView)itemView.findViewById(R.id.imageThumb2);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.channel_layout, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TextView channelTitle = myViewHolder.channelTitle;
        TextView des = myViewHolder.des;
        ImageView imageView = myViewHolder.thumbnail;

        final ListContentProvider listContentProvider = mlist.get(i);

        if(listContentProvider.getDescription().length() > 100){
            listContentProvider.setDesText(listContentProvider.getDescription().substring(0,50).concat(" ..."));
        }

        channelTitle.setText(listContentProvider.getTitleText());
        des.setText(listContentProvider.getDesText());

        //TODO: download image through url
        Picasso.with(mcontext).load(listContentProvider.getThumbnail()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
