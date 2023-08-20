package com.jackqiu.oj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.jackqiu.oj.model.dto.question.JudgeConfig;
import com.jackqiu.oj.model.dto.questionsubmit.JudgeInfo;
import com.jackqiu.oj.model.entity.Question;
import com.jackqiu.oj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * Java语言的判题策略
 * @author jackqiu
 */
public class JavaJudgeStrategy implements JudgeStrategy{

    private final long JAVA_PROGRAM_TIME_COST = 10000L;//10秒
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> needOutputList = judgeContext.getNeedOutputList();
        List<String> realOutputList = judgeContext.getRealOutputList();
        Question question = judgeContext.getQuestion();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long time = judgeInfo.getTime();
        Long memory = judgeInfo.getMemory();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        //5.1   先判断代码沙箱的实际输出结果的个数和题目的标准输入用例是否相同
        if(realOutputList.size() != inputList.size()){
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }
        //5.2   再判断代码沙箱的实际输出结果和题目的标准输出用例的每一项是否相同
        for (int i = 0; i < needOutputList.size(); i++) {
            if(!needOutputList.get(i).equals(realOutputList.get(i))){
                judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return judgeInfoResponse;
            }
        }
        //5.3   根据判题配置JudgeConfig判断是否成功（比对内存占用、执行时间是否符合）
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long needStackLimit = judgeConfig.getStackLimit();//todo 可拓展的内容-->限制堆栈内存
        if(time > needTimeLimit){
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        if(memory > needMemoryLimit){
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        return judgeInfoResponse;
    }
}
