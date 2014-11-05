/**
 * 
 */
package com.hehua.item.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.hehua.framework.localcache.AbstractLocalCache;
import com.hehua.item.domain.BrandgroupItem;
import com.hehua.item.domain.Flash;
import com.hehua.item.model.FlashSession;

/**
 * @author zhihua
 *
 */
@Named
public class FlashSessionLocalCache extends AbstractLocalCache<FlashSession> {

    public static final String KEY = "flashession";

    @Inject
    private FlashSessionService flashSessionService;

    @Inject
    private FlashService flashService;

    @Override
    public String key() {
        return KEY;
    }

    @Override
    public FlashSession load() {
        // TODO Auto-generated method stub
        return buildFlashSession(new Date());
    }

    public FlashSession buildFlashSession(Date date) {

        FlashSession flashSession = flashSessionService.getSessionByTime(date);
        List<Flash> flashes = flashService.getSessionAllOnlineFlashes(flashSession);
        long s = System.currentTimeMillis();
        flashSession.prepare(flashes);
        long e = System.currentTimeMillis();
        System.out.println("flashes size:" + flashes.size() + " time used:" + (e - s)
                + " miliseconds!");

        Map<Integer, Flash> itemFlashMapping = getItemFlashMapping(flashSession);
        flashSession.setItemFlashMapping(itemFlashMapping);

        return flashSession;

    }

    @Autowired
    private BrandgroupItemService brandgroupItemService;

    public Map<Integer, Flash> getItemFlashMapping(FlashSession session) {
        Map<Integer, Flash> mapping = new HashMap<>();
        List<Flash> flashes = session.getFlashes();
        for (Flash flash : flashes) {
            if (flash.isGroup()) {

                List<BrandgroupItem> flashItems = brandgroupItemService
                        .getFlashItemListByBrandGrandId(flash.getGroupid(), 1);
                for (BrandgroupItem item : flashItems) {
                    mapping.put(item.getItemid(), flash);
                }

            } else {
                mapping.put(flash.getItemid(), flash);
            }

        }
        return mapping;
    }
}
