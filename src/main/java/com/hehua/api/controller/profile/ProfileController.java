/**
 * 
 */
package com.hehua.api.controller.profile;

import java.util.Map;

import com.hehua.user.exeception.NickNameAlreadyExistException;
import com.hehua.user.exeception.NickNameIllegalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.hehua.api.controller.address.AddressRender;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.framework.web.render.ResponseRender;
import com.hehua.order.model.OrdersModel;
import com.hehua.order.service.OrderService;
import com.hehua.user.domain.Address;
import com.hehua.user.domain.Baby;
import com.hehua.user.domain.User;
import com.hehua.user.exeception.InvalidNicknameException;
import com.hehua.user.model.BabyView;
import com.hehua.user.service.AddressService;
import com.hehua.user.service.BabyService;
import com.hehua.user.service.UserManager;
import com.hehua.user.service.UserService;

/**
 * @author zhihua
 *
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private BabyService babyService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ProfileRender profileRender;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressRender addressRender;

    @Autowired
    private UserManager userManager;

    @LoginRequired
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getProfile() {

        long userId = HehuaRequestContext.getUserId();
        User user = userManager.getUserById(userId);
        Baby baby = babyService.getBabyByUidxUid(userId);
        Address defaultAddress = addressService.getDefaultAddress(userId);

        Map<Integer, Integer> statuesMap = orderService.getStatusesMap(userId);
        int unpays = Objects.firstNonNull(statuesMap.get(OrdersModel.STATUS_NEW), 0);
        int unrececives = Objects.firstNonNull(statuesMap.get(OrdersModel.STATUS_DELIVERIED), 0);

        return ResponseRender.renderResponse(profileRender.renderProfile(user, baby,
                defaultAddress, unpays, unrececives));
    }

    @LoginRequired
    @RequestMapping(value = "/profile/settings/baby", method = RequestMethod.POST)
    @ResponseBody
    public ResultView<BabyView> updateBabySettings(@RequestBody BabyView babyParams) {
        long userId = HehuaRequestContext.getUserId();
        return babyService.updateBaby(userId, babyParams);
    }

    @LoginRequired
    @RequestMapping(value = "/profile/settings/nickname", method = RequestMethod.POST)
    @ResponseBody
    public Object updateNickname(@RequestBody UpdateNicknameForm updateNicknameForm)
            throws InvalidNicknameException, NickNameAlreadyExistException, NickNameIllegalException {
        long userId = HehuaRequestContext.getUserId();
        String result = userService.updateNickname(userId, updateNicknameForm.getNickname());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", result);
        return ResponseRender.renderResponse(jsonObject);
    }
}
