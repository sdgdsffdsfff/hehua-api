/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.Transformers;
import com.hehua.item.domain.ItemSku;

@javax.inject.Named
public class ItemSkuService extends ItemSkuServiceI {

    @Autowired
    private PropertyService propertyService;

    private static final Logger log = Logger.getLogger(ItemSkuService.class);

    public List<ItemSku> getItemSkusByItemid(long itemid) {
        return dao.getItemSkusByItemid(itemid);
    }

    public boolean deleteItemSkuByItemid(long id) {
        return dao.deleteItemSkuByItemid(id) == 1;
    }

    //    public ItemSku getItemSku(long itemid, long skuid) {
    //        dao.getItemSkuById(skuid);
    //    }

    public String getSkuString(long id) {
        ItemSku itemSku = this.getItemSkuById(id);

        String pvids = itemSku.getPvids();

        return null;
    }

    public ListMultimap<Integer, ItemSku> getItemSkusByItemIds(Collection<Integer> itemIds) {

        if (CollectionUtils.isEmpty(itemIds)) {
            return ArrayListMultimap.create();
        }

        List<ItemSku> itemSkus = this.dao.getItemSkusByItemIds(itemIds);
        return Transformers.transformAsListMultimap(itemSkus, ItemSku.itemIdExtractor);

    }

    public boolean incrSkuQuantity(int skuid, int quantity) {
        if (quantity <= 0) {
            return false;
        }
        return this.dao.incrItemSkuQuantityById(skuid, quantity) > 0;
    }

    public boolean decrSkuQuantity(int skuid, int quantity) {
        if (quantity <= 0) {
            return false;
        }
        return this.dao.decrItemSkuQuantityById(skuid, quantity) > 0;
    }

    public boolean setSkuQuantity(int skuid, int quantity) {
        if (quantity < 0) {
            return false;
        }
        return this.dao.setItemSkuQuantityById(skuid, quantity) > 0;
    }
}
