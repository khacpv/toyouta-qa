package vn.com.toyota.checkdetail.model;

/**
 * Created by Do Hoang on 4/21/2017.
 */

public class Setting {
    private String ip;
    private int port;

    public Setting(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
