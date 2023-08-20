package com.jackqiu.oj.judge;

import com.jackqiu.oj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题服务
 * @author jackqiu
 */
public interface JudgeService {

    /**
     * 执行判题逻辑
     * @param questionSubmitId
     */
    QuestionSubmit doJudge(Long questionSubmitId);
}
