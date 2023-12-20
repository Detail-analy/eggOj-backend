package com.liu.yuoj.model.dto.questionSubmit;

import lombok.Data;

/**
 * @author 刘渠好
 * @date 2023-12-20 22:27
 * 题目提交请求类
 */
@Data
public class QuestionSubmitAddRequest {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目id
     */
    private Long questionId;
}
