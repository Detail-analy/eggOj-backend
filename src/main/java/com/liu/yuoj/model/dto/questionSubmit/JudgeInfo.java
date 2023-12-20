package com.liu.yuoj.model.dto.questionSubmit;

import lombok.Data;

/**
 * @author 刘渠好
 * @date 2023-12-20 21:21
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间(KB)
     */
    private Long time;
}
