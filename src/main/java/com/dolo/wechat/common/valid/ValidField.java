package com.dolo.wechat.common.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* 描述: 属性验证
* 作者: dolojia
* 修改日期: 2018/8/29 14:41
* E-mail: dolojia@gmail.com
**/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidField {

    String fieldName();

    boolean notBlank() default false;

    boolean bankNo() default false;

    boolean ident() default false;

    boolean mobile() default false;

    boolean money() default false;

    String regStr() default "";

    String msg() default "参数异常！";


}
