package com.jackqiu.oj.judge.codesandbox;

import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 * @author jackqiu
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest);
}
