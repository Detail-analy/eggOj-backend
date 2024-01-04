package com.liu.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.yuoj.common.BaseResponse;
import com.liu.yuoj.common.ErrorCode;
import com.liu.yuoj.common.ResultUtils;
import com.liu.yuoj.exception.BusinessException;
import com.liu.yuoj.exception.ThrowUtils;
import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.liu.yuoj.model.entity.QuestionSubmit;
import com.liu.yuoj.model.entity.User;
import com.liu.yuoj.model.vo.QuestionSubmitVO;
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
     添加
     */
    @PostMapping("/add")
    public BaseResponse<Long> doSubmitQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId () <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long result = questionsubmitService.doQuestionSubmit (questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取题目提交信息
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                   HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //获取当前用户
        User loginUser = userService.getLoginUser (request);
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionSubmitPage = questionsubmitService.page(new Page<>(current, size),
                questionsubmitService.getQueryWrapper(questionSubmitQueryRequest));
        return ResultUtils.success(questionsubmitService.getQuestionSubmitVOPage (questionSubmitPage,loginUser));
    }

}
