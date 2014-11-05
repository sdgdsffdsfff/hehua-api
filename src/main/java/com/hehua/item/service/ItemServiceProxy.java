package com.hehua.item.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.hehua.item.domain.Brand;
import com.hehua.item.domain.Category;
import com.hehua.item.domain.Item;
import com.hehua.item.domain.ItemImage;
import com.hehua.item.domain.ItemProperty;
import com.hehua.item.domain.ItemRecommend;
import com.hehua.item.domain.ItemSku;
import com.hehua.item.domain.Property;
import com.hehua.item.domain.PropertyValue;
import com.hehua.item.domain.PurchaseAddress;
import com.hehua.item.domain.Warehouse;
import com.hehua.item.model.BrandView;
import com.hehua.item.model.CategoryResult;
import com.hehua.item.model.CategoryView;
import com.hehua.item.model.ImageView;
import com.hehua.item.model.ItemResult;
import com.hehua.item.model.ItemView;
import com.hehua.item.model.PropertyValueView;
import com.hehua.item.model.PropertyView;
import com.hehua.item.model.PurchaseAddressView;
import com.hehua.item.model.RecommendView;
import com.hehua.item.model.SkuView;
import com.hehua.item.model.WarehouseView;

/**
 * 编辑页面接口代理
 *
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
@javax.inject.Named
public class ItemServiceProxy {

    @Inject
    private ItemService itemService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private BrandService brandService;

    @Inject
    private PropertyService propertyService;

    @Inject
    private PropertyValueService propertyValueService;

    @Inject
    private PurchaseAddressService purchaseAddressService;

    @Inject
    private WarehouseService warehouseService;

    //    @Inject
    //    private CrowdService crowdService;
    //
    //    @Inject
    //    private GenderService genderService;

    //**** begin category

    public List<CategoryResult> getAllCategory() {
        List<Category> categories = categoryService.getAllCategoryByParentId(0);
        List<Category> allCategories = categoryService.getAllCategory();

        List<CategoryResult> result = new ArrayList<CategoryResult>();
        for (Category category : categories) {
            if (category.getStatus() != 0) continue;
            CategoryResult CategoryResult = new CategoryResult(category.getId(), category.getName());
            List<CategoryResult> subcates = new ArrayList<CategoryResult>();
            for (Category subcate : allCategories) {
                if (subcate.getParentid() == category.getId() && subcate.getStatus() == 0) {
                    subcates.add(new CategoryResult(category.getId(), category.getName()));
                }
            }
            CategoryResult.setSubcates(subcates);
            result.add(CategoryResult);

        }
        return result;
    }

    public long addCategory(CategoryView view) {
        Category category = new Category();
        category.setCts(new Date());
        category.setUts(category.getCts());
        category.setName(view.getName());
        category.setParentid(view.getParentid());
        categoryService.createCategory(category);
        return category.getId();
    }

    public long updateCategory(CategoryView view) {
        Category category = categoryService.getCategoryById(view.getId());
        category.setUts(new Date());
        category.setName(view.getName());
        category.setParentid(view.getParentid());
        categoryService.updateCategoryById(category);
        return category.getId();
    }

    public boolean removeCategory(long id) {
        return categoryService.deleteCategoryById(id);
    }

    //**** end category

    //**** begin brand

    public List<BrandView> getAllBrand() {
        List<Brand> brands = brandService.getAllBrand();
        List<BrandView> result = new ArrayList<BrandView>();
        for (Brand brand : brands) {
            result.add(new BrandView(brand.getId(), brand.getName(), brand.getDesc()));
        }
        return result;
    }

    public long addBrand(BrandView view) {
        Brand brand = new Brand();
        brand.setCts(new Date());
        brand.setUts(brand.getCts());
        brand.setName(view.getName());
        brand.setDesc(view.getDesc());
        brandService.createBrand(brand);
        return brand.getId();
    }

    //**** end brand

    //**** begin Property

    public List<PropertyView> getAllProperty() {
        List<Property> properties = propertyService.getAllProperty();
        List<PropertyView> result = new ArrayList<PropertyView>();
        for (Property property : properties) {
            result.add(new PropertyView(property.getId(), property.getName()));
        }
        return result;
    }

    public long addProperty(PropertyView view) {
        Property property = new Property();
        property.setCts(new Date());
        property.setUts(property.getCts());
        property.setName(view.getName());
        propertyService.createProperty(property);
        return property.getId();
    }

    //**** end Property

    //**** begin Property Value

    public List<PropertyValueView> getPropertyValuesByPropertyId(long id) {
        List<PropertyValue> properties = propertyValueService.getPropertyValuesByPropertyId(id);
        List<PropertyValueView> result = new ArrayList<PropertyValueView>();
        for (PropertyValue property : properties) {
            result.add(new PropertyValueView(property.getId(), property.getName(), property
                    .getPropertyid()));
        }
        return result;
    }

    public long addPropertyValue(PropertyValueView view) {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.setCts(new Date());
        propertyValue.setUts(propertyValue.getCts());
        propertyValue.setName(view.getName());
        propertyValue.setPropertyid(view.getPropertyid());
        propertyValueService.createPropertyValue(propertyValue);
        return propertyValue.getId();
    }

    //**** end Property Value

    //**** begin Purchase Address

    public List<PurchaseAddressView> getAllPurchaseAddress() {
        List<PurchaseAddress> purchaseAddresses = purchaseAddressService.getAllPurchaseAddress();
        List<PurchaseAddressView> result = new ArrayList<PurchaseAddressView>();
        for (PurchaseAddress address : purchaseAddresses) {
            result.add(new PurchaseAddressView(address.getId(), address.getName()));
        }
        return result;
    }

    public long addPurchaseAddress(PurchaseAddressView view) {
        PurchaseAddress address = new PurchaseAddress();
        address.setCts(new Date());
        address.setUts(address.getCts());
        address.setName(view.getName());
        purchaseAddressService.createPurchaseAddress(address);
        return address.getId();
    }

    //**** end Purchase Address

    //**** begin Warehouse

    public List<WarehouseView> getAllWarehouse() {
        List<Warehouse> warehouses = warehouseService.getAllWarehouse();
        List<WarehouseView> result = new ArrayList<WarehouseView>();
        for (Warehouse warehouse : warehouses) {
            result.add(new WarehouseView(warehouse.getId(), warehouse.getName()));
        }
        return result;
    }

    public long addWarehouse(WarehouseView view) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCts(new Date());
        warehouse.setUts(warehouse.getCts());
        warehouse.setName(view.getName());
        warehouseService.createWarehouse(warehouse);
        return warehouse.getId();
    }

    //**** end Warehouse

    //**** begin Crowd

    //    public List<CrowdView> getAllCrowd() {
    //        List<Crowd> crowds = crowdService.getAllCrowd();
    //        List<CrowdView> result = new ArrayList<CrowdView>();
    //        for (Crowd crowd : crowds) {
    //            result.add(new CrowdView(crowd.getId(), crowd.getName()));
    //        }
    //        return result;
    //    }
    //**** end Crowd

    //**** begin Gender

    //    public List<GenderView> getAllGender() {
    //        List<Gender> genders = genderService.getAllGender();
    //        List<GenderView> result = new ArrayList<GenderView>();
    //        for (Gender gender : genders) {
    //            result.add(new GenderView(gender.getId(), gender.getName()));
    //        }
    //        return result;
    //    }
    //**** end Gender

    //**** begin Item

    private ItemResult convertItemView(ItemView item) {
        ItemResult result = new ItemResult();

        Item item1 = new Item();
        item1.setName(item.getName());
        item1.setOriginprice(item.getOriginprice());
        item1.setRealprice(item.getRealprice());
        result.setItem(item1);

        ItemRecommend itemRecommend = new ItemRecommend();
        if (item.getRecommend() != null) {
            itemRecommend.setUid(item.getRecommend().getUid());
            itemRecommend.setName(item.getRecommend().getName());
            itemRecommend.setType(item.getRecommend().getType());
            itemRecommend.setAvatar(item.getRecommend().getAvatar());
            itemRecommend.setReason(item.getRecommend().getReason());
        }
        result.setItemRecommend(itemRecommend);

        //        ItemStatus itemStatus = new ItemStatus();
        //        itemStatus.setSaletype(item.getSaletype());
        //        itemStatus.setStarttime(item.getStarttime());
        //        itemStatus.setEndtime(item.getEndtime());
        //        itemStatus.setOnlinetime(item.getOnlinetime());
        //        result.setItemStatus(itemStatus);

        //        ItemDetail itemDetail = new ItemDetail();
        //        itemDetail.setBrandid(item.getBrand().getId());
        ////        itemDetail.setImage(item.getDetailimage());
        ////        itemDetail.setImagetext(item.getDetailimagetext());
        //        itemDetail.setPurchaseid(item.getPurchaseAddress().getId());
        //        itemDetail.setWarehouseid(item.getWarehouse().getId());
        //        result.setItemDetail(itemDetail);

        List<ItemImage> itemImages = new ArrayList<ItemImage>();
        if (item.getImages() != null) {
            for (ImageView image : item.getImages()) {
                ItemImage itemImage = new ItemImage();
                itemImage.setImageid(image.getImageid());
                itemImage.setImagelocation(image.getLocation());
                itemImage.setIsmaster(image.isMaster() ? 1 : 0);
                itemImages.add(itemImage);
            }
        }
        result.setItemImages(itemImages);

        List<ItemProperty> itemProperties = new ArrayList<ItemProperty>();
        if (item.getProperties() != null) {
            for (PropertyValueView property : item.getProperties()) {
                ItemProperty itemProperty = new ItemProperty();
                itemProperty.setPid(property.getPropertyid());
                itemProperty.setPvid(property.getId());
                itemProperty.setIssku(0);
                itemProperties.add(itemProperty);
            }
        }

        List<ItemSku> itemSkus = new ArrayList<ItemSku>();
        if (item.getSkus() != null) {
            for (SkuView sku : item.getSkus()) {
                ItemSku itemSku = new ItemSku();
                String pvids = String.format("%d,%d", sku.getPvid1(), sku.getPvid2());
                itemSku.setPvids(pvids);
                itemSku.setQuantity(sku.getQuantity());
                itemSkus.add(itemSku);
                // TODO skuid
            }
        }

        result.setItemSkus(itemSkus);
        result.setItemProperties(itemProperties);
        return result;
    }

    private ItemView convertItemResult(ItemResult item) {
        ItemView result = new ItemView();

        Item item1 = item.getItem();
        result.setId(item1.getId());
        result.setName(item1.getName());
        result.setOriginprice(item1.getOriginprice());
        result.setRealprice(item1.getRealprice());

        RecommendView recommendView = new RecommendView();
        if (item.getItemRecommend() != null) {
            recommendView.setUid(item.getItemRecommend().getUid());
            recommendView.setName(item.getItemRecommend().getName());
            recommendView.setType(item.getItemRecommend().getType());
            recommendView.setAvatar(item.getItemRecommend().getAvatar());
            recommendView.setReason(item.getItemRecommend().getReason());
        }
        result.setRecommend(recommendView);

        //        result.setSaletype(item.getItemStatus().getSaletype());
        result.setStarttime(item.getFlash().getStarttime());
        result.setEndtime(item.getFlash().getEndtime());
        //        result.setOnlinetime(item.getItemStatus().getOnlinetime());

        BrandView brandView = new BrandView();
        brandView.setId(item.getItem().getBrandid());
        result.setBrand(brandView);

        //        CrowdView crowdView = new CrowdView();
        //        crowdView.setId(item.getItemDetail().getCrowdid());
        //        result.setCrowd(crowdView);
        //
        //        GenderView genderView = new GenderView();
        //        genderView.setId(item.getItemDetail().getGenderid());
        //        result.setGender(genderView);
        //
        //        result.setDetailimagetext(item.getItemDetail().getImagetext());

        PurchaseAddressView purchaseAddressView = new PurchaseAddressView();
        purchaseAddressView.setId(item.getItem().getPurchaseid());
        result.setPurchaseAddress(purchaseAddressView);

        WarehouseView warehouseView = new WarehouseView();
        warehouseView.setId(item.getItem().getWarehouseid());
        result.setWarehouse(warehouseView);

        List<ImageView> imageViews = new ArrayList<ImageView>();
        if (item.getItemImages() != null) {
            for (ItemImage image : item.getItemImages()) {
                ImageView imageView = new ImageView();
                imageView.setImageid(image.getImageid());
                imageView.setMaster(image.getIsmaster() != 0);
                imageViews.add(imageView);
            }
        }
        result.setImages(imageViews);

        List<PropertyValueView> propertyValueViews = new ArrayList<PropertyValueView>();
        if (item.getItemProperties() != null) {
            for (ItemProperty property : item.getItemProperties()) {
                if (property.getIssku() != 0) continue;
                PropertyValueView view = new PropertyValueView();
                view.setPropertyid(property.getPid());
                view.setId(property.getPvid());
                propertyValueViews.add(view);
            }
        }
        result.setProperties(propertyValueViews);

        List<SkuView> skuViews = new ArrayList<SkuView>();
        if (item.getItemSkus() != null) {
            for (ItemSku sku : item.getItemSkus()) {
                SkuView view = new SkuView();
                String[] pvids = sku.getPvids().split(",");
                view.setPvid1(Long.valueOf(pvids[0]));
                if (pvids.length > 1) view.setPvid2(Long.valueOf(pvids[1]));
                view.setQuantity(sku.getQuantity());
                skuViews.add(view);
            }
        }
        result.setSkus(skuViews);

        return result;
    }

    public long insertItem(ItemView item) {
        return itemService.insertItem(convertItemView(item));
    }

    public long updateItem(ItemView item) {
        return itemService.updateItem(convertItemView(item));
    }

    public ItemView getItem(long id) {
        return convertItemResult(itemService.getItem(id));
    }

    public boolean deleteItem(int id) {
        return itemService.deleteItem(id);
    }

    //**** end Item

}
