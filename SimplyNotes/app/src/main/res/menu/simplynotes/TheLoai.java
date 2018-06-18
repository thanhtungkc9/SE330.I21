package com.example.thanhtung.simplynotes;

/**
 * Created by ThanhTung on 11-Jun-18.
 */

public class TheLoai {
    private int id;
    private String tenTheLoai;
    private String mauNen;
    private String mauChu;

    public TheLoai(int id, String tenTheLoai, String mauNen, String mauChu) {
        this.id = id;
        this.tenTheLoai = tenTheLoai;
        this.mauNen = mauNen;
        this.mauChu = mauChu;
    }
    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
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
}
