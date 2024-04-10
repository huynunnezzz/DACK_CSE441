package com.example.dack.models;

public class Donhang {
    String id,tenkh,sdt,diachi,sanpham,thoigiandat;
    float tongtien;

    public Donhang() {
    }

    public Donhang(String id, String tenkh, String sdt, String diachi, String sanpham, String thoigiandat, float tongtien) {
        this.id = id;
        this.tenkh = tenkh;
        this.sdt = sdt;
        this.diachi = diachi;
        this.sanpham = sanpham;
        this.thoigiandat = thoigiandat;
        this.tongtien = tongtien;
    }
    public Donhang(String id,String thoigiandat,float tongtien){
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

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSanpham() {
        return sanpham;
    }

    public void setSanpham(String sanpham) {
        this.sanpham = sanpham;
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

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }
}
