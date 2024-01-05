package com.liu.yuoj.judge.codeSandBox.impl;

import com.liu.yuoj.judge.codeSandBox.CodeSandBox;
import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;

/**
 * @author 刘渠好
 * @date 2024-01-05 20:17
 * 远程代码沙箱
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public CodeSandBoxResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println ("远程代码沙箱");
        return null;
    }
}
