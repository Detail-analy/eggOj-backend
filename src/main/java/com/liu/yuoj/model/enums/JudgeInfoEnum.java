package com.liu.yuoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目提交信息枚举
 *

 */
public enum JudgeInfoEnum {
    /**
     * ACCEPTED WRONG_ANSWER COMPILE_ERROR MEMORY_LIMIT TIME_LIMIT_EXCEEDED PRESENTATION_EXCEEDED DANGEROUS_OPERATION
     * RUNTIME_ERROR SYSTEM_ERROR
     */
    ACCEPTED("成功","accepted"),
    WAITING("等待","waiting"),
    WRONG_ANSWER("答案错误","wrongAnswer"),
    COMPILE_ERROR("编译错误","compileError"),
    MEMORY_LIMIT("内存溢出","memoryLimit"),
    TIME_LIMIT_EXCEEDED("超时","timeLimitExceeded"),
    PRESENTATION_EXCEEDED("展示错误","presentationExceeded"),
    DANGEROUS_OPERATION("危险操作","wrongOperation"),
    RUNTIME_ERROR("运行错误","runtimeError"),
    SYSTEM_ERROR("系统错误","SystemError");

    private final String text;

    private final String value;

    JudgeInfoEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoEnum anEnum : JudgeInfoEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
