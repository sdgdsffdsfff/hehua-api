/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.service;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.framework.image.ImageService;
import com.hehua.item.enums.AppraiseStatus;
import com.hehua.item.model.ItemLite;
import org.apache.log4j.Logger;

import com.hehua.commons.web.HtmlUtils;
import com.hehua.item.domain.ItemAppraise;

import javax.inject.Inject;

@javax.inject.Named
public class ItemAppraiseService extends ItemAppraiseServiceI {

    private static final Logger log = Logger.getLogger(ItemAppraiseService.class);
    @Inject
    private ItemService itemService;

    @Inject
    private ImageService imageService;

    List<ItemAppraise> getItemAppraiseByItemid(long itemid) {
        return dao.getItemAppraiseByItemid(itemid, AppraiseStatus.AUDIT_PASS.getCode());
    }

    public ItemAppraise getItemAppraiseByUserIdAndNoAudit(long userId) {
        return dao.getItemAppraisesByUserId(userId, AppraiseStatus.NO_AUDIT.getCode());
    }

    List<ItemAppraise> getItemAppraiseByItemid(long itemid, int offset, int limit) {
        List<ItemAppraise> itemAppraises = dao.getItemAppraiseByItemidAndOffsetLimit(itemid,
                offset, limit, AppraiseStatus.AUDIT_PASS.getCode());
        patchOldDatas(itemAppraises);
        return itemAppraises;
    }

    /**
     * @param itemAppraises
     */
    public void patchOldDatas(List<ItemAppraise> itemAppraises) {
        for (ItemAppraise itemAppraise : itemAppraises) {
            if (itemAppraise.getSummary() == null) {
                itemAppraise.setSummary(abbreviateAppraise(itemAppraise.getAppraise()));
                dao.updateItemAppraiseById(itemAppraise);
            }
        }
    }

    public int getItemAppraisesCount(long itemid) {
        return this.dao.getItemAppraisesCount(itemid, AppraiseStatus.AUDIT_PASS.getCode());
    }

    //TODO 是否仅仅显示审核通过的评测信息
    public int getItemCountByUserId(long userid) {
        return this.dao.getItemAppraisesByUserCount(userid, AppraiseStatus.AUDIT_PASS.getCode());
    }

    public List<ItemAppraise> getByUserId(long userid, int offset, int size) {
        List<ItemAppraise> itemAppraiseList = dao.getItemAppraiseByUseridAndOffsetLimit(userid, offset, size, AppraiseStatus.AUDIT_PASS.getCode());
        patchOldDatas(itemAppraiseList);
        if (!CollectionUtils.isEmpty(itemAppraiseList)) {
            List<Integer> itemIds = Transformers.transformList(itemAppraiseList, new Function<ItemAppraise, Integer>() {
                @Override
                public Integer apply(ItemAppraise input) {
                    return Integer.valueOf(String.valueOf(input.getItemid()));
                }
            });
            final Map<Integer, ItemLite> itemLites = itemService.getItemLitesByIds(itemIds);
            List<ItemAppraise> retList = Transformers.transformList(itemAppraiseList, new Function<ItemAppraise, ItemAppraise>() {
                @Override
                public ItemAppraise apply(ItemAppraise input) {
                    ItemLite itemLite = itemLites.get(Integer.valueOf(String.valueOf(input.getItemid())));
                    if (itemLite != null) {
                        try {
                            itemLite.setImage(imageService.getImageById(Long.valueOf(itemLite.getImage())).getUrl());
                        } catch (Exception e) {
                            log.error("", e);
                        }
                        input.setItemLite(itemLite);
                    }
                    return input;
                }
            });

            return retList;
        }

        return itemAppraiseList;

    }

    public String abbreviateAppraise(String appraise) {
        String summary = HtmlUtils.html2Text(appraise);
        summary = com.hehua.commons.lang.StringUtils.abbreviateChinese(summary, 160);
        return summary;

    }


    public int updateState(long id,int state){
        return dao.updateState(id,state);
    }
}
