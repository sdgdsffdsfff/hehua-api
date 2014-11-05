/**
 * 
 */
package com.hehua.api.controller.findpsw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.api.controller.register.SendCaptchaForm;
import com.hehua.api.controller.register.VerifyCaptchaForm;
import com.hehua.framework.web.render.ResponseRender;
import com.hehua.user.domain.User;
import com.hehua.user.exeception.CaptchaExpiredException;
import com.hehua.user.exeception.CaptchaNotMatchException;
import com.hehua.user.exeception.CaptchaTooFastException;
import com.hehua.user.exeception.CaptchaTooManyException;
import com.hehua.user.exeception.ChangePwdTokenExpiredException;
import com.hehua.user.exeception.ChangePwdTokenNotFoundException;
import com.hehua.user.exeception.InvalidCaptchaException;
import com.hehua.user.exeception.InvalidMobileException;
import com.hehua.user.exeception.UsernameNotFoundException;
import com.hehua.user.model.ChangePwdToken;
import com.hehua.user.model.LoginResult;
import com.hehua.user.service.FindPwdService;
import com.hehua.user.service.LoginService;
import com.hehua.user.service.UserManager;

/**
 * @author zhihua
 *
 */
@Controller
public class FindPwdController {

    @Autowired
    private FindPwdService findPwdService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user/findpwd/sendcode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendCaptcha(@RequestBody SendCaptchaForm sendCaptchaForm)
            throws InvalidMobileException, UsernameNotFoundException, CaptchaTooFastException,
            CaptchaTooManyException {
        findPwdService.sendFindPwdCaptcha(sendCaptchaForm.getMobile());
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        result.put("data", data);
        return result;

    }

    @RequestMapping(value = "/user/findpwd/verifycode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject verifyCaptcha(@RequestBody VerifyCaptchaForm verifyCaptchaForm)
            throws InvalidMobileException, UsernameNotFoundException, CaptchaExpiredException,
            CaptchaNotMatchException, InvalidCaptchaException {

        findPwdService.checkFindPwdCaptcha(verifyCaptchaForm.getMobile(),
                verifyCaptchaForm.getCode());

        User user = userManager.getUserByMobile(verifyCaptchaForm.getMobile());
        ChangePwdToken changePswToken = findPwdService.generateChangePwdToken(user);

        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("changePwdToken", changePswToken.getToken());
        result.put("data", data);
        return result;
    }

    @RequestMapping(value = "/user/findpwd/modifypwd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changePwd(@RequestBody ChangePasswordForm changePasswordForm)
            throws InvalidMobileException, UsernameNotFoundException,
            ChangePwdTokenExpiredException, ChangePwdTokenNotFoundException {

        findPwdService.changePwd(changePasswordForm.getMobile(),
                changePasswordForm.getChangePwdToken(), changePasswordForm.getPassword());

        User user = userManager.getUserByMobile(changePasswordForm.getMobile());
        LoginResult loginResult = loginService.doLogin(user);
        return ResponseRender.renderResponse(loginResult);
    }
}
