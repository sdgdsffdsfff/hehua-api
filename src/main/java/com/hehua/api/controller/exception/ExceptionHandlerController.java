/**
 * 
 */
package com.hehua.api.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhihua
 *
 */
@Controller
public class ExceptionHandlerController {

    //    @org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView showLoginPage() {
        return new ModelAndView("redirect:/login");
    }
}
