package hyun.post.dashboard.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.model.common.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private final CommonRespHeaderComponent headerComponent;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        headerComponent.addContentTypeResponse(response);
        objectMapper.writeValue(response.getWriter(), new CommonResponse<>("access denied", null));
    }

}