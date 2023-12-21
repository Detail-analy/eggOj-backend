package com.liu.yuoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.yuoj.common.ErrorCode;
import com.liu.yuoj.exception.BusinessException;
import com.liu.yuoj.mapper.QuestionSubmitMapper;
import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.model.entity.QuestionSubmit;
import com.liu.yuoj.model.entity.User;
import com.liu.yuoj.model.enums.QuestionSubmitEnum;
import com.liu.yuoj.model.enums.QuestionSubmitLanguageEnum;
import com.liu.yuoj.service.QuestionService;
import com.liu.yuoj.service.QuestionSubmitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.liu.yuoj.model.enums.QuestionSubmitEnum.AWAITING;

/**
* @author Administrator
* @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2023-12-19 22:56:38
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionService questionService;


    /**
     * 提交题目
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //判断提交题目使用的编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage ();
        QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue (language);
        if (enumByValue==null){
            throw new BusinessException (ErrorCode.PARAMS_ERROR,"编程语言使用错误");
        }
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId ());
        if (question == null) {
            throw new BusinessException (ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
//        // 锁必须要包裹住事务方法
//        QuestionSubmitService questionThumbService = (QuestionSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return questionThumbService.doQuestionSubmitInner(userId, questionId);
        QuestionSubmit questionSubmit = new QuestionSubmit ();
        BeanUtils.copyProperties (questionSubmitAddRequest,questionSubmit);
        questionSubmit.setUserId (userId);
        //枚举值设置状态
        questionSubmit.setStatus (AWAITING.getValue ());
        questionSubmit.setJudgeInfo ("{}");
        boolean save = this.save (questionSubmit);
        if (!save){
            throw new BusinessException (ErrorCode.SYSTEM_ERROR,"数据插入失败!");
    }
        return questionSubmit.getId ();
    }


}




