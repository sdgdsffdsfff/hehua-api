/*
 * 由系统于2014-08-20 01:42:29生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.Date;

public class PropertyI {

    protected int id;

    protected Date cts;

    protected Date uts;

    protected String name;

    protected int parentid;

    protected long cateid;

    protected int status;

    protected int isshow;

    protected int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCts() {
        return cts;
    }

    public void setCts(Date cts) {
        this.cts = cts;
    }

    public Date getUts() {
        return uts;
    }

    public void setUts(Date uts) {
        this.uts = uts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public long getCateid() {
        return cateid;
    }

    public void setCateid(long cateid) {
        this.cateid = cateid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
