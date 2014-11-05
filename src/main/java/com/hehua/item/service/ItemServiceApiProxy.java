package com.hehua.item.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.Transformers;
import com.hehua.commons.time.DateUtils;
import com.hehua.commons.utils.JSONUtils;
import com.hehua.framework.image.ImageService;
import com.hehua.framework.image.domain.Image;
import com.hehua.item.domain.Brand;
import com.hehua.item.domain.BrandGroup;
import com.hehua.item.domain.BrandgroupItem;
import com.hehua.item.domain.Category;
import com.hehua.item.domain.Crowd;
import com.hehua.item.domain.Daren;
import com.hehua.item.domain.Flash;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.domain.Gender;
import com.hehua.item.domain.Item;
import com.hehua.item.domain.ItemAppraise;
import com.hehua.item.domain.ItemComment;
import com.hehua.item.domain.ItemDetail;
import com.hehua.item.domain.ItemProperty;
import com.hehua.item.domain.ItemRecommend;
import com.hehua.item.domain.ItemSku;
import com.hehua.item.domain.Property;
import com.hehua.item.domain.PropertyValue;
import com.hehua.item.manager.PropertyManager;
import com.hehua.item.model.FlashSession;
import com.hehua.item.model.ItemLite;
import com.hehua.item.model.ItemPropertyView;
import com.hehua.item.model.ItemResult;
import com.hehua.item.model.ItemSummary;
import com.hehua.user.domain.Baby;
import com.hehua.user.service.AvatarUtils;
import com.hehua.user.service.UserManager;
import com.hehua.user.service.UserService;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
@javax.inject.Named
public class ItemServiceApiProxy {

    @Autowired
    private FlashSessionService flashSessionService;

    @Inject
    private ItemService itemService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private ItemDetailService itemDetailService;

    @Inject
    private PropertyService propertyService;

    @Inject
    private PropertyValueService propertyValueService;

    @Inject
    private PropertyManager propertyManager;

    @Inject
    private ItemPropertyRender itemPropertyRender;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CrowedService crowedService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private ItemAppraiseService appraiseService;

    @Autowired
    private BrandgroupItemService brandgroupItemService;

    @Autowired
    private BrandGroupService brandGroupService;

    @Autowired
    private VoteFlashSessionService voteFlashSessionService;

    @Autowired
    private DarenService darenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private AvatarUtils avatarUtils;

    private static final Log logger = LogFactory.getLog(ItemServiceApiProxy.class);

    public JSONObject getFlashItemList(int babyStatus, int gender, String edc, String birthday,
            int offset, int limit) {
        //        int babyType = 0;
        //        return getFlashItemList(babyType, gender, offset, limit);

        try {
            Baby newBaby = Baby.createNewBaby(true, babyStatus, edc, gender, birthday);
            return getFlashItemList(newBaby, offset, limit);
        } catch (ParseException e) {
            throw new RuntimeException("edc=" + edc + ",birthday=" + birthday, e);
        }
    }

    public Double getBrandGroupByLowestDiscount(int brandGroupId) {
        BrandGroup brandGroup = brandGroupService.getBrandGroupById(brandGroupId);
        if (brandGroup == null) {
            return 10.0;
        }
        //TODO: 只计算了品牌团上线商品的最低折扣，没有包含下线商品
        List<BrandgroupItem> brandgroupItemList = brandgroupItemService
                .getFlashItemListByBrandGrandId(brandGroupId, 1);
        if (CollectionUtils.isEmpty(brandgroupItemList)) {
            return 10.0;
        }
        List<Integer> itemIds = Transformers.transformList(brandgroupItemList,
                BrandgroupItem.itemIdExtractor);
        Map<Integer, ItemLite> itemBases = itemService.getItemLitesByIds(itemIds);
        if (itemBases == null || itemBases.size() == 0) {
            return 10.0;
        }

        List<ItemLite> itemLites = Transformers.transformAsList(itemBases,
                new Function<ItemLite, ItemLite>() {

                    @Override
                    public ItemLite apply(ItemLite itemLite) {
                        return itemLite;
                    }
                });
        return getItemLiteListByLowDiscount(itemLites);
    }

    public JSONObject getFlashItemListByBrandGroup(int brandGroupId, int offset, int limit) {
        BrandGroup brandGroup = brandGroupService.getBrandGroupById(brandGroupId);
        if (brandGroup == null) {
            return getErrorJson();
        }

        List<BrandgroupItem> brandgroupItemList = brandgroupItemService
                .getFlashItemListByBrandGroupAndPage(brandGroupId, offset, limit);
        if (CollectionUtils.isEmpty(brandgroupItemList)) {
            return getErrorJson();
        }

        int size = brandgroupItemService.getBrandGroupItemSizeBy(brandGroupId);
        boolean hasMore = size > limit;

        List<ItemSummary> itemSummaryList = itemService
                .getBrandGroupItemSummaryList(brandgroupItemList);

        Double discount = getItemListByLowDiscount(itemSummaryList);

        JSONArray array = renderItemBases(itemSummaryList, brandGroup.getEndtime());
        FlashSession flashSession = flashSessionService.currentSession();
        JSONObject jsonObject = renderFlashPaginator(flashSession, array, offset, limit, hasMore);
        return renderBrandGroup(brandGroup, jsonObject, discount);
    }

