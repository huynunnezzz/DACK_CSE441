package com.example.dack.models;

import java.io.Serializable;

public class Giohang {
    private String idspgh;
    private String tensp;
    private float giasp;
    private int soluongsp;
    private String hinhanhsp;
    private float totalsp;

    public Giohang() {
    }

    public Giohang(String idspgh, String tensp, float giasp, int soluongsp, String hinhanhsp, float totalsp) {
        this.idspgh = idspgh;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluongsp = soluongsp;
        this.hinhanhsp = hinhanhsp;
        this.totalsp = totalsp;
    }

    public String getIdspgh() {
        return idspgh;
    }

    public void setIdspgh(String idspgh) {
        this.idspgh = idspgh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public float getGiasp() {
        return giasp;
    }

    public void setGiasp(float giasp) {
        this.giasp = giasp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public float getTotalsp() {
        return totalsp;
    }

    public void setTotalsp(float totalsp) {
        this.totalsp = totalsp;
    }
}
