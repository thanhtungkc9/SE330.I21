package com.example.thanhtung.simplynotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ThanhTung on 31-May-18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Tôi trong Receiver", "Xin chào");
        //Nhận id từ BaoThuc
        String chuoi_string = intent.getExtras().getString("extra");
        Log.e("Key", chuoi_string + "");
        String tieuDe,noiDung;
        int iD;
        tieuDe=intent.getExtras().getString("TieuDe");
        noiDung=intent.getExtras().getString("NoiDung");
        iD= intent.getExtras().getInt("ID");
        Log.e("GhiChuBaoThuc",tieuDe+"||"+noiDung+"||"+String.valueOf(iD));
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("extra", chuoi_string);
        myIntent.putExtra("TieuDe", tieuDe);
        myIntent.putExtra("NoiDung", noiDung);
        myIntent.putExtra("ID", iD);
        context.startService(myIntent);
    }
}
