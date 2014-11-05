package com.hehua.item.service;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hehua.framework.image.ImageService;
import com.hehua.framework.image.domain.Image;
import com.hehua.item.domain.BrandGroup;
import com.hehua.item.domain.Daren;
import com.hehua.item.domain.ItemRecommend;
import com.hehua.item.enums.Gender;
import com.hehua.item.model.FlashEntry;
import com.hehua.item.model.FlashEntryList;
import com.hehua.item.model.FlashSession;
import com.hehua.item.model.ItemSummary;
import com.hehua.item.utils.BabyUtils;
import com.hehua.user.domain.Baby;
import com.hehua.user.domain.BabyStage;
import com.hehua.user.service.UserManager;

/**
 * Created by liuweiwei on 14-9-13. 第二版
 */
@Named
public class ItemServiceApiV2Proxy {

    @Inject
    private FlashManager flashManager;

    @Inject
    private ItemService itemService;

    @Inject
    private BrandGroupService brandGroupService;

    @Inject
    private ItemServiceApiProxy itemServiceApiProxy;

    @Inject
    private ImageService imageService;

    @Inject
    private UserManager userManager;

    @Inject
    private DarenService darenService;

    /**
     * 闪购列表
     * 
     * @param babyStatus
     * @param gender
     * @param edc
     * @param birthday
     * @param offset
     * @param limit
     * @return
     */
    public JSONObject getFlashList(boolean isset, int babyStatus, int gender, String edc,
            String birthday, int offset, int limit) {

        offset = Math.max(0, offset);

        Baby newBaby = null;

        try {
            newBaby = Baby.createNewBaby(isset, babyStatus, edc, gender, birthday);
        } catch (ParseException e) {
            throw new RuntimeException("edc=" + edc + ",birthday=" + birthday, e);
        }

        BabyStage babyStage = BabyUtils.getBabyStage(newBaby);
        int population = FlashSession.calculatePopulation(Gender.fromCode(newBaby.getGender()),
                babyStage);

        FlashSession flashSession = flashManager.getTodayFlashSession();
        FlashEntryList flashEntryList = flashSession.getFlashEntryListByPop(population);
        List<FlashEntry> flashEntries = flashEntryList.getFlashEntries();
        boolean hasMore = false;
        if (offset >= flashEntries.size()) {
            flashEntries = Collections.EMPTY_LIST;
        } else {
            hasMore = flashEntries.size() > offset + limit;
            if (hasMore) {
                flashEntries = flashEntries.subList(offset, offset + limit);
            } else {
                flashEntries = flashEntries.subList(offset, flashEntries.size());
            }
        }

        boolean firstPage = (offset == 0);
        JSONArray entities = renderFlashEntryItemList(newBaby, babyStage, flashEntries,
                flashSession.getEndTime(), firstPage);
        JSONObject data = new JSONObject();
        data.put("entities", entities);
        data.put("offset", offset);
        data.put("limit", flashEntries.size());
        data.put("hasMore", hasMore);
        data.put("flashSessionEndTime", flashSession.getEndTime().getTime() / 1000);

        JSONObject ret = new JSONObject();
        ret.put("data", data);
        return ret;

    }

    private JSONObject newTag(int tagType, String tagText) {
        JSONObject tag = new JSONObject();
        tag.put("name", tagText);
        tag.put("type", tagType);
        return tag;
    }

