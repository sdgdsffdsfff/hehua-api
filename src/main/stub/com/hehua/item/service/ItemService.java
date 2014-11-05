/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.hehua.item.domain.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.Transformers;
import com.hehua.item.manager.PropertyManager;
import com.hehua.item.model.FlashEntry;
import com.hehua.item.model.FlashSession;
import com.hehua.item.model.ItemLite;
import com.hehua.item.model.ItemResult;
import com.hehua.item.model.ItemSummary;
import com.hehua.user.domain.Baby;

@javax.inject.Named
public class ItemService extends ItemServiceI {

    private static final Logger log = Logger.getLogger(ItemService.class);

    @Inject
    private ItemRecommendService itemRecommendService;

    @Inject
    private ItemAppraiseService itemAppraiseService;

    @Autowired
    private PropertyManager propertyManager;

    @Inject
    private ItemSkuService itemSkuService;

    @Inject
    private ItemPropertyService itemPropertyService;

    @Inject
    private PropertyValueService propertyValueService;

    @Inject
    private BrandgroupItemService brandgroupItemService;

    @Autowired
    private FlashService flashService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    private static final String IMAGE_SERVER = "http://img.hehuababy.com/";

    private static final String MAKR_TITLE_KEY = "title";

    private static final String MARK_ICON_KEY = "icon";

    private static List<Map<String, String>> markupList = new ArrayList<>(3);

    static {
        Map<String,String> itemMarkMap = new HashMap<>(3);
        itemMarkMap.put(MAKR_TITLE_KEY, "100%正品");
        itemMarkMap.put(MARK_ICON_KEY, IMAGE_SERVER + "icon_real_quantity.png");
        markupList.add(itemMarkMap);

        Map<String,String>  itemMarkMapBy = new HashMap<>(3);
        itemMarkMapBy.put(MAKR_TITLE_KEY, "全场包邮");
        itemMarkMapBy.put(MARK_ICON_KEY, IMAGE_SERVER + "icon_free_postage.png");
        markupList.add(itemMarkMapBy);

        Map<String,String>  itemMarkMapTh = new HashMap<>(3);
        itemMarkMapTh.put(MAKR_TITLE_KEY, "90天退货");
        itemMarkMapTh.put(MARK_ICON_KEY, IMAGE_SERVER + "icon_90day_return_good.png");
        markupList.add(itemMarkMapTh);

    }


    public List<ItemSummary> getBrandGroupItemSummaryList(List<BrandgroupItem> brandgroupItemList) {
        List<Integer> itemIds = Transformers.transformList(brandgroupItemList,
                BrandgroupItem.itemIdExtractor);
        Map<Integer, ItemLite> itemBases = getItemLitesByIds(itemIds);

        ListMultimap<Integer, ItemRecommend> itemRecommends = itemRecommendService
                .getItemRecommendsByItemIds(itemIds);

        ListMultimap<Integer, ItemSku> itemSkus = itemSkuService.getItemSkusByItemIds(itemIds);
        Map<Integer, ItemSummary> items = new HashMap<>(itemIds.size());
        for (Integer itemId : itemIds) {
            ItemLite itemBase = itemBases.get(itemId);
            if (itemBase == null) {
                continue;
            }

            List<ItemRecommend> recommends = itemRecommends.get(itemId);
            ItemRecommend recommend = Iterables.getFirst(recommends, null);

            List<ItemSku> skus = itemSkus.get(itemId);

            ItemSummary itemElement = new ItemSummary();
            itemElement.setItem(itemBase);
            if (recommend != null) {
                itemElement.setItemRecommend(recommend);
            }
            itemElement.setItemSkus(skus);

            items.put(itemId, itemElement);
        }

        List<ItemSummary> result = new ArrayList<>(itemIds.size());
        for (Integer itemId : itemIds) {
            result.add(items.get(itemId));
        }

        return result;
    }

