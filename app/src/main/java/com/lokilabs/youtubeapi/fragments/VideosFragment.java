package com.lokilabs.youtubeapi.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lokilabs.youtubeapi.R;
import com.lokilabs.youtubeapi.adapter.VideoAdapter;
import com.lokilabs.youtubeapi.listArray.ListContentProvider;
import com.lokilabs.youtubeapi.loaders.VideoDataLoader;

import java.util.ArrayList;
import java.util.Objects;

public class VideosFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ListContentProvider>> {

    public VideosFragment(){

    }

    private static String GOOGLE_YOUTUBE_API_KEY = "AIzaSyCVHKywG8pSHTkhRMjjF1pfYAYuH_vaGB4";
    private static String CHANNEL_ID = "UC-STjSPGtBGASVZ5BrdEUMQ";
    private static String VIDEO_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=leagueoflegends&type=video&videoDefinition=high&maxResults=50&key=AIzaSyCVHKywG8pSHTkhRMjjF1pfYAYuH_vaGB4";

    RecyclerView recyclerView;
    public ArrayList<ListContentProvider> mlist = null;
    public VideoAdapter adapter = null;
    static String jsonResponseFinal = null;
    View view;
    TextView emptyText;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel, container, false);

        emptyText = (TextView)view.findViewById(R.id.empty_view);
        progressBar = (ProgressBar)view.findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null, this).forceLoad();
        }
        else{
            emptyText.setText("NO Internet Connection");
            progressBar.setVisibility(View.GONE);
        }

        return view;
    }

    public void updateUi(ArrayList<ListContentProvider> flist){
        adapter = new VideoAdapter(getActivity(), flist);
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        return new VideoDataLoader(Objects.requireNonNull(getContext()), VIDEO_GET_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<ListContentProvider>> loader, ArrayList<ListContentProvider> listContentProviders) {
        progressBar.setVisibility(View.GONE);

        if(listContentProviders.isEmpty()){
            emptyText.setText("No videos available");
        }
        updateUi(listContentProviders);
        mlist = listContentProviders;
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        try {
            mlist.clear();
        }catch(NullPointerException e){}
    }

}