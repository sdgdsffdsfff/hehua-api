/*
 * 由系统于2014-08-11 00:23:22生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class ItemRecommendI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long itemid;
    protected long uid;
    protected String name;
    protected String type;
    protected String avatar;
    protected String reason;
    
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
    
    public long getUid(){
        return uid;
    }
    public void setUid(long uid){
        this.uid=uid;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    
    public String getAvatar(){
        return avatar;
    }
    public void setAvatar(String avatar){
        this.avatar=avatar;
    }
    
    public String getReason(){
        return reason;
    }
    public void setReason(String reason){
        this.reason=reason;
    }
    
}
