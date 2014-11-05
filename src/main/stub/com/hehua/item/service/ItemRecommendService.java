/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.Transformers;
import com.hehua.item.domain.ItemRecommend;

@javax.inject.Named
public class ItemRecommendService extends ItemRecommendServiceI {

    private static final Logger log = Logger.getLogger(ItemRecommendService.class);

    public List<ItemRecommend> getItemRecommendByItemid(long itemid) {
        return dao.getItemRecommendByItemid(itemid);
    }

    public boolean deleteItemRecommendByItemid(long itemid) {
        return dao.deleteItemRecommendByItemid(itemid) == 1;
    }

    public int createItemRecommendBy(ItemRecommend ent) {
        return dao.createItemRecommend(ent);
    }

    public ListMultimap<Integer, ItemRecommend> getItemRecommendsByItemIds(
            Collection<Integer> itemIds) {

        // TODO 冗余进去
        if (CollectionUtils.isEmpty(itemIds)) {
            return ArrayListMultimap.create();
        }

        List<ItemRecommend> itemRecommends = this.dao.getItemRecommendsByItemIds(itemIds);
        return Transformers.transformAsListMultimap(itemRecommends, ItemRecommend.itemIdExtractor);

    }
}
