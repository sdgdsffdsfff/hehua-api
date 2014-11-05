/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.hehua.commons.Transformers;
import com.hehua.item.domain.ItemStatus;
import com.hehua.item.enums.EnumItemStatus;
import com.hehua.item.enums.EnumSaleType;

@javax.inject.Named
@Deprecated
public class ItemStatusService extends ItemStatusServiceI {

    private static final Logger log = Logger.getLogger(ItemStatusService.class);

    List<ItemStatus> getOnlineFlashItemStatuses(int offset, int limit) {
        return dao.getFlashItemStatusesByStatus(EnumSaleType.SALE_TYPE_FLASH.getType(), new Date(),
                EnumItemStatus.ITEM_STATUS_ONLINE.getStatus(), offset, limit);
    }

    List<ItemStatus> getOnlineFlashItemStatuses(int babyType, int gender, int offset, int limit) {
        return dao.getFlashItemStatusesByStatus(EnumSaleType.SALE_TYPE_FLASH.getType(), new Date(),
                EnumItemStatus.ITEM_STATUS_ONLINE.getStatus(), offset, limit);
    }

    List<ItemStatus> getOnlineNormalItemStatuses(int offset, int limit) {
        return dao.getNormalItemStatusesByStatus(EnumSaleType.SALE_TYPE_NORMAL.getType(),
                EnumItemStatus.ITEM_STATUS_ONLINE.getStatus(), offset, limit);
    }

    boolean deleteItemStatusByItemid(long itemid) {
        return dao.updateItemStatusByItemid(new Date(), 4, itemid) == 1;
    }

    public Map<Integer, ItemStatus> getItemStatuesByItemIds(Collection<Integer> itemIds) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyMap();
        }

        List<ItemStatus> itemStatues = this.dao.getItemStatuesByItemIds(itemIds);
        return Transformers.transformAsOneToOneMap(itemStatues, ItemStatus.itemIdExtractor);
    }
}
