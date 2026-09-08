package com.example.youtubetvtest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.stream.StreamInfo;

import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;

    private final String youtubeUrl =
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_view);

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);


        NewPipe.init(
                DownloaderImpl.init(
                        new OkHttpClient.Builder().build()
                )
        );


        extractYoutube();
    }


    private void extractYoutube() {

        new Thread(() -> {

            try {

                StreamInfo info =
                        StreamInfo.getInfo(
                                ServiceList.YouTube,
                                youtubeUrl
                        );


                List streams = info.getVideoStreams();


                if(streams == null || streams.size() == 0){

                    showError("Tidak ada stream");

                    return;
                }


                Object stream = streams.get(0);


                String url =
                        stream.getClass()
                        .getMethod("getContent")
                        .invoke(stream)
                        .toString();


                runOnUiThread(() -> {

                    player.setMediaItem(
                            MediaItem.fromUri(url)
                    );

                    player.prepare();
                    player.play();

                });


            } catch(Exception e){

                showError(
                        e.getClass().getSimpleName()
                        + "\n"
                        + e.getMessage()
                );

            }

        }).start();

    }


    private void showError(String msg){

        runOnUiThread(() -> {

            Toast.makeText(
                    this,
                    msg,
                    Toast.LENGTH_LONG
            ).show();

        });

    }


    @Override
    protected void onStop(){

        super.onStop();

        if(player != null)
            player.release();
    }
}
