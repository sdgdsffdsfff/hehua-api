package com.hehua.api.controller.item;

import com.alibaba.fastjson.JSONObject;
import com.hehua.api.controller.BaseController;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.item.service.ItemServiceApiProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author hewenjerry
 * @version 1.0
 * @created 14-09-12
 */
@Controller
public class BrandGroupController extends BaseController {

    @Inject
    private ItemServiceApiProxy itemServiceApiProxy;

    @RequestMapping(value = "/flash/brandgroup/{brandgroupid}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject flashItemList(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @PathVariable("brandgroupid") int brandgroupid) {
        JSONObject jo = itemServiceApiProxy.getFlashItemListByBrandGroup(brandgroupid, offset, limit);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("group");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setGroupid(brandgroupid);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }

}
