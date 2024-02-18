package hyun.post.dashboard.aop;

import hyun.post.dashboard.common.StaticString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DaoAop {

    @Around("execution(public * hyun.post.dashboard.dao.*.*delete*(..))")
    public Object deleteQueryTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long endTime = System.nanoTime();
        log.info("[Method] : {}, [TimeResource] {}",
                joinPoint.getSignature(), timeValue(startTime, endTime));
        return proceed;
    }

    private String timeValue(long startTime, long endTime) {
        return (endTime - startTime) / 1000000 +
                StaticString.MILLISECOND;
    }

}
