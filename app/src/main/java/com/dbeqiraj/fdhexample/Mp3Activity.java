package com.dbeqiraj.fdhexample;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class Mp3Activity extends AppCompatActivity {

    private Button pause;
    private Button play;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private SeekBar seekBar;
    private double startTime = 0;
    private double endTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);

        pause = (Button) findViewById(R.id.pause);
        play = (Button) findViewById(R.id.play);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        handler = new Handler();

        String url = getIntent().getStringExtra("url");
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                endTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                seekBar.setMax((int) endTime);
                seekBar.setProgress((int)startTime);
                handler.postDelayed(UpdateSongTime,100);
                pause.setEnabled(true);
                play.setEnabled(false);
            }
        });

        pause.setEnabled(false);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int)startTime);
            handler.postDelayed(this, 100);
        }
    };
}
