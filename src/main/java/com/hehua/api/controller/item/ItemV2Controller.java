package com.hehua.api.controller.item;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.api.controller.BaseController;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.item.service.FlashSessionLocalCache;
import com.hehua.item.service.ItemServiceApiV2Proxy;

/**
 * Created by liuweiwei on 14-9-13.
 */
@Controller
public class ItemV2Controller extends BaseController {

    @Inject
    private ItemServiceApiV2Proxy itemServiceApiV2Proxy;

    @Inject
    private FlashSessionLocalCache flashSessionLocalCache;

    @RequestMapping(value = "/flash/item/list/v2", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getFlashList(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "21") Integer limit,
            @RequestParam(value = "babystatus", required = false, defaultValue = "0") int babyStatus,
            @RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
            @RequestParam(value = "edc", required = false) String edc,
            @RequestParam(value = "birthday", required = false) String birthday,
            @RequestParam(value = "isset", required = false, defaultValue = "true") boolean isset) {

        JSONObject jo = itemServiceApiV2Proxy.getFlashList(isset, babyStatus, gender, edc,
                birthday, offset, limit);
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

    @RequestMapping(value = "/flash/reload", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject reloadFlashSession() {
        flashSessionLocalCache.reload();
        return new JSONObject();
    }
}
