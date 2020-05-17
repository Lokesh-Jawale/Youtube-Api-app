package com.lokilabs.youtubeapi.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lokilabs.youtubeapi.R;
import com.lokilabs.youtubeapi.adapter.PlayListAdapter;
import com.lokilabs.youtubeapi.listArray.ListContentProvider;
import com.lokilabs.youtubeapi.listArray.QueryDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlayListFragment extends Fragment {

    String jsonResponse = "";

    final private static String YOUTUBE_API_KEY = "AIzaSyCVHKywG8pSHTkhRMjjF1pfYAYuH_vaGB4";
    String PLAYLIST_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=leagueoflegends&type=playlist&maxResults=20&key=AIzaSyCVHKywG8pSHTkhRMjjF1pfYAYuH_vaGB4";

    View view;
    TextView emptyText;

    public PlayListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_playlist, container, false);

        emptyText = (TextView)view.findViewById(R.id.empty_view);

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            PlayListBackgroundTask playListBackgroundTask = new PlayListBackgroundTask();
            playListBackgroundTask.execute();
        }
        else{
            emptyText.setText("NO Internet Connection");
        }

        return view;
    }

    void initiateList(){
        ArrayList<ListContentProvider> arrayList = QueryDetails.extractJsonResponsePlayList(jsonResponse);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_playlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PlayListAdapter playListAdapter = new PlayListAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(playListAdapter);
    }

    class PlayListBackgroundTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            initiateList();
        }

        @Override
        protected String doInBackground(Void... voids) {

            jsonResponse = getJsonResponse();

            return null;
        }

        private String getJsonResponse() {
            StringBuilder jsonResponse1 = new StringBuilder();

            URL url = null;
            try{
                url = new URL(PLAYLIST_GET_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;

            if(url != null) {
                try {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setReadTimeout(15000);
                    httpURLConnection.connect();

                    inputStream = httpURLConnection.getInputStream();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    try {
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            jsonResponse1.append(line);
                            line = bufferedReader.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return jsonResponse1.toString();
        }

//        private String getJsonResponseId() {
//            StringBuilder playlistId1 = new StringBuilder();
//
//            URL url = null;
//            try{
//                url = new URL(PLAYLIST_ID_URL);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            HttpURLConnection httpURLConnection = null;
//            InputStream inputStream = null;
//
//            if(url != null) {
//                try {
//                    httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.setConnectTimeout(10000);
//                    httpURLConnection.setReadTimeout(15000);
//                    httpURLConnection.connect();
//
//                    inputStream = httpURLConnection.getInputStream();
//
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                    try {
//                        String line = bufferedReader.readLine();
//                        while (line != null) {
//                            playlistId1.append(line);
//                            line = bufferedReader.readLine();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return playlistId1.toString();
//        }
    }

}

