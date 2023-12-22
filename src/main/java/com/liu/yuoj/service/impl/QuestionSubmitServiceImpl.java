package com.liu.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.yuoj.common.ErrorCode;
import com.liu.yuoj.constant.CommonConstant;
import com.liu.yuoj.exception.BusinessException;
import com.liu.yuoj.mapper.QuestionSubmitMapper;
import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.liu.yuoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.model.entity.QuestionSubmit;
import com.liu.yuoj.model.entity.User;
import com.liu.yuoj.model.enums.QuestionSubmitEnum;
import com.liu.yuoj.model.enums.QuestionSubmitLanguageEnum;
import com.liu.yuoj.model.vo.QuestionSubmitVO;
import com.liu.yuoj.model.vo.QuestionVO;
import com.liu.yuoj.model.vo.UserVO;
import com.liu.yuoj.service.QuestionService;
import com.liu.yuoj.service.QuestionSubmitService;
import com.liu.yuoj.service.UserService;
import com.liu.yuoj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private UserService userService;

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
//        QuestionSubmitSubmitService questionThumbService = (QuestionSubmitSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return questionThumbService.doQuestionSubmitSubmitInner(userId, questionId);
        QuestionSubmit  questionSubmit = new QuestionSubmit ();
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

    /**
     * 获取查询包装类
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage ();
        Integer status = questionSubmitQueryRequest.getStatus ();
        Long questionId = questionSubmitQueryRequest.getQuestionId ();
        Long userId = questionSubmitQueryRequest.getUserId ();
        String sortField = questionSubmitQueryRequest.getSortField ();
        String sortOrder = questionSubmitQueryRequest.getSortOrder ();


        // 拼接查询条件
        queryWrapper.eq (StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq (ObjectUtils.isNotEmpty (userId), "userId", userId);
        queryWrapper.eq (ObjectUtils.isNotEmpty (questionId), "questionId", questionId);
        //这里不是判断这个值是否为空了 要判断这个值是否存在
        queryWrapper.eq (ObjectUtils.isNotEmpty (status) && QuestionSubmitEnum.getEnumByValue (status)!=null,"status",status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //脱敏 仅自己和管理元可以查看
        Long userId = loginUser.getId ();
        if (!Objects.equals (userId, questionSubmitVO.getUserId ()) && !userService.isAdmin (loginUser)){
            questionSubmitVO.setCode (null);
        }
//        UserVO userVO = userService.getUserVO (loginUser);
//        questionSubmitVO.setUserVO (userVO);
        QuestionVO questionVO = questionService.getQuestionVO (questionService.getById (questionSubmit.getQuestionId ()), loginUser);
        questionSubmitVO.setQuestionVO (questionVO);
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVOPage;
        }
        /**
         * 这行代码的意思是从questionList取出每一个questionSubmit 之后使用getQuestionSubmitVO方法获取返回VO实现类，之后
         * 将这些VO实现类收集在一个List<QuestionSubmitVO>中
         *
         * 注意：上述旧getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request)存在一个
         * bug，就是插入用户信息的时候 我们都要去查询getLoginUser(request)，导致我们在收集questionSubmitVO每次使用
         * 这个方法都要执行一遍这个方法 浪费资源 所以我们会直接从controller层传一个User就好了
         * getQuestionSubmitVOPage(Page<QuestionSubmit> questionPage, User loginUser)
         * todo 也可以从本地获取用户信息
         * ThreadLocal里面获取
         */
        List<QuestionSubmitVO> questionSubmitVOList = questionList.stream ().map (questionSubmit ->
                getQuestionSubmitVO (questionSubmit, loginUser)).collect (Collectors.toList ());
        questionVOPage.setRecords(questionSubmitVOList);
        return questionVOPage;
    }

}




