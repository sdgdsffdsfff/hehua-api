package com.hehua.item.service.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hehua.item.domain.*;
import com.hehua.item.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.image.ImageService;
import com.hehua.user.domain.User;
import com.hehua.user.service.AvatarUtils;
import com.hehua.user.service.UserManager;
import com.hehua.user.service.UserService;

/**
 * Created by hewenjerry on 14-8-23.
 */
@javax.inject.Named
public class ItemManageService {

    @Inject
    private com.hehua.item.service.ItemService itemService;

    @Inject
    private ItemDetailService itemDetailService;

    //    @Inject
    //    private ItemImageService itemImageService;
    @Inject
    private ImageService imageService;

    @Inject
    private ItemPropertyService itemPropertyService;

    @Inject
    private ItemSkuService itemSkuService;

    @Inject
    private UserService userService;

    @Inject
    private ItemRecommendService itemRecommendService;

    @Inject
    private AvatarUtils avatarUtils;

    @Inject
    private UserManager userManager;

    @Inject
    private PropertyService propertyService;

    @Inject
    private PropertyValueService propertyValueService;

    private static final Log logger = LogFactory.getLog(ItemManageService.class);

    public boolean updateItemImageInfo(List<Integer> imageLists, int itemId) {
        Item item = itemService.getItemById(itemId);
        item.setImage(String.valueOf(imageLists.get(0)));
        JSONArray jsonArray = new JSONArray(imageLists.size());
        for (Integer image : imageLists) {
            jsonArray.add(image);
        }
        item.setImages(jsonArray.toJSONString());
        return itemService.updateItemById(item);
    }

    public int addItem(String name, String realPrice, String originPrice, int brandid, int sales,
            String units, String crowedid, int genderid, int purchaseid, int warehouseid,
            int materialid, String desc, int postTypeId) {
        Item item = new Item();
        Date date = new Date();
        item.setUts(date);
        item.setCts(date);
        item.setName(name);
        item.setImage("");
        item.setImages("");

        item.setOriginprice(Double.valueOf(originPrice));
        item.setRealprice(Double.valueOf(realPrice));

        item.setBrandid(brandid);
        item.setSales(sales);
        item.setUnits(units);
        item.setCrowedid(crowedid);
        item.setGenderid(genderid);
        item.setPurchaseid(purchaseid);
        item.setWarehouseid(warehouseid);
        item.setMaterialid(materialid);
        item.setDesc(desc);
        item.setFlashid(0);
        item.setPostagetype(postTypeId);
        item.setStatus(Item.STATUS_OK); // 上线

        try {
            itemService.createItemBy(item);
            return (int) item.getId();
        } catch (Exception e) {
            logger.error("ItemService.addItem is error!", e);
            return 0;
        }

    }

    public int addItem(String name, List<String> images, String realPrice, String originPrice) {
        Item item = new Item();
        Date date = new Date();
        item.setUts(date);
        item.setCts(date);
        item.setName(name);
        if (images != null && images.size() != 0) {
            item.setImage(images.get(0));
            JSONArray jsonArray = new JSONArray(images.size());
            for (String image : images) {
                jsonArray.add(image);
            }
            item.setImages(jsonArray.toJSONString());
        }
        item.setOriginprice(Double.valueOf(originPrice));
        item.setRealprice(Double.valueOf(realPrice));
        item.setStatus(Item.STATUS_OK); // 上线
        try {
            itemService.createItemBy(item);
            return (int) item.getId();
        } catch (Exception e) {
            logger.error("ItemService.addItem is error!", e);
            return 0;
        }
    }

    /**
     *
     * @param itemId 商品id
     * @param brandId 商品的品牌
     * @param desc 商品详情描述
     * @param purchaseId 商品购买地
     * @param warehouseId 商品的仓库
     * @return
     */
    public int addItemDetatil(int itemId, int brandId, String desc, int purchaseId,
            int warehouseId, int genderId, String crowedId, int materialId) {
        ItemDetail itemDetail = new ItemDetail();
        Date date = new Date();
        itemDetail.setUts(date);
        itemDetail.setCts(date);
        itemDetail.setBrandid(Long.valueOf(brandId));
        itemDetail.setItemid(Long.valueOf(itemId));
        itemDetail.setPurchaseid(Long.valueOf(purchaseId));
        itemDetail.setWarehouseid(Long.valueOf(warehouseId));
        itemDetail.setImages("");
        itemDetail.setDesc(desc);
        itemDetail.setImage(1);
        itemDetail.setGenderid(genderId);
        itemDetail.setCrowedid(crowedId);
        itemDetail.setMaterialid(materialId);

        try {
            return itemDetailService.createItemDetailBy(itemDetail);
        } catch (Exception e) {
            logger.error("ItemService.addItemDetatil is error!", e);
            return 0;
        }
    }

