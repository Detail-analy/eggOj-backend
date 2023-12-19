package com.liu.yuoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.yuoj.model.entity.Question;
import com.liu.yuoj.service.QuestionService;
import com.liu.yuoj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2023-12-19 22:53:35
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




