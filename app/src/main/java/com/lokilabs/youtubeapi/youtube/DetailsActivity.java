package com.lokilabs.youtubeapi.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lokilabs.youtubeapi.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView titleText;
    TextView desText;
    ImageView thumbnail, playButton;
    ImageButton backButton;

    String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleText = (TextView)findViewById(R.id.title_text);
        desText = (TextView)findViewById(R.id.des_text);
        thumbnail = (ImageView)findViewById(R.id.video_image);
        backButton = (ImageButton)findViewById(R.id.back_button);
        playButton = (ImageView) findViewById(R.id.play_video_button);

        if((getIntent().hasExtra("title_text"))&&(getIntent().hasExtra("des_text"))&&(getIntent().hasExtra("thumbnail_url"))){
            titleText.setText(getIntent().getStringExtra("title_text"));
            desText.setText(getIntent().getStringExtra("des_text"));

            try {
                Picasso.with(this).load(getIntent().getStringExtra("thumbnail_url")).into(thumbnail);
            }catch (Exception e){
                Log.d("aDebugTag",e.getMessage());
            }

            videoId = getIntent().getStringExtra("videoId");
        }
    }



    public void playVideo(View view) {
        Intent intent = new Intent(DetailsActivity.this, VideoPlayActivity.class);
        intent.putExtra("videoId",videoId);
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }
}
