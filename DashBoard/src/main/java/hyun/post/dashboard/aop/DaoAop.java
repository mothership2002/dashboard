package hyun.post.dashboard.aop;

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
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("[Method] : {}, [TimeResource] {}",
                joinPoint.getSignature(), timeValue(startTime, endTime));
        return proceed;
    }

    private String timeValue(long startTime, long endTime) {
        long usedTime = endTime - startTime;
//        String timeString = String.valueOf(usedTime);
//        int length = timeString.length();
        // TODO 추가적으로 보기편한 로깅을 위한 계산 로직이 필요할거 같음.
//        return timeString.substring(0, length - 3) +
//                StaticString.DOT +
//                timeString.substring(length - 3, length);
        return usedTime + "ms";
    }


}
