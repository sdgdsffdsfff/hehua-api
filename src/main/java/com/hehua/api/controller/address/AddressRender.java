/**
 * 
 */
package com.hehua.api.controller.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hehua.user.domain.Address;
import com.hehua.user.domain.City;
import com.hehua.user.domain.County;
import com.hehua.user.domain.Province;
import com.hehua.user.service.LocationManager;

/**
 * @author zhihua
 *
 */
@Component
public class AddressRender {

    @Autowired
    private LocationManager locationManager;

    public JSONArray renderAddressList(List<Address> addresses) {

        JSONArray jsonArray = new JSONArray(addresses.size());
        for (Address address : addresses) {
            jsonArray.add(renderAddress(address));
        }
        return jsonArray;
    }

    public JSONObject renderAddress(Address address) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", address.getId());
        jsonObject.put("name", address.getReceiver());
        jsonObject.put("phone", address.getMobile());
        Province province = locationManager.getProvince(address.getProvince());
        City city = locationManager.getCity(address.getCity());
        County county = locationManager.getCounty(address.getCounty());
        jsonObject.put("province", province.getName());
        jsonObject.put("provinceId", province.getID());
        jsonObject.put("city", city.getName());
        jsonObject.put("cityId", city.getID());
        jsonObject.put("county", county.getName());
        jsonObject.put("countyId", county.getID());
        jsonObject.put("detail", address.getDetail());
        jsonObject.put("postCode", address.getPostcode());
        jsonObject.put("isDefault", address.getIsdefault());
        return jsonObject;
    }

    public JSONArray renderProvinceList(List<Province> provinces) {
        JSONArray jsonArray = new JSONArray(provinces.size());
        for (Province province : provinces) {
            jsonArray.add(renderProvince(province));
        }
        return jsonArray;
    }

    public JSONObject renderProvince(Province province) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("i", province.getID());
        jsonObject.put("n", province.getName());
        jsonObject.put("c", renderCityList(province.getCities()));
        return jsonObject;
    }

    public JSONArray renderCityList(List<City> cities) {
        JSONArray jsonArray = new JSONArray(cities.size());
        for (City city : cities) {
            jsonArray.add(renderCity(city));
        }
        return jsonArray;
    }

    public JSONObject renderCity(City city) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("i", city.getID());
        jsonObject.put("n", city.getName());
        jsonObject.put("c", renderCountyList(city.getCounties()));
        return jsonObject;
    }

    public JSONArray renderCountyList(List<County> counties) {
        JSONArray jsonArray = new JSONArray(counties.size());
        for (County county : counties) {
            jsonArray.add(renderCounty(county));
        }
        return jsonArray;
    }

    public JSONObject renderCounty(County county) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("i", county.getID());
        jsonObject.put("n", county.getName());
        return jsonObject;
    }
}
