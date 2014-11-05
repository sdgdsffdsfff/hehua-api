package com.hehua.api.controller.vote;

import com.alibaba.fastjson.JSONObject;
import com.hehua.api.controller.BaseController;
import com.hehua.api.controller.item.ItemController;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.item.service.ItemServiceApiProxy;
import com.hehua.user.domain.PhoneVersion;
import com.hehua.user.service.PhoneVersionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class VoteAppraiseController extends BaseController {

    @Inject
    private ItemServiceApiProxy itemServiceApiProxy;

    @RequestMapping(value = "item/{itemid}/appraise/list/v2", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteAppraise(@PathVariable("itemid") int itemid,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
             @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        JSONObject jo = itemServiceApiProxy.getItemAppraiseV2(itemid, offset, limit);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("review");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        userAccessInfo.setItemid((long)itemid);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }

    @RequestMapping(value = "item/appraise/{appraiseId}/v2", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteAppraiseDetail(
                                      @PathVariable("appraiseId") int appraiseId) {
        JSONObject jo = itemServiceApiProxy.getItemAppraiseDetailByAppraiseId(appraiseId);
        flumeEventLogger.info("appraiseId="+ appraiseId);
        return jo;
    }

    @RequestMapping(value = "free/order/{freeOrderid}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteAppraiseDetailByOrderId(@PathVariable("freeOrderid") int freeOrderid
                                           ) {
        JSONObject jo = itemServiceApiProxy.getItemAppraiseDetailByOrderId(freeOrderid);
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("review");
        int status = jo.containsKey("data") ? 0 : -1;
        userAccessInfo.setStatus(status);
        flumeEventLogger.info(userAccessInfo.toString());
        return jo;
    }
}
