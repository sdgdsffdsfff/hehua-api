package com.hehua.api.controller.order;

import com.hehua.api.controller.BaseController;
import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.item.domain.ItemSku;
import com.hehua.item.service.ItemSkuService;
import com.hehua.order.enums.CartErrorEnum;
import com.hehua.order.info.CartSummaryInfo;
import com.hehua.order.info.CartsInfo;
import com.hehua.order.model.CartModel;
import com.hehua.order.service.CartService;
import com.hehua.order.utils.OrderTransactionManagerUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liuweiwei on 14-8-4.
 */
@Controller
@RequestMapping(value = "cart")
public class CartController extends BaseController {
    
    private static final Log logger = LogFactory.getLog(CartController.class);
    

    @Inject
    private OrderTransactionManagerUtil orderTransactionManagerUtil;

    @Inject
    private CartService cartService;

    @Inject
    private ItemSkuService itemSkuService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<CartSummaryInfo> add2Cart() {

        long userid = HehuaRequestContext.getUserId();

        HttpServletRequest req = HehuaRequestContext.getRequest();
        if (org.apache.commons.lang3.StringUtils.isBlank(req.getParameter("skuid"))) {
            return new ResultView<>(CartErrorEnum.SKUID_NOT_PRESENT);
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(req.getParameter("quantity"))) {
            return new ResultView<>(CartErrorEnum.SKU_QUANTITY_NOT_PRESENT);
        }
        if (!NumberUtils.isNumber(req.getParameter("skuid")) || !NumberUtils.isNumber(req.getParameter("quantity"))) {
            return new ResultView<>(CartErrorEnum.PARAM_TYPE_ERROR);
        }

        int skuid = Integer.parseInt(req.getParameter("skuid"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("addcart");
        ItemSku itemSku = itemSkuService.getItemSkuById(skuid);
        if (itemSku != null) {
            userAccessInfo.setItemid(itemSku.getItemid());
        }
        userAccessInfo.setSkuid(String.valueOf(skuid));

        ResultView<CartSummaryInfo> result = null;
        TransactionStatus txStatus = orderTransactionManagerUtil.getTxStatus();
        try {
            result = cartService.add2Cart(userid, skuid, quantity);
        } catch (Exception e) {
            orderTransactionManagerUtil.rollback(txStatus);
            throw new RuntimeException("add2Cart error", e);
        }
        if (result.getMeta().getCode() == CommonMetaCode.Success) {
            orderTransactionManagerUtil.commit(txStatus);
            userAccessInfo.setStatus(UserAccessInfo.STATUS_SUCCESS);
        } else {
            orderTransactionManagerUtil.rollback(txStatus);
            userAccessInfo.setStatus(result.getMeta().getCode().getCode());
        }
        flumeEventLogger.info(userAccessInfo);

        return result;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public ResultView<CartSummaryInfo> getListByUserID() {

        long userid = HehuaRequestContext.getUserId();
        CartSummaryInfo cartSummaryInfo = cartService.getCartSummaryInfoByUserId(userid);

        Set<Long> itemids = new HashSet<>();
        for (CartsInfo info : cartSummaryInfo.getCarts()) {
            itemids.add(info.getItemid());
        }

        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("getcart");
        userAccessInfo.setStatus(UserAccessInfo.STATUS_SUCCESS);
        userAccessInfo.setCartSize(cartSummaryInfo.getCarts().size());
        userAccessInfo.setItemQuantity(itemids.size());
        flumeEventLogger.info(userAccessInfo);

        return new ResultView<>(CommonMetaCode.Success, cartSummaryInfo);
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public ResultView<CartSummaryInfo> removeFromCart(@PathVariable("id") String id) {
        long userid = HehuaRequestContext.getUserId();

        /**
         * 很蛋疼的记录日志。。。
         */
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("deletecart");
        Set<Integer> idSet = com.hehua.commons.lang.StringUtils.getIntegerSet(id);
        Iterator<Integer> iter = idSet.iterator();
        while (iter.hasNext()) {
            int cartid = iter.next();
            CartModel cart = cartService.getById(cartid);
            if (cart == null) {
                userAccessInfo.setStatus(CartErrorEnum.ITEM_NOT_EXIST.getCode());
                flumeEventLogger.info(userAccessInfo);
                continue;
            }
            userAccessInfo.setSkuid(String.valueOf(cart.getSkuid()));
            ItemSku itemSku = itemSkuService.getItemSkuById(cart.getSkuid());
            if (itemSku != null) {
                userAccessInfo.setItemid(itemSku.getItemid());
            }
            userAccessInfo.setStatus(UserAccessInfo.STATUS_SUCCESS);
            flumeEventLogger.info(userAccessInfo);
        }

        ResultView<CartSummaryInfo> result = null;
        TransactionStatus txStatus = orderTransactionManagerUtil.getTxStatus();
        try {
            result = cartService.deleteByIds(userid, id);
        } catch (Exception e) {
            orderTransactionManagerUtil.rollback(txStatus);
            throw new RuntimeException(e);
        }
        if (result.getMeta().getCode() != CommonMetaCode.Success) {
            orderTransactionManagerUtil.rollback(txStatus);
        } else {
            orderTransactionManagerUtil.commit(txStatus);
        }
        return result;
    }
}
