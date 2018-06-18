package com.example.thanhtung.simplynotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ThanhTung on 17-Jun-18.
 */

public class BaoThucDialog extends AppCompatActivity {
    EditText noiDung,tieuDe;
    Button btnTatBaoThuc;
    int iD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_baothuc);
        noiDung=findViewById(R.id.edtSuaNoiDungg);
        tieuDe=findViewById(R.id.edtTieuDee);
        tieuDe.setText(getIntent().getExtras().getString("TieuDe"));
        noiDung.setText(getIntent().getExtras().getString("NoiDung"));
        btnTatBaoThuc=(Button) findViewById(R.id.btnTatBaoThuc);
        btnTatBaoThuc.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              Intent  intentBaoThuc = new Intent(BaoThucDialog.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), iD, intentBaoThuc, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                sendBroadcast(intentBaoThuc);
                intentBaoThuc.putExtra("extra", "off");
                sendBroadcast(intentBaoThuc);
                finish();
                System.exit(0);
            }
        });
    }

}