    /**
     * 得到商品列表最低折扣
     *
     * @param itemSummaryList
     * @return
     */
    private double getItemListByLowDiscount(List<ItemSummary> itemSummaryList) {
        List<Double> itemPrice = Transformers.transformList(itemSummaryList,
                new Function<ItemSummary, Double>() {

                    @Override
                    public Double apply(ItemSummary o) {
                        return getItemDiscount(o.getItem().getRealprice(), o.getItem()
                                .getOriginprice(), true);
                    }
                });

        Collections.sort(itemPrice);

        return itemPrice.get(0);
    }

    private double getItemLiteListByLowDiscount(List<ItemLite> itemLiteList) {
        List<Double> itemPrice = Transformers.transformList(itemLiteList,
                new Function<ItemLite, Double>() {

                    @Override
                    public Double apply(ItemLite o) {
                        return getItemDiscount(o.getRealprice(), o.getOriginprice(), true);
                    }
                });

        Collections.sort(itemPrice);

        return itemPrice.get(0);
    }

    public double getItemDiscount(double realPrice, double originPrice, boolean isSplit) {
        String tempStr = String.valueOf((realPrice / originPrice) * 10);
        String discountStr = "10";
        if (isSplit) {
            return Double.valueOf(tempStr.substring(0, tempStr.indexOf(".")));
        }

        if (tempStr.indexOf(".") != -1) {
            if (tempStr.indexOf(".0") != -1) {
                discountStr = tempStr.substring(0, tempStr.indexOf("."));
            } else {
                discountStr = tempStr.substring(0, tempStr.indexOf(".") + 2);
            }
        }
        return Double.valueOf(discountStr);
    }

    private JSONObject getErrorJson() {
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("code", 11001);
        jsonObject.put("message", "品牌团下不存在商品");
        JSONObject retJson = new JSONObject(1);
        retJson.put("error", jsonObject);

        return retJson;
    }

    public JSONObject getFlashItemList(Baby baby, int offset, int limit) {
        FlashSession flashSession = flashSessionService.currentSession();
        List<ItemSummary> items = itemService.getSessionItemSummaryList(flashSession, baby, offset,
                limit + 1);
        boolean hasMore = items.size() > offset + limit;
        if (hasMore) {
            items = items.subList(offset, offset + limit);
        }
        JSONArray array = renderItemBases(items, null);
        return renderFlashPaginator(flashSession, array, offset, limit, hasMore);

    }

    /**
     * @param items
     * @return
     */
    private JSONArray renderItemBases(List<ItemSummary> items, Date endTime) {
        JSONArray array = new JSONArray(items.size());
        for (ItemSummary item : items) {
            JSONObject o = renderItemBase(item, endTime);
            array.add(o);
        }
        return array;
    }

    /**
     * @param item
     * @return
     */
    private JSONObject renderItemBase(ItemSummary item, Date endTime) {
        JSONObject o = new JSONObject();
        o.put("id", item.getItem().getId());
        o.put("name", item.getItem().getName());
        o.put("originprice", item.getItem().getOriginprice());
        o.put("realprice", item.getItem().getRealprice());
        o.put("discount",
                getItemDiscount(item.getItem().getRealprice(), item.getItem().getOriginprice(),
                        false));
        // 为了兼容id
        if (NumberUtils.isNumber(item.getItem().getImage())) {
            try {
                Image image = imageService.getImageById(Long.valueOf(item.getItem().getImage()));
                if (image != null) {
                    o.put("image", image.getUrl());
                }
            } catch (Exception e) {
                o.put("image", "");
            }
        }
        o.put("sales", item.getItem().getSales());
        o.put("expiration",
                item.getFlash() != null ? (item.getFlash().getEndtime().getTime() / 1000) : endTime
                        .getTime() / 1000);
        boolean soldOut = item.isSoldOut();
        o.put("soldout", soldOut);

        if (item.getItemRecommend() != null) {
            JSONObject rec = new JSONObject();
            rec.put("avatar", item.getItemRecommend().getAvatar());
            rec.put("reason", item.getItemRecommend().getReason());
            o.put("recommend", rec);
        }
        return o;
    }

