package com.liu.yuoj.judge;

import com.liu.yuoj.model.entity.QuestionSubmit;


/**
 * @author 刘渠好
 * @date 2024-01-05 21:42
 * 代码沙箱执行提交题目接口
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
