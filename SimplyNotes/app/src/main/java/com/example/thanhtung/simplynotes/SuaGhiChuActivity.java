package com.example.thanhtung.simplynotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class SuaGhiChuActivity extends AppCompatActivity {
    EditText edtSuaTieuDe, edtSuaNoiDung, edtSuaNgayNhacNho;
    Button btnLuu;
    GhiChu ghiChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suaghichu);
        // Gọi phương thức ivitView và getData
        initView();
        getData();
    }
    // Khởi tạo initView
    private void initView() {
        edtSuaTieuDe = (EditText) findViewById(R.id.edtSuaTieuDe);
        edtSuaNoiDung = (EditText) findViewById(R.id.edtSuaNoiDung);
        edtSuaNgayNhacNho = (EditText) findViewById(R.id.edtSuaNgayNhacNho);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(SaveEdit_Click);
    }
    // Khơi tạo getData
    private void getData() {
        try {
            if (getIntent().getExtras() != null) {
                ghiChu = (GhiChu) getIntent().getSerializableExtra("EDIT");
                int a = ghiChu.getId();
                edtSuaTieuDe.setText(ghiChu.getTieuDe());
                edtSuaNoiDung.setText(ghiChu.getNoiDung());
                edtSuaNgayNhacNho.setText(ghiChu.getNgayNhacNho());
            }
        }catch (Exception e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    View.OnClickListener SaveEdit_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SuaGhiChuActivity.this, MainActivity.class);
            int b = ghiChu.getId();
            Date ngayHienTai = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
            GhiChu ghiChuu = new GhiChu(b,
                    edtSuaTieuDe.getText().toString(),
                    edtSuaNoiDung.getText().toString(),
                    "#000000",
                    "#ffffff",
                    ghiChu.getTheLoai(),
                    ghiChu.getNgayTao(),
                    edtSuaNgayNhacNho.getText().toString(),
                    ft.format(ngayHienTai).toString());

            intent.putExtra("EDIT", (Serializable) ghiChuu);
            setResult(200, intent);
            finish();
        }
    };

}