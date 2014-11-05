package com.hehua.api.controller;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.hehua.framework.log.LogService;
import com.hehua.framework.web.HehuaRequestContext;

/**
 * Created by liuweiwei on 14-7-29.
 */
@Named
public class BaseController {

    protected static LogService flumeEventLogger = LogService.getInstance();

}
