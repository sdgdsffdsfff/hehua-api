/**
 * 
 */
package com.hehua.api.controller.profile;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hehua.user.domain.Baby;

/**
 * @author zhihua
 *
 */
@Component
public class BabyRender {

    public JSONObject renderBaby(Baby baby) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", Math.max(0, baby.getStatus()));
        jsonObject.put("isset", baby.isIsset());
        if (baby.getEdc() != null) {
            jsonObject.put("edc", DateFormatUtils.format(baby.getEdc(), "yyyy-MM-dd"));
        }
        jsonObject.put("gender", baby.getGender());
        if (baby.getBirthday() != null) {
            jsonObject.put("birthday", DateFormatUtils.format(baby.getBirthday(), "yyyy-MM-dd"));
        }
        return jsonObject;
    }

}
