/**
 * 
 */
package com.hehua.item.enums;

/**
 * @author zhihua
 *
 */
public enum Gender {

    Unkown(0),

    Male(1),

    Female(2),

    ;

    private final int code;

    public int getCode() {
        return code;
    }

    /**
     * @param code
     */
    private Gender(int code) {
        this.code = code;
    }

    public static Gender fromCode(int code) {
        switch (code) {
            case 0:
                return Unkown;
            case 1:
                return Male;
            case 2:
                return Female;
            default:
                throw new RuntimeException("unkown gender code: " + code);
        }
    }
}
