/**
 * 
 */
package com.hehua.item.enums;

/**
 * @author hewen
 *
 */
public enum AppraiseStatus {

    NO_AUDIT(1), // 未审核

    AUDIT_PASS(2), // 审核通过

    AUDIT_NO_PASS(3),  // 审核拒绝

    AUDIT_DELETE(4),; //审核删除

    private final int code;

    /**
     * @param code
     */
    private AppraiseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
