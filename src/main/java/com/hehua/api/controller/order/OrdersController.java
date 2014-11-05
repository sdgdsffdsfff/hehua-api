package com.hehua.api.controller.order;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehua.api.controller.BaseController;
import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.commons.page.Paginator;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.framework.web.interceptor.ClientIdInterceptor;
import com.hehua.framework.web.interceptor.IgnoreIntereptor;
import com.hehua.item.domain.ItemSku;
import com.hehua.item.service.ItemSkuService;
import com.hehua.order.enums.OrdersErrorEnum;
import com.hehua.order.info.DeliveryInfo;
import com.hehua.order.info.OrderInfo;
import com.hehua.order.info.SkuInfo;
import com.hehua.order.info.TraceInfo;
import com.hehua.order.model.OrdersModel;
import com.hehua.order.service.NotifyService;
import com.hehua.order.service.OrdersService;
import com.hehua.order.service.PayService;
import com.hehua.order.service.params.OrderServiceConfirmParam;
import com.hehua.order.service.params.OrderServiceConfirmRetParam;
import com.hehua.order.service.params.OrdersServiceCreateOrderParam;
import com.hehua.order.service.params.PayServicePayParam;
import com.hehua.order.service.params.PayServicePayRetParam;
import com.hehua.order.utils.DecimalUtil;
import com.hehua.order.utils.ItemTransactionManagerUtil;
import com.hehua.order.utils.OrderTransactionManagerUtil;

/**
 * Created by liuweiwei on 14-7-29.
 */
@Controller
@RequestMapping(value = "orders", produces = "application/json;charset=utf-8")
public class OrdersController extends BaseController {

    @Inject
    private OrdersService ordersService;

    @Inject
    private NotifyService notifyService;

    @Inject
    private PayService payService;

    @Inject
    private ItemSkuService itemSkuService;

    @Inject
    private OrderTransactionManagerUtil orderTransactionManagerUtil;

    @Inject
    private ItemTransactionManagerUtil itemTransactionManagerUtil;

    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<OrderServiceConfirmRetParam> confirm(
            @RequestBody OrderServiceConfirmParam param) {
        long userid = HehuaRequestContext.getUserId();
        param.setUserid(userid);
        ResultView<OrderServiceConfirmRetParam> result = ordersService.confirmBeforeOrder(param);

        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("payinfo");
        if (result.getMeta().getCode() == CommonMetaCode.Success) {
            userAccessInfo.setStatus(UserAccessInfo.STATUS_SUCCESS);
        } else {
            userAccessInfo.setStatus(result.getMeta().getCode().getCode());
        }
        userAccessInfo.setCartSelected(param.getItems().size());
        Set<Long> itemIds = new HashSet<>();
        int skuQuantity = 0;
        for (SkuInfo skuInfo : param.getItems()) {
            ItemSku itemSku = itemSkuService.getItemSkuById(skuInfo.getSkuid());
            if (itemSku != null) {
                itemIds.add(itemSku.getItemid());
            }
            skuQuantity += skuInfo.getQuantity();
        }
        userAccessInfo.setItemQuantity(itemIds.size());
        userAccessInfo.setSkuQuantity(skuQuantity);

        if (result.getMeta().getCode() == CommonMetaCode.Success) {
            userAccessInfo.setTotolFee(result.getData().getTotalFee().doubleValue());
            userAccessInfo.setDeliveryFee(result.getData().getDeliveryFee().doubleValue());
        }
        flumeEventLogger.info(userAccessInfo);

        return result;
    }

