package com.hehua.item.model;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-20
 */
public class ImageView {
    private long imageid;
    private String location;
    private boolean isMaster;

    public long getImageid() {
        return imageid;
    }

    public void setImageid(long imageid) {
        this.imageid = imageid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }
}
