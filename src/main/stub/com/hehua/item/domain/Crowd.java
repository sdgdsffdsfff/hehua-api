/*
 * 由系统于2014-08-20 00:04:12生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

public class Crowd extends CrowdI {

    public static final Function<Crowd, Integer> idExtractor = new Function<Crowd, Integer>() {

        @Override
        public Integer apply(Crowd input) {
            return (int) input.getId();
        }
    };
}
