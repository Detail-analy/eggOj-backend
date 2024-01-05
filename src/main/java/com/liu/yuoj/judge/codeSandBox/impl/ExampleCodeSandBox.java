package com.liu.yuoj.judge.codeSandBox.impl;

import com.liu.yuoj.judge.codeSandBox.CodeSandBox;
import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.liu.yuoj.model.enums.JudgeInfoEnum;

import java.util.List;

/**
 * @author 刘渠好
 * @date 2024-01-05 20:17
 * 实例代码沙箱(仅仅是测试使用)
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public CodeSandBoxResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList ();
        /**
         * 测试(插入测试数据)
         */
        CodeSandBoxResponse codeSandBoxResponse = new CodeSandBoxResponse ();
        codeSandBoxResponse.setMessage ("代码沙箱执行成功!!!");
        codeSandBoxResponse.setOutput (inputList);
        codeSandBoxResponse.setStatus (0);
        JudgeInfo judgeInfo = new JudgeInfo ();
        judgeInfo.setMessage (JudgeInfoEnum.ACCEPTED.getText ());
        judgeInfo.setMemory (100L);
        judgeInfo.setTime (100L);
        codeSandBoxResponse.setJudgeInfo (judgeInfo);
        return codeSandBoxResponse;

    }
}
