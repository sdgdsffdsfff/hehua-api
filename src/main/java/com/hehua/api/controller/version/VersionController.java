package com.hehua.api.controller.version;

import com.alibaba.fastjson.JSONObject;
import com.hehua.api.controller.BaseController;
import com.hehua.api.controller.login.LoginForm;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.user.domain.PhoneVersion;
import com.hehua.user.model.LoginResult;
import com.hehua.user.service.LoginService;
import com.hehua.user.service.PhoneVersionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class VersionController {
    @Inject
    private PhoneVersionService phoneVersionService;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject versionUpdate() {
       JSONObject retJson = new JSONObject(1);
       JSONObject dataJson = new JSONObject();
       HttpServletRequest req = HehuaRequestContext.getRequest();
       String channel = req.getParameter("channel");
       String version = req.getParameter("version");

        if (StringUtils.isEmpty(channel)) {
           dataJson.put("code", 1002);
           dataJson.put("message", "channel为空");
           retJson.put("error", dataJson);
           return retJson;
       }
       PhoneVersion phoneVersion = phoneVersionService.getPhoneVersionBy(version, channel);
       if (phoneVersion != null) {
           dataJson.put("channel", phoneVersion.getChannel());
           dataJson.put("version", phoneVersion.getVersion());
           dataJson.put("downloadurl", phoneVersion.getDownloadurl());
           dataJson.put("forceupdate", phoneVersion.getForceupdate());
           dataJson.put("releasenote", phoneVersion.getReleasenote());
           retJson.put("data", dataJson);
       } else {
           dataJson.put("code", 1001);
           dataJson.put("message", "更新失败");
           retJson.put("error", dataJson);
       }
       return retJson;
    }
}