    @Deprecated
    // 不需要改方法
    public List<Integer> addItemImage(int itemId, List<String> itemImages) {
        if (itemImages != null && itemImages.size() != 0) {
            List<Integer> retList = new ArrayList<Integer>(itemImages.size());
            for (int i = 0; i <= itemImages.size(); i++) {
                ItemImage itemImage = new ItemImage();
                if (i == 0) {
                    itemImage.setIsmaster(1);
                } else {
                    itemImage.setIsmaster(0);
                }

                itemImage.setUts(new Date());
                itemImage.setCts(new Date());
                itemImage.setImagelocation(itemImages.get(0));
                itemImage.setItemid(itemId);

                //                itemImageService.createItemImageBy(itemImage);
                retList.add(Long.valueOf(itemImage.getId()).intValue());
            }

            return retList;

        } else {
            return null;
        }

    }

    public int addItemProperty(int itemId, int pId, int pvId) {
        ItemProperty itemProperty = new ItemProperty();
        itemProperty.setItemid(itemId);
        itemProperty.setUts(new Date());
        itemProperty.setCts(new Date());
        itemProperty.setPid(pId);
        itemProperty.setPvid(pvId);
        itemProperty.setIssku(0);
        itemProperty.setSort(0);
        itemProperty.setSkuid(0);

        try {
            return itemPropertyService.createItemPropertyBy(itemProperty);
        } catch (Exception e) {
            logger.error("ItemService.addItemProperty is error!", e);
            return 0;
        }
    }

    public int addItemWithSkuProperty(JSONObject jsonObject, int itemId, int skuId) {
        ItemProperty itemProperty = new ItemProperty();
        itemProperty.setItemid(itemId);
        itemProperty.setUts(new Date());
        itemProperty.setCts(new Date());
        itemProperty.setIssku(1);
        itemProperty.setSort(0);
        itemProperty.setSkuid(skuId);

        int pid = jsonObject.getIntValue(ItemProperty.PROPERTY_ID);
        itemProperty.setPid(pid);
        int pvid = jsonObject.getIntValue(itemProperty.PROPERTY_VID);
        itemProperty.setPvid(pvid);

        Property property = propertyService.getPropertyById(pid);
        if (property != null && !StringUtils.equalsIgnoreCase(property.getName(), jsonObject.getString(ItemProperty.PROPERTY_NAME))) {
            itemProperty.setPropertyExtJSON(jsonObject);
        }

        PropertyValue propertyValue = propertyValueService.getPropertyValueById(pvid);
        if (propertyValue != null && !StringUtils.equalsIgnoreCase(propertyValue.getName(), jsonObject.getString(ItemProperty.PROPERTY_VNAME))) {
            itemProperty.setPropertyValueExtJSON(jsonObject);
        }

        try {
            return itemPropertyService.createItemPropertyBy(itemProperty);
        } catch (Exception e) {
            logger.error("ItemService.addItemWithSkuProperty is error!", e);
            return 0;
        }
    }

    public int addItemSku(int itemId, int quantity, String pvid, String barCode, String realPrice,
            String originPrice) {
        ItemSku itemSku = new ItemSku();
        itemSku.setItemid(itemId);
        itemSku.setUts(new Date());
        itemSku.setCts(new Date());
        itemSku.setBarcode(barCode);
        itemSku.setOriginprice(Double.valueOf(originPrice));
        itemSku.setRealprice(Double.valueOf(realPrice));
        itemSku.setPvids(pvid);
        itemSku.setQuantity(quantity);
        itemSku.setStatus(1);

        try {
            itemSkuService.createItemSkuBy(itemSku);
            return (int) itemSku.getId();
        } catch (Exception e) {
            logger.error("ItemService.addItemSku is error!", e);
            return 0;
        }
    }

    public List<User> getAllDaren() {
        ResultView<List<User>> result = userService.getDarenList();
        return result.getData();
    }

    public int addItemRecommend(int itemId, int darenId, String reason) {
        User user = userManager.getUserById(darenId);
        ItemRecommend itemRecommend = new ItemRecommend();
        itemRecommend.setCts(new Date());
        itemRecommend.setUts(new Date());
        itemRecommend.setName(user.getName());
        //TODO: type什么意思
        itemRecommend.setType("");

        String avatarUrl = avatarUtils.getAvatarUrl(user.getAvatarid());
        itemRecommend.setAvatar(avatarUrl);
        itemRecommend.setReason(reason);
        itemRecommend.setUid(darenId);
        itemRecommend.setItemid(itemId);

        try {
            return itemRecommendService.createItemRecommendBy(itemRecommend);
        } catch (Exception e) {
            logger.error("ItemService.addItemRecommend is error!", e);
            return 0;
        }

    }
}
