package com.liu.yuoj.judge.codeSandBox;

import com.liu.yuoj.judge.codeSandBox.impl.ExampleCodeSandBox;
import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.liu.yuoj.model.enums.QuestionSubmitLanguageEnum;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class ExampleCodeSandBoxTest {
    /**
     * 从配置文件中取出对应属性
     */
    @Value ("${codesandbox.type}")
    private String type;

    @Test
    public void Demo1(){
        CodeSandBox codeSandBox=new ExampleCodeSandBox ();
        String code="int main(){}";
        List<String> inputList= Arrays.asList ("{1 2,3 4}");
        String language= QuestionSubmitLanguageEnum.JAVA.getValue ();
        ExecuteCodeRequest build = ExecuteCodeRequest.builder ()
                .code (code)
                .inputList (inputList)
                .language (language).
                build ();
        CodeSandBoxResponse codeSandBoxResponse = codeSandBox.executeCode (build);
        Assertions.assertNotNull (codeSandBoxResponse);

    }

    @Test
    public void Demo2(){
        String code="int main(){}";
        List<String> inputList= Arrays.asList ("{1 2,3 4}");
        String language= QuestionSubmitLanguageEnum.JAVA.getValue ();
        //根据类型来获取对应代码沙箱实例
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance ("remote");
        ExecuteCodeRequest build = ExecuteCodeRequest.builder ()
                .code (code)
                .inputList (inputList)
                .language (language).
                build ();
        CodeSandBoxResponse codeSandBoxResponse = codeSandBox.executeCode (build);
        Assertions.assertNotNull (codeSandBoxResponse);
    }

    @Test
    public void Demo3(){
        String code="int main(){}";
        List<String> inputList= Arrays.asList ("{1 2,3 4}");
        String language= QuestionSubmitLanguageEnum.JAVA.getValue ();
        //根据类型来获取对应代码沙箱实例
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance (type);
        ExecuteCodeRequest build = ExecuteCodeRequest.builder ()
                .code (code)
                .inputList (inputList)
                .language (language).
                build ();
        CodeSandBoxResponse codeSandBoxResponse = codeSandBox.executeCode (build);
        Assertions.assertNotNull (codeSandBoxResponse);
    }

    @Test
    public void  Demo4(){
        String code="int main(){}";
        List<String> inputList= Arrays.asList ("{1 2,3 4}");
        String language= QuestionSubmitLanguageEnum.JAVA.getValue ();
        //根据类型来获取对应代码沙箱实例
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance (type);
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy (codeSandBox);
        ExecuteCodeRequest build = ExecuteCodeRequest.builder ()
                .code (code)
                .inputList (inputList)
                .language (language).
                build ();
        CodeSandBoxResponse codeSandBoxResponse = codeSandBoxProxy.executeCode (build);
        Assertions.assertNotNull (codeSandBoxResponse);
    }
}