package com.hehua.item.model;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-20
 */
public class PropertyValueView {

    private int id;

    private String name;

    private int propertyid;

    public PropertyValueView() {
    }

    public PropertyValueView(int id, String name, int propertyid) {
        this.id = id;
        this.name = name;
        this.propertyid = propertyid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(int propertyid) {
        this.propertyid = propertyid;
    }
}
