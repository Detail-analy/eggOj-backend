package com.liu.yuoj.judge;

import com.liu.yuoj.judge.strategy.DefaultJudgeStrategy;
import com.liu.yuoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.liu.yuoj.judge.strategy.JudgeContext;
import com.liu.yuoj.judge.strategy.JudgeStrategy;
import com.liu.yuoj.model.dto.question.JudgeCase;
import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.model.entity.QuestionSubmit;
import com.liu.yuoj.model.enums.QuestionSubmitLanguageEnum;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘渠好
 * @date 2024-01-05 23:43
 * 判题管理 简化调用
 */
@Service
public class JudgeManager {

     JudgeInfo doJudge(JudgeContext judgeContext){
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy ();
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit ();
        if (questionSubmit.getLanguage ().equals (QuestionSubmitLanguageEnum.JAVA.getValue ())){
            judgeStrategy=new JavaLanguageJudgeStrategy ();
        }
        return judgeStrategy.doJudge (judgeContext);

    }

}
