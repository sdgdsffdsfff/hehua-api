/**
 * 
 */
package com.hehua.api.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hehua.api.controller.address.AddressRender;
import com.hehua.user.domain.Address;
import com.hehua.user.domain.Baby;
import com.hehua.user.domain.User;
import com.hehua.user.service.AvatarUtils;

/**
 * @author zhihua
 *
 */
@Component
public class ProfileRender {

    @Autowired
    private AddressRender addressRender;

    @Autowired
    private BabyRender babyRender;

    @Autowired
    private AvatarUtils avatarUtils;

    public JSONObject renderProfile(User user, Baby baby, Address address, int unpays,
            int unreceives) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", user.getId());
        jsonObject.put("name", user.getName());

        String avatarUrl = avatarUtils.getAvatarUrl(user.getAvatarid());

        jsonObject.put("avatar", avatarUrl);
        jsonObject.put("baby", babyRender.renderBaby(baby));

        if (address != null) {
            jsonObject.put("address", addressRender.renderAddress(address));
        }
        jsonObject.put("unpays", unpays);
        jsonObject.put("unreceives", unreceives);
        return jsonObject;
    }

}
