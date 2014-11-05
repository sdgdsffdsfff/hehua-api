package com.hehua.item.domain.enums;

import com.hehua.commons.model.MetaCode;

/**
 */
public enum FreeFlashEnums implements MetaCode {

    ONLINE(1, "申请中"),
    NEED_AUDIT(2, "待审核"),// 需要刷选人
    ONLINE_ING(0, "待上线"),
    WAIT_SEND_GOOD(3, "待发货"),//选择完发货的人
    SEND_GOOD(4, "发货完毕"),//可以写评测
    APPRAISE_GOOD(5, "评测完毕"),//可以写评测

    DELETE(6, "删除下线"),
    ;

    private final int code;
    private final String msg;

    private FreeFlashEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public static FreeFlashEnums convertStatus(int status) {
        switch (status) {
            case 0 :
                return ONLINE_ING;
            case 1 :
                return ONLINE;
            case 2 :
                return NEED_AUDIT;
            case 3 :
                return WAIT_SEND_GOOD;
            case 4 :
                return SEND_GOOD;
            case 5 :
                return APPRAISE_GOOD;
            case 6 :
                return DELETE;

            default:
                 throw new RuntimeException("不知道的status＝"+ status);

        }
    }
}
