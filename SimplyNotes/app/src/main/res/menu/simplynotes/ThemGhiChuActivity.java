package com.example.thanhtung.simplynotes;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class ThemGhiChuActivity extends AppCompatActivity {
    EditText edtTieuDe, edtNoiDung, edtNgayNhacNho,edtGioNhacNho;
    Switch switchBaoThuc;
    Button btnThem;
     String mauChu="#000000";
     String mauNen="#fafafa";

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    AlarmManager alarmManager;
    Intent intent,intentBaoThuc;
    PendingIntent pendingIntent;

    int iyear, imonth, iday;
    int ihour, iminute;

    SQLiteDatabaseHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themghichu);
        getSupportActionBar().hide();
        initview();
    }
    private void initview() {
        edtTieuDe = (EditText) findViewById(R.id.edtTieuDe);
        edtNoiDung = (EditText) findViewById(R.id.edtNoiDung);
        edtNgayNhacNho = (EditText) findViewById(R.id.edtNgayNhacNho);
        edtGioNhacNho=(EditText) findViewById(R.id.edtGioNhacNho);
        btnThem = (Button) findViewById(R.id.btnThem);
        switchBaoThuc=(Switch) findViewById(R.id.switchBaoThuc);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); //truy cập vào hệ thống của máy
        intentBaoThuc = new Intent(ThemGhiChuActivity.this, AlarmReceiver.class);

        switchBaoThuc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {

                    intentBaoThuc.putExtra("extra", "on");
                   // pendingIntent = PendingIntent.getBroadcast(ThemGhiChuActivity.this, getIntent().getIntExtra("LENGTH", -1) + 1, intentBaoThuc, 0);
                     //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                else
                {
                    pendingIntent = PendingIntent.getBroadcast(ThemGhiChuActivity.this, getIntent().getIntExtra("LENGTH", -1) + 1, intentBaoThuc, 0);
                    alarmManager.cancel(pendingIntent);
                    intentBaoThuc.putExtra("extra", "off");
                    sendBroadcast(intentBaoThuc);
                }
            }
        });
        EventEdittext();
       // edtGioNhacNho.setText(String.valueOf(ihour)+":"+String.valueOf(iminute)+":00");
        //edtNgayNhacNho.setText(String.valueOf(iday)+":"+String.valueOf(imonth)+":"+String.valueOf(iyear));

        calendar = calendar.getInstance();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date ngayHienTai = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                GhiChu ghiChu = new GhiChu(getIntent().getIntExtra("LENGTH", -1) + 1,
                        edtTieuDe.getText().toString(),
                        edtNoiDung.getText().toString(),
                        mauNen,
                        mauChu,
                        "personal",
                        ft.format(ngayHienTai).toString(),
                        edtNgayNhacNho.getText().toString(),
                        ihour+":"+iminute,
                        ft.format(ngayHienTai).toString());

                Intent intent = new Intent(ThemGhiChuActivity.this, MainActivity.class);
                if (switchBaoThuc.isChecked()) {
                    if (edtNgayNhacNho.getText().length() == 0 || edtGioNhacNho.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thời gian báo thức hoặc tắt báo thức để thêm", Toast.LENGTH_LONG).show();
                    } else {

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(iyear, imonth, iday, ihour, iminute, 0);
                        intentBaoThuc.putExtra("TieuDe", ghiChu.getTieuDe());
                        intentBaoThuc.putExtra("NoiDung", ghiChu.getNoiDung());
                        intentBaoThuc.putExtra("ID", ghiChu.getId());

                        pendingIntent = PendingIntent.getBroadcast(ThemGhiChuActivity.this, getIntent().getIntExtra("LENGTH", -1) + 1, intentBaoThuc, 0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        intent.putExtra("ADD", (Serializable) ghiChu);
                        setResult(100, intent);
                        finish();
                    }

                }
                else {
                    ghiChu.setGioNhacNho("");
                    ghiChu.setNgayNhacNho("");
                    intent.putExtra("ADD", (Serializable) ghiChu);
                    setResult(100, intent);
                    finish();
                }
            }
        })
        ;

        Button btnTest=findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(ThemGhiChuActivity.this);
                final ArrayList<String> colors=new ArrayList<>();
                colors.add("#F0F8FF");
                colors.add("#F5D1CE");
                colors.add("#A8FFD4");
                colors.add("#FFF8F0");
                colors.add("#E6E6E6");

                colorPicker.setColors(colors).setColumns(5)
                        .setRoundColorButton(true).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        mauNen=colors.get(position);
                    }

                    @Override
                    public void onCancel() {
                        //Toast.makeText(getBaseContext(),"Canceled",Toast.LENGTH_SHORT).show();

                    }
                }).setDefaultColorButton(Color.parseColor(mauNen)).show();
            }
        });
    }
    public void openColorPicker()
    {

    }
    private void EventEdittext() {
        edtNgayNhacNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iyear = calendar.get(Calendar.YEAR);
                imonth = calendar.get(Calendar.MONTH);
                iday = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ThemGhiChuActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgayNhacNho.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        iyear = year;
                        imonth = month;
                        iday = dayOfMonth;
                    }
                }, iyear, imonth, iday);
                datePickerDialog.show();
            }
        });

        edtGioNhacNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ihour = calendar.get(Calendar.HOUR_OF_DAY);
                iminute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(ThemGhiChuActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtGioNhacNho.setText(ConvertTime(hourOfDay, minute));
                        ihour = hourOfDay;
                        iminute = minute;
                    }
                }, ihour, iminute, true);
                timePickerDialog.show();
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
}