    public List<ItemSummary> getSessionItemSummaryList(FlashSession session, Baby baby, int offset,
            int limit) {
        List<Flash> onlineFlashes = flashService.getSessionOnlineFlashes(session, baby, limit,
                offset);

        Map<Integer, Flash> onlineFlashesMapByItemId = Transformers.transformAsOneToOneMap(
                onlineFlashes, Flash.itemIdExtractor);

        List<Integer> itemIds = Transformers.transformList(onlineFlashes, Flash.itemIdExtractor);
        Map<Integer, ItemLite> itemBases = getItemLitesByIds(itemIds);

        ListMultimap<Integer, ItemRecommend> itemRecommends = itemRecommendService
                .getItemRecommendsByItemIds(itemIds);

        ListMultimap<Integer, ItemSku> itemSkus = itemSkuService.getItemSkusByItemIds(itemIds);

        Map<Integer, ItemSummary> items = new HashMap<>(itemIds.size());
        for (Integer itemId : itemIds) {
            ItemLite itemBase = itemBases.get(itemId);
            if (itemBase == null) {
                continue;
            }

            List<ItemRecommend> recommends = itemRecommends.get(itemId);
            ItemRecommend recommend = Iterables.getFirst(recommends, null);

            List<ItemSku> skus = itemSkus.get(itemId);

            ItemSummary itemElement = new ItemSummary();
            itemElement.setItem(itemBase);
            if (recommend != null) {
                itemElement.setItemRecommend(recommend);
            }
            itemElement.setItemSkus(skus);

            Flash flash = onlineFlashesMapByItemId.get(itemId);
            itemElement.setFlash(flash);

            items.put(itemId, itemElement);
        }

        List<ItemSummary> result = new ArrayList<>(itemIds.size());
        for (Integer itemId : itemIds) {
            result.add(items.get(itemId));
        }
        return result;
    }

    public List<ItemSummary> getSessionItemSummaryList(List<FlashEntry> flashEntries) {

        Map<Integer, FlashEntry> itemIdFlashEntryMap = Transformers.transformAsOneToOneMap(
                flashEntries, FlashEntry.itemIdExtractor);

        List<Integer> itemIds = Transformers
                .transformList(flashEntries, FlashEntry.itemIdExtractor);
        Map<Integer, ItemLite> itemBases = getItemLitesByIds(itemIds);

        ListMultimap<Integer, ItemRecommend> itemRecommends = itemRecommendService
                .getItemRecommendsByItemIds(itemIds);

        ListMultimap<Integer, ItemSku> itemSkus = itemSkuService.getItemSkusByItemIds(itemIds);

        Map<Integer, ItemSummary> items = new HashMap<>(itemIds.size());
        for (Integer itemId : itemIds) {
            ItemLite itemBase = itemBases.get(itemId);
            if (itemBase == null) {
                continue;
            }

            List<ItemRecommend> recommends = itemRecommends.get(itemId);
            ItemRecommend recommend = Iterables.getFirst(recommends, null);

            List<ItemSku> skus = itemSkus.get(itemId);

            ItemSummary itemElement = new ItemSummary();
            itemElement.setItem(itemBase);
            if (recommend != null) {
                itemElement.setItemRecommend(recommend);
            }
            itemElement.setItemSkus(skus);

            Flash flash = itemIdFlashEntryMap.get(itemId).getFlash();
            itemElement.setFlash(flash);

            items.put(itemId, itemElement);
        }

        List<ItemSummary> result = new ArrayList<>(itemIds.size());
        for (Integer itemId : itemIds) {
            if (items.get(itemId) == null) {
                continue;
            }
            result.add(items.get(itemId));
        }
        return result;
    }

