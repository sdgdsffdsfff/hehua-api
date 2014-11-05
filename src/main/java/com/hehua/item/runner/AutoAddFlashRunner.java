/**
 * 
 */
package com.hehua.item.runner;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hehua.commons.ProbabilityUtils;
import com.hehua.item.domain.Flash;
import com.hehua.item.domain.Item;
import com.hehua.item.model.FlashSession;
import com.hehua.item.model.ItemResult;
import com.hehua.item.service.FlashService;
import com.hehua.item.service.FlashSessionService;
import com.hehua.item.service.ItemService;

/**
 * @author zhihua
 *
 */
public class AutoAddFlashRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    "classpath*:spring/applicationContext*.xml");

            ItemService itemService = applicationContext.getBean(ItemService.class);
            FlashService flashService = applicationContext.getBean(FlashService.class);
            FlashSessionService sessionService = applicationContext
                    .getBean(FlashSessionService.class);

            int requireSize = 20;
            FlashSession session = sessionService.getSessionByDate(new Date());
            List<Flash> onlineFlashes = flashService.getSessionAllOnlineFlashes(session);

            int addSize = requireSize - onlineFlashes.size();
            List<Item> allItems = itemService.getAllOnlineItem();
            List<Item> addItems = ProbabilityUtils.randomElements(allItems, addSize);

            for (Item item : addItems) {
                try {
                    ItemResult itemFull = itemService.getItem(item.getId());
                    Flash addFlash = flashService.addFlash(session, itemFull);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
