/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemImage;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

@javax.inject.Named
public class ItemImageService extends ItemImageServiceI{
    private static final Logger log = Logger.getLogger(ItemImageService.class);

    public List<ItemImage> mgetItemImageBy(Collection<Long> ids){
        return dao.getByIds(ids);
    }

    public ItemImage getGroupImageByGroupId(int groupId) {
        return dao.getItemImageByGroupId(groupId);
    }
}
