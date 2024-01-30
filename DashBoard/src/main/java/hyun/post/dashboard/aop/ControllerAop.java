package hyun.post.dashboard.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.aop.annotation.InboundContent;
import hyun.post.dashboard.common.StaticString;
import hyun.post.dashboard.component.XssConverter;
import hyun.post.dashboard.exception.DtoConvertXssException;
import hyun.post.dashboard.exception.NotMatchArgumentException;
import hyun.post.dashboard.exception.NotJsonObjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
        log.info("[RequestTime] {}, [Method] : {}",
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(StaticString.DATE_DEFAULT_FORMAT)),
                joinPoint.getSignature().getName());

        // 어노테이션에 등록된 Dto class 메타데이터
        Class<?> clazz = inboundContent.value();
        
        // 해당 메소드에 arg으로 등록된 Dto 인스턴스 확인 및 케스팅
        Optional<?> castToObject = Arrays.stream(joinPoint.getArgs())
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();

        // 없을 경우 - 개발자가 잘못 지정했을 경우
        Object dto = castToObject.orElseThrow(NotMatchArgumentException::new);

        // 필드의 Type이 String.class 일 경우 xss 컨버트
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getType() == String.class)
                .filter(field -> !xssConverter.isExceptField(field.getName()))
                .forEach(field -> convertStringField(field, dto));
    }

    private String jsonParam(Object param) {
        try {
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(param);
        } catch (JsonProcessingException e) {
            throw new NotJsonObjectException(e);
        }
    }

    private void convertStringField(Field field, Object dto) {
        try {
            field.setAccessible(true);
            log.info("[param] before");
            log.info("\n{}", jsonParam(dto));
            field.set(dto, xssConverter.inbound((String) field.get(dto)));
            log.info("[param] after");
            log.info("\n{}", jsonParam(dto));
        } catch (IllegalAccessException e) {
            throw new DtoConvertXssException(e);
        }
    }
}
