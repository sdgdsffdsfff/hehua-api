/*
 * 由系统于2014-08-20 00:04:12生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class GenderI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected String name;
    protected long value;
    
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
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    
    public long getValue(){
        return value;
    }
    public void setValue(long value){
        this.value=value;
    }
    
}
