package com.hehua.item.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hehua.commons.time.DateUtils;
import com.hehua.commons.utils.JSONUtils;
import com.hehua.framework.config.DatabaseConfigManager;
import com.hehua.framework.image.ImageService;
import com.hehua.framework.image.domain.Image;
import com.hehua.item.domain.*;
import com.hehua.item.manager.PropertyManager;
import com.hehua.item.model.FreeFlashList;
import com.hehua.item.utils.ItemConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

/**
 * autor:hewenjerry
 */
@javax.inject.Named
public class VoteServiceApiProxy {

    @Inject
    private VoteFlashSessionService voteFlashSessionService;

    @Inject
    private ItemService itemService;

    @Inject
    private ImageService imageService;

    @Inject
    private DatabaseConfigManager databaseConfigManager;

    @Inject
    private BrandService brandService;

    @Inject
    private CrowedService crowedService;

    @Inject
    private GenderService genderService;

    private static final Logger logger = LoggerFactory.getLogger(VoteServiceApiProxy.class);

    @Inject
    private PropertyManager propertyManager;

    @Inject
    private ItemPropertyService itemPropertyService;

    @Inject
    private DarenService darenService;

    @Inject
    private ItemAppraiseService itemAppraiseService;

    @Inject
    private VoteFlashManager voteFlashManager;

    public JSONObject getVoteFlashItemListByUserId(int userId, Integer offset, Integer limit) {
        JSONObject jsonObject = new JSONObject(2);
        renderProfileAndInfo(jsonObject, userId);

        renderItemList(jsonObject, userId, offset, limit);

        JSONObject retJson = new JSONObject();

        retJson.put("data", jsonObject);

        return retJson;
    }

    private void renderItemList(JSONObject jsonObject, int userId, Integer offset, Integer limit) {
        int totalNum = itemAppraiseService.getItemCountByUserId(userId);
        jsonObject.put("totalNum", totalNum);
        boolean isMore = totalNum > (offset + limit);
        jsonObject.put("hasMore", isMore);
        jsonObject.put("offset", offset);
        jsonObject.put("limit", limit);

        List<ItemAppraise> appraiseList = itemAppraiseService.getByUserId(userId, offset, limit);
        JSONArray jsonArray = new JSONArray(appraiseList.size());
        for (ItemAppraise itemAppraise : appraiseList) {
            JSONObject tmp = new JSONObject(5);
            tmp.put("id", itemAppraise.getId());
            tmp.put("title",itemAppraise.getTitle());
            tmp.put("score", itemAppraise.getScore());
            JSONObject itemJson = new JSONObject(4);
            if (itemAppraise.getItemLite() != null) {
                itemJson.put("id", itemAppraise.getItemLite().getId());
                itemJson.put("name", itemAppraise.getItemLite().getName());
                itemJson.put("image", itemAppraise.getItemLite().getImage());
            }
            tmp.put("item", itemJson);
            jsonArray.add(tmp);
        }
        jsonObject.put("entities", jsonArray);

    }

    private void renderProfileAndInfo(JSONObject jsonObject, int userId) {
        Daren daren = darenService.getDarenInfo(userId);
        if (daren == null) {
            logger.error("daren info is null by userId=" + userId);
            jsonObject.put("profile", new JSONObject(1));
        } else {
            JSONObject profileJson = new JSONObject(6);
            profileJson.put("id", daren.getId());
            profileJson.put("name", daren.getName());
            try {

                profileJson.put("avatar", imageService.getImageById(daren.getAvatarid()).getUrl());
            } catch (Exception e) {
                logger.error("", e);
                profileJson.put("avatar", "");
            }

            profileJson.put("location", daren.getLocation());
            String gender = daren.getGender();
            String birthday = daren.getBirthday();
            if (StringUtils.isNotEmpty(gender) && StringUtils.isNotEmpty(birthday)) {
                profileJson.put("stage", DateUtils.getBabbyStage(gender, DateUtils.formatStrToDate(birthday)));
            } else {
                profileJson.put("stage", daren.getStage());
            }


            JSONObject certJson = new JSONObject(4);
            certJson.put("title", daren.getTitle());
            certJson.put("desc", daren.getDescription());
            profileJson.put("certification", certJson);

            jsonObject.put("profile", profileJson);

        }
    }

