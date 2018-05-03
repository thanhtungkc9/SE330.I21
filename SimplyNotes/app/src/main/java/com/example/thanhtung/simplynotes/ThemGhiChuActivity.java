package com.example.thanhtung.simplynotes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class ThemGhiChuActivity extends AppCompatActivity {
    TextView edtTieuDe, edtNoiDung, edtNgayNhacNho;
    Button btnThem;
     String mauChu="#000000";
     String mauNen="#ffffff";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themghichu);
        getSupportActionBar().hide();
        initview();
    }

    private void initview() {
        edtTieuDe = (EditText) findViewById(R.id.edtTieuDe);
        edtNoiDung = (TextView) findViewById(R.id.edtNoiDung);
        edtNgayNhacNho = (TextView) findViewById(R.id.edtNgayNhacNho);
        btnThem = (Button) findViewById(R.id.btnThem);
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
                        ft.format(ngayHienTai).toString());

                Intent intent = new Intent(ThemGhiChuActivity.this, MainActivity.class);

                intent.putExtra("ADD", (Serializable) ghiChu);
                setResult(100, intent);
                finish();
            }
        })
        ;

        Button btnTest=findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(ThemGhiChuActivity.this);
                final ArrayList<String> colors=new ArrayList<>();
                colors.add("#258174");
                colors.add("#3C8D2F");
                colors.add("#000000");
                colorPicker.setColors(colors).setColumns(5)
                        .setRoundColorButton(true).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        mauChu=colors.get(position);
                        edtNgayNhacNho.setTextColor(Color.parseColor(mauChu));
                        edtNoiDung.setTextColor(Color.parseColor(mauChu));
                        edtTieuDe.setTextColor(Color.parseColor(mauChu));
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getBaseContext(),"DDD",Toast.LENGTH_SHORT).show();

                    }
                }).setDefaultColorButton(Color.parseColor(mauChu)).show();
            }
        });
    }
    public void openColorPicker()
    {

    }

}