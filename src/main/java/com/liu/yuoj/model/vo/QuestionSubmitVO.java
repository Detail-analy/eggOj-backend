package com.liu.yuoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.liu.yuoj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 刘渠好
 * @date 2023-12-21 23:23
 */
@Data
public class QuestionSubmitVO implements Serializable {


    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 提交用户信息 （可要可不要）
     */
//    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;


    private static final long serialVersionUID = -4420780971585856129L;

    /**
     * 包装类转对象
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit ();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfo1 = questionSubmitVO.getJudgeInfo ();
        if (judgeInfo1!=null){
            questionSubmit.setLanguage (JSONUtil.toJsonStr (judgeInfo1));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类n
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit ) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        String judgeInfo1 = questionSubmit.getJudgeInfo ();
        if (judgeInfo1!=null){
            questionSubmitVO.setJudgeInfo (JSONUtil.toBean (judgeInfo1, JudgeInfo.class));
        }

        return questionSubmitVO;
    }

}
