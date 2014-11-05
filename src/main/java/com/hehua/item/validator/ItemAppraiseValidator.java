package com.hehua.item.validator;

import com.hehua.item.domain.ItemAppraise;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Date 14/10/20.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class ItemAppraiseValidator implements org.springframework.validation.Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ItemAppraise.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "errorCode", "评测人名称不可以为空!");
    }







}
