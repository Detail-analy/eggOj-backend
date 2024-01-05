package com.liu.yuoj.judge.strategy;

import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;

/**
 * @author 刘渠好
 * @date 2024-01-05 23:05
 * 设计模式--策略模式
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
