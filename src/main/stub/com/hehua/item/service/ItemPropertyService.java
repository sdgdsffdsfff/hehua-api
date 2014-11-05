/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemProperty;
import org.apache.log4j.Logger;

import java.util.List;

@javax.inject.Named
public class ItemPropertyService extends ItemPropertyServiceI {

    private static final Logger log = Logger.getLogger(ItemPropertyService.class);

    public List<ItemProperty> getItemPropertiesByItemid(long itemid) {
        return dao.getItemPropertiesByItemid(itemid);
    }

    public List<ItemProperty> getItemSkuPropertiesByItemid(long itemid) {
        return dao.getItemPropertiesByItemidAndIssku(itemid, 1);
    }

    public List<ItemProperty> getItemNoSkuPropertiesByItemid(long itemid) {
        return dao.getItemPropertiesByItemidAndIssku(itemid, 0);
    }

    public boolean deleteItemPropertyByItemid(long id) {
        return dao.deleteItemPropertyByItemid(id) == 1;
    }

    public ItemProperty getByItemIdPVIDAndSkuid(long itemid, int pvid, long skuid) {
        return dao.getByItemIdPVIDAndSkuid(itemid, pvid, skuid);
    }

}
