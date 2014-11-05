/*
 * 由系统于2014-09-12 15:02:10生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class BrandgroupItemI{
    protected long id;
    protected long groupid;
    protected int itemid;
    protected Date cts;
    protected Date uts;
    protected int status;
    private int priority;
    
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }
    
    public long getGroupid(){
        return groupid;
    }
    public void setGroupid(long groupid){
        this.groupid=groupid;
    }
    
    public int getItemid(){
        return itemid;
    }
    public void setItemid(int itemid){
        this.itemid=itemid;
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
    
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
