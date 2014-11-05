/**
 * 
 */
package com.hehua.api.controller.register;

import com.hehua.user.model.BabyView;

/**
 * @author zhihua
 *
 */
public class RegisterForm {

    private String freshtoken;

    private String password;

    private BabyView baby;

    public String getFreshtoken() {
        return freshtoken;
    }

    public void setFreshtoken(String freshtoken) {
        this.freshtoken = freshtoken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BabyView getBaby() {
        return baby;
    }

    public void setBaby(BabyView baby) {
        this.baby = baby;
    }

}
