package com.lokilabs.youtubeapi.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lokilabs.youtubeapi.R;
import com.lokilabs.youtubeapi.adapter.ChannelAdapter;
import com.lokilabs.youtubeapi.adapter.VideoAdapter;
import com.lokilabs.youtubeapi.listArray.ListContentProvider;
import com.lokilabs.youtubeapi.listArray.QueryDetails;
import com.lokilabs.youtubeapi.youtube.DetailsActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ChannelFragment extends Fragment {

    private static String GOOGLE_YOUTUBE_API_KEY = "AIzaSyCVHKywG8pSHTkhRMjjF1pfYAYuH_vaGB4";
    private static String CHANNEL_ID = "UC-STjSPGtBGASVZ5BrdEUMQ";
    private static String Channel_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=leagueoflegends&type=video&videoDefinition=high&maxResults=50&key=AIzaSyCVHKywG8pSHTkhRMjjF1pfYAYuH_vaGB4";

    RecyclerView recyclerView;
    public ArrayList<ListContentProvider> mlist = null;
    public ChannelAdapter adapter = null;
    static String jsonResponseFinal = null;
    View view;
    TextView emptyText;

    public ChannelFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel, container, false);

        emptyText = (TextView)view.findViewById(R.id.empty_view);

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            GetData getData = new GetData();
            getData.execute();
        }
        else{
            emptyText.setText("NO Internet Connection");
        }
        return view;
    }

    public void initList(){
        Log.d("aDebugTag :",jsonResponseFinal);
        mlist = QueryDetails.extractJsonResponse(jsonResponseFinal);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean b) {
//
//            }
//        });
        adapter = new ChannelAdapter(getActivity(), mlist);
        recyclerView.setAdapter(adapter);
    }

    //Creating an AsyncTask (background Thread & main thread running class) for getting the data from youtube api

    private class GetData extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            initList();
        }

        @Override
        protected String doInBackground(Void... voids) {

            URL url = createUrl(Channel_GET_URL);

            jsonResponseFinal = makeHttpRequest(url);

            return null;
        }

        private URL createUrl(String Channel_GET_URL_){
            URL url = null;
            try{
                url = new URL(Channel_GET_URL_);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        private String makeHttpRequest(URL url){
            String jsonResponse = null;
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;

            try{
                //setup the httpUrlConnection
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();

                jsonResponse = readInputStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            return jsonResponse;
        }

        private String readInputStream(InputStream inputStream){
            StringBuilder finalString = new StringBuilder();

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try {
                    String line = bufferedReader.readLine();
                    while(line != null){
                        finalString.append(line);
                        //reads line until whole response is read
                        line = bufferedReader.readLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return finalString.toString();
        }
//        public void updateUi(String jsonResponse){
//            //Creating a json ROOT Object
//             ArrayList<ListContentProvider> arrayList = new ArrayList<>();
//
//            try {
//                JSONObject jsonRootObject = new JSONObject(jsonResponse);
//                JSONObject jsonObjectSnippet = jsonRootObject.getJSONObject("snippet");
//
//                String title = jsonObjectSnippet.getString("title");
//                String date = jsonObjectSnippet.getString("publishedAt");
//                String des = jsonObjectSnippet.getString("description");
//
//                ListContentProvider listContentProvider = new ListContentProvider();
//                listContentProvider.setTitleText(title);
//                listContentProvider.setDateText(date);
//                listContentProvider.setDesText(des);
//
//                arrayList.add(listContentProvider);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return arrayList;
//        }
    }
}

