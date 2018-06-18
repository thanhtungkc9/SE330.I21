package com.example.thanhtung.simplynotes;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ThanhTung on 31-May-18.
 */

public class Music extends Service {
    MediaPlayer mediaPlayer;
    int id;
    int BaoThucid;
    String tieuDe,noiDung;
    int iD;
    private Dialog dialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Tôi trong Music", "Xin chào");
        //Nhận id từ AlarmReceiver
        BaoThucid=startId;
        String key = intent.getExtras().getString("extra");
        Log.e("Music nhận key", key + "");
        tieuDe=intent.getExtras().getString("TieuDe");
        noiDung=intent.getExtras().getString("NoiDung");
        iD=intent.getExtras().getInt("ID");
        if(key.equals("on")){
            id = 1;
        }else if(key.equals("off")){
            id = 0;
        }

        if(id == 1) {
            Log.e("ID", id + "");
            mediaPlayer = MediaPlayer.create(this, R.raw.nhacchuong);
            Toast.makeText(getApplicationContext(),"Bao thuc",Toast.LENGTH_LONG).show();
            mediaPlayer.start();
            showDialog();
            id = 0;
        }else if(id == 0){
            Log.e("ID", id + "");
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
        return START_NOT_STICKY;
    }

    public void showDialog() {

        Intent i = new Intent();
        i.putExtra("TieuDe", tieuDe);
        i.putExtra("NoiDung", noiDung);
        i.setClass(this, BaoThucDialog.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }
}
