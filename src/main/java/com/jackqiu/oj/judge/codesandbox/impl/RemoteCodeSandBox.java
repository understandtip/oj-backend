package com.jackqiu.oj.judge.codesandbox.impl;

import com.jackqiu.oj.judge.codesandbox.CodeSandBox;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远端代码沙箱   （自己实现的代码沙箱逻辑）
 * @author jackqiu
 */
public class RemoteCodeSandBox implements CodeSandBox {

    /**
     * 真正执行代码的地方
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远端代码沙箱");
        return null;
    }
}
