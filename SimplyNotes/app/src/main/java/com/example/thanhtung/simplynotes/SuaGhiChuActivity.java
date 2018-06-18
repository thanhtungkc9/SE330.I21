package com.example.thanhtung.simplynotes;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class SuaGhiChuActivity extends AppCompatActivity {
    EditText edtSuaTieuDe, edtSuaNoiDung, edtSuaGioNhacNho, edtSuaNgayNhacNho;
    Switch switchSuaBaoThuc;
    Button btnLuu, btnSuaMauNen;
    GhiChu ghiChu;
    String mauNen = "#ffffff";
    String mauChu = "#000000";

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    AlarmManager alarmManager;
    Intent intent, intentBaoThuc;
    PendingIntent pendingIntent;

    int iyear, imonth, iday;
    int ihour, iminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suaghichu);
        // Gọi phương thức ivitView và getData
        initView();
        getData();
    }
    // Khơi tạo getData
    private void getData() {
        try {
            if (getIntent().getExtras() != null) {
                ghiChu = (GhiChu) getIntent().getSerializableExtra("EDIT");
                mauNen=ghiChu.getMauNen();
                mauChu=ghiChu.getMauChu();
                int a = ghiChu.getId();
                edtSuaTieuDe.setText(ghiChu.getTieuDe());
                edtSuaNoiDung.setText(ghiChu.getNoiDung());
                edtSuaNgayNhacNho.setText(ghiChu.getNgayNhacNho());
                edtSuaGioNhacNho.setText(ghiChu.getGioNhacNho());
                if (edtSuaNgayNhacNho.getText().length()>0)
                    switchSuaBaoThuc.setChecked(true);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    // Khởi tạo initView
    private void initView() {

        edtSuaTieuDe = (EditText) findViewById(R.id.edtSuaTieuDe);
        edtSuaNoiDung = (EditText) findViewById(R.id.edtSuaNoiDung);
        edtSuaNgayNhacNho = (EditText) findViewById(R.id.edtSuaNgayNhacNho);
        edtSuaGioNhacNho = (EditText) findViewById(R.id.edtSuaGioNhacNho);
        switchSuaBaoThuc=(Switch) findViewById(R.id.switchSuaBaoThuc);
        btnSuaMauNen=(Button) findViewById(R.id.btnSuaMauNen);
        btnLuu = (Button) findViewById(R.id.btnLuu);


        getData();
        try {

            if (ghiChu.getNgayNhacNho().length() > 0) {
                iday = Integer.valueOf(ghiChu.getNgayNhacNho().split("/")[0]);
                imonth = Integer.valueOf(ghiChu.getNgayNhacNho().split("/")[1]);
                iyear = Integer.valueOf(ghiChu.getNgayNhacNho().split("/")[2]);
            }
            if (ghiChu.getGioNhacNho().length() > 0) {
                ihour = Integer.valueOf(ghiChu.getGioNhacNho().split(":")[0]);
                iminute = Integer.valueOf(ghiChu.getGioNhacNho().split(":")[1]);
            }
        }
        catch (Exception e)
        {

        }

        edtSuaTieuDe.setTextColor(Color.parseColor(ghiChu.getMauChu()));
        edtSuaNoiDung.setTextColor(Color.parseColor(ghiChu.getMauChu()));
        edtSuaNgayNhacNho.setTextColor(Color.parseColor(ghiChu.getMauChu()));
        edtSuaGioNhacNho.setTextColor(Color.parseColor(ghiChu.getMauChu()));

        edtSuaTieuDe.setBackgroundColor(Color.parseColor(ghiChu.getMauNen()));
        edtSuaNoiDung.setBackgroundColor(Color.parseColor(ghiChu.getMauNen()));
        edtSuaNgayNhacNho.setBackgroundColor(Color.parseColor(ghiChu.getMauNen()));
        edtSuaGioNhacNho.setBackgroundColor(Color.parseColor(ghiChu.getMauNen()));


        intent=new Intent(SuaGhiChuActivity.this,MainActivity.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); //truy cập vào hệ thống của máy
        intentBaoThuc = new Intent(SuaGhiChuActivity.this, AlarmReceiver.class);

        switchSuaBaoThuc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {

                    intentBaoThuc.putExtra("extra", "on");
                    //pendingIntent = PendingIntent.getBroadcast(SuaGhiChuActivity.this, ghiChu.getId(), intentBaoThuc, 0);
                     //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                else
                {
                    pendingIntent = PendingIntent.getBroadcast(SuaGhiChuActivity.this, ghiChu.getId(), intentBaoThuc, 0);
                    alarmManager.cancel(pendingIntent);
                    intentBaoThuc.putExtra("extra", "off");
                    sendBroadcast(intentBaoThuc);
                }
            }
        });
        EventEdittext();

        calendar = calendar.getInstance();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date ngayHienTai = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                GhiChu ghiChuu = new GhiChu(ghiChu.getId(),
                        edtSuaTieuDe.getText().toString(),
                        edtSuaNoiDung.getText().toString(),
                        mauNen,
                        mauChu,
                        "personal",
                        ft.format(ngayHienTai).toString(),
                        edtSuaNgayNhacNho.getText().toString(),
                        ihour+":"+iminute,
                        ft.format(ngayHienTai).toString());

                if (switchSuaBaoThuc.isChecked()) {
                    if (edtSuaGioNhacNho.getText().length() == 0 || edtSuaNgayNhacNho.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thời gian báo thức hoặc tắt báo thức để thêm", Toast.LENGTH_LONG).show();
                    } else {

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(iyear, imonth, iday, ihour, iminute, 0);
                        intentBaoThuc.putExtra("TieuDe", ghiChuu.getTieuDe());
                        intentBaoThuc.putExtra("NoiDung", ghiChuu.getNoiDung());
                        intentBaoThuc.putExtra("ID", ghiChuu.getId());

                        intentBaoThuc.putExtra("extra", "on");

                        pendingIntent = PendingIntent.getBroadcast(SuaGhiChuActivity.this, ghiChu.getId(), intentBaoThuc, 0);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        ghiChuu.setGioNhacNho(ihour+":"+iminute);
                        ghiChuu.setNgayNhacNho(edtSuaNgayNhacNho.getText().toString());
                        intent.putExtra("EDIT", (Serializable) ghiChuu);
                        Log.d("Intent",ghiChuu.getNoiDung());
                        setResult(200, intent);
                        finish();
                    }

                }
                else {
                    ghiChuu.setGioNhacNho("");
                    ghiChuu.setNgayNhacNho("");
                    intent.putExtra("EDIT", (Serializable) ghiChuu);
                    setResult(200, intent);
                    finish();
                }
            }
        })
        ;

        btnSuaMauNen=findViewById(R.id.btnSuaMauNen);
        btnSuaMauNen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(SuaGhiChuActivity.this);
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
        edtSuaNgayNhacNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iyear = calendar.get(Calendar.YEAR);
                imonth = calendar.get(Calendar.MONTH);
                iday = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(SuaGhiChuActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtSuaNgayNhacNho.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        iyear = year;
                        imonth = month;
                        iday = dayOfMonth;
                    }
                }, iyear, imonth, iday);
                datePickerDialog.show();
            }
        });

        edtSuaGioNhacNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ihour = calendar.get(Calendar.HOUR_OF_DAY);
                iminute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(SuaGhiChuActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtSuaGioNhacNho.setText(ConvertTime(hourOfDay, minute));
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