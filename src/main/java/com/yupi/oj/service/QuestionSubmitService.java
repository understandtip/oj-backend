package com.yupi.oj.service;

import com.yupi.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.oj.model.entity.User;

/**
* @author jackqiu
 * 题目提交服务
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-08-16 13:21:44
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 点赞
     *
     * @param questionSubmitAddRequest  前端传递过来的信息
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
}
