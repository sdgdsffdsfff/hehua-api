/**
 * 
 */
package com.hehua.api.controller.register;

import com.hehua.api.controller.BaseController;
import com.hehua.commons.exception.BusinessException;
import com.hehua.commons.model.UserAccessInfo;
import com.hehua.framework.web.HehuaRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.commons.NormalizeUtils;
import com.hehua.framework.web.render.ResponseRender;
import com.hehua.user.domain.Freshtoken;
import com.hehua.user.exeception.CaptchaExpiredException;
import com.hehua.user.exeception.CaptchaNotMatchException;
import com.hehua.user.exeception.CaptchaTooFastException;
import com.hehua.user.exeception.CaptchaTooManyException;
import com.hehua.user.exeception.InvalidCaptchaException;
import com.hehua.user.exeception.InvalidMobileException;
import com.hehua.user.exeception.MobileAlreadyBindedException;
import com.hehua.user.service.RegisterService;

/**
 * @author zhihua
 *
 */
@Controller
public class CaptchaController extends BaseController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/register/mobile/sendcode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendCaptcha(@RequestBody SendCaptchaForm sendCaptchaForm)
            throws InvalidMobileException, MobileAlreadyBindedException, CaptchaTooFastException,
            CaptchaTooManyException {
        UserAccessInfo userAccessInfo = HehuaRequestContext.getUserAccessInfo();
        userAccessInfo.setEvent("regrequest");
        try {
            registerService.sendRegisterCaptcha(sendCaptchaForm.getMobile());
            userAccessInfo.setStatus(0);
            flumeEventLogger.info(userAccessInfo.toString());
        } catch (BusinessException e) {
            userAccessInfo.setStatus(e.getCode().getCode());
            flumeEventLogger.info(userAccessInfo.toString());
            throw e;
        }
        return ResponseRender.renderResponse(new JSONObject());
    }

    @RequestMapping(value = "/register/mobile/verifycode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject verifyCaptcha(@RequestBody VerifyCaptchaForm verifyCaptchaForm)
            throws InvalidMobileException, CaptchaExpiredException, CaptchaNotMatchException,
            InvalidCaptchaException {

        String mobile = NormalizeUtils.normalizeMobile(verifyCaptchaForm.getMobile());
        registerService.verifyRegisterCaptcha(mobile, verifyCaptchaForm.getCode());
        Freshtoken freshtoken = registerService.generateFreshtoken(mobile);

        JSONObject data = new JSONObject();
        data.put("freshtoken", freshtoken.getFreshtoken());
        return ResponseRender.renderResponse(data);

    }
}
