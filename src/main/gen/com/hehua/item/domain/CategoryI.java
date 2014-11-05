/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class CategoryI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected int parentid;
    protected String name;
    protected int status;
    protected int sort;
    private String display;
    
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
    
    public int getParentid(){
        return parentid;
    }
    public void setParentid(int parentid){
        this.parentid=parentid;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
    
    public int getSort(){
        return sort;
    }
    public void setSort(int sort){
        this.sort=sort;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
