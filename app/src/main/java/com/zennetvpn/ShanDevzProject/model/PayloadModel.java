package com.zennetvpn.ShanDevzProject.model;

public class PayloadModel {

    private String name;
    private String info;
    private String payload;

    private String logo;   // <--- ต้องมีตัวนี้

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }

    // ---- เพิ่มส่วน LOGO ให้ถูกต้อง ----
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
}
