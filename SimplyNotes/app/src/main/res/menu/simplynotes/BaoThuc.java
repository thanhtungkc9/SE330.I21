package com.example.thanhtung.simplynotes;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by ThanhTung on 31-May-18.
 */

public class BaoThuc extends AppCompatActivity {

    Dialog dialog;

    EditText etxtDate, etxtTime;
    Button btnBaoThuc, btnHengio, btnDunglai;
    TextView txtHienthi;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;

    int iyear, imonth, iday;
    int ihour, iminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themghichu);
    }

    private void EventEdittext() {
        //Chọn ngày đặt báo thức
        etxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(BaoThuc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etxtDate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        iyear = year;
                        imonth = month;
                        iday = dayOfMonth;
                    }
                }, iyear, imonth, iday);
                datePickerDialog.show();
            }
        });
        //Chọn giờ đặt báo thức
        etxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(BaoThuc.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etxtTime.setText(ConvertTime(hourOfDay, minute));
                        ihour = hourOfDay;
                        iminute = minute;
                    }
                }, ihour, iminute, true);
                timePickerDialog.show();
            }
        });
    }

    private void EventButton() {
        //Đồng ý đặt báo thức
        btnHengio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(iyear, imonth, iday, ihour, iminute);
//                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
//                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                //Tồn tại xuyên suốt ứng dụng kể cả khi tắt
                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(BaoThuc.this, 1, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                txtHienthi.setText("Giờ bạn đặt là: " + etxtTime.getText() + " ngày " + etxtDate.getText());
            }
        });

        //Dừng báo thức
        btnDunglai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHienthi.setText("Dừng lại");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
                dialog.dismiss();
            }
        });
    }

    String ConvertTime(int hour, int minute){
        int hourTemp = hour;
        if(hourTemp >= 12){
            hourTemp = hourTemp - 12;
        }
        String Strhour = String.valueOf(hourTemp);
        String Strminute = String.valueOf(minute);
        if(minute < 10){
            Strminute = "0" + Strminute;
        }
        return Strhour + ":" + Strminute + (hour >= 12? " PM" : " AM");
    }


    private void Anhxa() {
        etxtDate = (EditText) dialog.findViewById(R.id.edittextdate);
        etxtTime = (EditText) dialog.findViewById(R.id.edittexttime);
        btnHengio = (Button) dialog.findViewById(R.id.btnHengio);
        btnDunglai = (Button) dialog.findViewById(R.id.btnDunglai);
        txtHienthi = (TextView) dialog.findViewById(R.id.txtHienthi);

        calendar = calendar.getInstance();
        iyear = calendar.get(Calendar.YEAR);
        imonth = calendar.get(Calendar.MONTH);
        iday = calendar.get(Calendar.DAY_OF_MONTH);
        ihour = calendar.get(Calendar.HOUR_OF_DAY);
        iminute = calendar.get(Calendar.MINUTE);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); //truy cập vào hệ thống của máy
        intent = new Intent(BaoThuc.this, AlarmReceiver.class);
    }
}
