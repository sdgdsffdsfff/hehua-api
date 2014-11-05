package com.hehua.api.controller.item;

import javax.inject.Inject;

import com.alibaba.fastjson.JSONArray;
import com.hehua.api.controller.BaseController;
import com.hehua.commons.model.UserAccessInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.item.service.ItemServiceApiProxy;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
@Controller
public class ItemController extends BaseController {

    @Inject
    private ItemServiceApiProxy itemServiceApiProxy;

    @RequestMapping(value = "/flash/item/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject flashItemList(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(value = "babystatus", required = false, defaultValue = "0") int babyStatus,
            @RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
            @RequestParam(value = "edc", required = false) String edc,
            @RequestParam(value = "birthday", required = false) String birthday,
            @RequestParam(value = "version", required = false, defaultValue = "") String version) {
        HehuaRequestContext.getRequestAttributes().setAttribute("version", version, RequestAttributes.SCOPE_REQUEST);
        JSONObject jo = itemServiceApiProxy.getFlashItemList(babyStatus, gender, edc, birthday, offset, limit);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("snap");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setPreganancy(babyStatus);
        userAccessInfo.setBabyGender(gender);
        userAccessInfo.setBabyBirthday(birthday);
        userAccessInfo.setEdc(edc);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }

    @RequestMapping(value = "/flash/item/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getFlashItem(@PathVariable("id") int id) {
        JSONObject jo = itemServiceApiProxy.getFlashItem(id, false);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("item");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)id);
        if (jo.containsKey("data")) {
            JSONObject data = jo.getJSONObject("data");
            JSONArray skus = data.getJSONArray("skus");
            if (skus == null) {
                flumeEventLogger.info(userAccessInfo);
                return jo;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < skus.size(); i++) {
                JSONObject sku = skus.getJSONObject(i);
                sb.append(sku.get("skuid")).append(",");
            }
            String skuStr = sb.toString();
            userAccessInfo.setSkuid(skuStr.substring(0, skuStr.length() - 1 ));
            flumeEventLogger.info(userAccessInfo.toString());
        }
        return jo;
    }


    @RequestMapping(value = "/flash/item/{id}/v2", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getFlashItemV2(@PathVariable("id") int id) {
        JSONObject jo = itemServiceApiProxy.getFlashItem(id, true);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("item");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)id);
        if (jo.containsKey("data")) {
            JSONObject data = jo.getJSONObject("data");
            JSONArray skus = data.getJSONArray("skus");
            if (skus == null) {
                flumeEventLogger.info(userAccessInfo);
                return jo;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < skus.size(); i++) {
                JSONObject sku = skus.getJSONObject(i);
                sb.append(sku.get("skuid")).append(",");
            }
            String skuStr = sb.toString();
            userAccessInfo.setSkuid(skuStr.substring(0, skuStr.length() - 1 ));
            flumeEventLogger.info(userAccessInfo.toString());
        }
        return jo;
    }

    @RequestMapping(value = "/item/{id}/appraise/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemAppraise(@PathVariable("id") int id,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        JSONObject jo = itemServiceApiProxy.getItemAppraise(id, offset, limit);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("review");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)id);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }

    @RequestMapping(value = "/item/{id}/comment/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemComment(@PathVariable("id") int id,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        return itemServiceApiProxy.getItemComment(id, offset, limit);
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItem(@PathVariable("id") int id) {
        return itemServiceApiProxy.getItem(id);
    }

    @RequestMapping(value = "/item/{id}/desc", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemDesc(@PathVariable("id") int id) {
        JSONObject jo = itemServiceApiProxy.getItemDesc(id);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("picdetail");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)id);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }

    @RequestMapping(value = "/item/{id}/meta", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemMeta(@PathVariable("id") int id) {
        JSONObject jo = itemServiceApiProxy.getItemMeta(id);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("quality");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)id);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }


    @RequestMapping(value = "/item/{id}/appraise/{appraiseId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemAppraise(@PathVariable("id") int id,
            @PathVariable("appraiseId") int appraiseId) {
        JSONObject jo = itemServiceApiProxy.getItemAppraise(id, appraiseId);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("reviewdetail");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)id);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }

}
