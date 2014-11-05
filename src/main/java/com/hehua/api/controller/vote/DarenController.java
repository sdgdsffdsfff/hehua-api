package com.hehua.api.controller.vote;

import com.alibaba.fastjson.JSONObject;
import com.hehua.item.service.VoteServiceApiProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
public class DarenController {

    @Inject
    VoteServiceApiProxy voteServiceApiProxy;

    @RequestMapping(value = "daren/{userid}/profile", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteList(@PathVariable("userid") int userid,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        JSONObject jo = voteServiceApiProxy.getVoteFlashItemListByUserId(userid, offset, limit);
        return jo;
    }
}
