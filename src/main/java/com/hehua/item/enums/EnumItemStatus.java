package com.hehua.item.enums;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
public enum EnumItemStatus {
    ITEM_STATUS_STORE(0, "仓库"),
    ITEM_STATUS_ONLINE(1, "上架"),
    ITEM_STATUS_READY(2, "待上架"),
    ITEM_STATUS_OFFLINE(4, "下架"),
    ;

    private int status;
    private String name;

    EnumItemStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
