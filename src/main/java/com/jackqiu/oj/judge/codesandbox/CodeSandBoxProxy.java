package com.jackqiu.oj.judge.codesandbox;

import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理类，实现将一些增强功能添加到执行代码的方法前后
 * @author jackqiu
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{
    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.execute(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
