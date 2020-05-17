package com.lokilabs.youtubeapi.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.lokilabs.youtubeapi.listArray.ListContentProvider;
import com.lokilabs.youtubeapi.listArray.QueryDetails;

import java.util.ArrayList;

public class VideoDataLoader extends AsyncTaskLoader<ArrayList<ListContentProvider>> {

    String mUrl;

    public VideoDataLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public ArrayList<ListContentProvider> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        ArrayList<ListContentProvider> arrayList = QueryDetails.extractJsonResponseVideos(mUrl);
        return arrayList;
    }
}
