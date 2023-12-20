package com.liu.yuoj.model.dto.question;

import lombok.Data;

/**
 * @author 刘渠好
 * @date 2023-12-20 21:13
 * 🧘判题用例对象(这里有以下思考点：为了防止多个表中都有类似的类，产生冲突，不加前缀，因为这个类可能被共用，这里我们不加前缀，共用)
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;
    /**
     * 输出用例
     */
    private String output;
}