    @RequestMapping(value = "payconfirm/{id}", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<OrderServiceConfirmRetParam> confirm(@PathVariable("id") long orderid) {
        return ordersService.confirmAfterOrder(orderid);
    }

    @RequestMapping(value = "createandpay", method = RequestMethod.POST, headers = { "Content-type=application/json" })
    @ResponseBody
    @LoginRequired
    public ResultView<PayServicePayRetParam> createOrder(
            @RequestBody OrdersServiceCreateOrderParam param) {

        long userid = HehuaRequestContext.getUserId();
        UserAccessInfo accessInfo = HehuaRequestContext.getUserAccessInfo();
        accessInfo.setEvent("order");

        param.setUserid(userid);
        param.setIp(accessInfo.getIp());
        TransactionStatus txStatus = orderTransactionManagerUtil.getTxStatus();
        ResultView<PayServicePayRetParam> result = null;
        try {
            result = ordersService.createOrderAndPay(param);
        } catch (Exception e) {
            orderTransactionManagerUtil.rollback(txStatus);
            throw new RuntimeException(e);
        }

        if (result.getMeta().getCode() != CommonMetaCode.Success) {
            orderTransactionManagerUtil.rollback(txStatus);
            accessInfo.setStatus(result.getMeta().getCode().getCode());
        } else {
            accessInfo.setStatus(UserAccessInfo.STATUS_SUCCESS);
            //提交订单事务
            orderTransactionManagerUtil.commit(txStatus);
            //减库存
            TransactionStatus itemTxStatus = itemTransactionManagerUtil.getTxStatus();
            boolean bolSucc = false;
            try {
                bolSucc = ordersService.updateItemQuantity(result.getData().getOrderid(), false);
            } catch (Exception e) {
                itemTransactionManagerUtil.rollback(itemTxStatus);
                throw new RuntimeException(e);
            }
            if (bolSucc) {
                itemTransactionManagerUtil.commit(itemTxStatus);
            } else {
                itemTransactionManagerUtil.rollback(itemTxStatus);
                result = new ResultView<>(OrdersErrorEnum.SKU_QUANTITY_ERROR);
                accessInfo.setStatus(result.getMeta().getCode().getCode());
            }
        }
        accessInfo.setCartSelected(param.getItems().size());
        Set<Long> itemIds = new HashSet<>();
        int skuQuantity = 0;
        for (SkuInfo skuInfo : param.getItems()) {
            ItemSku itemSku = itemSkuService.getItemSkuById(skuInfo.getSkuid());
            if (itemSku != null) {
                itemIds.add(itemSku.getItemid());
            }
            skuQuantity += skuInfo.getQuantity();
        }
        accessInfo.setItemQuantity(itemIds.size());
        accessInfo.setSkuQuantity(skuQuantity);
        if (result.getMeta().getCode() == CommonMetaCode.Success) {
            OrdersModel order = ordersService.getByOrderId(result.getData().getOrderid());
            if (order != null) {
                accessInfo.setOrderid(order.getId());
                accessInfo.setTotolFee(DecimalUtil.toStandard(order.getTotalfee()).doubleValue());
                accessInfo.setDeliveryFee(DecimalUtil.toStandard(order.getDeliveryfee())
                        .doubleValue());
            }
        }
        flumeEventLogger.info(accessInfo);
        return result;
    }

    @RequestMapping(value = "pay", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<PayServicePayRetParam> pay(@RequestBody PayServicePayParam param) {
        TransactionStatus txStatus = orderTransactionManagerUtil.getTxStatus();
        ResultView<PayServicePayRetParam> result = null;
        try {
            result = payService.pay(param);
        } catch (Exception e) {
            orderTransactionManagerUtil.rollback(txStatus);
            throw new RuntimeException(e);
        }
        if (result.getMeta().getCode() == CommonMetaCode.Success) {
            orderTransactionManagerUtil.commit(txStatus);
        } else {
            orderTransactionManagerUtil.rollback(txStatus);
        }
        return result;
    }

    @IgnoreIntereptor(ClientIdInterceptor.class)
    @RequestMapping(value = "notify/{paytype}", method = RequestMethod.POST)
    public String payNotify(@PathVariable String paytype, Model model) {

        HttpServletRequest req = HehuaRequestContext.getRequest();

        if (paytype.equalsIgnoreCase("alipaymobile")) {
            StringBuffer sb = new StringBuffer();
            boolean hasElement = false;
            Enumeration pNames = req.getParameterNames();
            while (pNames.hasMoreElements()) {
                String name = (String) pNames.nextElement();
                String value = req.getParameter(name);
                sb.append(name + "=" + value + "&");
                hasElement = true;
            }
            String data = null;
            if (hasElement) {
                data = sb.toString();
                data = data.substring(0, data.length() - 1);
            } else {
                model.addAttribute("data", "error");
                return "notify";
            }
            if (notifyService.saveNotify(paytype, data)) {
                model.addAttribute("data", "success");
            } else {
                model.addAttribute("data", "error");
            }
        }
        return "paynotify";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<Paginator> getListByUserId(HttpServletRequest request) {

        int offset = 0;
        int limit = 5;
        int status = -1;

        if (request.getParameter("offset") != null
                && Integer.parseInt(request.getParameter("offset")) > 0) {
            offset = Integer.parseInt(request.getParameter("offset"));
        }
        if (request.getParameter("limit") != null
                && Integer.parseInt(request.getParameter("limit")) > 0) {
            limit = Integer.parseInt(request.getParameter("limit"));
        }
        if (request.getParameter("status") != null) {
            status = Integer.parseInt(request.getParameter("status"));
        }
        long userid = HehuaRequestContext.getUserId();
        List<OrderInfo> orderInfos = ordersService.getListByUserId(userid, status, offset,
                limit + 1);

        boolean hasMore = orderInfos.size() == (limit + 1);
        if (hasMore) {
            orderInfos.remove(limit);
        }
        limit = orderInfos.size();

        return new ResultView<>(CommonMetaCode.Success, new Paginator(orderInfos, limit, offset,
                hasMore));
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<OrderInfo> getOrderById(@PathVariable("id") long orderid) {
        ResultView<OrderInfo> result = ordersService.getById(orderid);
        OrderInfo orderInfo = result.getData();
        if (orderInfo != null) {
            filterDeliveryInfo(orderInfo);
        }
        return result;
    }

    private void filterDeliveryInfo(OrderInfo orderInfo) {
        DeliveryInfo deliveryInfo = orderInfo.getDeliveryInfo();
        if (deliveryInfo == null) {
            return;
        }

        List<TraceInfo> traces = deliveryInfo.getTraceInfo();
        if (traces != null) {
            for (TraceInfo trace : traces) {
                trace.setEvent(removeTabChars(trace.getEvent()));
            }
        }
    }

    private static String removeTabChars(String event) {
        if (StringUtils.isEmpty(event)) {
            return event;
        }
        return StringUtils.replace(event, "\\t", "");
    }

}
