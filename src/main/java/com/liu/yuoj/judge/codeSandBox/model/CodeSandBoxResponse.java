package com.liu.yuoj.judge.codeSandBox.model;

import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * @author 刘渠好
 * @date 2024-01-05 20:11
 * 代码沙箱相应类
 */
@Data
public class CodeSandBoxResponse {
    /**
     * 接口信息(不同于执行信息 这个信息主要范围更广 比如：代码沙箱执行死机了 可以输出这个信息 )
     */
    private String message;

    /**
     * 输出用例
     */
    private List<String> output;
    /**
     *执行状态
     */
    private Integer status;

    /**
     * 执行信息
     */
    private JudgeInfo judgeInfo;



}