    /**
     * 获取商品基础数据
     * 
     * @param itemIds
     * @return
     */
    public Map<Integer, Item> getItemBasesByIds(Collection<Integer> itemIds) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyMap();
        }

        List<Item> itemBases = dao.getItemsByIds(itemIds);
        return Transformers.transformAsOneToOneMap(itemBases, Item.idExtractor);
    }

    /**
     * 获取商品基础数据（轻量级版本）
     * 
     * @param itemIds
     * @return
     */
    public Map<Integer, ItemLite> getItemLitesByIds(Collection<Integer> itemIds) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyMap();
        }

        List<ItemLite> itemBases = dao.getItemLitesByIds(itemIds);
        return Transformers.transformAsOneToOneMap(itemBases, ItemLite.idExtractor);
    }

    /**
     * 获取闪购商品详情
     *
     * @param id
     * @return
     */
    public ItemResult getFlashItem(long id) {
        ItemResult result = getItem(id);
        return result;
    }

    public Item getItem_(long id) {
        Item item = dao.getItemById(id);
        return item;
    }

    public ItemResult getItem(long id) {
        Item item = dao.getItemById(id);
        // 商品状态
        //        ItemStatus itemStatus = itemStatusService.getItemStatusByItemid(id);
        // 细节
        //        ItemDetail itemDetail = itemDetailService.getItemDetailByItemid(id);
        // 评测
        List<ItemAppraise> itemAppraises = getItemAppraise(id, 0, 3);
        // 评论
        List<ItemComment> itemComments = getItemComment(id, 0, 3);
        // 图片
        //        List<ItemImage> images = itemImageService.getItemImagesByItemid(id);
        // 属性
        List<ItemProperty> properties = itemPropertyService.getItemPropertiesByItemid(id);
        // SKU
        List<ItemSku> skus = itemSkuService.getItemSkusByItemid(id);

        // 当前闪购信息
        Flash flash = flashService.getItemCurrentFlash((int) id);

        // 推荐理由
        List<ItemRecommend> itemRecommends = itemRecommendService.getItemRecommendByItemid(id);
        ItemRecommend recommend = Iterables.getFirst(itemRecommends, null);

        ItemResult result = new ItemResult();
        result.setItem(item);
        result.setItemAppraises(itemAppraises);
        result.setItemComments(itemComments);
        result.setItemProperties(properties);
        result.setItemSkus(skus);
        result.setFlash(flash);
        result.setItemRecommend(recommend);
        try {

            result.setBrand(brandService.getBrandById(item.getBrandid()).getName());
            ItemCategory itemCategory = itemCategoryService.getItemCategoryByItemId(id);
            result.setCateName(categoryService.getCategoryBy(Long.valueOf(itemCategory.getCategory()).intValue()).getName());
            result.setItemCategory(itemCategory);
        } catch (Exception e) {
            log.error("get item category is error by itemid=" + id, e);
        }

        result.setMarkupMap(markupList);
        return result;
    }


    public List<ItemAppraise> getItemAppraise(long id, int offset, int limit) {
        return itemAppraiseService.getItemAppraiseByItemid(id, offset, limit);
    }

    public List<ItemComment> getItemComment(long id, int offset, int limit) {
        return Collections.emptyList();
    }

    public long insertItem(ItemResult item) {
        Date now = new Date();
        item.getItem().setCts(now);
        item.getItem().setUts(now);
        createItem(item.getItem());
        long itemid = item.getItem().getId();

        if (item.getItemRecommend() != null) {
            item.getItemRecommend().setCts(now);
            item.getItemRecommend().setUts(now);
            item.getItemRecommend().setItemid(itemid);
            itemRecommendService.createItemRecommend(item.getItemRecommend());
        }

        if (item.getItemSkus() != null) {
            for (ItemSku sku : item.getItemSkus()) {
                sku.setCts(now);
                sku.setUts(now);
                sku.setItemid(itemid);
                itemSkuService.createItemSku(sku);
                String[] pvids = sku.getPvids().split(",");
                for (String pvid : pvids) {
                    ItemProperty property = new ItemProperty();
                    property.setCts(now);
                    property.setUts(now);
                    property.setItemid(itemid);
                    PropertyValue value = propertyValueService.getPropertyValueById(Long
                            .valueOf(pvid));
                    property.setPid(value.getPropertyid());
                    property.setPvid(value.getId());
                    property.setIssku(1);
                    property.setSkuid(sku.getId());
                    itemPropertyService.createItemProperty(property);
                }
            }
        }

        if (item.getItemProperties() != null) {
            for (ItemProperty property : item.getItemProperties()) {
                property.setCts(now);
                property.setUts(now);
                property.setItemid(itemid);
                property.setIssku(0);
                itemPropertyService.createItemProperty(property);
            }
        }

        return itemid;
    }

    public long updateItem(ItemResult item) {
        Item exist = getItemById(item.getItem().getId());
        Date now = new Date();
        item.getItem().setCts(exist.getCts());
        item.getItem().setUts(now);
        updateItemById(item.getItem());
        long itemid = item.getItem().getId();

        itemRecommendService.deleteItemRecommendByItemid(itemid);
        if (item.getItemRecommend() != null) {
            item.getItemRecommend().setCts(now);
            item.getItemRecommend().setUts(now);
            item.getItemRecommend().setItemid(itemid);
            itemRecommendService.createItemRecommend(item.getItemRecommend());
        }

        itemPropertyService.deleteItemPropertyByItemid(itemid);
        itemSkuService.deleteItemSkuByItemid(itemid);
        if (item.getItemSkus() != null) {
            for (ItemSku sku : item.getItemSkus()) {
                sku.setCts(now);
                sku.setUts(now);
                sku.setItemid(itemid);
                itemSkuService.createItemSku(sku);
                String[] pvids = sku.getPvids().split(",");
                for (String pvid : pvids) {
                    ItemProperty property = new ItemProperty();
                    property.setCts(now);
                    property.setUts(now);
                    property.setItemid(itemid);
                    PropertyValue value = propertyValueService.getPropertyValueById(Long
                            .valueOf(pvid));
                    property.setPid(value.getPropertyid());
                    property.setPvid(value.getId());
                    property.setIssku(1);
                    property.setSkuid(sku.getId());
                    itemPropertyService.createItemProperty(property);
                }
            }
        }
        if (item.getItemProperties() != null) {
            for (ItemProperty property : item.getItemProperties()) {
                property.setCts(now);
                property.setUts(now);
                property.setItemid(itemid);
                property.setIssku(0);
                itemPropertyService.createItemProperty(property);
            }
        }

        return itemid;
    }

    public boolean deleteItem(int itemid) {
        // 讲商品状态设置为已删除
        return this.dao.setStatus(itemid, Item.STATUS_DELETED) > 0;
    }

    public boolean isSellable(long itemid) {
        ItemResult item = getItem(itemid);

        // 必须是闪购
        Flash flash = item.getFlash();
        if (flash == null) {
            return false;
        }

        if (!flash.isOnline()) {
            return false;
        }

        // 必须已开始
        Date now = new Date();
        if (now.before(flash.getStarttime())) {
            return false;
        }
        // 必须未结束
        if (now.after(flash.getEndtime())) {
            return false;
        }

        // 未售完
        return !item.isSoldOut();

    }

    public boolean incrItemSales(int itemId, int sales) {
        return this.dao.incrSales(itemId, sales) > 0;
    }

    public boolean setItemSales(int itemId, int sales) {
        return this.dao.setSales(itemId, sales) > 0;
    }

    public boolean setFlash(int itemId, int flashId) {
        return this.dao.setFlash(itemId, flashId) > 0;
    }

    public List<Item> getAllOnlineItem() {
        return dao.getAllOnlineItem();
    }

    public boolean flashIsExist(int itemId) {
        return flashService.getFlashByItemIdAndOnline(itemId) == null ? false : true;
    }

    public boolean brandGroupIsExist(int itemId) {
        return brandgroupItemService.getBrandGroupItemSizeByItemId(itemId) > 0 ? true : false;
    }
}
