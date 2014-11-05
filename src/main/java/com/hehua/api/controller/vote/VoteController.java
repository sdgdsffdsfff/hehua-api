package com.hehua.api.controller.vote;

import com.alibaba.fastjson.JSONObject;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.freeorder.service.FreeOrderService;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.service.VoteServiceApiProxy;
import com.hehua.user.domain.PhoneVersion;
import com.hehua.user.service.PhoneVersionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class VoteController {

    @Inject
    VoteServiceApiProxy voteServiceApiProxy;

    @Inject
    FreeOrderService freeOrderService;

    @RequestMapping(value = "/free/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteList(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                  @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {

        List<FreeFlash> flashList = voteServiceApiProxy.getVoteFlashItemList(offset, limit);
        if (CollectionUtils.isEmpty(flashList)) {
            JSONObject jsonObject = new JSONObject(2);
            jsonObject.put("msg", "不存在众测商品数据");
        }

        long userId = HehuaRequestContext.getUserId();
        boolean hasMore = flashList.size() > (offset + limit);
        JSONObject jo ;
        if (userId > 0) {
            flashList = freeOrderService.setApplyFreeFlashByUserId(flashList, userId);
            jo = voteServiceApiProxy.getVoteFlashItemJson(flashList,offset, limit, hasMore);
            freeOrderService.resetApplyFreeFlashList(flashList);
            return jo;
        }

        jo = voteServiceApiProxy.getVoteFlashItemJson(flashList,offset, limit, hasMore);

        return jo;
    }

    @RequestMapping(value = "/free/help", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteHelp() {
        JSONObject jo = voteServiceApiProxy.getVoteFlashHelp();
        return jo;
    }

    @RequestMapping(value = "/free/detail/{freeid}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getVoteDetail(@PathVariable("freeid") int freeid) {
        FreeFlash freeFlash = voteServiceApiProxy.getVoteFlashById(freeid);
        long userId = HehuaRequestContext.getUserId();
        if (userId > 0) {
            if (freeFlash != null) {
                freeFlash.setApply(freeOrderService.isExsitApplyByUserIdAndFreeId(userId, freeid));
            }

        }
        return voteServiceApiProxy.getVoteFlashJsonById(freeFlash);
    }
}
