/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

public class ItemRecommend extends ItemRecommendI {

    public static Function<ItemRecommend, Integer> itemIdExtractor = new Function<ItemRecommend, Integer>() {

        @Override
        public Integer apply(ItemRecommend input) {
            return (int) input.getItemid();
        }
    };
}
