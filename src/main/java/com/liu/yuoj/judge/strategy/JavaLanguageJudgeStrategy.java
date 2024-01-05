package com.liu.yuoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.liu.yuoj.model.dto.question.JudgeCase;
import com.liu.yuoj.model.dto.question.JudgeConfig;
import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.model.enums.JudgeInfoEnum;

import java.util.List;

/**
 * @author 刘渠好
 * @date 2024-01-05 23:07
 * java语言判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> input = judgeContext.getInput ();
        List<String> output = judgeContext.getOutput ();
        Question question = judgeContext.getQuestion ();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo ();
        //提交题目的时间，内存
        Long memory = judgeInfo.getMemory ();
        Long time = judgeInfo.getTime ();
        List<JudgeCase> judgeCases = judgeContext.getJudgeCases ();
        JudgeInfoEnum accepted = JudgeInfoEnum.ACCEPTED;

        JudgeInfo judgeInfoResponse = new JudgeInfo ();
        judgeInfoResponse.setMemory (memory);
        judgeInfoResponse.setTime (time);
        //提交题目judgeInfo信息
        if (output.size () != input.size ()) {
            judgeInfoResponse.setMessage (JudgeInfoEnum.WRONG_ANSWER.getText ());
            return judgeInfoResponse;
        }
        //判断每一次输出和预期输出是否一样
        for (int i = 0; i < judgeCases.size (); i++) {
            //获取对饮judgeCase对象
            JudgeCase judgeCase1 = judgeCases.get (i);
            if (!judgeCase1.getOutput ().equals (output.get (i))) {
                judgeInfoResponse.setMessage (JudgeInfoEnum.WRONG_ANSWER.getText ());
                return judgeInfoResponse;
            }
        }
        //判断题目限制
        String judgeConfig = question.getJudgeConfig ();
        JudgeConfig judgeConfig1 = JSONUtil.toBean (judgeConfig, JudgeConfig.class);


        if (memory > judgeConfig1.getMemoryLimit ()) {
            judgeInfoResponse.setMessage (JudgeInfoEnum.MEMORY_LIMIT.getText ());
            return judgeInfoResponse;
        }
        //java程序本身额外需要消耗10s中
        long JAVA_PROGRAM_TIME_COST=10L;
        if ((time-JAVA_PROGRAM_TIME_COST) > judgeConfig1.getTimeLimit ()) {
            judgeInfoResponse.setMessage (JudgeInfoEnum.TIME_LIMIT_EXCEEDED.getText ());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage (accepted.getText ());
        return judgeInfoResponse;
    }
}
