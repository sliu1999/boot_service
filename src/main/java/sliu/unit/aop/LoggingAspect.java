package sliu.unit.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 在控制台打印错误日志
 */
@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @Pointcut("within(sliu.mapper..*) || within(sliu.service..*) || within(sliu*.web.rest..*)")
    public void loggingPointcut() {
    }

    /**
     * 捕获被抛出的异常，然后输出，如果异常被try{}catch(){}捕获后，没有被throw 抛出，这不会被捕获，目前rest捕获的都没抛出
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(
            pointcut = "loggingPointcut()",
            throwing = "e"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        this.log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", new Object[]{joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e});
    }

    @Around("loggingPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        if (this.log.isInfoEnabled()) {
            this.log.debug("-------- 调用开始 --------");
            this.log.debug("function : {}.{}() ", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            this.log.debug("argument[s] : {} ", Arrays.toString(joinPoint.getArgs()));
            this.log.debug("-------------------------");
        }

        try {
            Object result = joinPoint.proceed();
            if (this.log.isInfoEnabled()) {
                this.log.debug("-------- 调用结束 --------");
                this.log.debug("function : {}.{}() ", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
                this.log.debug("result : {} ", result);
                this.log.debug("time : {} ms ", System.currentTimeMillis() - start);
                this.log.debug("-------------------------");
            }

            return result;
        } catch (IllegalArgumentException var5) {
            this.log.error("-------- 调用出错 --------");
            this.log.error("function : {}.{}() ", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            this.log.error("argument[s] : {} ", Arrays.toString(joinPoint.getArgs()));
            this.log.error("errorMsg : {} ", var5.getMessage());
            this.log.error("time : {} ms ", System.currentTimeMillis() - start);
            this.log.error("-------------------------");
            throw var5;
        }
    }
}
