package com.liu.yuoj.judge.codeSandBox;


import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 刘渠好
 * @date 2024-01-05 21:04
 * 设计模式--动态代理模式
 * 增强代码沙箱能力(之前前后加上日志) 对其增强(所以也要去实现这个接口)
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {

    //只会改变一次 这里可以使用final修饰符
    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public CodeSandBoxResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info ("代码沙箱请求信息:"+executeCodeRequest.toString ());
        CodeSandBoxResponse codeSandBoxResponse = codeSandBox.executeCode (executeCodeRequest);
        log.info ("代码沙箱响应信息:"+codeSandBoxResponse.toString ());
        return codeSandBoxResponse;
    }
}
