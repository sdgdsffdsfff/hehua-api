package com.hehua.api.controller.register;

import javax.inject.Inject;

import com.hehua.api.controller.BaseController;
import com.hehua.commons.exception.BusinessException;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.user.model.RegisterResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.framework.web.render.ResponseRender;
import com.hehua.user.exeception.FreshTokenNotFoundException;
import com.hehua.user.exeception.InvalidPasswordException;
import com.hehua.user.exeception.MobileAlreadyBindedException;
import com.hehua.user.service.RegisterService;

@Controller
public class RegisterController extends BaseController {

    @Inject
    private RegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestBody RegisterForm registerForm)
            throws MobileAlreadyBindedException, FreshTokenNotFoundException,
            InvalidPasswordException {
        RegisterResult result = null;
        UserAccessInfo accessInfo = HehuaRequestContext.getUserAccessInfo();
        accessInfo.setEvent("reglogin");
        int status = 0;
        try {
            result = registerService.register(registerForm.getFreshtoken(),registerForm.getPassword(), registerForm.getBaby());
            accessInfo.setStatus(status);
            accessInfo.setUid(result.getId());
            accessInfo.setPreganancy(result.getBaby().getStatus());
            flumeEventLogger.info(accessInfo);
        } catch (BusinessException e) {
            status = e.getCode().getCode();
            accessInfo.setStatus(status);
            flumeEventLogger.info(accessInfo);
            throw e;
        }

        return ResponseRender.renderResponse(result);
    }
}