    public List<FreeFlash> getVoteFlashItemList(Integer offset, Integer limit) {

        FreeFlashList freeFlashList = voteFlashManager.getTodayVoteFlashSession();
        List<FreeFlash> freeFlashs = freeFlashList.getFreeFlashList();
        if (offset >= freeFlashs.size()) {
            return Collections.EMPTY_LIST;
        }
        boolean hasMore = freeFlashs.size() > (offset + limit);
        if (hasMore) {
            freeFlashs = freeFlashs.subList(offset, offset + limit);
        } else if(freeFlashs.size() > offset) {
            freeFlashs = freeFlashs.subList(offset, freeFlashs.size());
        } else {
            freeFlashs = Collections.emptyList();
        }

        return freeFlashs;
    }

    public JSONObject getVoteFlashItemJson(List<FreeFlash> freeFlashList, Integer offset, Integer limit, boolean hasMore) {
        JSONArray array = renderVoteItemBases(freeFlashList);
        return renderFlashPaginator(array, offset, limit, hasMore);
    }


    public JSONObject getVoteFlashHelp() {
        JSONObject result = new JSONObject();
        result.put("data", databaseConfigManager.getString(ItemConfig.VOTE_FLASH_HELP));
        return result;
    }

    public FreeFlash getVoteFlashById(int freeId){
       return getFreeFlash(freeId);
    }

    public JSONObject getVoteFlashJsonById(FreeFlash freeFlash) {
        JSONObject result = new JSONObject(2);
        if (freeFlash == null) {
            return renderItemNotFoundError();
        }

        JSONObject o = renderFreeItem(freeFlash);
        result.put("data", o);
        return result;
    }


