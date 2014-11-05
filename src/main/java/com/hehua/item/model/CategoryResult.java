package com.hehua.item.model;

import java.util.List;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-20
 */
public class CategoryResult {
    private long id;
    private String name;
    private List<CategoryResult> subcates;

    public CategoryResult() {
    }

    public CategoryResult(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryResult(long id, String name, List<CategoryResult> subcates) {
        this.id = id;
        this.name = name;
        this.subcates = subcates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryResult> getSubcates() {
        return subcates;
    }

    public void setSubcates(List<CategoryResult> subcates) {
        this.subcates = subcates;
    }
}
