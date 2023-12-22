package com.liu.yuoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.liu.yuoj.model.dto.question.JudgeConfig;
import com.liu.yuoj.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 刘渠好
 * @date 2023-12-20 21:26
 * 题目返回类
 */
@Data
public class QuestionVO implements Serializable {

    private static final long serialVersionUID = -4941108445909030112L;
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;


    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptNum;


    /**
     * 判题配置(json对象)
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

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
     * 创建题目人的信息 (可写可不写)
     */
    private UserVO userVO;

    /**
     * 包装类转对象
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags ();
        if (tagList != null) {
            //包装类中tags属性是List类型，而question类中是字符串类型，使用json工具进行转换
            question.setTags(JSONUtil.toJsonStr (tagList));
        }
        JudgeConfig judgeConfig1 = questionVO.getJudgeConfig ();
        if (judgeConfig1!=null){
            question.setJudgeCase (JSONUtil.toJsonStr (judgeConfig1));
        }
        return question;
    }

    /**
     * 对象转包装类n
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        String tags1 = question.getTags ();
        //question类中是字符串类型，而包装类中tags属性是List类型，使用GSON工具进行转换
        List<String> list=new ArrayList<> ();
        if (tags1!=null){
             list = JSONUtil.toList (tags1, String.class);
        }
        questionVO.setTags (list);

        /**
         * memoryLimit:1000,stackLimit:1000,timeLimit:1000
         */
        String judgeConfig = question.getJudgeConfig ();
        JudgeConfig judgeConfig1=null;
        if (judgeConfig!=null){
            //将json对象变为实体类对象
             judgeConfig1 = JSONUtil.toBean (judgeConfig, JudgeConfig.class);
        }

        questionVO.setJudgeConfig (judgeConfig1);

        return questionVO;
    }

}
