/*
 * 由系统于2014-08-10 18:21:04生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import java.util.List;

import com.hehua.item.dao.ItemDetailDAO;
import com.hehua.item.domain.ItemDetail;

public class ItemDetailServiceI {

    @javax.inject.Inject
    protected ItemDetailDAO dao;

    public ItemDetail getItemDetailById(long id) {
        return dao.getItemDetailById(id);
    }

    public boolean hasItemDetailWithId(long id) {
        return dao.hasItemDetailWithId(id) != null;
    }

    public ItemDetail getItemDetailByItemid(long itemid) {
        return dao.getItemDetailByItemid(itemid);
    }

    public boolean hasItemDetailWithItemid(long itemid) {
        return dao.hasItemDetailWithItemid(itemid) != null;
    }

    public List<ItemDetail> getAllItemDetail() {
        return dao.getAllItemDetail();
    }

    public boolean createItemDetail(ItemDetail ent) {
        return dao.createItemDetail(ent) == 1;
    }

    public int createItemDetailBy(ItemDetail ent) {
        return dao.createItemDetail(ent);
    }

    public boolean updateItemDetailById(ItemDetail ent) {
        return dao.updateItemDetailById(ent) == 1;
    }

    public boolean deleteItemDetailById(long id) {
        return dao.deleteItemDetailById(id) == 1;
    }

    public boolean updateItemByItemId(int itemId, String meta) {
        //        return dao.updateItemMetaById(meta, itemId)==1;
        // TODO FIXME 这个问题大条的很
        return dao.createOrUpdateItemMeta(meta, itemId) > 0;
    }

}
