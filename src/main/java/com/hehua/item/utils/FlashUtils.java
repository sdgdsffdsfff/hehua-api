package com.hehua.item.utils;

import com.hehua.commons.lang.StringUtils;
import com.hehua.item.domain.Flash;

import java.security.acl.Group;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuweiwei on 14-9-13.
 */
public class FlashUtils {

    //团购flash适用人群
    public static final int GROUP_CROWD = -1;

    public static Map<Integer, Integer> getFlashMapByCrowd(List<Flash> flashs, boolean max) {
        Map<Integer, Integer> flashIdCrowdMap = new HashMap<>();
        for (Flash flash : flashs) {
            if (flash.isGroup()) {
                flashIdCrowdMap.put(flash.getId(), GROUP_CROWD);
                continue;
            }
            if (max) {
                flashIdCrowdMap.put(flash.getId(), Collections.max(StringUtils.getIntegerList(flash.getCroweds())));
            } else {
                flashIdCrowdMap.put(flash.getId(), Collections.min(StringUtils.getIntegerList(flash.getCroweds())));
            }
        }
        return flashIdCrowdMap;
    }
}
