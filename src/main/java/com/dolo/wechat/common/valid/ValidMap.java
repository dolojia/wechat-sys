package com.dolo.wechat.common.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: Map类型参数验证注解
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:42
 * E-mail: dolojia@gmail.com
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidMap {
    ValidField[] fields();
}
