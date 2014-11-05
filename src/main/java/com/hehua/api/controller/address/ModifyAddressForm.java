/**
 * 
 */
package com.hehua.api.controller.address;

import com.hehua.user.domain.Address;

/**
 * @author zhihua
 *
 */
public class ModifyAddressForm extends CreateAddressForm {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Address toAddress() {
        Address address = super.toAddress();
        address.setId(id);
        return address;
    }

}
