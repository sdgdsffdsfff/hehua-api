package com.hehua.api.controller.login;

import javax.inject.Inject;

import com.hehua.api.controller.BaseController;
import com.hehua.commons.exception.BusinessException;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.user.model.LoginResult;
import com.hehua.user.service.BabyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.framework.web.render.ResponseRender;
import com.hehua.user.exeception.PasswordNotMatchException;
import com.hehua.user.exeception.UsernameNotFoundException;
import com.hehua.user.service.LoginService;

@Controller
public class LoginController extends BaseController {

    @Inject
    private LoginService loginService;

    @Inject
    private BabyService babyService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestBody LoginForm loginForm) throws UsernameNotFoundException,
            PasswordNotMatchException {
        LoginResult loginResult = null;
        UserAccessInfo accessInfo = HehuaRequestContext.getUserAccessInfo();
        accessInfo.setEvent("login");
        int status = 0;
        try {
            loginResult = loginService.login(loginForm.getMobile(),loginForm.getPassword());
            accessInfo.setUid(loginResult.getId());
            accessInfo.setPreganancy(loginResult.getBaby().getStatus());
            accessInfo.setStatus(status);
            flumeEventLogger.info(accessInfo.toString());
        } catch (BusinessException e) {
            status = e.getCode().getCode();
            accessInfo.setStatus(status);
            flumeEventLogger.info(accessInfo.toString());
            throw e;
        }
        return ResponseRender.renderResponse(loginResult);
    }
}