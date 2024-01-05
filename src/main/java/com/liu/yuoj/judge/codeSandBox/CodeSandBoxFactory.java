package com.liu.yuoj.judge.codeSandBox;

import com.liu.yuoj.judge.codeSandBox.impl.ExampleCodeSandBox;
import com.liu.yuoj.judge.codeSandBox.impl.RemoteCodeSandBox;
import com.liu.yuoj.judge.codeSandBox.impl.ThirdPartyCodeSandBox;

/**
 * @author 刘渠好
 * @date 2024-01-05 20:32
 * 设计模式--静态工厂模式(根据传入类型去获取不同的代码沙箱实例)
 * (我们不同每次需要那种代码沙箱实例就去new 一个对应的实例 这样一来拓展性不强 使用工厂模式之后
 * 以后有更多的代码沙箱 可以更好的去拓展)
 */
public class CodeSandBoxFactory {
    //提供一个静态的方法(返回一个代码沙箱接口类)
    public static CodeSandBox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox ();
            case "thirdParty":
                return new ThirdPartyCodeSandBox ();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
