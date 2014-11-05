/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.*;

public class BrandCategoryI{
    protected long id;
    protected Date cts;
    protected Date uts;
    protected long brandid;
    protected long cateid;
    
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
    
    public long getBrandid(){
        return brandid;
    }
    public void setBrandid(long brandid){
        this.brandid=brandid;
    }
    
    public long getCateid(){
        return cateid;
    }
    public void setCateid(long cateid){
        this.cateid=cateid;
    }
    
}
