package hyun.post.dashboard.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.exception.auth.AlreadyHaveSessionException;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final CommonRespHeaderComponent headerComponent;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        headerComponent.addContentTypeResponse(response);
        response.setStatus(406);
        Object body = null;
        String message = exception.getMessage();;

        // 이 부분이 AlreadyHaveSessionException과 결합도가 지나치게 강함.
        // TODO 개선이 필요
        if (exception instanceof AlreadyHaveSessionException) {
            String[] split = message.split(";");
            message = split[0];
            body = split[1];
        }
        objectMapper.writeValue(response.getWriter(), new CommonResponse<>(message, body));
    }
}