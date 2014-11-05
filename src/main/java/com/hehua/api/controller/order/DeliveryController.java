package com.hehua.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.freeorder.dao.FreeOrderDAO;
import com.hehua.freeorder.model.FreeOrderModel;
import com.hehua.order.constants.PaiyouConfig;
import com.hehua.order.dao.DeliveryCompanyDAO;
import com.hehua.order.dao.OrdersDAO;
import com.hehua.order.info.DeliveryCompanyInfo;
import com.hehua.order.listener.SyncOrderToPaiuListener;
import com.hehua.order.model.DeliveryCompanyModel;
import com.hehua.order.model.OrderTraceModel;
import com.hehua.order.model.OrdersModel;
import com.hehua.order.service.DeliveryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liuweiwei on 14-8-15.
 */
@Controller
@RequestMapping(value = "delivery", produces="application/json;charset=utf-8")
public class DeliveryController {

    private static Logger log = Logger.getLogger(SyncOrderToPaiuListener.class.getName());
    @Inject
    private DeliveryService deliveryService;

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private FreeOrderDAO freeOrderDAO;

    @Inject
    private DeliveryCompanyDAO deliveryCompanyDAO;

    @RequestMapping(value = "companylist", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<List<DeliveryCompanyInfo>> getSupportedCompanyList() {

        return new ResultView<>(CommonMetaCode.Success, deliveryService.getSupportedComapnyList());
    }

    @RequestMapping(value = "filldeliveryinfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject fillDeliveryInfo() {
        HttpServletRequest req = HehuaRequestContext.getRequest();
        log.info(req.getParameterMap().toString());
        JSONObject jo = new JSONObject();
        if (log.isDebugEnabled()) {
            System.out.println(req.getParameterMap().toString());
        }

        String appkey = req.getParameter("appkey");
        String appsecret = req.getParameter("appsecret");
        if (StringUtils.isBlank(appkey) || StringUtils.isBlank(appsecret)) {
            jo.put("state", "false");
            jo.put("reason", "未授权不能访问");
            return jo;
        }
        if (!appkey.equalsIgnoreCase(PaiyouConfig.hehua_paiu_appkey) || !appsecret.equalsIgnoreCase(PaiyouConfig.hehua_paiu_appsecret)) {
            jo.put("state", "false");
            jo.put("reason", "appsecret错误");
            return jo;
        }
        String deliveryNum = req.getParameter("logisticCode");
        if (StringUtils.isBlank(deliveryNum)) {
            jo.put("state", "false");
            jo.put("reason", "物流单号不存在");
            return jo;
        }

        String deliveryCompPinyin = req.getParameter("expressCode");
        if (StringUtils.isBlank(deliveryCompPinyin)) {
            jo.put("state", "false");
            jo.put("reason", "物流公司不存在");
            return jo;
        }
        DeliveryCompanyModel deliveryCompanyModel = deliveryCompanyDAO.getByPinyin(deliveryCompPinyin);
        if (deliveryCompanyModel == null) {
            jo.put("state", "false");
            jo.put("reason", "物流公司不支持");
            return jo;
        }
        String orderid = req.getParameter("orderCode");
        if (StringUtils.isBlank(orderid)) {
            jo.put("state", "false");
            jo.put("reason", "订单号不存在");
            return jo;
        }
        String[] orderidInfo = orderid.split("-");
        int type = OrderTraceModel.TYPE_DEFAULT;
        if (orderidInfo.length > 1) {
            orderid = orderidInfo[1];
            if (orderidInfo[0].equals("n")) {
                OrdersModel order = ordersDAO.getById(Long.parseLong(orderid));
                if (order == null) {
                    jo.put("state", "false");
                    jo.put("reason", "订单号错误");
                    return jo;
                }
            } else if (orderidInfo[0].equals("f")) {
                type = OrderTraceModel.TYPE_FREE;
                FreeOrderModel order = freeOrderDAO.getById(Integer.parseInt(orderid));
                if (order == null) {
                    jo.put("state", "false");
                    jo.put("reason", "订单号错误");
                    return jo;
                }
            } else {
                jo.put("state", "false");
                jo.put("reason", "订单号错误");
                return jo;
            }
        }


        if (deliveryService.fillDeliveryInfo(Long.parseLong(orderid), type, deliveryNum, deliveryCompPinyin)) {
            jo.put("state", "true");
            jo.put("reason", "处理成功");
            return jo;
        } else {
            jo.put("state", "false");
            jo.put("reason", "内部错误");
            return jo;
        }
    }
}
