package com.jackqiu.oj.judge.codesandbox;

import com.jackqiu.oj.judge.codesandbox.impl.RemoteCodeSandBox;
import com.jackqiu.oj.judge.codesandbox.impl.SampleCodeSandBox;
import com.jackqiu.oj.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * 代码沙箱工厂类 （根据不同的字符串参数创建对应的代码实现类的实例对象）
 * @author jackqiu
 */
public class CodeSandBoxFactory {

    //根据不同的字符串参数创建对应的代码实现类的实例对象
    public static CodeSandBox newInstance(String type){
        switch (type){
            case "sample":
                return new SampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new SampleCodeSandBox();
        }
    }
}
