package com.example.thanhtung.simplynotes;

import java.io.Serializable;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class GhiChu implements Serializable {
    private int id;
    private String tieuDe;
    private String noiDung;
    private String mauNen;
    private String mauChu;
    private String theLoai;
    private String ngayTao;
    private String ngayNhacNho;
    private String thoiGianChinhSuaCuoi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getMauNen() {
        return mauNen;
    }

    public void setMauNen(String mauNen) {
        this.mauNen = mauNen;
    }

    public String getMauChu() {
        return mauChu;
    }

    public void setMauChu(String mauChu) {
        this.mauChu = mauChu;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayNhacNho() {
        return ngayNhacNho;
    }

    public void setNgayNhacNho(String ngayNhacNho) {
        this.ngayNhacNho = ngayNhacNho;
    }

    public String getThoiGianChinhSuaCuoi() {
        return thoiGianChinhSuaCuoi;
    }

    public void setThoiGianChinhSuaCuoi(String thoiGianChinhSuaCuoi) {
        this.thoiGianChinhSuaCuoi = thoiGianChinhSuaCuoi;
    }

    public GhiChu(int id, String tieuDe, String noiDung, String mauNen, String mauChu, String theLoai, String ngayTao, String ngayNhacNho, String thoiGianChinhSuaCuoi) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;

        this.mauNen = mauNen;
        this.mauChu = mauChu;
        this.theLoai = theLoai;
        this.ngayTao = ngayTao;
        this.ngayNhacNho = ngayNhacNho;
        this.thoiGianChinhSuaCuoi = thoiGianChinhSuaCuoi;
    }
}
