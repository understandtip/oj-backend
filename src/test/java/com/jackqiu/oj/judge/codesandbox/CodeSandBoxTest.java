package com.jackqiu.oj.judge.codesandbox;

import com.jackqiu.oj.judge.codesandbox.impl.ThirdPartyCodeSandBox;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.jackqiu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.jackqiu.oj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandBoxTest {

    @Value("${codesandbox.type:sample}")
    private String type;

    @Test
    void execute() {
        CodeSandBox codeSandBox = new ThirdPartyCodeSandBox();
        String code = "int main(){  }";
        List<String> inputList = Arrays.asList("1 2","3 4");
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse execute = codeSandBox.execute(executeCodeRequest);
        Assertions.assertNotNull(execute);
    }

    @Test
    void executeByFactory() {
        String type = "thirdParty";
        CodeSandBox codeSandBox= CodeSandBoxFactory.newInstance(type);
        String code = "int main(){  }";
        List<String> inputList = Arrays.asList("1 2","3 4");
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse execute = codeSandBox.execute(executeCodeRequest);
        Assertions.assertNull(execute);
    }

    @Test
    void executeByConfig() {
        CodeSandBox codeSandBox= CodeSandBoxFactory.newInstance(type);
        String code = "int main(){  }";
        List<String> inputList = Arrays.asList("1 2","3 4");
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse execute = codeSandBox.execute(executeCodeRequest);
        Assertions.assertNull(execute);
    }

    @Test
    void executeByProxy() {
        CodeSandBox codeSandBox= CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果:\" + (a + b));\n" +
                "    }\n" +
                "}";
        List<String> inputList = Arrays.asList("1 2","3 4");
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse execute = codeSandBox.execute(executeCodeRequest);
    }
}