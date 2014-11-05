/*
 * 由系统于2014-09-12 15:02:10生成。
 */

package com.hehua.item.service;

import com.hehua.item.domain.BrandGroup;
import com.hehua.item.domain.Flash;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@javax.inject.Named
public class BrandGroupService extends BrandGroupServiceI{
    private static final Logger log = Logger.getLogger(BrandGroupService.class);
    private static final int ONLIN_STATUS = 1;

    @Autowired
    FlashService flashService;


    public boolean addFlashBrandGroup(int groupId, Date date) {
        Flash fla = flashService.getFlashByGroupId(groupId);
        if (fla != null) {
            log.info("add repeat flash groupId= " + groupId);
            return false;
        }
        Flash flash = flashService.addBrandGroupFlash(date, groupId);
        BrandGroup brandGroup = dao.getBrandGroupById(groupId);
        if (brandGroup == null) {
            return false;
        }
        brandGroup.setStatus(ONLIN_STATUS);
        brandGroup.setPriority(1);
        brandGroup.setUts(new Date());
        brandGroup.setOnlinetime(flash.getOnlinedate());
        brandGroup.setStarttime(flash.getStarttime());
        brandGroup.setEndtime(flash.getEndtime());
        return dao.updateBrandGroupById(brandGroup) == 1;
    }
}
