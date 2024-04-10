package com.example.dack.models;

public class Doanhthu {
    private String id,thoigiandat;
    private float tongtien;

    public Doanhthu() {
    }

    public Doanhthu(String id, String thoigiandat, float tongtien) {
        this.id = id;
        this.thoigiandat = thoigiandat;
        this.tongtien = tongtien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThoigiandat() {
        return thoigiandat;
    }

    public void setThoigiandat(String thoigiandat) {
        this.thoigiandat = thoigiandat;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float totien) {
        this.tongtien = totien;
    }
}
