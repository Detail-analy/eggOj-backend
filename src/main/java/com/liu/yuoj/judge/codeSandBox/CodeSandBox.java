package com.liu.yuoj.judge.codeSandBox;

import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;

/**
 * 代码沙箱执行接口
 */
public interface CodeSandBox {
    CodeSandBoxResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
