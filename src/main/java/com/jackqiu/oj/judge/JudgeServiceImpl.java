package com.jackqiu.oj.judge;

import cn.hutool.json.JSONUtil;
import com.jackqiu.oj.common.ErrorCode;
import com.jackqiu.oj.exception.BusinessException;
import com.jackqiu.oj.judge.codesandbox.CodeSandBox;
import com.jackqiu.oj.judge.codesandbox.CodeSandBoxFactory;
import com.jackqiu.oj.judge.codesandbox.CodeSandBoxProxy;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.jackqiu.oj.judge.strategy.DefaultJudgeStrategy;
import com.jackqiu.oj.judge.strategy.JudgeContext;
import com.jackqiu.oj.judge.strategy.JudgeStrategy;
import com.jackqiu.oj.model.dto.question.JudgeCase;
import com.jackqiu.oj.model.dto.question.JudgeConfig;
import com.jackqiu.oj.model.dto.questionsubmit.JudgeInfo;
import com.jackqiu.oj.model.entity.Question;
import com.jackqiu.oj.model.entity.QuestionSubmit;
import com.jackqiu.oj.model.enums.JudgeInfoMessageEnum;
import com.jackqiu.oj.model.enums.QuestionSubmitLanguageEnum;
import com.jackqiu.oj.model.enums.QuestionSubmitStatusEnum;
import com.jackqiu.oj.service.QuestionService;
import com.jackqiu.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务实现类
 * @author jackqiu
 */
@Service
public class JudgeServiceImpl implements JudgeService{

    @Value("${codesandbox.type:sample}")
    private String type;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;
    /**
     * 执行判题逻辑
     * @param questionSubmitId
     * @return
     */
    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        //1.获取QuestionSubmit（题目提交的状态，用户提交的代码、提交的语言）和Question（题目的输入输出用例、判题配置等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"用户提交题目的信息未找到");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目信息未找到");
        }
        //2.如果题目提交状态不为等待中，就不用重复执行了
        if(!QuestionSubmitStatusEnum.WAITING.getValue().equals(questionSubmit.getStatus())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"正在判题中，无法重复判题");
        }
        //3.如果未处于判题中，则将状态修改成判题中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更改用户的题目提交信息失败");
        }
        //4.判题服务传递 用户提交的代码 、题目信息里的输入用例JudgeCase.input、代码语言给代码沙箱
        CodeSandBox codeSandBox= CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = questionSubmit.getCode();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(),JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        String language = questionSubmit.getLanguage();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.execute(executeCodeRequest);
        List<String> needOutputList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        //5.根据代码沙箱的返回结果    (执行结果JudgeInfo，执行信息，执行状态，输出用例JudgeCase.output)
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInputList(inputList);
        judgeContext.setNeedOutputList(needOutputList);
        judgeContext.setRealOutputList(executeCodeResponse.getOutput());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfoResponse = judgeManager.doJudge(judgeContext);
        //5.4   设置好对应的返回信息（可能还有其他异常信息）
        //6.更新数据库的信息
        QuestionSubmit questionSubmitEnd = new QuestionSubmit();
        questionSubmitEnd.setId(questionSubmitId);
        questionSubmitEnd.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitEnd.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoResponse));
        boolean flag = questionSubmitService.updateById(questionSubmitEnd);
        if(!flag){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更改用户的题目提交信息失败");
        }
        QuestionSubmit questionSubmitResponse = questionSubmitService.getById(questionSubmitId);
        return questionSubmitResponse;
    }
}
