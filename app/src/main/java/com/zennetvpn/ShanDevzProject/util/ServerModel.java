package com.zennetvpn.ShanDevzProject.util;

public class ServerModel {
    String sSname,sSinfo,flags,shost,sport;
    public String getServerName() {
        return sSname;
    }
    public void setServerName(String name) {
        this.sSname = name;
    }

    public String getServerInfo() {
        return sSinfo;
    }
    public void setServerflag(String flag) {
        this.flags = flag;
    }
    public String getServerflag() {
        return flags;
    }
    public void setServerInfo(String info) {
        this.sSinfo = info;
    }
	public String getServerHost() {
        return shost;
    }
    public void setServerHost(String u) {
        this.shost = u;
    }
	public String getServerPort() {
        return sport;
    }
    public void setServerPort(String y) {
        this.sport = y;
    }

    private String onlineAPI;
    private int onlineUsers;
    private int onlineLimit;

    public void setOnlineAPI(String onlineAPI) { this.onlineAPI = onlineAPI; }
    public String getOnlineAPI() { return onlineAPI; }

    public void setOnlineUsers(int v) { this.onlineUsers = v; }
    public int getOnlineUsers() { return onlineUsers; }

    public void setOnlineLimit(int v) { this.onlineLimit = v; }
    public int getOnlineLimit() { return onlineLimit; }


}
