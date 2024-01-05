package com.liu.yuoj.judge.strategy;

import com.liu.yuoj.model.dto.question.JudgeCase;
import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author 刘渠好
 * @date 2024-01-05 23:06
 *上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private List<String> input;

    private List<String> output;

    private Question question;

    private JudgeInfo judgeInfo;

    private List<JudgeCase> judgeCases;

    private QuestionSubmit questionSubmit;


}
