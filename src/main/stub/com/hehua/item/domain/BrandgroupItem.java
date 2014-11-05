/*
 * 由系统于2014-09-12 15:02:10生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

public class BrandgroupItem extends BrandgroupItemI implements Comparable<BrandgroupItem>{

    public static Function<BrandgroupItem, Integer> itemIdExtractor = new Function<BrandgroupItem, Integer>() {

        @Override
        public Integer apply(BrandgroupItem input) {
            return input.getItemid();
        }
    };

    @Override
    public int compareTo(BrandgroupItem o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }
}
