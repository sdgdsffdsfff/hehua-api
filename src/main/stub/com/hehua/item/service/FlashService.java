/*
 * 由系统于2014-08-25 15:22:37生成。
 */

package com.hehua.item.service;

import java.util.*;

import com.hehua.commons.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.hehua.commons.Filters;
import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.commons.lang.StringUtils;
import com.hehua.framework.subscribe.ZookeeperPubSubService;
import com.hehua.item.domain.BrandgroupItem;
import com.hehua.item.domain.Flash;
import com.hehua.item.model.FlashSession;
import com.hehua.item.model.ItemResult;
import com.hehua.item.utils.BabyUtils;
import com.hehua.user.domain.Baby;

@javax.inject.Named
public class FlashService extends FlashServiceI {

    @Autowired
    private FlashManager flashManager;

    @Autowired
    private ItemService itemService;

    @Autowired
    private FlashSessionService flashSessionService;

    @Autowired
    private BrandgroupItemService brandgroupItemService;

    public void getCurrentOnlineFlashList(FlashSession session) {
        List<Flash> onlineFlashes = getSessionAllOnlineFlashes(session);
        List<Flash> brandGroupFlashs = Filters.filter(onlineFlashes, new Predicate<Flash>() {

            @Override
            public boolean apply(Flash flash) {
                return flash.getFlashtype() == BabyUtils.FLASH_TYPE_GROUP;
            }
        });
        List<Integer> brandGroupIds = Transformers.transformList(brandGroupFlashs,
                new Function<Flash, Integer>() {

                    @Override
                    public Integer apply(Flash flash) {
                        return flash.getGroupid();
                    }
                });
        List<BrandgroupItem> brandgroupItemList = brandgroupItemService
                .mgetBrandGroupItemByGroupIds(brandGroupIds);

    }

    private static final Logger log = Logger.getLogger(FlashService.class);

    private Flash addFlash(Flash flash) {
        this.dao.createFlash(flash);
        ZookeeperPubSubService.getInstance().post(FlashSessionLocalCache.KEY,
                "addflash_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        return flash;
    }

    public Flash addFlash(FlashSession session, ItemResult item) {
        return addFlash(session.getSessionDate(), item);
    }

    public Flash addFlash(Date onlineDate, ItemResult item) {

        Flash flash = new Flash();
        flash.setItemid((int) item.getItem().getId());
        flash.setOnlinedate(onlineDate);
        flash.setCreatetime(new Date());

        FlashSession session = flashSessionService.getSessionByDate(onlineDate);

        flash.setOnlinedate(session.getSessionDate());
        flash.setStarttime(session.getStartTime());
        flash.setEndtime(session.getEndTime());

        flash.setPriority(0);
        flash.setStatus(0);
        flash.setCroweds(item.getItem().getCrowedid());
        flash.setGender(item.getItem().getGenderid());
        flash.setFlashtype(BabyUtils.FLASH_TYPE_ITEM);
        flash.setGroupid(0);
        addFlash(flash);

        setOnline(flash.getId());
        return flash;
    }

    public Flash addBrandGroupFlash(Date onlineDate, int groupId) {

        Flash flash = new Flash();
        flash.setItemid(0);
        flash.setOnlinedate(onlineDate);
        flash.setCreatetime(new Date());

        FlashSession session = flashSessionService.getSessionByDate(onlineDate);

        flash.setOnlinedate(session.getSessionDate());
        flash.setStarttime(session.getStartTime());
        flash.setEndtime(session.getEndTime());

        flash.setPriority(0);
        flash.setStatus(0);
        flash.setCroweds("");
        flash.setGender(0);
        flash.setGroupid(groupId);
        flash.setFlashtype(BabyUtils.FLASH_TYPE_GROUP);
        addFlash(flash);

        setOnline(flash.getId());
        return flash;
    }

    public void setOnline(int flashId) {
        Flash flash = getFlashById(flashId);
        this.itemService.setFlash(flash.getItemid(), flashId);
        this.dao.setStatus(flashId, Flash.STATUS_ONLINE);

        ZookeeperPubSubService.getInstance().post(FlashSessionLocalCache.KEY,
                "addflash_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
    }

    public void setOffline(int flashId) {
        this.dao.setStatus(flashId, 0);
        Flash flash = getFlashById(flashId);
        this.itemService.setFlash(flash.getItemid(), Flash.STATUS_DELETED);

        ZookeeperPubSubService.getInstance().post(FlashSessionLocalCache.KEY,
                "addflash_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
    }

    public Flash getFlashByItemIdAndOnline(int itemId) {
        Calendar calendar = Calendar.getInstance();
        calendar = DateUtils.truncate(calendar, Calendar.DATE);
        return dao.getFlashByItemIdAndOnline(itemId, calendar.getTime());
    }

    public Flash getFlashByGroupId(int groupId) {
        return dao.getFlashByGroupId(groupId);
    }

    @Override
    public Flash getFlashById(int flashId) {
        return this.dao.getFlashById(flashId);
    }

    public Map<Integer, Flash> getFlashesByIds(Collection<Integer> flashIds) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(flashIds)) {
            return Collections.emptyMap();
        }

        List<Flash> flashes = this.dao.getFlashesByIds(flashIds);
        return Transformers.transformAsOneToOneMap(flashes, Flash.idExtractor);
    }

    public List<Flash> getSessionOnlineFlashes(FlashSession session, final int crowd,
            final int gender, int offset, int limit) {

        List<Flash> results = getFlashNormalItems(session, crowd, gender);
        return CollectionUtils.subList(results, offset, limit);

    }

    private List<Flash> getFlashNormalItems(FlashSession session, final int crowd, final int gender) {
        final Predicate<Flash> filter = new Predicate<Flash>() {

            @Override
            public boolean apply(Flash input) {

                /**
                 * 第一版api，要过滤品牌团
                 */
                if (input.getFlashtype() == BabyUtils.FLASH_TYPE_GROUP) {
                    return false;
                }
                Set<Integer> croweds = StringUtils.getIntegerSet(input.getCroweds());
                if (!croweds.contains(crowd)) {
                    return false;
                }

                // 备孕不检查性别
                if (crowd <= 1) {
                    return true;
                }

                switch (input.getGender()) {
                    case 4:
                        return true;
                    default:
                        return input.getGender() == gender;
                }
            }
        };

        // 暂时使用内存排序，TODO 做缓冲
        List<Flash> onlineFlashes = getSessionAllOnlineFlashes(session);
        List<Flash> results = Filters.filter(onlineFlashes, filter);
        Collections.sort(results);
        Collections.reverse(results);
        return results;
    }

    /**
     * @return
     */
    public List<Flash> getSessionAllOnlineFlashes(FlashSession session) {
        return this.dao.getSessionOnlineFlashes(session.getSessionDate());
    }

    public List<Flash> getSessionOnlineFlashes(FlashSession session, Baby baby, int limit,
            int offset) {
        int crowd = BabyUtils.getCrowd(baby);
        int gender = baby.getGender();
        return getSessionOnlineFlashes(session, crowd, gender, offset, limit);
    }

    public Flash getItemCurrentFlash(int itemId) {

        FlashSession todayFlashSession = flashManager.getTodayFlashSession();
        //        FlashSession currentSession = flashSessionService.currentSession();
        return todayFlashSession.getItemFlashMapping().get(itemId);
        //        return dao.getFlashByOnlineDateAndItemId(currentSession.getSessionDate(), itemId);
    }



}
