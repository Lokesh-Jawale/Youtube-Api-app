package com.lokilabs.youtubeapi.adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewholder> {

    ArrayList<ListContentProvider> mlist = null;
    Context mContext;

    public PlayListAdapter(Context mContext, ArrayList<ListContentProvider> mlist){
        this.mContext = mContext;
        this.mlist = mlist;
    }

    public class PlayListViewholder extends RecyclerView.ViewHolder{

        TextView textViewTitle1;
        TextView textViewDes1;
        ImageView imageThamb1;
        LinearLayout parentLayout1;

        public PlayListViewholder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle1 = (TextView)itemView.findViewById(R.id.textViewTitle1);
            this.textViewDes1 = (TextView)itemView.findViewById(R.id.textViewDes1);
            this.imageThamb1 = (ImageView)itemView.findViewById(R.id.imageThumb1);
        }

    }

    @NonNull
    @Override
    public PlayListViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playlist_layout, viewGroup, false);

        return new PlayListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewholder playListViewholder, int i) {

        TextView title = playListViewholder.textViewTitle1;
        TextView des = playListViewholder.textViewDes1;
        ImageView imageThumb = playListViewholder.imageThamb1;

        ListContentProvider listContentProvider = mlist.get(i);

        if(listContentProvider.getDescription().length() > 50){
            listContentProvider.setDesText(listContentProvider.getDescription().substring(0,50).concat(" ..."));
        }

        title.setText(listContentProvider.getTitleText());
        des.setText(listContentProvider.getDesText());
        Picasso.with(mContext).load(listContentProvider.getThumbnail()).into(imageThumb);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
