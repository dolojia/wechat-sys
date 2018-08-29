package com.dolo.wechat.common.valid;

import com.dolo.wechat.common.response.ResponseCode;
import com.dolo.wechat.common.util.BankCardUtil;
import com.dolo.wechat.common.util.IdentCardUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 描述: 参数校验Aspect
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:42
 * E-mail: dolojia@gmail.com
 **/
@Aspect
@Order(2)
@Component
public class ValidMapAspect {

    protected Logger logger = LogManager.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    @Around("@annotation(ValidMap)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ValidMap validMap = method.getAnnotation(ValidMap.class);
        Object obj = joinPoint.getArgs()[0];
        if (validMap != null && validMap.fields() != null && Map.class.isInstance(obj)) {

            try {
                valided(validMap.fields(), (Map<String, Object>) obj);
            } catch (RuntimeException rx) {
                return ResponseCode.buildFail("-1", rx.getMessage());
            }

        }

        return joinPoint.proceed();
    }


    public void valided(ValidField[] fields, Map<String, Object> map) throws RuntimeException {

        for (ValidField v : fields) {
            String key = v.fieldName();
            String msg = v.msg();

            Object obj = map.get(key);
            if (v.notBlank()) {
                if (obj == null || StringUtils.isBlank(obj + "")) {
                    logger.info("参数[{}]不能为空！返回提示[{}]！", key, msg);
                    throw new RuntimeException(msg);
                }
            }

            if (v.bankNo()) {
                if (obj == null || !BankCardUtil.checkBankCard((String) obj)) {
                    logger.info("参数[{}]银行卡验证失败！返回提示[{}]！", key, msg);
                    throw new RuntimeException(msg);
                }
            }

            if (v.ident()) {
                if (obj == null || !IdentCardUtil.isRealIdent((String) obj)) {
                    logger.info("参数[{}]身份证验证失败！返回提示[{}]！", key, msg);
                    throw new RuntimeException(msg);
                }
            }

            if (v.mobile()) {
                if (obj == null || !((String) obj).matches("^(?:\\+86)?1\\d{10}$")) {
                    logger.info("参数[{}]手机号验证失败！返回提示[{}]！", key, msg);
                    throw new RuntimeException(msg);
                }
            }

            if (v.money()) {
                if (obj == null || !((String) obj).matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$")) {
                    logger.info("参数[{}]金额验证失败！返回提示[{}]！", key, msg);
                    throw new RuntimeException(msg);
                }
            }

            if (StringUtils.isNotBlank(v.regStr())) {
                if (obj == null || !((String) obj).matches(v.regStr())) {
                    logger.info("参数[{}]验证失败！返回提示[{}]！", key, msg);
                    throw new RuntimeException(msg);
                }
            }

        }


    }


}
