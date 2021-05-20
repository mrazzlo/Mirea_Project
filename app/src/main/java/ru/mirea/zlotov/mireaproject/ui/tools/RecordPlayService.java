package ru.mirea.zlotov.mireaproject.ui.tools;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class RecordPlayService extends Service {
    private MediaPlayer media_player;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        try {
            media_player = new MediaPlayer();

        } catch (Exception e) {
            e.printStackTrace();
        }
        media_player.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            media_player.setDataSource(ToolsFragment.file_name);
            media_player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        media_player.start();
        return START_STICKY;
    }

    private void releasePlayer() {
        if (media_player != null) {
            media_player.release();
            media_player = null;
        }
    }

    @Override
    public void onDestroy() {
        media_player.stop();
    }
}