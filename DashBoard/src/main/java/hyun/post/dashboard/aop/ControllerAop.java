package hyun.post.dashboard.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.aop.annotation.InboundContent;
import hyun.post.dashboard.component.XssConverter;
import hyun.post.dashboard.exception.DtoConvertXssException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ControllerAop {

    private final XssConverter xssConverter;
    private final ObjectMapper om;

    @Before("@annotation(inboundContent)")
    public void convertInboundContent(JoinPoint joinPoint, InboundContent inboundContent) throws Throwable {
        Class<?> clazz = inboundContent.value();
        Optional<?> castToObject = Arrays.stream(joinPoint.getArgs())
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
        Object dto = castToObject.orElseThrow(RuntimeException::new);
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getType() == String.class)
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        field.set(dto, xssConverter.inbound((String) field.get(dto)));
                    } catch (IllegalAccessException e) {
                        throw new DtoConvertXssException(e);
                    }
                });
        log.info("[RequestTime] {}, [Method] : {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")),
                joinPoint.getSignature().getName());

        log.info("[param]");
        log.info("\n{}", om.writerWithDefaultPrettyPrinter().writeValueAsString(dto));
    }
}
