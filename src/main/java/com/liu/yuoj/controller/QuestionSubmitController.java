package com.liu.yuoj.controller;

import com.liu.yuoj.common.BaseResponse;
import com.liu.yuoj.common.ErrorCode;
import com.liu.yuoj.common.ResultUtils;
import com.liu.yuoj.exception.BusinessException;
import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.liu.yuoj.model.entity.User;
import com.liu.yuoj.service.QuestionSubmitService;
import com.liu.yuoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *

 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionsubmitService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doSubmitQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId () <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long result = questionsubmitService.doQuestionSubmit (questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

}
