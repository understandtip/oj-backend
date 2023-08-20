package com.jackqiu.oj.judge.strategy;

import com.jackqiu.oj.model.dto.questionsubmit.JudgeInfo;

/**
 * 判题策略
 * @author jackqiu
 */
public interface JudgeStrategy {
    /**
     *
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
