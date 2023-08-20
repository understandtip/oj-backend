package com.jackqiu.oj.judge.strategy;

import com.jackqiu.oj.model.dto.questionsubmit.JudgeInfo;
import com.jackqiu.oj.model.entity.Question;
import com.jackqiu.oj.model.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 传递给代码沙箱的上下文参数
 * @author jackqiu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeContext {

    /**
     * 标准输入用例
     */
    private List<String> inputList;

    /**
     * 标准输出用例
     */
    private List<String> needOutputList;

    /**
     * 实际输出用例
     */
    private List<String> realOutputList;

    private Question question;

    private JudgeInfo judgeInfo;

    private QuestionSubmit questionSubmit;
}
