/*
 * 由系统于2014-09-12 15:02:10生成。
 */

package com.hehua.item.service;

import com.hehua.commons.collection.CollectionUtils;
import org.apache.log4j.Logger;
import com.hehua.item.domain.BrandgroupItem;

import java.util.*;

@javax.inject.Named
public class BrandgroupItemService extends BrandgroupItemServiceI {
    private static final Logger log = Logger.getLogger(BrandgroupItemService.class);

    public List<BrandgroupItem> getFlashItemListByBrandGroupAndPage(int brandGroupId, int offset, int limit) {
        return dao.getBrandgroupItemByIdAndPage(brandGroupId, offset, limit);
    }

    public List<BrandgroupItem> mgetBrandGroupItemByGroupIds(List<Integer> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return null;
        }
        return null;
    }

    public List<BrandgroupItem> getFlashItemListByBrandGrandId(int brandGroupId, Integer status) {
        if (status == -1)
            return dao.getBrandgroupItemByGroupId(brandGroupId);
        return dao.getBrandgroupItemByGroupIdAndStatus(brandGroupId, status);
    }

    public List<BrandgroupItem> getFlashItemListByBrandGrandItemId(long brandGroupItemId) {
        return dao.getBrandgroupByItemId(brandGroupItemId);
    }


    public int getBrandGroupItemSizeBy(int brandGroupId) {
        return dao.getBrandGroupItemSizeBy(brandGroupId);
    }

    public int getBrandGroupItemSizeByItemId(long itemId) {
        return dao.getBrandgroupSizeByItemIdAndStatus(itemId);
    }

    public int updateBrandGroupItemStatus(int id, int status) {
        return dao.updateBrandGroupItemStatus(status, id);
    }

    public void batchUpdateBrandGroupItemStatus(List<BrandgroupItem> brandgroupItems, int status) {
        for (BrandgroupItem brandgroupItem : brandgroupItems)
            dao.updateBrandGroupItemStatus(status, brandgroupItem.getId());
    }

    public boolean batchInsertBrandgroupItem(List<BrandgroupItem> brandgroupItemList) {
        if (brandgroupItemList.isEmpty()) {
            return true;
        }
        BrandgroupItem tempItem = null;
        for (BrandgroupItem brandgroupItem : brandgroupItemList) {
            try {
                tempItem = dao.getBrandgroupByItemIdAndGroupId(brandgroupItem.getItemid(), brandgroupItem.getGroupid());
                if (tempItem == null) {
                    dao.createBrandgroupItem(brandgroupItem);
                }
            } catch (Exception e) {
                log.error("fail!", e);
                return false;
            }
        }
        return true;
    }
}
