package com.hehua.item;

import com.hehua.item.dao.ItemDAO;
import com.hehua.item.service.DarenService;
import com.hehua.item.service.FreeFlashService;
import com.hehua.item.service.ItemService;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hewenjerry on 14-9-17.
 */
public class TestFreeFlashService extends TestCase {

    private ClassPathXmlApplicationContext applicationContext;

    @Override
    public void setUp() {
        applicationContext = new ClassPathXmlApplicationContext(
                "classpath*:/spring/applicationContext*.xml");
    }

    @Test
    public void testUpdateApplyNum() {
      FreeFlashService freeFlashService = applicationContext.getBean(FreeFlashService.class);
        Assert.assertEquals(true, freeFlashService.updateApplyNum(1));
    }

    @Test
    public void testDarenList() {
        DarenService  darenService = applicationContext.getBean(DarenService.class);
        List<Long> userIds = new ArrayList<>();
        userIds.add(324345479l);
        userIds.add(123603l);
        System.out.println(darenService.getDarenListByUserIds(userIds));
    }

    @Test
    public void testItemService() {
        ItemDAO itemDao = applicationContext.getBean(ItemDAO.class);
        System.out.println("size = "  + itemDao.getItemBySearchCount("",2,100,0));
        System.out.println(itemDao.getItemBySearch("", 2, 100, 0, 20, 0).size());

    }

}
