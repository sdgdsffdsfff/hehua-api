/*
 * 由系统于2014-08-10 17:12:48生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class ItemCategoryI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long itemid;
    protected int category;
    
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
    
    public int getCategory(){
        return category;
    }
    public void setCategory(int category){
        this.category=category;
    }
    
}
