package com.example.dack.models;

import java.io.Serializable;

public class Sanpham implements Serializable {
    private String idsp;
    private String tensp;
    private float giasp;
    private String motasp,hinhanhsp;

    public Sanpham() {
    }

    public Sanpham(String idsp, String tensp, float giasp, String motasp, String hinhanhsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.motasp = motasp;
        this.hinhanhsp = hinhanhsp;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
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

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }
}
