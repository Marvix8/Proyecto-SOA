package com.unlam.hologramhand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class VideoPlayer extends AppCompatActivity {

    private VideoView videoView;
    private MessageReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mMessageReceiver = new MessageReceiver();
        this.mMessageReceiver.setActivityContext(this);
        setContentView(R.layout.activity_video_player);
        this.videoView = (VideoView) findViewById(R.id.video_player);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.holograma);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("gesture-instruction"));

    }

    public VideoView getVideoView() {
        return videoView;
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
