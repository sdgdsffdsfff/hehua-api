/*
 * 由系统于2014-08-10 17:04:26生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class ItemAttrI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long itemid;
    protected String attributes;
    
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
    
    public String getAttributes(){
        return attributes;
    }
    public void setAttributes(String attributes){
        this.attributes=attributes;
    }
    
}
