package com.hehua.item.model;

import com.hehua.item.domain.PropertyValue;

import java.util.Date;
import java.util.List;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
public class ItemView {
    private long id;
    private int cateid;
    private BrandView brand;
    private String name;
    private double originprice;
    private double realprice;
    private List<PropertyValueView> properties;
    private List<SkuView> skus;
    private PurchaseAddressView purchaseAddress;
    private List<ImageView> images;
    private String detailimage;
    private String detailimagetext;
    private RecommendView recommend;
    private CrowdView crowd;
    private GenderView gender;
    private Date onlinetime;
    private int saletype;
    private Date starttime;
    private Date endtime;
    private WarehouseView warehouse;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCateid() {
        return cateid;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public BrandView getBrand() {
        return brand;
    }

    public void setBrand(BrandView brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginprice() {
        return originprice;
    }

    public void setOriginprice(double originprice) {
        this.originprice = originprice;
    }

    public double getRealprice() {
        return realprice;
    }

    public void setRealprice(double realprice) {
        this.realprice = realprice;
    }

    public List<PropertyValueView> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyValueView> properties) {
        this.properties = properties;
    }

    public List<SkuView> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuView> skus) {
        this.skus = skus;
    }

    public PurchaseAddressView getPurchaseAddress() {
        return purchaseAddress;
    }

    public void setPurchaseAddress(PurchaseAddressView purchaseAddress) {
        this.purchaseAddress = purchaseAddress;
    }

    public List<ImageView> getImages() {
        return images;
    }

    public void setImages(List<ImageView> images) {
        this.images = images;
    }

    public String getDetailimage() {
        return detailimage;
    }

    public void setDetailimage(String detailimage) {
        this.detailimage = detailimage;
    }

    public String getDetailimagetext() {
        return detailimagetext;
    }

    public void setDetailimagetext(String detailimagetext) {
        this.detailimagetext = detailimagetext;
    }

    public RecommendView getRecommend() {
        return recommend;
    }

    public void setRecommend(RecommendView recommend) {
        this.recommend = recommend;
    }

    public CrowdView getCrowd() {
        return crowd;
    }

    public void setCrowd(CrowdView crowd) {
        this.crowd = crowd;
    }

    public GenderView getGender() {
        return gender;
    }

    public void setGender(GenderView gender) {
        this.gender = gender;
    }

    public Date getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Date onlinetime) {
        this.onlinetime = onlinetime;
    }

    public int getSaletype() {
        return saletype;
    }

    public void setSaletype(int saletype) {
        this.saletype = saletype;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public WarehouseView getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseView warehouse) {
        this.warehouse = warehouse;
    }
}
