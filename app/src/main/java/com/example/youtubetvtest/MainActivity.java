package com.example.youtubetvtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;


public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;

    private String videoUrl =
    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";


    @Override
    protected void onCreate(Bundle b){

        super.onCreate(b);

        setContentView(R.layout.activity_main);

        PlayerView view =
        findViewById(R.id.player_view);


        player =
        new ExoPlayer.Builder(this).build();


        view.setPlayer(player);


        player.setMediaItem(
        MediaItem.fromUri(videoUrl));


        player.prepare();
        player.play();

    }


    @Override
    protected void onStop(){

        super.onStop();

        if(player!=null)
            player.release();

    }

}
