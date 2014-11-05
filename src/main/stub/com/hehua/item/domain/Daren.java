/*
 * 由系统于2014-08-10 17:12:48生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.Date;

public class Daren {
    protected long id;
    private long userid;
    protected Date cts;
    protected Date uts;
    protected String name;
    protected String nickname;
    private String location;
    private String stage;
    private String gender;
    private String birthday;
    protected String title;

    private String description;
    private int avatarid;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvatarid() {
        return avatarid;
    }

    public void setAvatarid(int avatarid) {
        this.avatarid = avatarid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
