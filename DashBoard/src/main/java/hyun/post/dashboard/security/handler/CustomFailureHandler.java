package hyun.post.dashboard.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.model.common.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
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

        if (exception instanceof BadCredentialsException) {
            log.warn("try login remote ip = {}", request.getRemoteHost());
        }

        headerComponent.addContentTypeResponse(response);
        objectMapper.writeValue(response.getWriter(),
                new CommonResponse<>("Fail To Login", exception.getMessage()));
    }
}