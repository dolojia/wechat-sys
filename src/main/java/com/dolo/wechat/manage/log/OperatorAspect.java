package com.dolo.wechat.manage.log;

import com.dolo.wechat.service.ILogdailyService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * admin系统操作日志
 * 
 * @author j1deng
 */
@Aspect
@Component
public class OperatorAspect
{

    @Autowired
    private ILogdailyService logdailyService;

    @Autowired
    private HttpServletRequest request;


//    @After(value = "operator()")
//    public void recordOperator(ProceedingJoinPoint point) throws Throwable
//    {
//        Object[] param = point.getArgs(); // 方法参数列表
//        String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
//        long begin = 0L; // 开始时间
//        long end = 0L;// 结束时间
//        begin = System.currentTimeMillis();
//        point.proceed();
//        end = System.currentTimeMillis();
//        // String operatorName = SpringSecurityUtil.getCurrentUserName();//操作人
//        String expend = (end - begin) + ""; // 一共花费多长时间
//        Signature signature = point.getSignature(); // 返回当前连接点签名
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        Operator operator = (Operator) Util.getAnnotation(method, Operator.class);
//        LogDaily logDaily = new LogDaily(); // 日志
//        logDaily.setCreateTime(new Date()); // 创建时间
//        logDaily.setExpendTime(expend); // 执行花费时间
//        logDaily.setOperatorName(userName); // 操作人
//        logDaily.setRecordType(""); // 操作类型
//        logDaily.setDescription(operator.operator());// 操作描述
//        logDailyService.insertLogDaily(logDaily);
//    }
}
