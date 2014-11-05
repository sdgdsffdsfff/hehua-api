/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class ItemStatusI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long itemid;
    protected int saletype;
    protected Date starttime;
    protected Date endtime;
    protected int sales;
    protected String units;
    protected int status;
    protected Date onlinetime;
    
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
    
    public int getSaletype(){
        return saletype;
    }
    public void setSaletype(int saletype){
        this.saletype=saletype;
    }
    
    public Date getStarttime(){
        return starttime;
    }
    public void setStarttime(Date starttime){
        this.starttime=starttime;
    }
    
    public Date getEndtime(){
        return endtime;
    }
    public void setEndtime(Date endtime){
        this.endtime=endtime;
    }
    
    public int getSales(){
        return sales;
    }
    public void setSales(int sales){
        this.sales=sales;
    }
    
    public String getUnits(){
        return units;
    }
    public void setUnits(String units){
        this.units=units;
    }
    
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
    
    public Date getOnlinetime(){
        return onlinetime;
    }
    public void setOnlinetime(Date onlinetime){
        this.onlinetime=onlinetime;
    }
    
}
