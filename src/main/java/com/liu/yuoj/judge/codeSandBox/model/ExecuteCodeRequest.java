package com.liu.yuoj.judge.codeSandBox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * @author 刘渠好
 * @date 2024-01-05 20:05
 * 执行代码沙箱请求类
 */
@Data
@Builder //之前的都是set/get 方式 这次是一种链式(get/set) 方式
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入用例
     */
    private List<String> inputList;

    /**
     *编程语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 时间限制(这里可以加也可以不加 因为代码沙箱和判题机是解耦的 代码沙箱只负责代码的执行和编译最后返回结果
     * 但是也可以加上 在代码沙箱中判断时间是否超时 可以及时终止代码沙箱的运行);
     */
//    Long timeLimit;
}
