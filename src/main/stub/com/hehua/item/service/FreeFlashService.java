package com.hehua.item.service;

import com.google.common.base.Function;
import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.item.dao.FreeFlashDAO;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.domain.enums.FreeFlashEnums;
import com.hehua.item.model.ItemLite;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by hesheng on 14-10-11.
 */
@javax.inject.Named
public class FreeFlashService {
    private static final Logger log = Logger.getLogger(FreeFlashService.class);

    @javax.inject.Inject
    protected FreeFlashDAO freeFlashDAO;

    @Inject
    private ItemService itemService;

    public FreeFlash getFreeFlash(int freeId) {
        return freeFlashDAO.getFreeFlashNotContainStatusById(freeId);
    }

    public FreeFlash getFreeFlashNotContainStatusById(int freeId) {
        return freeFlashDAO.getFreeFlashNotContainStatusById(freeId);
    }

    public FreeFlash getFreeFlashByItemId(int itemId) {
        return freeFlashDAO.getFreeFlashByItemid(itemId);
    }

    public FreeFlash getFreeFlashByItemidNotContainStatus(int itemId) {
        return freeFlashDAO.getFreeFlashByItemidNotContainStatus(itemId);
    }

    public boolean updateApplyNum(int id) {
        FreeFlash freeFlash = freeFlashDAO.getFreeFlashById(id);
        if (freeFlash == null) {
            return false;
        } else {
            return freeFlashDAO.updateFreeFlashApplyNumById(id) == 1 ? true : false;
        }
    }

    public boolean updateFreeFlashApplyStatusById(int freeId, int status) {
        FreeFlash freeFlash = freeFlashDAO.getFreeFlashNotContainStatusById(freeId);

        if (freeFlash == null) {
            return false;
        } else {
            return freeFlashDAO.updateFreeFlashApplyStatusById(status,freeFlash.getId()) == 1 ? true : false;
        }
    }

    public List<FreeFlash> getFreeListByPage(int startPos, int limit) {
        List<FreeFlash> freeFlashList = freeFlashDAO.getFreeFlashByPage(startPos, limit);
        List<Integer> itemIds = Transformers.transformList(freeFlashList, FreeFlash.itemIdExtractor);
        if (CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyList();
        }

        final Map<Integer, ItemLite> itemMaps = itemService.getItemLitesByIds(itemIds);
        for (FreeFlash freeFlash : freeFlashList) {
            freeFlash.setItemLite(itemMaps.get(freeFlash.getItemid()));
            freeFlash.setFreeFlashEnums(FreeFlashEnums.convertStatus(freeFlash.getStatus()));
        }

        return freeFlashList;
    }

    public List<FreeFlash> getFreeListByStateAndPage(int state,int startPos, int limit) {
        List<FreeFlash> freeFlashList = freeFlashDAO.getFreeFlashByStateAndPage(state,startPos, limit);
        List<Integer> itemIds = Transformers.transformList(freeFlashList, FreeFlash.itemIdExtractor);
        if (CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyList();
        }

        final Map<Integer, ItemLite> itemMaps = itemService.getItemLitesByIds(itemIds);
        for (FreeFlash freeFlash : freeFlashList) {
            freeFlash.setItemLite(itemMaps.get(freeFlash.getItemid()));
            freeFlash.setFreeFlashEnums(FreeFlashEnums.convertStatus(freeFlash.getStatus()));
        }

        return freeFlashList;
    }

    public boolean isBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long currentTime = calendar.getTime().getTime();

        return currentTime >= date.getTime();
    }


    public int getFreeListCount() {
        return freeFlashDAO.getFreeFlashCount();
    }

    public int getFreeListCountByState(int state) {
        return freeFlashDAO.getFreeFlashCountByState(state);
    }

    public boolean createFreeFlash(FreeFlash freeFlash) {
        return freeFlashDAO.createFreeFlash(freeFlash) == 1;
    }
    /**
     * 当前众测是否可申请
     * @param flash
     * @return
     */
    public boolean canApply(FreeFlash flash) {
        if (flash == null) {
            return false;
        }
        if (!flash.isOnline()) {
            return false;
        }
        if (flash.isSoldout()) {
            return false;
        }
        Date now = new Date();
        if (now.before(flash.getStarttime())) {
            return false;
        }
        if (now.after(flash.getEndtime())) {
            return false;
        }
        return true;
    }
}