    public List<String> convertImageIdToImageUrl(Item item) {
        String images = item.getImages();
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
     *
     * @param freeFlash
     * @return
     */
    private JSONObject renderFreeItem(FreeFlash freeFlash) {
        // 基本信息
        JSONObject o = new JSONObject();
        o.put("id", freeFlash.getItem().getId());
        o.put("freeFlashId", freeFlash.getId());

        o.put("name", freeFlash.getItem().getName());
        o.put("originprice", freeFlash.getItem().getOriginprice());
        o.put("isApply", freeFlash.isApply());
        if (freeFlash == null) {
            // 已经下线
            o.put("expiration", 0);
        } else {
            o.put("expiration", freeFlash.getEndtime().getTime() / 1000);
        }
        o.put("soldout", false);
        List<String> imageUrlList = convertImageIdToImageUrl(freeFlash.getItem());
        o.put("images",
                imageUrlList == null ? new Object[] {} : JSONUtils.convertJSONArrayBy(imageUrlList));
        o.put("quantity", freeFlash.getFreequantity());

        o.put("units", freeFlash.getItem().getUnits());
        o.put("applyNum", freeFlash.getApplynum());

        //// 0 仓库 1 上架 2 待上架 4 下架
        int clientStatus = freeFlash.getItem().getStatus() == Item.STATUS_OK ? 1 : 4;
        o.put("status", clientStatus);

        // 详情信息
        JSONObject detail = renderItemDetail(freeFlash);
        o.put("detail", detail);

        JSONObject guideLine = renderItemGuideLine();
        o.put("guideLine", guideLine);

        return o;
    }

    private JSONObject renderItemGuideLine() {
        JSONObject retObject = new JSONObject(3);
        JSONArray jsonArray = new JSONArray(3);
        JSONObject jsonObject = new JSONObject(4);
        retObject.put("name", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_NAME));

        jsonObject.put("title", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_ONE_TITLE));
        jsonObject.put("icon", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_ONE_ICON));
        jsonArray.add(jsonObject);

        jsonObject = new JSONObject(3);
        jsonObject.put("title", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_TWO_TITLE));
        jsonObject.put("icon", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_TWO_ICON));
        jsonArray.add(jsonObject);

        jsonObject = new JSONObject(3);
        jsonObject.put("title", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_THREE_TITLE));
        jsonObject.put("icon", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_THREE_ICON));
        jsonArray.add(jsonObject);

        jsonObject = new JSONObject(3);
        jsonObject.put("title", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_FOUR_TITLE));
        jsonObject.put("icon", databaseConfigManager.getString(ItemConfig.GUIDE_LINE_STEP_FOUR_ICON));
        jsonArray.add(jsonObject);

        retObject.put("guide", jsonArray);

        return retObject;
    }

    private JSONObject renderItemDetail(FreeFlash freeFlash) {
        Item item = freeFlash.getItem();

        JSONObject detail = new JSONObject();
        Brand brand = brandService.getBrandById(item.getBrandid());
        detail.put("brand", brand.getName());

        String crowds = getCrowds(item);
        detail.put("crowd", crowds);

        Gender gender = genderService.getGenderById(item.getGenderid());
        detail.put("gender", gender.getName());

        String imageUrl = item.getImage();
        if (imageUrl != null) {
            try {
                detail.put("image", imageService.getImageById(Long.valueOf(imageUrl)).getUrl());
            } catch (Exception e) {
                logger.error("get image fail!by imageId=" + imageUrl);
                detail.put("image", "");
            }
        }

        // 基本属性
        detail.put("property", renderProps(freeFlash));

        return detail;
    }

    private JSONArray renderProps(FreeFlash freeFlash) {
        JSONArray array = new JSONArray();
        for (ItemProperty itemProperty : freeFlash.getItemProperties()) {
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
     * @param item
     * @return
     */
    private String getCrowds(Item item) {
        List<Integer> crowdIds = com.hehua.commons.lang.StringUtils.getIntegerList(item
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

    public FreeFlash getFreeFlash(int freeId) {
        FreeFlash freeFlash = voteFlashSessionService.getFreeFlash(freeId);
        if (freeFlash != null) {
            Item item = itemService.getItem_(freeFlash.getItemid());
            freeFlash.setItem(item);

            List<ItemProperty> properties = itemPropertyService.getItemPropertiesByItemid(freeFlash.getItemid());
            freeFlash.setItemProperties(properties);

        }
        return freeFlash;
    }


    private JSONObject renderFlashPaginator(JSONArray array, Integer offset, Integer limit, boolean hasMore) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("entities", array);
        data.put("offset", offset);
        data.put("limit", limit);
        data.put("hasMore", hasMore);

        JSONObject titleJson = new JSONObject(3);
        titleJson.put("desc", databaseConfigManager.getString(ItemConfig.FREE_TITLE_DESC));
        titleJson.put("summary", databaseConfigManager.getString(ItemConfig.FREE_TITLE_SUMMARY));
        data.put("title", titleJson);
        JSONArray jsonArray = new JSONArray(3);

        JSONObject service1 = new JSONObject(3);
        service1.put("title", databaseConfigManager.getString(ItemConfig.ITEM_FREE));
        service1.put("icon", databaseConfigManager.getString(ItemConfig.ITEM_FREE_ICON));
        JSONObject service2 = new JSONObject(3);
        service2.put("title", databaseConfigManager.getString(ItemConfig.DAY_FREE_ITEM));
        service2.put("icon", databaseConfigManager.getString(ItemConfig.DAY_FREE_ITEM_ICON));
        JSONObject service3 = new JSONObject(3);
        service3.put("title", databaseConfigManager.getString(ItemConfig.POSTAGE_FREE));
        service3.put("icon", databaseConfigManager.getString(ItemConfig.POSTAGE_FREE_ICON));
        jsonArray.add(service1);
        jsonArray.add(service2);
        jsonArray.add(service3);

        data.put("services", jsonArray);
        //TODO   本节点应该是废弃掉了
        data.put("voteSessionEndTime", DateUtils.getUnitxTimestamp(new Date()));

        result.put("data", data);

        return result;
    }

    /**
     * @param freeFlashList
     * @return
     */
    private JSONArray renderVoteItemBases(List<FreeFlash> freeFlashList) {
        JSONArray array = new JSONArray(freeFlashList.size());
        for (FreeFlash freeFlash : freeFlashList) {
            JSONObject o = renderItemBase(freeFlash);
            array.add(o);
        }
        return array;
    }

    /**
     * @param freeFlash
     * @return
     */
    private JSONObject renderItemBase(FreeFlash freeFlash) {
        JSONObject o = new JSONObject();
        o.put("freeid", freeFlash.getId());
        o.put("name", freeFlash.getItemLite().getName());
        o.put("originprice", freeFlash.getItemLite().getOriginprice());
        o.put("quantity", freeFlash.getFreequantity());
        o.put("isApply", freeFlash.isApply());

        try {
            Image image = imageService.getImageById(Long.valueOf(freeFlash.getItemLite().getImage()));
            if (image != null) {
                o.put("image", image.getUrl());
            }
        } catch (Exception e) {
            logger.error("call image service is error", e);
            o.put("image", "");
        }
        o.put("expiration",(freeFlash.getEndtime().getTime() / 1000));

        return o;
    }
}
