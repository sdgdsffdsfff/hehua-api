/*
 * 由系统于2014-08-10 17:12:48生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class ItemAppraiseI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long itemid;
    protected long uid;
    protected String name;
    protected String appraise;
    protected String summary;
    protected boolean offical;
    private float score;
    private int freeflashid;
    private long freeorderid;
    private int status;
    private String title;
    
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
    
    public String getAppraise(){
        return appraise;
    }
    public void setAppraise(String appraise){
        this.appraise=appraise;
    }
    
    public boolean getOffical(){
        return offical;
    }
    public void setOffical(boolean offical){
        this.offical=offical;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }


    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getFreeflashid() {
        return freeflashid;
    }

    public void setFreeflashid(int freeflashid) {
        this.freeflashid = freeflashid;
    }

    public long getFreeorderid() {
        return freeorderid;
    }

    public void setFreeorderid(long freeorderid) {
        this.freeorderid = freeorderid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
