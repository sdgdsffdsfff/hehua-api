package com.hehua.api.controller.credit;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.commons.page.Paginator;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.order.info.CreditLogInfo;
import com.hehua.order.service.CreditLogService;
import com.hehua.order.service.CreditService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by liuweiwei on 14-8-16.
 */
@Controller
@RequestMapping(value = "/credit")
public class CreditController {

    @Inject
    CreditLogService creditLogService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<Paginator> getList() {
        long userid = HehuaRequestContext.getUserId();
        int offset = 0;
        int limit = 20;
        HttpServletRequest req = HehuaRequestContext.getRequest();
        if (req.getParameter("offset") != null && Integer.parseInt(req.getParameter("offset")) > 0) {
            offset = Integer.parseInt(req.getParameter("offset"));
        }
        if (req.getParameter("limit") != null && Integer.parseInt(req.getParameter("limit")) > 0) {
            limit = Integer.parseInt(req.getParameter("limit"));
        }
        List<CreditLogInfo> creditLogInfos = creditLogService.getListByUserId(userid, offset, limit + 1);
        boolean hasMore = false;
        if (creditLogInfos.size() == limit + 1) {
            hasMore = true;
            creditLogInfos.remove(limit);
        }
        limit = creditLogInfos.size();
        return new ResultView<>(CommonMetaCode.Success, new Paginator(creditLogInfos, limit, offset, hasMore));
    }

}
