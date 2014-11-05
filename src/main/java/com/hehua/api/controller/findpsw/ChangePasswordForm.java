/**
 * 
 */
package com.hehua.api.controller.findpsw;

/**
 * @author zhihua
 *
 */
public class ChangePasswordForm {

    private String mobile;

    private String changePwdToken;

    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChangePwdToken() {
        return changePwdToken;
    }

    public void setChangePwdToken(String changePwdToken) {
        this.changePwdToken = changePwdToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
