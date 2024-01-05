package com.liu.yuoj.judge.codeSandBox.impl;

import com.liu.yuoj.judge.codeSandBox.CodeSandBox;
import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;

/**
 * @author 刘渠好
 * @date 2024-01-05 20:17
 * 第三方代码沙箱(可以更好的去使用别人的代码沙箱)
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public CodeSandBoxResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println ("第三方代码沙箱");
        return null;
    }
}
