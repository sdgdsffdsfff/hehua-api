package com.hehua.api.controller.feedback;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.order.dao.OrdersDAO;
import com.hehua.order.enums.OrdersErrorEnum;
import com.hehua.order.info.OrderItemFeedbackInfo;
import com.hehua.order.model.OrdersModel;
import com.hehua.order.service.FeedbackService;
import com.hehua.order.service.OrdersService;
import com.hehua.order.service.params.FeedbackServiceAddParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by liuweiwei on 14-8-18.
 */
@Controller
@RequestMapping(value = "/feedback", produces = "application/json;charset=utf-8")
public class FeedbackController {

    @Inject
    private FeedbackService feedbackService;

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private OrdersService ordersService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<String> add(@RequestBody FeedbackServiceAddParam param) {

        return feedbackService.updateAdd(param);
    }

    @RequestMapping(value = "order/{orderid}", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<List<OrderItemFeedbackInfo>> getByOrderId(@PathVariable("orderid") long orderid) {
        long userid = HehuaRequestContext.getUserId();
        OrdersModel order = ordersDAO.getById(orderid);
        if (order == null) {
            return new ResultView<>(OrdersErrorEnum.ORDER_NOT_EXIST);
        }
        if (order.getUserid() != userid) {
            return new ResultView<>(OrdersErrorEnum.ORDER_NOT_YOURS);
        }
        return new ResultView<>(CommonMetaCode.Success, ordersService.getOrderItemsFeedbackInfo(orderid));
    }

 }
