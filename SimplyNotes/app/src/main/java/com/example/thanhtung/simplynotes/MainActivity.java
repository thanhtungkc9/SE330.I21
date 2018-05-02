package com.example.thanhtung.simplynotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    ListView lvGhiChu;
    ArrayList<GhiChu> mangGhiChu;
    GhiChuAdapter adapter;
    SQLiteDatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabThemGhiChu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(MainActivity.this, ThemGhiChuActivity.class);
                inte.putExtra("LENGTH",adapter.getListData().size());
                startActivityForResult(inte, 10);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        lvGhiChu=(ListView)findViewById(R.id.lvGhiChu);
        mangGhiChu=new ArrayList<GhiChu>();
        database=new SQLiteDatabaseHandler(MainActivity.this);
        mangGhiChu=database.getAllNotes();
        adapter=new GhiChuAdapter(MainActivity.this,R.layout.lvitem_ghichu,mangGhiChu);
        try{
            lvGhiChu.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        lvGhiChu.setOnItemClickListener(this);
        lvGhiChu.setOnItemLongClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, SuaGhiChuActivity.class);

        intent.putExtra("EDIT", (Serializable) adapter.getListData().get(position));
        startActivityForResult(intent, 20);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, SuaGhiChuActivity.class);
        database.deleteNote(adapter.getListData().get(position).getId());
        adapter.getListData().remove(position);
        Toast.makeText(MainActivity.this,"Đã xóa ghi chú",Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
        return false;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10: // Kiểm tra nếu requestCode = 10 thì sẽ chạy hàm dưới
                if (data != null) {

                    // Lấy ra giá trị truyền về từ AddpersonActivity và gán vào đối tượng person
                    GhiChu ghiChu = (GhiChu) data.getSerializableExtra("ADD");
                    if (resultCode == 100) {
                        // gán thuộc tính code trong Person bằng số lượng của adapter + 1

                        adapter.getListData().add(ghiChu);
                        database.addNote(ghiChu);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    break;


                }
            case 20:
                if (data != null) {
                    // Lấy ra dữ liệu được truyền về từ EditActivity
                    GhiChu select = (GhiChu) data.getSerializableExtra("EDIT");
                    if (resultCode == 200) {
                        for (GhiChu item : adapter.getListData()) {
                            if (item.getId() == select.getId()) {
                                item.setTieuDe(select.getTieuDe());
                                item.setNoiDung(select.getNoiDung());
                                item.setNgayNhacNho(select.getNgayNhacNho());
                                item.setMauChu(select.getMauChu());
                                item.setMauNen(select.getMauNen());
                                item.setNgayTao(select.getNgayTao());
                                item.setTheLoai(select.getTheLoai());
                                item.setThoiGianChinhSuaCuoi(select.getThoiGianChinhSuaCuoi());
                                break;
                            }
                        }
                        database.updateNote(select);
                        adapter.notifyDataSetChanged();
                        break;
                    }

                }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
