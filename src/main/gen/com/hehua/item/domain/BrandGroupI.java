/*
 * 由系统于2014-09-12 15:02:10生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class BrandGroupI{
    protected int id;
    protected String imageurl;
    protected String name;
    protected String title;
    protected String desc;
    protected Date onlinetime;
    protected int status;
    protected int priority;
    protected Date cts;
    protected Date uts;
    private Date starttime;
    private Date endtime;

    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    
    public String getImageurl(){
        return imageurl;
    }
    public void setImageurl(String imageurl){
        this.imageurl=imageurl;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    
    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
        this.desc=desc;
    }
    
    public Date getOnlinetime(){
        return onlinetime;
    }
    public void setOnlinetime(Date onlinetime){
        this.onlinetime=onlinetime;
    }
    
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
    
    public int getPriority(){
        return priority;
    }
    public void setPriority(int priority){
        this.priority=priority;
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

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
