package com.jackqiu.oj.judge;

import com.jackqiu.oj.judge.strategy.DefaultJudgeStrategy;
import com.jackqiu.oj.judge.strategy.JavaJudgeStrategy;
import com.jackqiu.oj.judge.strategy.JudgeContext;
import com.jackqiu.oj.judge.strategy.JudgeStrategy;
import com.jackqiu.oj.judge.codesandbox.model.JudgeInfo;
import com.jackqiu.oj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @author jackqiu
 */
@Service
public class JudgeManager {
    /**
     * 根据不同的语言执行不同的逻辑
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
