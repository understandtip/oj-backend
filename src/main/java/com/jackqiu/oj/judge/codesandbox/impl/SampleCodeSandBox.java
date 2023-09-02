package com.jackqiu.oj.judge.codesandbox.impl;

import com.jackqiu.oj.judge.codesandbox.CodeSandBox;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.jackqiu.oj.judge.codesandbox.model.JudgeInfo;
import com.jackqiu.oj.model.enums.JudgeInfoMessageEnum;
import com.jackqiu.oj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 实例代码沙箱
 * @author jackqiu
 */
public class SampleCodeSandBox implements CodeSandBox {
    /**
     * 真正执行代码的地方
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutput(inputList);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCESS.getText());
        executeCodeResponse.setMessage("执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
