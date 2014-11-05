/**
 * 
 */
package com.hehua.api.controller.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hehua.commons.exception.BusinessException;
import com.hehua.commons.model.ResultView;
import com.hehua.framework.web.HehuaRequestContext;
import com.hehua.framework.web.annotation.LoginRequired;
import com.hehua.framework.web.render.ResponseRender;
import com.hehua.user.domain.Address;
import com.hehua.user.service.AddressService;
import com.hehua.user.service.LocationManager;

/**
 * @author zhihua
 *
 */
@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private AddressRender addressRender;

    @LoginRequired
    @RequestMapping(value = "/address/list")
    @ResponseBody
    public JSONObject getAddressList() {
        long currentUserId = HehuaRequestContext.getUserId();
        List<Address> addresses = addressService.getAddressListByUid(currentUserId);
        return ResponseRender.renderResponse(addressRender.renderAddressList(addresses));
    }

    @LoginRequired
    @RequestMapping(value = "/address/create")
    @ResponseBody
    public JSONObject createAddress(@RequestBody CreateAddressForm createAddressForm)
            throws BusinessException {
        long currentUserId = HehuaRequestContext.getUserId();
        ResultView<Address> addAddressResultView = addressService.addAddress(currentUserId,
                createAddressForm.toAddress());
        return ResponseRender.renderResponse(addressRender.renderAddress(addAddressResultView
                .getData()));

    }

    @LoginRequired
    @RequestMapping(value = "/address/delete")
    @ResponseBody
    public JSONObject deleteAddress(@RequestBody DeleteAddressForm deleteAddressForm) {
        long currentUserId = HehuaRequestContext.getUserId();
        ResultView<Address> deleteAddressResultView = addressService.deleteAddress(currentUserId,
                deleteAddressForm.getId());
        return ResponseRender.renderResponse(addressRender.renderAddress(deleteAddressResultView
                .getData()));
    }

    @LoginRequired
    @RequestMapping(value = "/address/modify")
    @ResponseBody
    public JSONObject modifyAddress(@RequestBody ModifyAddressForm modifyAddressForm)
            throws BusinessException {
        long currentUserId = HehuaRequestContext.getUserId();
        ResultView<Address> deleteAddressResultView = addressService.modifyAddress(currentUserId,
                modifyAddressForm.toAddress());
        return ResponseRender.renderResponse(addressRender.renderAddress(deleteAddressResultView
                .getData()));
    }

    @LoginRequired
    @RequestMapping(value = "/address/default")
    @ResponseBody
    public JSONObject setDefaultAddress(@RequestBody SetDefaultAddressForm setDefaultAddressForm)
            throws BusinessException {
        // TODO
        long currentUserId = HehuaRequestContext.getUserId();
        ResultView<Address> setDefaultAddressResultView = addressService.setDefaultAddress(
                currentUserId, setDefaultAddressForm.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", setDefaultAddressResultView.getData().getId());
        return ResponseRender.renderResponse(jsonObject);

    }

    @RequestMapping(value = "/data/locations")
    @ResponseBody
    public JSONObject getLocationsData() {
        return ResponseRender.renderResponse(addressRender.renderProvinceList(locationManager
                .getAllProvince()));
    }

}
