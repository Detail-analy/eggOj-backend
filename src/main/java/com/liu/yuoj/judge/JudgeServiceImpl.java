package com.liu.yuoj.judge;

import cn.hutool.json.JSONUtil;
import com.liu.yuoj.common.ErrorCode;
import com.liu.yuoj.exception.BusinessException;
import com.liu.yuoj.judge.codeSandBox.CodeSandBox;
import com.liu.yuoj.judge.codeSandBox.CodeSandBoxFactory;
import com.liu.yuoj.judge.codeSandBox.CodeSandBoxProxy;
import com.liu.yuoj.judge.codeSandBox.model.CodeSandBoxResponse;
import com.liu.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.liu.yuoj.judge.strategy.JudgeContext;
import com.liu.yuoj.model.dto.question.JudgeCase;
import com.liu.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.model.entity.QuestionSubmit;
import com.liu.yuoj.model.enums.QuestionSubmitEnum;
import com.liu.yuoj.service.QuestionService;
import com.liu.yuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘渠好
 * @date 2024-01-05 21:44
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value ("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.传入题目提交的id，获取到对应的题目，提交信息（包括提交代码，编程语言）
        QuestionSubmit questionSubmit = questionSubmitService.getById (questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException (ErrorCode.NOT_FOUND_ERROR);
        }
        //获取题目信息
        Question question = questionService.getById (questionSubmit.getQuestionId ());
        if (question == null) {
            throw new BusinessException (ErrorCode.NOT_FOUND_ERROR);
        }
        //2.只需要执行状态为待判题的提交题目
        if (!questionSubmit.getStatus ().equals (QuestionSubmitEnum.AWAITING.getValue ())) {
            throw new BusinessException (ErrorCode.SYSTEM_ERROR, "该提交题目不需要判题");
        }
        //3.更改提交题目执行状态，防止重复执行 这里我们不要直接去更新questionSubmit 而是去搞一个新的类去更新(todo 这里就好比是加了一个锁)
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit ();
        questionSubmitUpdate.setId (questionSubmitId);
        questionSubmitUpdate.setStatus (QuestionSubmitEnum.RUNNING.getValue ());
        boolean b = questionSubmitService.updateById (questionSubmitUpdate);
        //更新失败直接报错
        if (!b){
            throw new BusinessException (ErrorCode.SYSTEM_ERROR,"操作失败!!!");

        }
        //4.调代码用沙箱 获取执行结果
        String language = questionSubmit.getLanguage ();
        String code = questionSubmit.getCode ();
        List<JudgeCase> list = JSONUtil.toList (question.getJudgeCase (), JudgeCase.class);
        //获取输入用例(可能会有多个输入用例)
        List<String> inputList = list.stream ().map (JudgeCase::getInput).collect (Collectors.toList ());
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance (type);
        //使用代理对象
        codeSandBox=new CodeSandBoxProxy (codeSandBox);
        ExecuteCodeRequest build = ExecuteCodeRequest.builder ()
                .code (code)
                .language (language)
                .inputList (inputList)
                .build ();
        CodeSandBoxResponse codeSandBoxResponse = codeSandBox.executeCode (build);
        //5.设置题目的执行结果，设置题目的判题状态和信息
        /**
         * 注意：不一样的编程语言 提交的策略是不一样 我们要是都在一个地方写 那后面就不便于维护了 可以抽出来(使用策略模式)
         * List<String> output = codeSandBoxResponse.getOutput ();
         *         //提交题目judgeInfo信息
         *         JudgeInfoEnum judgeInfoEnum = JudgeInfoEnum.WAITING;
         *         if (output.size ()!=inputList.size ()){
         *             judgeInfoEnum=JudgeInfoEnum.WRONG_ANSWER;
         *             return null;
         *         }
         *         //判断每一次输出和预期输出是否一样
         *         String judgeCase = question.getJudgeCase ();
         *         List<JudgeCase> list1 = JSONUtil.toList (judgeCase, JudgeCase.class);
         *         for (int i = 0; i < list1.size (); i++) {
         *             //获取对饮judgeCase对象
         *             JudgeCase judgeCase1 = list1.get (i);
         *             if (!judgeCase1.getOutput ().equals (output.get (i))){
         *                 judgeInfoEnum=JudgeInfoEnum.WRONG_ANSWER;
         *                 return null;
         *             }
         *         }
         *         //判断题目限制
         *         String judgeConfig = question.getJudgeConfig ();
         *         JudgeConfig judgeConfig1 = JSONUtil.toBean (judgeConfig, JudgeConfig.class);
         *         //提交题目的时间，内存
         *         JudgeInfo judgeInfo = codeSandBoxResponse.getJudgeInfo ();
         *         Long memory = judgeInfo.getMemory ();
         *         Long time = judgeInfo.getTime ();
         *         if (memory>judgeConfig1.getMemoryLimit ()){
         *             judgeInfoEnum=JudgeInfoEnum.MEMORY_LIMIT;
         *             return null;
         *         }
         *         if (time>judgeConfig1.getTimeLimit ()){
         *             judgeInfoEnum=JudgeInfoEnum.TIME_LIMIT_EXCEEDED;
         *             return null;
         *         }
         */
//        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy ();
//        //判断提交的语言 切换提交策略 但是随着可提交代码种类逐渐变多 我们需要写大量的if else 所以也需要将这一部分抽离出来
//        if (questionSubmit.getLanguage ().equals (QuestionSubmitLanguageEnum.JAVA.getValue ())){
//            judgeStrategy=new JavaLanguageJudgeStrategy ();
//        }
        JudgeContext judgeContext=new JudgeContext ();
        judgeContext.setInput (inputList);
        List<String> output = codeSandBoxResponse.getOutput ();
        judgeContext.setOutput (output);
        judgeContext.setQuestion (question);
        JudgeInfo judgeInfo = codeSandBoxResponse.getJudgeInfo ();
        judgeContext.setJudgeInfo (judgeInfo);
        judgeContext.setJudgeCases (list);
        judgeContext.setQuestionSubmit (questionSubmit);
//        JudgeInfo judgeInfo1 = judgeStrategy.doJudge (judgeContext);
        JudgeInfo judgeInfo1 = judgeManager.doJudge (judgeContext);
        questionSubmitUpdate= new QuestionSubmit ();
        questionSubmitUpdate.setId (questionSubmitId);
        questionSubmitUpdate.setStatus (QuestionSubmitEnum.SUCCEED.getValue ());
        questionSubmitUpdate.setJudgeInfo (JSONUtil.toJsonStr (judgeInfo1));
        //更新
        boolean b1 = questionSubmitService.updateById (questionSubmitUpdate);
        if (!b1){
            throw new BusinessException (ErrorCode.PARAMS_ERROR,"数据库操作失败!!!");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById (questionSubmitId);
        return questionSubmitResult;
    }
}