    public JSONArray renderFlashEntryItemList(Baby newBaby, BabyStage babyStage,
            List<FlashEntry> flashEntries, Date endTime, boolean firstPage) {

        List<ItemSummary> itemSummaries = itemService.getSessionItemSummaryList(flashEntries);
        Map<Integer, ItemSummary> itemIdSummaryMap = new HashMap<>();
        Set<Long> recommendUserIds = new HashSet<>();
        Set<Long> imageIds = new HashSet<>();

        for (ItemSummary itemSummary : itemSummaries) {
            itemIdSummaryMap.put(itemSummary.getItem().getId(), itemSummary);
            ItemRecommend itemRecommend = itemSummary.getItemRecommend();
            if (itemRecommend != null) {
                recommendUserIds.add(itemRecommend.getUid());
            }

            if (NumberUtils.isNumber(itemSummary.getItem().getImage())) {
                imageIds.add(Long.valueOf(itemSummary.getItem().getImage()));
            }
        }

        //        Map<Long, User> recommendUsers = userManager.getUsersByIds(recommendUserIds);
        //        for (Map.Entry<Long, User> entry : recommendUsers.entrySet()) {
        //            imageIds.add(entry.getValue().getAvatarid());
        //        }

        Map<Long, Daren> darenMap = darenService.getDarenListByUserIds(recommendUserIds);
        for (Map.Entry<Long, Daren> entry : darenMap.entrySet()) {
            imageIds.add((long) entry.getValue().getAvatarid());
        }

        Map<Long, Image> imagesMap = imageService.getImagesById(imageIds);

        JSONArray ja = new JSONArray();
        //        int lastFlashType = -1;
        if (firstPage) {
            ja.add(newTag(1001, BabyUtils.getHomePromptMessage(newBaby)));
        }

        for (FlashEntry flashEntry : flashEntries) {
            JSONObject o = new JSONObject();
            //优选品牌，规则：当前闪购类型是团购
            if (flashEntry.getFlash().isGroup()) {
                BrandGroup brandGroup = brandGroupService.getBrandGroupById(flashEntry.getFlash()
                        .getGroupid());
                o.put("id", brandGroup.getId());
                o.put("name", brandGroup.getName());
                o.put("type", flashEntry.getFlash().getFlashtype());

                o.put("discount",
                        itemServiceApiProxy.getBrandGroupByLowestDiscount(brandGroup.getId()));
                Image image = imageService.getImageById((Long.valueOf(brandGroup.getImageurl())));;
                o.put("image", image == null ? "" : image.getUrl());
                ja.add(o);
                continue;
            }
            //优选商品，规则：前面一个是品牌，当前是商品
            //            if (BabyUtils.FLASH_TYPE_GROUP == lastFlashType
            //                    && BabyUtils.FLASH_TYPE_ITEM == flashEntry.getFlash().getFlashtype()) {
            //                tag = new JSONObject();
            //                tag.put("name", "优选单品");
            //                tag.put("type", 1001);
            //                ja.add(tag);
            //                lastFlashType = flashEntry.getFlash().getFlashtype();
            //            }
            //适用人群标签，规则：当前商品适用人群大于当前用户选择的适用人群
            if (flashEntry.getCrowd() > babyStage.getCode()
                    && flashEntry.getPreCrow() < flashEntry.getCrowd()) {
                ja.add(newTag(1000, BabyStage.fromCode(flashEntry.getCrowd()).getDesc()));
            }

            ItemSummary itemSummary = itemIdSummaryMap.get(flashEntry.getFlash().getItemid());
            o.put("id", itemSummary.getItem().getId());
            o.put("name", itemSummary.getItem().getName());
            o.put("originprice", itemSummary.getItem().getOriginprice());
            o.put("realprice", itemSummary.getItem().getRealprice());
            o.put("discount", itemServiceApiProxy.getItemDiscount(itemSummary.getItem()
                    .getRealprice(), itemSummary.getItem().getOriginprice(), false));
            o.put("image", itemSummary.getItem().getImage());
            // 为了兼容id
            if (NumberUtils.isNumber(itemSummary.getItem().getImage())) {
                //                Image image = imageService.getImageById(Long.valueOf(itemSummary.getItem()
                //                        .getImage()));
                Image image = imagesMap.get(Long.valueOf(itemSummary.getItem().getImage()));
                if (image != null) {
                    o.put("image", image.getUrl());
                }
            }
            o.put("sales", itemSummary.getItem().getSales());
            o.put("expiration", itemSummary.getFlash() != null ? (itemSummary.getFlash()
                    .getEndtime().getTime() / 1000) : endTime.getTime() / 1000);
            boolean soldOut = itemSummary.isSoldOut();
            o.put("soldout", soldOut);

            if (itemSummary.getItemRecommend() != null) {
                JSONObject rec = new JSONObject();

                String avatar = itemSummary.getItemRecommend().getAvatar();
                Daren daren = darenMap.get(itemSummary.getItemRecommend().getUid());
                if (daren != null && daren.getAvatarid() > 0) {
                    Image image = imagesMap.get((long) daren.getAvatarid());
                    if (image != null) {
                        avatar = image.getUrl();
                    }
                }
                //                User user = recommendUsers.get(itemSummary.getItemRecommend().getUid();
                //                if (user != null && user.getAvatarid() > 0) {
                //                    Image image = imagesMap.get(user.getAvatarid());
                //                    if (image != null) {
                //                        avatar = image.getUrl();
                //                    }
                //                }

                rec.put("avatar", avatar);
                rec.put("reason", itemSummary.getItemRecommend().getReason());
                o.put("recommend", rec);
            }
            o.put("type", itemSummary.getFlash().getFlashtype());
            ja.add(o);
        }
        return ja;
        /*
        JSONArray ja = new JSONArray(4);
        JSONObject jo1 = new JSONObject();
        jo1.put("id", 1);
        jo1.put("name", "品牌街，秋季上新，优雅来袭！");
        jo1.put("type", 1);
        jo1.put("discount", 5);
        jo1.put("image", "http://img11.360buyimg.com/cms/jfs/t439/61/219200110/61597/b896299f/54115fc6N40157b58.jpg");
        ja.add(jo1);

        JSONObject jo2 = new JSONObject();
        jo2.put("id", 105);
        jo2.put("name", "贝亲婴儿奶瓶套装，200ml/400ml/600ml");
        jo2.put("type", 0);
        jo2.put("originprice", 58);
        jo2.put("realprice", 48);
        jo2.put("sales", 19);
        jo2.put("discount", "8");
        jo2.put("expiration", 1410660000);
        jo2.put("image", "http://hhimg.oss-cn-beijing.aliyuncs.com/4a3b9993-d121-443a-a90f-fb87be9bef63.jpeg");
        JSONObject jor = new JSONObject();
        jor.put("avatar", "http://hhimg.oss-cn-beijing.aliyuncs.com/head.jpg");
        jor.put("reason", "这一套奶瓶，也是完全环保材料，够宝宝用两年了，非常划算");
        jo2.put("recommend", jor);
        ja.add(jo2);

        JSONObject jo3 = new JSONObject();
        jo3.put("name", "1-2岁宝宝篇");
        jo3.put("type", 1000);
        ja.add(jo3);

        JSONObject jo4 = new JSONObject();
        jo4.put("id", 104);
        jo4.put("name", "贝亲婴儿玻璃水杯新生儿母婴水杯120m");
        jo4.put("type", 0);
        jo4.put("originprice", 108);
        jo4.put("realprice", 98);
        jo4.put("sales", 1);
        jo4.put("discount", "8");
        jo4.put("expiration", 1410660000);
        jo4.put("image", "http://hhimg.oss-cn-beijing.aliyuncs.com/5f184a85-34ca-490e-b345-b6688ab88032.jpeg");
        JSONObject jor2 = new JSONObject();
        jor2.put("avatar", "http://hhimg.oss-cn-beijing.aliyuncs.com/head.jpg");
        jor2.put("reason", "宝宝专用水杯，环保玻璃，并且有夹层，不烫手");
        jo4.put("recommend", jor2);
        ja.add(jo4);

        return ja;
        */
    }
}
