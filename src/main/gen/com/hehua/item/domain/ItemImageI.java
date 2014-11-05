/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class ItemImageI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long itemid;
    protected long imageid;
    protected String imagelocation;
    protected int ismaster;
    private int groupid;
    
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }
    
    public Date getCts(){
        return cts;
    }
    public void setCts(Date cts){
        this.cts=cts;
    }
    
    public Date getUts(){
        return uts;
    }
    public void setUts(Date uts){
        this.uts=uts;
    }
    
    public long getItemid(){
        return itemid;
    }
    public void setItemid(long itemid){
        this.itemid=itemid;
    }
    
    public long getImageid(){
        return imageid;
    }
    public void setImageid(long imageid){
        this.imageid=imageid;
    }
    
    public String getImagelocation(){
        return imagelocation;
    }
    public void setImagelocation(String imagelocation){
        this.imagelocation=imagelocation;
    }
    
    public int getIsmaster(){
        return ismaster;
    }
    public void setIsmaster(int ismaster){
        this.ismaster=ismaster;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