    /**
     * 渲染分页
     *
     * @param enties
     * @param offset
     * @param limit
     * @param hasMore
     * @return
     */
    private JSONObject renderFlashPaginator(FlashSession flashSession, JSONArray enties,
            int offset, int limit, boolean hasMore) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("entities", enties);
        data.put("offset", offset);
        data.put("limit", limit);
        data.put("hasMore", hasMore);

        //        String version = (String) HehuaRequestContext.getRequestAttributes().getAttribute(
        //                "version", RequestAttributes.SCOPE_REQUEST);
        String version = null;

        if (StringUtils.equals("1.0", version) || StringUtils.equals("0.1", version)) {
            data.put("flashSessionEndTime", flashSession.getEndTime());
        } else {
            data.put("flashSessionEndTime", DateUtils.getUnitxTimestamp(flashSession.getEndTime()));
        }

        result.put("data", data);

        return result;
    }

    public JSONObject renderBrandGroup(BrandGroup brandGroup, JSONObject retObject, double discount) {
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("name", brandGroup.getName());
        jsonObject.put("desc", brandGroup.getDesc());
        jsonObject.put("discount", discount);

        try {
            jsonObject.put("image",
                    imageService.getImageById(Long.valueOf(brandGroup.getImageurl())).getUrl());
        } catch (Exception e) {
            logger.error("get image url fail by imageId=" + brandGroup.getImageurl(), e);
            jsonObject.put("image", "");
        }
        JSONObject jsonDataObject = (JSONObject) retObject.get("data");
        jsonDataObject.put("brand", jsonObject);

        return retObject;
    }

    /**
     * 渲染分页
     *
     * @param enties
     * @param offset
     * @param limit
     * @param hasMore
     * @return
     */
    private JSONObject renderPaginator(JSONArray enties, int offset, int limit, boolean hasMore) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("entities", enties);
        data.put("offset", offset);
        data.put("limit", limit);
        data.put("hasMore", hasMore);
        result.put("data", data);
        return result;
    }

    /**
     * 渲染分页
     *
     * @param enties
     * @param offset
     * @param limit
     * @param hasMore
     * @return
     */
    private JSONObject renderPaginator(int count, JSONArray enties, int offset, int limit,
            boolean hasMore) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("count", count);
        data.put("entities", enties);
        data.put("offset", offset);
        data.put("limit", limit);
        data.put("hasMore", hasMore);
        result.put("data", data);
        return result;
    }

    private JSONObject renderPaginatorV2(BigDecimal count, int peopleNum, JSONArray enties,
            int offset, int limit, boolean hasMore) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("totalScore", count);
        data.put("entities", enties);
        data.put("offset", offset);
        data.put("limit", limit);
        data.put("hasMore", hasMore);
        data.put("applyNum", (peopleNum == 0 ? 20 : peopleNum) + "人参加");

        result.put("data", data);
        return result;
    }

    /**
     * 获取商品详情
     *
     * @param item
     * @return
     */
    private JSONObject renderItem(ItemResult item, boolean newVersion) {
        // 基本信息
        JSONObject o = new JSONObject();
        o.put("id", item.getItem().getId());
        o.put("name", item.getItem().getName());
        o.put("originprice", item.getItem().getOriginprice());
        o.put("realprice", item.getItem().getRealprice());
        o.put("discount",
                getItemDiscount(item.getItem().getRealprice(), item.getItem().getOriginprice(),
                        false));
        List<String> imageUrlList = convertImageIdToImageUrl(item);
        o.put("images",
                imageUrlList == null ? new Object[] {} : JSONUtils.convertJSONArrayBy(imageUrlList));
        o.put("sales", item.getItem().getSales());
        o.put("units", item.getItem().getUnits());

        //// 0 仓库 1 上架 2 待上架 4 下架
        int clientStatus = item.getItem().getStatus() == Item.STATUS_OK ? 1 : 4;
        o.put("status", clientStatus);

        // 评测信息
        int appraisesCount = appraiseService.getItemAppraisesCount(item.getItem().getId());
        if (newVersion) {
            JSONObject app = new JSONObject();
            app.put("count", appraisesCount);
            app.put("itemid", item.getItem().getId());
            app.put("name", "商品评测");
            if (item.getItemAppraises() != null && !item.getItemAppraises().isEmpty()) {
                renderAppraiseInfo(item, app);
            }
            o.put("appraise", app);

        } else {
            if (item.getItemAppraises() != null && !item.getItemAppraises().isEmpty()) {
                JSONObject app = new JSONObject();
                app.put("count", appraisesCount);
                app.put("appraises", renderItemAppraises(item.getItemAppraises(), true));
                o.put("appraise", app);
            }
        }

        // 详情信息
        JSONObject detail = renderItemDetail(item);
        o.put("detail", detail);

        // 评论信息
        if (item.getItemComments() != null && !item.getItemComments().isEmpty()) {
            JSONObject comments = new JSONObject();
            comments.put("count", item.getItemComments().size());
            comments.put("comments", renderItemComments(item.getItemComments()));
            o.put("comment", comments);
        }

        // skus
        List<ItemSku> itemSkus = item.getItemSkus();
        if (CollectionUtils.isNotEmpty(itemSkus)) {
            JSONArray jsonSkuArray = renderSkus(itemSkus);
            o.put("skus", jsonSkuArray);
        }

        if (CollectionUtils.isNotEmpty(item.getMarkupMap())) {
            o.put("services", item.getMarkupMap());
        }

        List<ItemProperty> itemProperties = item.getItemProperties();
        if (CollectionUtils.isNotEmpty(itemProperties)) {
            JSONArray array = renderItemPropMetas(itemProperties);
            o.put("skuprops", array);
        }

        return o;
    }

    private void renderAppraiseInfo(ItemResult item, JSONObject app) {
        JSONObject detail = new JSONObject();
        JSONArray jsonArray = new JSONArray(1);
        ItemAppraise itemAppraise = item.getItemAppraises().get(0);
        detail.put("id", itemAppraise.getId());
        detail.put("title", itemAppraise.getTitle());
        detail.put("summary", itemAppraise.getSummary());
        detail.put("score", itemAppraise.getScore());
        jsonArray.add(detail);
        app.put("detail", jsonArray);

        JSONObject jsonObject = new JSONObject(5);
        Daren daren = darenService.getDarenInfo(itemAppraise.getUid());
        if (daren != null) {
            jsonObject.put("id", daren.getUserid());
            try {
                jsonObject.put("avatar", imageService.getImageById(daren.getAvatarid()).getUrl());
            } catch (Exception e) {
                logger.error("", e);
                jsonObject.put("avatar", "");
            }
            jsonObject.put("name", daren.getName());
            jsonObject.put("title", daren.getTitle());
        }
        app.put("daren", jsonObject);
    }

    public List<String> convertImageIdToImageUrl(ItemResult item) {
        String images = item.getItem().getImages();
        if (org.apache.commons.lang.StringUtils.isEmpty(images)) {
            return null;
        }
        JSONArray jsonArray = (JSONArray) JSON.parse(images);
        List<Long> lists = new ArrayList<Long>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            lists.add(Long.valueOf((Integer) jsonArray.get(i)));
        }
        Map<Long, Image> imageMap = imageService.getImagesById(lists);
        if (imageMap == null || imageMap.size() == 0) {
            return null;
        }
        List<String> retList = new ArrayList<String>(lists.size());
        for (Long id : lists) {
            retList.add(String.valueOf(imageMap.get(id).getUrl()));
        }
        return retList;

    }

    /**
     * @param
     * @return
     */
    private JSONArray renderItemAppraises(List<ItemAppraise> itemAppraises, boolean summary) {
        JSONArray array = new JSONArray(itemAppraises.size());
        ItemAppraise itemA = itemAppraises.get(0);
        JSONObject a = renderItemAppraise(itemA, summary);
        array.add(a);
        return array;
    }

    /**
     * @param itemA
     * @return
     */
    private JSONObject renderItemAppraise(ItemAppraise itemA, boolean summary) {
        JSONObject a = new JSONObject();
        a.put("id", itemA.getId());
        a.put("uid", itemA.getUid());
        a.put("name", itemA.getName());
        if (summary) {
            a.put("appraise", itemA.getSummary());
        } else {
            a.put("appraise", itemA.getAppraise());
        }
        if (itemA.getOffical()) a.put("official", true);
        return a;
    }

    /**
     * @param
     * @return
     */
    private JSONObject renderItemAppraiseV2(ItemAppraise itemA, boolean summary) {
        JSONObject a = new JSONObject();
        a.put("id", itemA.getId());
        a.put("title", itemA.getTitle());
        if (summary) {
            a.put("summary", itemA.getSummary());
        } else {
            a.put("appraise", itemA.getAppraise());
        }
        a.put("score", itemA.getScore());
        a.put("authorName", itemA.getName() + "推荐");

        return a;
    }

    /**
     * @param
     * @return
     */
    private JSONArray renderItemComments(List<ItemComment> itemComments) {
        JSONArray array = new JSONArray(itemComments.size());
        for (int i = 0; i < 3 && i < itemComments.size(); i++) {
            ItemComment itemC = itemComments.get(i);
            JSONObject a = renderItemComment(itemC);
            array.add(a);
        }
        return array;
    }

    /**
     * @param itemC
     * @return
     */
    private JSONObject renderItemComment(ItemComment itemC) {
        JSONObject a = new JSONObject();
        a.put("uid", itemC.getUid());
        a.put("name", itemC.getName());
        a.put("comment", itemC.getComment());
        return a;
    }

    /**
     * @param item
     * @return
     */
    private JSONObject renderItemDetail(ItemResult item) {

        JSONObject detail = new JSONObject();
        Brand brand = brandService.getBrandById(item.getItem().getBrandid());
        detail.put("brand", brand.getName());

        String crowds = getCrowds(item);
        detail.put("crowd", crowds);

        Gender gender = genderService.getGenderById(item.getItem().getGenderid());
        detail.put("gender", gender.getName());

        //        ItemDetail itemDetail = itemDetailService.getItemDetailByItemid(item.getItem().getId());
        //        detail.put("meta", itemDetail.getMeta() == null ? "" : itemDetail.getMeta());
        //        logger.info("meta=" + itemDetail.getMeta());

        String imageUrl = item.getItem().getImage();
        if (imageUrl != null) {
            try {
                detail.put("image", imageService.getImageById(Long.valueOf(imageUrl)).getUrl());
            } catch (Exception e) {
                logger.error("get image fail!by imageId=" + imageUrl);
                detail.put("image", "");
            }
        }

        // FIXME 
        detail.put("imagetext", item.getItem().getDesc());
        detail.put("desc", item.getItem().getDesc());
        // 基本属性
        detail.put("property", renderProps(item));

        // FIXME 规格（SKU）属性
        detail.put("attr", renderOldSkus(item));
        return detail;
    }

    public static void main(String[] args) {
        System.out.println(mergeCrowdIds(Arrays.asList(0, 1, 2, 3)));
        System.out.println(mergeCrowdIds(Arrays.asList(0, 1, 2)));

        System.out.println(mergeCrowdIds(Arrays.asList(0, 1)));

        System.out.println(mergeCrowdIds(Arrays.asList(0, 2, 3)));
    }

    private static List<Integer> mergeCrowdIds(List<Integer> crowdIds) {

        if (crowdIds.size() <= 1) {
            return crowdIds;
        }

        int merge = -1;
        List<Integer> mergeCids = new ArrayList<>(4);
        for (int i = 0; i < crowdIds.size(); i++) {

            Integer crowdId = crowdIds.get(i);

            if (crowdId == 0) {
                continue;
            }

            if (crowdId == 1) {
                mergeCids.add(crowdId);
                continue;
            }

            if (i + 1 >= crowdIds.size()) {
                if (merge < 0) {
                    mergeCids.add(crowdId);
                    break;
                } else {
                    merge *= 10;
                    merge += crowdId;
                    mergeCids.add(merge);
                    break;
                }
            }

            Integer nextCrowId = crowdIds.get(i + 1);
            if (nextCrowId == crowdId + 1) {
                if (merge < 0) {
                    merge = crowdId;
                } else {
                    merge *= 10;
                    merge += crowdId;
                }
            } else {
                if (merge < 0) {
                    mergeCids.add(crowdId);
                } else {
                    merge *= 10;
                    merge += crowdId;
                    mergeCids.add(merge);
                    merge = -1;
                }
            }
        }

        return mergeCids;
    }

    /**
     * @param item
     * @return
     */
    private String getCrowds(ItemResult item) {
        List<Integer> crowdIds = com.hehua.commons.lang.StringUtils.getIntegerList(item.getItem()
                .getCrowedid());
        Map<Integer, Crowd> crowdMap = crowedService.getCrowdsByIds(crowdIds);

        // merge

        Collections.sort(crowdIds);
        List<Integer> mergeCrowdIds = mergeCrowdIds(crowdIds);

        StringBuilder crowds = new StringBuilder();
        for (int i = 0; i < mergeCrowdIds.size(); i++) {
            Integer crowdId = mergeCrowdIds.get(i);
            String name = getCrowdName(crowdMap, crowdId);
            if (name == null) {
                continue;
            }

            if (crowds.length() > 0) {
                crowds.append(",");
            }
            crowds.append(name);
        }
        return crowds.toString();
    }

    /**
     * @param crowdMap
     * @param crowdId
     * @return
     */
    private String getCrowdName(Map<Integer, Crowd> crowdMap, Integer crowdId) {
        String name = null;
        switch (crowdId) {
            case 23:
                name = "宝宝0-2岁";
                break;
            case 234:
                name = "宝宝0-3岁";
                break;
            case 34:
                name = "宝宝1-3岁";
                break;
            default:
                Crowd crowd = crowdMap.get(crowdId);
                if (crowd != null) {
                    name = crowd.getName();
                }
        }
        return name;
    }

    private JSONArray renderProps(ItemResult item) {
        JSONArray array = new JSONArray();
        for (ItemProperty itemProperty : item.getItemProperties()) {
            if (itemProperty.getIssku() != 0) {
                continue;
            }

            Property property = propertyManager.getProperty(itemProperty.getPid());
            PropertyValue propertyValue = propertyManager.getPropertyValue(itemProperty.getPvid());
            if (property != null && propertyValue != null) {
                JSONObject object = new JSONObject();
                object.put("name", property.getName());
                object.put("value", propertyValue.getName());
                array.add(object);
            }
        }
        return array;
    }

    /**
     * FIXME
     * 
     * @param item
     * @return
     * @Deprecated 废弃了，暂时保留适配上线审核版本
     */
    @Deprecated
    private JSONArray renderOldSkus(ItemResult item) {
        return renderOldSkus(item.getItemSkus());
    }

    /**
     * FIXME
     * 
     * @param
     * @return
     * @Deprecated 废弃了，暂时保留适配上线审核版本
     */
    @Deprecated
    private JSONArray renderOldSkus(List<ItemSku> itemSkus) {
        if (CollectionUtils.isEmpty(itemSkus)) {
            return null;
        }

        JSONArray jsonArray = new JSONArray();
        ItemSku first = Iterables.getFirst(itemSkus, null);
        List<ItemPropertyView> properties = itemPropertyRender.renderProperties(first);
        for (ItemPropertyView propertyView : properties) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", propertyView.getProperty().getName());

            JSONArray values = new JSONArray();
            for (PropertyValue value : propertyView.getProperty().getValues()) {
                JSONObject jsonValue = new JSONObject();
                jsonValue.put("id", value.getId());
                jsonValue.put("desc", value.getName());
                values.add(jsonValue);
            }
            jsonObject.put("value", values);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * @param itemSkus
     * @return
     */
    private JSONArray renderSkus(List<ItemSku> itemSkus) {
        JSONArray jsonSkuArray = new JSONArray(itemSkus.size());
        for (ItemSku itemSku : itemSkus) {
            JSONObject jsonObject = renderSku(itemSku);
            jsonSkuArray.add(jsonObject);
        }
        return jsonSkuArray;
    }

    /**
     * @param itemSku
     * @return
     */
    private JSONObject renderSku(ItemSku itemSku) {
        JSONObject jsonObject = new JSONObject(5);
        jsonObject.put("skuid", itemSku.getId());
        jsonObject.put("props", itemSku.getPvids());
        jsonObject.put("originprice", itemSku.getOriginprice());
        jsonObject.put("realprice", itemSku.getRealprice());
        jsonObject.put("quantity", itemSku.getQuantity());
        return jsonObject;
    }

    /**
     * @param
     * @return
     */
    private JSONArray renderItemPropMetas(List<ItemProperty> itemProperties) {
        List<ItemProperty> itemProWithSkuList = new ArrayList<ItemProperty>(3);
        for (ItemProperty itemProperty : itemProperties) {
            if (itemProperty.getIssku() != 0) {
                itemProWithSkuList.add(itemProperty);
            }
        }

        //基于sort排序
        List<Property> propertyList = new ArrayList<Property>(3);

        // 将商品属性分组如(颜色、尺寸),key:propertyId,value:属性列表
        ListMultimap<Integer, PropertyValue> listMap = ArrayListMultimap.create();
        for (ItemProperty itemProperty : itemProWithSkuList) {
            Property property = propertyManager.getProperty(itemProperty.getPid());

            itemProperty.convertPropertyToMap();

            PropertyValue propertyValue = propertyManager.getPropertyValue(itemProperty.getPvid());
            Map<String, String> pvextMap = itemProperty.getPvextMap();
            if (MapUtils.isNotEmpty(pvextMap)) {
                propertyValue.setAlias(pvextMap.get(ItemProperty.PROPERTY_ALIAS));
            }
            int pKey = itemProperty.getPid();
            if (!listMap.containsKey(pKey)) {
                Map<String, String> pextMap = itemProperty.getPextMap();
                String alias = pextMap.get(ItemProperty.PROPERTY_ALIAS);
                if (StringUtils.isNotBlank(alias)) {
                    property.setAlias(alias);
                }
                //                }
                propertyList.add(property);
            }

            listMap.put(pKey, propertyValue);
        }

        Collections.sort(propertyList);

        for (Integer key : listMap.keySet()) {
            Collections.sort(listMap.get(key));
        }

        JSONArray array = new JSONArray();
        for (Property property : propertyList) {
            JSONObject jsonObject = new JSONObject(3);
            jsonObject.put("pid", property.getId());
            if (StringUtils.isNotEmpty(property.getAlias())) {
                jsonObject.put("pname", property.getAlias());
            } else {
                jsonObject.put("pname", property.getName());
            }
            List<PropertyValue> tempPros = listMap.get(property.getId());
            JSONArray tempArray = new JSONArray();
            for (PropertyValue propertyValue : new LinkedHashSet<>(tempPros)) {
                JSONObject jsonTemp = new JSONObject(2);
                jsonTemp.put("pvid", propertyValue.getId());
                if (StringUtils.isNotEmpty(propertyValue.getAlias())) {
                    jsonTemp.put("pvname", propertyValue.getAlias());
                } else {
                    jsonTemp.put("pvname", propertyValue.getName());
                }
                tempArray.add(jsonTemp);
            }
            jsonObject.put("poptions", tempArray);
            array.add(jsonObject);
        }
        return array;
    }

    private JSONObject renderFlashItem(ItemResult item, boolean newVersion) {
        JSONObject o = renderItem(item, newVersion);
        // 过期时间
        Flash flash = item.getFlash();
        if (flash == null) {
            // 已经下线
            o.put("expiration", 0);
        } else {
            o.put("expiration", flash.getEndtime().getTime() / 1000);
        }
        // 售罄标识
        boolean soldOut = item.isSoldOut();
        o.put("soldout", soldOut);
        // 推荐信息
        if (item.getItemRecommend() != null && !newVersion) {
            JSONObject rec = renderRecommend(item.getItemRecommend());
            o.put("recommend", rec);
        }
        return o;
    }

    /**
     * @param
     * @return
     */
    private JSONObject renderRecommend(ItemRecommend itemRecommend) {
        JSONObject rec = new JSONObject();
        rec.put("uid", itemRecommend.getUid());
        String type = StringUtils.isEmpty(itemRecommend.getType()) ? "母婴达人为您推荐" : itemRecommend
                .getType();
        rec.put("type", type);

        //        User user = userManager.getUserById(itemRecommend.getUid());
        Daren daren = darenService.getDarenInfo(itemRecommend.getUid());

        String name = itemRecommend.getName();
        String avatar = itemRecommend.getAvatar();

        //        if (user != null) {
        //            name = user.getName();
        //            avatar = avatarUtils.getAvatarUrl(user.getAvatarid());
        //        }

        if (daren != null) {
            name = daren.getName();
            avatar = avatarUtils.getAvatarUrl(daren.getAvatarid());
        }

        rec.put("name", name);
        rec.put("avatar", avatar);

        rec.put("reason", itemRecommend.getReason());
        return rec;
    }

    public JSONObject getFlashItem(long id) {
        return getFlashItem(id, false);
    }

    /**
     * 闪购详情
     *
     * @param id
     * @return
     */
    public JSONObject getFlashItem(long id, boolean newVersion) {
        ItemResult item = itemService.getItem(id);
        if (item == null) {
            return renderItemNotFoundError();
        }

        JSONObject result = new JSONObject();
        JSONObject o = renderFlashItem(item, newVersion);
        result.put("data", o);
        return result;
    }

    /**
     * @return
     */
    public JSONObject renderItemNotFoundError() {
        JSONObject result = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 11002);
        jsonObject.put("msg", "商品不存在");
        result.put("error", jsonObject);
        return result;
    }

    /**
     * 商品详情
     * 
     * @param id
     * @return
     */
    public JSONObject getItem(long id) {
        ItemResult item = itemService.getItem(id);
        if (item == null) {
            return renderItemNotFoundError();
        }

        JSONObject result = new JSONObject();
        JSONObject o = renderItem(item, false);
        result.put("data", o);
        return result;

    }

    public JSONObject getItemDesc(long id) {
        JSONObject result = new JSONObject();
        Item item = itemService.getItemById(id);
        JSONObject data = new JSONObject();
        data.put("desc", item.getDesc());
        result.put("data", data);
        return result;
    }

    public JSONObject getItemMeta(long id) {
        JSONObject result = new JSONObject();
        ItemDetail itemDetail = itemDetailService.getItemDetailByItemid(id);
        JSONObject data = new JSONObject();
        if (itemDetail != null) {
            data.put("meta", org.apache.commons.lang.StringUtils.isEmpty(itemDetail.getMeta()) ? ""
                    : itemDetail.getMeta());
        } else {
            data.put("meta", "");
        }
        result.put("data", data);
        return result;
    }

    /**
     * 评测列表
     * 
     * @param itemid
     * @param offset
     * @param limit
     * @return
     */
    public JSONObject getItemAppraise(long itemid, int offset, int limit) {
        List<ItemAppraise> items = itemService.getItemAppraise(itemid, offset, limit);

        // 评测信息
        int appraisesCount = appraiseService.getItemAppraisesCount(itemid);
        boolean hasMore = appraisesCount > limit + offset;

        JSONArray array = new JSONArray(items.size());
        for (ItemAppraise itemA : items) {
            JSONObject a = renderItemAppraise(itemA, true);
            array.add(a);
        }
        return renderPaginator(appraisesCount, array, offset, limit, hasMore);
    }

    public JSONObject getItemAppraiseDetailByAppraiseId(int appraiseId) {
        ItemAppraise itemAppraise = appraiseService.getItemAppraiseById(appraiseId);
        return getItemAppraiseDetailV2(itemAppraise);
    }

    public JSONObject getItemAppraiseDetailByOrderId(int orderId) {
        ItemAppraise itemAppraise = appraiseService.getItemAppraiseByOrderId(orderId);
        return getItemAppraiseDetailV2(itemAppraise);
    }

    public JSONObject getItemAppraiseDetailV2(ItemAppraise itemAppraise) {
        JSONObject retObject = new JSONObject(2);
        if (itemAppraise == null) {
            retObject.put("msg", "不存在众测信息");
            return retObject;
        }
        JSONObject jsonObject = new JSONObject(7);
        jsonObject.put("id", itemAppraise.getId());
        jsonObject.put("title", itemAppraise.getTitle());
        jsonObject.put("summary", itemAppraise.getSummary());
        jsonObject.put("score", itemAppraise.getScore());
        jsonObject.put("authorName", itemAppraise.getName() + "推荐");
        jsonObject.put("content", itemAppraise.getAppraise());

        retObject.put("data", jsonObject);
        return retObject;
    }

    /**
     * 评测列表V2版本
     *
     * @param itemid
     * @param offset
     * @param limit
     * @return
     */
    public JSONObject getItemAppraiseV2(int itemid, int offset, int limit) {
        List<ItemAppraise> items = itemService.getItemAppraise(itemid, offset, limit);

        // 评测信息
        int appraisesCount = appraiseService.getItemAppraisesCount(itemid);
        boolean hasMore = appraisesCount > (offset + limit);

        JSONArray array = new JSONArray(items.size());
        double totalScore = 0;
        BigDecimal bigDecimal = BigDecimal.valueOf(0);
        if (!com.hehua.commons.collection.CollectionUtils.isEmpty(items)) {
            for (ItemAppraise itemA : items) {
                JSONObject a = renderItemAppraiseV2(itemA, true);
                totalScore += itemA.getScore();
                array.add(a);
            }
            totalScore = totalScore / items.size();
            bigDecimal = BigDecimal.valueOf(totalScore).setScale(1, BigDecimal.ROUND_HALF_UP);
        }

        FreeFlash freeFlash = voteFlashSessionService.getFreeFlashByItemId(itemid);

        return renderPaginatorV2(bigDecimal, freeFlash != null ? freeFlash.getApplynum() : 0,
                array, offset, limit, hasMore);
    }

    public JSONObject getItemAppraise(long itemid, int appraiseId) {
        ItemAppraise itemAppraise = this.appraiseService.getItemAppraiseById(appraiseId);
        JSONObject data = renderItemAppraise(itemAppraise, false);
        JSONObject result = new JSONObject();
        result.put("data", data);
        return result;
    }

    /**
     * 评论列表
     * 
     * @param itemid
     * @param offset
     * @param limit
     * @return
     */
    public JSONObject getItemComment(long itemid, int offset, int limit) {
        List<ItemComment> items = itemService.getItemComment(itemid, offset, limit);
        boolean hasMore = items.size() > limit;
        if (hasMore) {
            items = items.subList(0, items.size() - 1);
        }

        JSONArray array = new JSONArray(items.size());
        for (ItemComment itemC : items) {
            JSONObject a = new JSONObject();
            a.put("id", itemC.getId());
            a.put("uid", itemC.getUid());
            a.put("name", itemC.getName());
            a.put("comment", itemC.getComment());
            array.add(a);
        }

        return renderPaginator(array, offset, limit, hasMore);
    }

    /**
     * 品类列表（只支持二级品类）
     * 
     * @return
     */
    public JSONObject getAllCategory() {
        List<Category> categories = categoryService.getAllCategoryByParentId(0);
        List<Category> allCategories = categoryService.getAllCategory();

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        for (Category category : categories) {
            if (category.getStatus() != 0) continue;
            JSONObject c = new JSONObject();
            c.put("id", category.getId());
            c.put("name", category.getName());
            JSONArray c2s = new JSONArray();
            for (Category subcate : allCategories) {
                if (subcate.getParentid() == category.getId() && subcate.getStatus() == 0) {
                    JSONObject c2 = new JSONObject();
                    c2.put("id", category.getId());
                    c2.put("name", category.getName());
                    c2s.add(c2);
                }
            }
            if (c2s.size() > 0) {
                c.put("subcates", c2s);
            }
            array.add(c);
        }
        result.put("data", array);
        return result;
    }

}
