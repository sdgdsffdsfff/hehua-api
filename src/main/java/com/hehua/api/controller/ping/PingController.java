/**
 * 
 */
package com.hehua.api.controller.ping;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.framework.web.interceptor.ClientIdInterceptor;
import com.hehua.framework.web.interceptor.IgnoreIntereptor;
import com.hehua.framework.web.interceptor.TokenInterceptor;
import com.hehua.framework.web.util.ResponseUtils;

/**
 * @author zhihua
 *
 */
@Controller
public class PingController {

    @IgnoreIntereptor({ ClientIdInterceptor.class, TokenInterceptor.class })
    @RequestMapping("/ping")
    public ModelAndView ping(HttpServletResponse response) {
        ResponseUtils.output(response, "OK");
        return null;
    }
}
