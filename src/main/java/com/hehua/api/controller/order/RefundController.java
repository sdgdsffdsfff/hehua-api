package com.hehua.api.controller.order;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.framework.web.interceptor.ClientIdInterceptor;
import com.hehua.framework.web.interceptor.IgnoreIntereptor;
import com.hehua.order.alipay.Rsa;
import com.hehua.order.enums.OrdersErrorEnum;
import com.hehua.order.enums.RefundErrorEnum;
import com.hehua.order.exceptions.NoSuchPayConfigException;
import com.hehua.order.info.RefundInfo;
import com.hehua.order.model.OrdersModel;
import com.hehua.order.pay.BasePayProvider;
import com.hehua.order.pay.PayAlipayMobileProvider;
import com.hehua.order.pay.PayManager;
import com.hehua.order.refund.RefundActionManager;
import com.hehua.order.refund.params.ApplyParam;
import com.hehua.order.refund.params.ItemlistParam;
import com.hehua.order.refund.params.ReturngoodsParam;
import com.hehua.order.service.OrdersService;
import com.hehua.order.service.RefundService;
import com.hehua.order.service.params.RefundServiceApplyParam;
import com.hehua.order.service.params.RefundServiceConfirmParam;
import com.hehua.order.service.params.RefundServiceConfirmRetParam;
import com.hehua.order.utils.OrderTransactionManagerUtil;

/**
 * Created by liuweiwei on 14-8-11.
 */
@Named
@RequestMapping(value = "refund", produces = "application/json;charset=utf-8")
public class RefundController {

    @Inject
    private RefundService refundService;

    @Inject
    private OrdersService ordersService;

    @Inject
    private PayManager payManager;

    @Inject
    private RefundActionManager refundActionManager;

    @Inject
    private OrderTransactionManagerUtil orderTransactionManagerUtil;

    @RequestMapping(value = "apply", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<String> apply(@RequestBody RefundServiceApplyParam param) {
        return new ResultView<>(RefundErrorEnum.API_EXPIRED);
        //return refundService.updateApply(param);
    }

    @RequestMapping(value = "apply/v2", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<List<RefundInfo>> applyV2(@RequestBody ApplyParam param) {

        param.setUserid(HehuaRequestContext.getUserId());
        TransactionStatus txStatus = orderTransactionManagerUtil.getTxStatus();
        ResultView<List<RefundInfo>> result = null;
        try {
            result = refundService.actionApply(param);
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

    @RequestMapping(value = "items/{orderid}", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<ItemlistParam> getRefundItems(@PathVariable("orderid") long orderid) {
        long userid = HehuaRequestContext.getUserId();
        OrdersModel order = ordersService.getByOrderId(orderid);
        if (order == null || order.getUserid() != userid) {
            return new ResultView<>(OrdersErrorEnum.PARAM_ERROR);
        }
        if (!order.canRefund()) {
            return new ResultView<>(RefundErrorEnum.CAN_NOT_APPLY);
        }
        ItemlistParam itemlistParam = new ItemlistParam();
        itemlistParam.setItems(refundService.getRefundItemInfos(orderid));
        return new ResultView<>(CommonMetaCode.Success, itemlistParam);
    }

    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<RefundServiceConfirmRetParam> refundConfirm(
            @RequestBody RefundServiceConfirmParam param) {
        long userid = HehuaRequestContext.getUserId();
        param.setUserid(userid);
        return refundService.refundConfirm(param);
    }

    @RequestMapping(value = "cancel/{refundid}", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<List<RefundInfo>> refundCancel(@PathVariable("refundid") int refundid) {
        long userid = HehuaRequestContext.getUserId();
        return refundService.actionCancel(refundid, userid);
    }

    @RequestMapping(value = "deliveryinfo", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<List<RefundInfo>> fillDeliveryInfo(@RequestBody ReturngoodsParam param) {
        long userid = HehuaRequestContext.getUserId();
        param.setUserid(userid);
        return refundService.actionReturngoods(param);
    }

    @IgnoreIntereptor(ClientIdInterceptor.class)
    @RequestMapping(value = "notify/{paytype}", method = RequestMethod.POST)
    public ModelAndView refundNotify(@PathVariable("paytype") String paytype) {
        Logger log = Logger.getLogger("notifyLogFile");
        ModelAndView mav = new ModelAndView("refundnotify");
        if (paytype.equalsIgnoreCase("alipaymobile")) {
            HttpServletRequest req = HehuaRequestContext.getRequest();
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
                mav.addObject("data", "error");
                return mav;
            }
            log.info(data);
            BasePayProvider payProvider = null;
            try {
                payProvider = payManager.getPayObj(1);
            } catch (NoSuchPayConfigException e) {
                mav.addObject("data", "error");
                return mav;
            }
            Map<String, String> params = payProvider.url2map(data);
            String sign = params.get("sign");
            String sign_type = params.get("sign_type");
            params = payProvider.paraFilter(params);
            String strTosign = payProvider.prepareSignStr(params, false);
            if (!sign_type.equalsIgnoreCase("RSA")) {
                mav.addObject("data", "error");
                return mav;
            }
            //TODO 返回错误
            if (Rsa.doCheck(strTosign, sign,
                    ((PayAlipayMobileProvider) payProvider).alipay_public_key)) {
                log.error("sign error:" + data);
            }

            refundService.updateProcessRefundNotify(params);
            mav.addObject("data", "success");
        }
        return mav;
    }
}
