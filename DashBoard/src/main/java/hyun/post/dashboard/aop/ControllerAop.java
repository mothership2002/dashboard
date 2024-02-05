package hyun.post.dashboard.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.aop.annotation.InboundContent;
import hyun.post.dashboard.common.StaticString;
import hyun.post.dashboard.component.RequestLog;
import hyun.post.dashboard.component.XssConverter;
import hyun.post.dashboard.exception.DtoConvertXssException;
import hyun.post.dashboard.exception.NotJsonObjectException;
import hyun.post.dashboard.exception.NotMatchArgumentException;
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
import java.util.Optional;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ControllerAop {

    private final XssConverter xssConverter;
    private final ObjectMapper om;
    private final RequestLog requestLog;

    @Before("@annotation(inboundContent)")
    public void convertInboundContent(JoinPoint joinPoint, InboundContent inboundContent) {
        requestLog.inboundLog("[RequestTime] {}, [Class] : {}, [Method] : {}",
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(StaticString.DATE_DEFAULT_FORMAT)),
                joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
        // 어노테이션에 등록된 Dto class 메타데이터
        Class<?> clazz = inboundContent.value();
        
        // 해당 메소드에 arg으로 등록된 Dto 인스턴스 확인 및 케스팅
        Optional<?> castToObject = Arrays.stream(joinPoint.getArgs())
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();

        // 없을 경우 - 개발자가 잘못 지정했을 경우
        Object dto = castToObject.orElseThrow(NotMatchArgumentException::new);
        // 인입 파람을 inboundFilter에서 찍을 예정
        //log.info("[param] before \n{}", jsonParam(dto));
        // 필드의 Type이 String.class 일 경우 xss 컨버트
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getType() == String.class
                        && !xssConverter.isExceptField(field.getName()))
                .forEach(field -> convertStringField(field, dto));
        requestLog.inboundLog("[param] after \n{}", jsonParam(dto));
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
            field.set(dto, xssConverter.inbound((String) field.get(dto)));
        } catch (IllegalAccessException e) {
            throw new DtoConvertXssException(e);
        }
    }
}
