package com.hehua.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehua.api.controller.register.RegisterForm;

@Controller
public class WelcomeController {

    private static final Log logger = LogFactory.getLog(WelcomeController.class);

    @RequestMapping(value = { "/welcome", "/" }, method = RequestMethod.GET)
    @ResponseBody
    public Object showWelcomeMessage() {
        Map<String, String> result = new HashMap<>();
        result.put("message", "Welcome To hehuababy.com!");
        return result;
    }

    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public Object test(Map<String, String> params) {

        if (logger.isDebugEnabled()) {
            logger.debug("test1: params=" + params);
        }

        Map<String, String> result = new HashMap<>();
        result.put("message", "hello hehua api!");
        return result;
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public Object test(RegisterForm registerForm) {

        if (logger.isDebugEnabled()) {
            logger.debug("test2: params=" + registerForm);
        }

        Map<String, String> result = new HashMap<>();
        result.put("message", "hello hehua api!");
        return result;
    }

    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ResponseBody
    public Object test(@RequestParam("baby.birthday") Date birthday) {

        if (logger.isDebugEnabled()) {
            logger.debug("test3: birthday=" + birthday);
        }

        Map<String, String> result = new HashMap<>();
        result.put("message", "hello hehua api!");
        return result;
    }
}
