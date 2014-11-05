/**
 * 
 */
package com.hehua.api.controller.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.framework.web.render.ResponseRender;

/**
 * @author zhihua
 *
 */
@Controller
public class LogController {

    private static final Log logger = LogFactory.getLog(LogController.class);

    @RequestMapping("/log")
    @ResponseBody
    public JSONObject log(@RequestBody LogForm logForm) {
        logger.info(logForm.getType() + "|" + logForm.getContent());
        return ResponseRender.renderResponse(new JSONObject());
    }
}
