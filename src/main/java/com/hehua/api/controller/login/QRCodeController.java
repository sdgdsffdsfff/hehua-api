package com.hehua.api.controller.login;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.user.exeception.QRCodeNotExistException;
import com.hehua.user.exeception.QRCodeStatusErrorException;
import com.hehua.user.service.QrcodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by liuweiwei on 14-10-8.
 */
@Controller
@RequestMapping(value = "/qrcode")
public class QRCodeController {

    @Inject
    private QrcodeService qrcodeService;

    @LoginRequired
    @RequestMapping(value = "ack/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResultView<Integer> ack(@PathVariable("uuid") String uuid) throws QRCodeStatusErrorException, QRCodeNotExistException {
        return new ResultView<>(CommonMetaCode.Success, qrcodeService.ack(HehuaRequestContext.getUserId(), uuid));
    }

    @LoginRequired
    @RequestMapping(value = "login/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResultView<Integer> login(@PathVariable("uuid") String uuid) throws QRCodeStatusErrorException, QRCodeNotExistException {
        return new ResultView<>(CommonMetaCode.Success, qrcodeService.login(HehuaRequestContext.getUserId(), uuid));
    }
}
