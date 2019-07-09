package com.example.garage_owner.model.HazemModel;

import android.util.Log;


import com.example.garage_owner.view.HazemView.LiveCameraFragment;

import org.videolan.libvlc.MediaPlayer;

import java.lang.ref.WeakReference;

public class MyPlayerListener implements MediaPlayer.EventListener {

    private static String TAG = "PlayerListener";
    private WeakReference<LiveCameraFragment> mOwner;


    public MyPlayerListener(LiveCameraFragment owner) {
        mOwner = new WeakReference<LiveCameraFragment>(owner);
    }

    @Override
    public void onEvent(MediaPlayer.Event event) {
        LiveCameraFragment player = mOwner.get();

        switch(event.type) {
            case MediaPlayer.Event.EndReached:
                Log.d(TAG, "MediaPlayerEndReached");
                player.releasePlayer();
                break;
            case MediaPlayer.Event.Playing:
            case MediaPlayer.Event.Paused:
            case MediaPlayer.Event.Stopped:
            default:
                break;
        }
    }
}
