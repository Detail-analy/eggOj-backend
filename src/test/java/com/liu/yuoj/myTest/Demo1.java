package com.liu.yuoj.myTest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @author 刘渠好
 * @date 2023-12-21 22:16
 */
@SpringBootTest
public class Demo1 {

    @Test
    public void A(){
        JSONArray objects = JSONUtil.parseArray ("[memoryLimit:1000 stackLimit:1000 timeLimit:1000]");
        System.out.println (objects);

    }
}
