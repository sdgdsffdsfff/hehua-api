/**
 * 
 */
package com.hehua.api.controller.address;

import com.alibaba.fastjson.JSON;
import com.hehua.user.domain.Address;

/**
 * @author zhihua
 *
 */
public class CreateAddressForm {

    private String name;

    private String phone;

    private int province;

    private int city;

    private int county;

    private String detail;

    private String postCode;

    private boolean isDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCounty() {
        return county;
    }

    public void setCounty(int county) {
        this.county = county;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Address toAddress() {
        Address address = new Address();
        address.setProvince(province);
        address.setCity(city);
        address.setPostcode(postCode);
        address.setCounty(county);
        address.setDetail(detail);
        address.setMobile(phone);
        address.setReceiver(name);
        address.setIsdefault(isDefault);
        return address;

    }

    public static void main(String[] args) {
        String json = "{\"postCode\":\"100109\",\"phone\":\"18611041135\",\"detail\":\"Test\",\"name\":\"Xuehui\",\"province\":1,\"county\":1,\"city\":1,\"isDefault\":true,\"id\":7}";

        System.out.println(JSON.parseObject(json, CreateAddressForm.class));
    }

    @Override
    public String toString() {
        return "CreateAddressForm [name=" + name + ", phone=" + phone + ", province=" + province
                + ", city=" + city + ", county=" + county + ", detail=" + detail + ", postCode="
                + postCode + ", isDefault=" + isDefault + "]";
    }

}
