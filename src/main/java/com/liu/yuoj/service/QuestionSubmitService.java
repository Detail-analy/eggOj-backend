package com.liu.yuoj.service;

import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.liu.yuoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.yuoj.model.entity.User;

/**
* @author Administrator
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2023-12-19 22:56:38
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交题目

     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


}
