/*
 * 由系统于2014-08-25 15:22:37生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;
import com.hehua.item.utils.BabyUtils;

public class Flash extends FlashI implements Comparable<Flash> {

    public static final int STATUS_ONLINE = 1;

    public static final int STATUS_DELETED = 0;

    public static Function<Flash, Integer> itemIdExtractor = new Function<Flash, Integer>() {

        @Override
        public Integer apply(Flash input) {
            return input.getItemid();
        }
    };

    public static Function<Flash, Integer> idExtractor = new Function<Flash, Integer>() {

        @Override
        public Integer apply(Flash input) {
            return input.getId();
        }
    };

    @Override
    public int compareTo(Flash o) {
        return Integer.compare(this.priority, o.priority);
    }

    public boolean isOnline() {
        return this.status == 1;
    }

    private int flashtype = 1;

    private int groupid = 0;

    public int getFlashtype() {
        return flashtype;
    }

    public boolean isGroup() {
        return flashtype == BabyUtils.FLASH_TYPE_GROUP;
    }

    public void setFlashtype(int flashtype) {
        this.flashtype = flashtype;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
