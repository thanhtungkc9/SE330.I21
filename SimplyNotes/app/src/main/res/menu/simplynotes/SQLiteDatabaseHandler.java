package com.example.thanhtung.simplynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="NotesManager";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME = "notes";

    private static final String KEY_ID = "id";
    private static final String KEY_TIEUDE = "tieude";
    private static final String KEY_NOIDUNG = "noidung";
    private static final String KEY_MAUNEN = "maunen";
    private static final String KEY_MAUCHU = "mauchu";
    private static final String KEY_THELOAI = "theloai";
    private static final String KEY_NGAYTAO ="ngaytao";
    private static final String KEY_NGAYNHACNHO = "ngaynhacnho";
    private static final String KEY_GIONHACNHO = "gionhacnho";
    private static final String KEY_THOIGIANCHINHSUACUOI = "thoigianchinhsuacuoi";
    //the loai
    private static final String TABLE_THELOAI = "theloai";
    private static final String KEY_THELOAIID = "theloaiid";
    private static final String KEY_THELOAITEN = "theloaiten";
    private static final String KEY_THELOAIMAUNEN = "theloaimaunen";
    private static final String KEY_THELOAIMAUCHU = "theloaimauchu";
    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+ "(id integer primary key autoincrement, tieude text,noidung text,maunen text, mauchu text, theloai text, ngaytao text, ngaynhacnho text,gionhacnho text, thoigianchinhsuacuoi text)");
        db.execSQL("create table "+TABLE_THELOAI+ "(id integer primary key autoincrement, theloaiten text, theloaimaunen text,  theloaimauchu text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addNote(GhiChu ghiChu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //  values.put(KEY_ID, ghiChu.getId());
        values.put(KEY_TIEUDE, ghiChu.getTieuDe());
        values.put(KEY_NOIDUNG, ghiChu.getNoiDung());
        values.put(KEY_MAUNEN, ghiChu.getMauNen());
        values.put(KEY_MAUCHU, ghiChu.getMauChu());
        values.put(KEY_THELOAI, ghiChu.getTheLoai());
        values.put(KEY_NGAYTAO, ghiChu.getNgayTao());
        values.put(KEY_NGAYNHACNHO, ghiChu.getNgayNhacNho());
        values.put(KEY_GIONHACNHO, ghiChu.getGioNhacNho());
        values.put(KEY_THOIGIANCHINHSUACUOI, ghiChu.getThoiGianChinhSuaCuoi());
        db.insert(TABLE_NAME, null, values);

        db.close();
    }
    public void addTheLoai(TheLoai theLoai) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //  values.put(KEY_ID, ghiChu.getId());
        values.put(KEY_THELOAITEN, theLoai.getTenTheLoai());
        values.put(KEY_THELOAIMAUNEN, theLoai.getMauNen());
        values.put(KEY_THELOAIMAUCHU, theLoai.getMauChu());
        db.insert(TABLE_THELOAI, null, values);
        db.close();
    }
    public GhiChu getNote(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(noteId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        GhiChu ghiChu = new GhiChu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
        return ghiChu;
    }
    public ArrayList<GhiChu> getAllNotes() {
        ArrayList<GhiChu>  noteList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            GhiChu ghiChu = new GhiChu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
            noteList.add(ghiChu);
            cursor.moveToNext();
        }

        return noteList;
    }

    public void updateNote(GhiChu ghiChu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, ghiChu.getId());
        values.put(KEY_TIEUDE, ghiChu.getTieuDe());
        values.put(KEY_NOIDUNG, ghiChu.getNoiDung());
        values.put(KEY_MAUNEN, ghiChu.getMauNen());
        values.put(KEY_MAUCHU, ghiChu.getMauChu());
        values.put(KEY_THELOAI, ghiChu.getTheLoai());
        values.put(KEY_NGAYTAO, ghiChu.getNgayTao());
        values.put(KEY_NGAYNHACNHO, ghiChu.getNgayNhacNho());
        values.put(KEY_GIONHACNHO, ghiChu.getGioNhacNho());
        values.put(KEY_THOIGIANCHINHSUACUOI, ghiChu.getThoiGianChinhSuaCuoi());

        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(ghiChu.getId()) });
        db.close();
    }
    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(noteId) });
        db.close();
    }

    public ArrayList<TheLoai> getAllTheLoai() {
        ArrayList<TheLoai>  theLoaiList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_THELOAI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            TheLoai theLoai = new TheLoai(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            theLoaiList.add(theLoai);
            cursor.moveToNext();
        }
        return theLoaiList;
    }
}
