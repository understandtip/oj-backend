package com.jackqiu.oj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jackqiu.oj.common.ErrorCode;
import com.jackqiu.oj.exception.BusinessException;
import com.jackqiu.oj.judge.codesandbox.CodeSandBox;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 远端代码沙箱   （自己实现的代码沙箱逻辑）
 * @author jackqiu
 */
@Service
public class RemoteCodeSandBox implements CodeSandBox {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Value("${codesandbox.url}")
    private String url;

    /**
     * 真正执行代码的地方
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远端代码沙箱");
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String url = "http://localhost:8090/executeCode";
//        String url = "https://oj-code-sandbox-72541-5-1321173904.sh.run.tcloudbase.com//executeCode";
//        String url = "http://120.26.124.229:8090/executeCode";
        String response = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(response)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"远程接口调用失败 : " + response);
        }
        return JSONUtil.toBean(response, ExecuteCodeResponse.class);
    }
}
