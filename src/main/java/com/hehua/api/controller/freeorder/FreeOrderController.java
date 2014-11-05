package com.hehua.api.controller.freeorder;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.commons.page.Paginator;
import com.hehua.commons.utils.IPUtil;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.freeorder.action.params.ApplyGetParam;
import com.hehua.freeorder.action.params.ApplyParam;
import com.hehua.freeorder.info.FreeOrderInfo;
import com.hehua.freeorder.service.FreeOrderService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liuweiwei on 14-10-7.
 */
@Controller
@RequestMapping(value = "/free")
public class FreeOrderController {

    @Inject
    private FreeOrderService freeOrderService;

    @LoginRequired
    @RequestMapping(value = "apply/{freeid}", method = RequestMethod.GET)
    @ResponseBody
    public ResultView<ApplyGetParam> applyGet(@PathVariable("freeid") int flashid) {
        return freeOrderService.applyConfirm(HehuaRequestContext.getUserId(), flashid);
    }

    @LoginRequired
    @RequestMapping(value = "apply/{freeid}", method = RequestMethod.POST)
    @ResponseBody
    public ResultView<String> applyPost(@PathVariable("freeid") int flashid, @RequestBody ApplyParam param) {
        param.setFreeFlashid(flashid);
        param.setUserid(HehuaRequestContext.getUserId());
        param.setApplyip(IPUtil.ip2long(HehuaRequestContext.getUserAccessInfo().getIp()));
        return freeOrderService.apply(param);
    }

    @LoginRequired
    @RequestMapping(value = "order/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultView<Paginator> list(HttpServletRequest request) {
        int offset = NumberUtils.isNumber(request.getParameter("offset")) ? Integer.parseInt(request.getParameter("offset")) : 0;
        int limit = NumberUtils.isNumber(request.getParameter("limit")) ? Integer.parseInt(request.getParameter("limit")) : 10;
        int status = NumberUtils.isNumber(request.getParameter("status")) ? Integer.parseInt(request.getParameter("status")) : -1;
        offset = offset < 0 ? 0 : offset;
        limit = limit < 0 || limit > 100 ? 10 : limit;
        long userid = HehuaRequestContext.getUserId();
        List<FreeOrderInfo> orderInfos = freeOrderService.getListByUserId(userid, status, offset, limit + 1);
        boolean hasMore = orderInfos.size() == (limit + 1);
        if (hasMore) {
            orderInfos.remove(limit);
        }
        limit = orderInfos.size();
        return new ResultView<>(CommonMetaCode.Success, new Paginator(orderInfos, limit, offset, hasMore));
    }
}
