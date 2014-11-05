package com.hehua.item.model;

import java.util.List;
import java.util.Map;

import com.hehua.item.domain.*;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
public class ItemResult {

    private Item item;

    private ItemRecommend itemRecommend;

    private List<Property> propertyList;

    private List<Map<String, String>> markupMap;

    private ItemAttr itemAttr;

    private List<ItemAppraise> itemAppraises;

    private List<ItemComment> itemComments;

    private List<ItemImage> itemImages;

    private List<ItemProperty> itemProperties;

    private ItemCategory itemCategory;

    private String cateName;

    private List<ItemSku> itemSkus;

    private String brand;

    private String crowd;

    private String gender;

    private Flash flash;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemRecommend getItemRecommend() {
        return itemRecommend;
    }

    public void setItemRecommend(ItemRecommend itemRecommend) {
        this.itemRecommend = itemRecommend;
    }

    public ItemAttr getItemAttr() {
        return itemAttr;
    }

    public void setItemAttr(ItemAttr itemAttr) {
        this.itemAttr = itemAttr;
    }

    public List<ItemAppraise> getItemAppraises() {
        return itemAppraises;
    }

    public void setItemAppraises(List<ItemAppraise> itemAppraises) {
        this.itemAppraises = itemAppraises;
    }

    public List<ItemComment> getItemComments() {
        return itemComments;
    }

    public void setItemComments(List<ItemComment> itemComments) {
        this.itemComments = itemComments;
    }

    public List<ItemImage> getItemImages() {
        return itemImages;
    }

    public void setItemImages(List<ItemImage> itemImages) {
        this.itemImages = itemImages;
    }

    public List<ItemProperty> getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(List<ItemProperty> itemProperties) {
        this.itemProperties = itemProperties;
    }

    public List<ItemSku> getItemSkus() {
        return itemSkus;
    }

    public void setItemSkus(List<ItemSku> itemSkus) {
        this.itemSkus = itemSkus;
    }

    /**
     * 通过SKU判断是否卖光
     *
     * @param
     * @return
     */
    public boolean isSoldOut() {
        boolean soldOut = true;
        if (itemSkus != null) {
            for (ItemSku sku : itemSkus) {
                if (sku.getQuantity() > 0) {
                    soldOut = false;
                    break;
                }
            }
        }
        return soldOut;
    }

    public String getBrand() {
        return brand;
    }

    public String getCrowd() {
        return crowd;
    }

    public String getGender() {
        return gender;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCrowd(String crowd) {
        this.crowd = crowd;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<Map<String, String>> getMarkupMap() {
        return markupMap;
    }

    public void setMarkupMap(List<Map<String, String>> markupMap) {
        this.markupMap = markupMap;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }
}
