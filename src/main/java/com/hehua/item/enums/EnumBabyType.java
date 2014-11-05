package com.hehua.item.enums;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-16
 */
public enum EnumBabyType {
    BABY_TYPE_UNKNOWN(0, "未知"),
    BABY_TYPE_PREPARING(1, "未知"),
    BABY_TYPE_PREGNANT_0(2, "未知"),
    BABY_TYPE_PREGNANT_8(3, "未知"),
    ;

    private int type;
    private String description;

    EnumBabyType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static EnumBabyType getBabyType(int type) {
        for (EnumBabyType babyType: values()) {
            if (babyType.getType() == type)
                return babyType;
        }
        return BABY_TYPE_UNKNOWN;
    }
}
