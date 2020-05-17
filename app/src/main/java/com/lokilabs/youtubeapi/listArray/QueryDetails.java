package com.lokilabs.youtubeapi.listArray;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class QueryDetails {

    private static String title , date, des, thumbnail, videoid;
    private static ListContentProvider listContentProvider1;

    public static ArrayList<ListContentProvider> extractJsonResponse(String jsonResponse){

        //Creating a json ROOT Object
        ArrayList<ListContentProvider> arrayList = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonRootObject.getJSONArray("items");

            //only for my personal channel as it doesnt need
            for(int i = 0; i < 20;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObjectSnippet = jsonObject.getJSONObject("snippet");

                JSONObject jsonObjectId = jsonObject.getJSONObject("id");

                title = jsonObjectSnippet.getString("channelTitle");

                thumbnail = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                ListContentProvider listContentProvider = new ListContentProvider();
                listContentProvider.setTitleText(title);
                listContentProvider.setThumbnail(thumbnail);

                arrayList.add(listContentProvider);
//                listContentProvider1 = listContentProvider;
            }

            Log.d("aDebugTag", title+"    "+date+"    "+des);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

//    public static ListContentProvider getList() {
//        if(listContentProvider1 == null){
//            Log.e("aDebugTag","Empty LISTCONTENTPROVIDER RETURNed");
//        }
//        return listContentProvider1;
//    }

    public static ArrayList<ListContentProvider> extractJsonResponsePlayList(String jsonResponse){
        ArrayList<ListContentProvider> arrayList = new ArrayList<>();

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonRootObject.getJSONArray("items");

            for(int i=0; i < 4; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObjectSnippet = jsonObject.getJSONObject("snippet");

                title = jsonObjectSnippet.getString("title");
                des = jsonObjectSnippet.getString("description");
                thumbnail = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                ListContentProvider listContentProvider = new ListContentProvider();
                listContentProvider.setTitleText(title);
                listContentProvider.setDesText(des);
                listContentProvider.setThumbnail(thumbnail);

                arrayList.add(listContentProvider);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<ListContentProvider> extractJsonResponseVideos(String videosGetUrl) {

        URL url = null;
        try{
            url = new URL(videosGetUrl);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        videosGetUrl = makeHttpRequest(url);

        //Creating a json ROOT Object
        ArrayList<ListContentProvider> arrayList = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(videosGetUrl);
            JSONArray jsonArray = jsonRootObject.getJSONArray("items");

            //only for my personal channel as it doesnt need
            for(int i = 0; i < 20;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObjectSnippet = jsonObject.getJSONObject("snippet");

                JSONObject jsonObjectId = jsonObject.getJSONObject("id");
                videoid = jsonObjectId.getString("videoId");

                title = jsonObjectSnippet.getString("title");
                date = jsonObjectSnippet.getString("publishedAt");
                des = jsonObjectSnippet.getString("description");
                thumbnail = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                ListContentProvider listContentProvider = new ListContentProvider();
                listContentProvider.setTitleText(title);
                listContentProvider.setDateText(date);
                listContentProvider.setDesText(des);
                listContentProvider.setThumbnail(thumbnail);
                listContentProvider.setVideoId(videoid);

                arrayList.add(listContentProvider);
//                listContentProvider1 = listContentProvider;
            }

            Log.d("aDebugTag", title+"    "+date+"    "+des);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public static String makeHttpRequest(URL url){
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
    public static String readInputStream(InputStream inputStream){
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
}
