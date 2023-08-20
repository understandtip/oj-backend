package com.jackqiu.oj.judge.codesandbox.impl;

import com.jackqiu.oj.judge.codesandbox.CodeSandBox;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 * @author jackqiu
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    /**
     * 真正执行代码的地方
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
