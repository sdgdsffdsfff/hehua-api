package com.hehua.item.enums;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
public enum EnumSaleType {
    SALE_TYPE_NORMAL(1, "普通"),
    SALE_TYPE_FLASH(2, "闪购"),
    ;

    private int type;
    private String name;

    EnumSaleType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
