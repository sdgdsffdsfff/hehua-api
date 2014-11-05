package com.hehua.item.service;

import com.hehua.item.dao.FreeItemQuestionDAO;
import com.hehua.item.domain.FreeItemQuestion;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by hesheng on 14-10-16.
 */
@Component
public class FreeItemQuestionService {
    @Inject
    private FreeItemQuestionDAO freeItemQuestionDAO;

    public boolean saveOrUpdateFreeItemQuestionBy(FreeItemQuestion freeItemQuestion) {
        FreeItemQuestion oldFreeQuestion = freeItemQuestionDAO.getFreeItemQuestionByItemIdAndUserId(
                freeItemQuestion.getItemid(),
                freeItemQuestion.getUid());
        if (oldFreeQuestion == null) {
            return freeItemQuestionDAO.createFreeItemQuestion(freeItemQuestion) == 1;
        } else {
            oldFreeQuestion.setIsdesiredbuy(freeItemQuestion.getIsdesiredbuy());
            oldFreeQuestion.setIsrecommendfriend(freeItemQuestion.getIsrecommendfriend());
            return freeItemQuestionDAO.updateFreeItemQuestionById(oldFreeQuestion) == 1;
        }
    }
}
